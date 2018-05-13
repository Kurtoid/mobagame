package mobagame.server.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * manages login and signup of users securely
 *
 * @author Kurt Wilson
 *
 */
public class PlayerAccountDBO {
	private static int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 128;
	DatabaseConnectionManager db;

	PreparedStatement createAccount;
	PreparedStatement loginAccount;
	PreparedStatement getAccountByUsername;
	PreparedStatement getAccountByEmail;

	public PlayerAccountDBO() {
		try {
			db = DatabaseConnectionManager.getInstance();
			createAccount = db.getConnection().prepareStatement(
					"INSERT INTO PlayerAccount (username, password, email, questionID, questionAnswer, xp, level) VALUES (?, ?, ?, ?, ?, ?, ?)");
			loginAccount = db.getConnection()
					.prepareStatement("SELECT * FROM PlayerAccount WHERE username = ? AND password = ?");
			getAccountByUsername = db.getConnection().prepareStatement("SELECT * FROM PlayerAccount WHERE username = ?");
			getAccountByEmail = db.getConnection().prepareStatement("SELECT * FROM PlayerAccount WHERE email = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: return player
	/**
	 * signs up a user to check if the signup is successful, sign the user in if it
	 * fails, returns a SQLException
	 *
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @param securityQuestionID
	 * @param securityQuestionAnswer
	 * @throws SQLException
	 *             if user cant be created
	 */
	public void createAccount(String username, String password, String emailAddress, byte securityQuestionID,
			String securityQuestionAnswer) throws SQLException {
		try {

			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(password.getBytes());

			createAccount.setString(1, username);
			createAccount.setBytes(2, result);
			createAccount.setString(3, emailAddress);
			createAccount.setInt(4, securityQuestionID);
			createAccount.setString(5, securityQuestionAnswer);
			createAccount.setInt(6, 0);
			createAccount.setInt(7, 1);
			createAccount.executeUpdate();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return new PlayerAccount();
	}

	/**
	 * logs in an account, and returns it
	 * @param username
	 * @param password
	 * @return the player, or null if it fails
	 * @throws SQLException
	 */
	public PlayerAccount loginAccount(String username, String password) throws SQLException {
		try {
			loginAccount.setString(1, username);
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(password.getBytes());
			loginAccount.setBytes(2, result);
			loginAccount.execute();
			ResultSet rs = loginAccount.getResultSet();
			rs.next();
			PlayerAccount p = getPlayerAccountFromResultSet(rs);
			return p;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * gets a player based off of username
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public PlayerAccount getAccountByUsername(String username) throws SQLException {
		getAccountByUsername.setString(1,username);
		getAccountByUsername.executeQuery();
		ResultSet rs = getAccountByUsername.getResultSet();
		rs.next();
		PlayerAccount p = getPlayerAccountFromResultSet(rs);
		return p;
	}

	/**
	 * gets a player based off of email
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public PlayerAccount getAccountByEmail(String email) throws SQLException {
		getAccountByEmail.setString(1,email);
		getAccountByEmail.executeQuery();
		ResultSet rs = getAccountByEmail.getResultSet();
		rs.next();
		PlayerAccount p = getPlayerAccountFromResultSet(rs);
		return p;
	}

	private PlayerAccount getPlayerAccountFromResultSet(ResultSet rs) throws SQLException {
		PlayerAccount p = new PlayerAccount();
		p.id = rs.getInt("id");
		p.level = rs.getInt("level");
		p.username = rs.getString("username");
		return p;
	}

	public static void main(String[] args) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		try {
			dbo.createAccount("hgeugfiw", "hfeiow", null, (byte) 0x00, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
