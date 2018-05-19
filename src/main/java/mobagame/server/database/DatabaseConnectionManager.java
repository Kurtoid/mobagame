package mobagame.server.database;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mobagame.core.settings.SettingManager;

/**
 * singleton to manage connection to server's database
 *
 * @author Kurt Wilson
 */
public class DatabaseConnectionManager {
	Logger logger = Logger.getLogger(this.getClass().getName());

	private Connection con;
	private static DatabaseConnectionManager dbObject = null;

	/**
	 * connects to server defined in default_server_settings.conf
	 *
	 * @throws SQLException
	 */
	private DatabaseConnectionManager() throws SQLException {
		SettingManager manager = new SettingManager();
		manager.openFile(Paths.get("default_server_settings.conf"));
		manager.readSettings();
		boolean useConnectionString = Boolean.parseBoolean(manager.getSetting("server.useconnectionstring").getValue());
		String connString = manager.getSetting("server.dbconnectionstring").getValue();

		if (useConnectionString) {
			logger.log(Level.INFO, "Connecting with only string");
			con = DriverManager.getConnection(connString);
		} else {
			String username = manager.getSetting("server.dbusername").getValue();
			String password = manager.getSetting("server.dbpassword").getValue();
			logger.log(Level.INFO, "Connecting with username and password");
			con = DriverManager.getConnection(connString, username, password);

		}
		System.out.println("Connected");
		try {
			if (con != null) {
				DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
				logger.log(Level.INFO, "Driver name: " + dm.getDriverName());
				logger.log(Level.INFO, "Driver version: " + dm.getDriverVersion());
				logger.log(Level.INFO, "Product name: " + dm.getDatabaseProductName());
				logger.log(Level.INFO, "Product version: " + dm.getDatabaseProductVersion());
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
