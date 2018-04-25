package mobagame.server.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerAccountDBO {
	private static int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 128;
	DatabaseConnectionManager db;

	PreparedStatement createAccount;
	PreparedStatement loginAccount;

	public PlayerAccountDBO() {
		try {
			db = DatabaseConnectionManager.getInstance();
			createAccount = db.getConnection()
					.prepareStatement("INSERT INTO PlayerAccount (username, password, level) VALUES (?, ?, ?)");
			loginAccount = db.getConnection()
					.prepareStatement("SELECT * FROM PlayerAccount WHERE username = ? AND password = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: return player
	public void createAccount(String username, String password) {
		try {

			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(password.getBytes());

			createAccount.setString(1, username);
			createAccount.setBytes(2, result);
			createAccount.setInt(3, 1);
			createAccount.executeUpdate();

		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return new PlayerAccount();
	}

	public  PlayerAccount loginAccount(String username, String password) throws SQLException{
		try {
			loginAccount.setString(1, username);
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(password.getBytes());
			loginAccount.setBytes(2, result);
			loginAccount.execute();
			ResultSet rs = loginAccount.getResultSet();
			rs.next();
			PlayerAccount p = new PlayerAccount();
			p.id = rs.getInt("playerId");
			p.level = rs.getInt("level");
			p.username = rs.getString("username");
			return p;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		dbo.createAccount("hgeugfiw", "hfeiow");
	}
}
