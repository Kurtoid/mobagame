package mobagame.server.database;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

import mobagame.core.settings.SettingManager;

/**
 * singleton to manage connection to server's database
 *
 * @author Kurt Wilson
 */
public class DatabaseConnectionManager {
	private Connection con;
	private static DatabaseConnectionManager dbObject = null;

	/**
	 * connects to server defined in default_server_settings.conf
	 *
	 * @throws SQLException
	 */
	private DatabaseConnectionManager() throws SQLException {
		SettingManager manager = new SettingManager();
		try {
			manager.openFile(Paths.get("default_server_settings.conf"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		manager.readSettings();
		boolean useConnectionString = Boolean.parseBoolean(manager.getSetting("server.useconnectionstring").getValue());
		String connString = manager.getSetting("server.dbconnectionstring").getValue();

		if (useConnectionString) {
			System.out.println("Connecting with only string");
			con = DriverManager.getConnection(connString);
		} else {
			String username = manager.getSetting("server.dbusername").getValue();
			String password = manager.getSetting("server.dbpassword").getValue();
			System.out.println("Connecting with username and password");
			con = DriverManager.getConnection(connString, username, password);

		}
		System.out.println("Connected");
		try {
			if (con != null) {
				DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get the instance of this singleton connects to server defined in<br>
	 * default_server_settings.conf
	 *
	 * @return the only instance of DataBaseConnectionManager
	 * @throws SQLException
	 */
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
