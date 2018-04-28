package mobagame.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

public class DatabaseConnectionManager {
	private static final String dbURL = "jdbc:mysql://johnny.heliohost.org/kurt4wil_mobagame?serverTimezone=UTC";
	private Connection con;
	private static DatabaseConnectionManager dbObject = null;
	private static final String user = "kurt4wil_appserv";
	private static final String pass = "mrpoopybutthole";
	private Statement s;

	private DatabaseConnectionManager() throws SQLException {

		System.out.println("Connecting");
		con = DriverManager.getConnection(dbURL, user, pass);
		System.out.println("Connected");
		try {
			if (con != null) {
				DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}
			s = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static DatabaseConnectionManager getInstance() throws SQLException {
		if (dbObject == null) {
			dbObject = new DatabaseConnectionManager();
		}

		return dbObject;
	}

	public Connection getConnection() {
		return con;
	}

}
