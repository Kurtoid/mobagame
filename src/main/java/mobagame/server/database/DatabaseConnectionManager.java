package mobagame.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
	private static final String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MobaDB";
	private Connection con;
	private static DatabaseConnectionManager dbObject = null;
	private static final String user = "sa";
	private static final String pass = "Suncoast$1";
	private Statement s;

	private DatabaseConnectionManager() throws SQLException {

		con = DriverManager.getConnection(dbURL, user, pass);

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
