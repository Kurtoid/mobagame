package mobagame.server;

import mobagame.core.game.InGamePlayer;
import mobagame.server.database.DatabaseConnectionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServer {
	ConnectionListener l;
	MasterGameRunner gRunner;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public MainServer() {
		logger.log(Level.INFO, "Starting Server");
		logger.log(Level.INFO, "initializing objects");
		gRunner = new MasterGameRunner();
		ResponseWorker worker = new ResponseWorker();
		// required, TODO: make this neater later
		worker.runner = gRunner;
		new Thread(worker).start();
		try {
			DatabaseConnectionManager.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("connected to database");
		logger.log(Level.INFO, "started message parser");
		ConnectionListener conn = null;
		try {
			conn = new ConnectionListener(null, 8666, worker);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(conn).start();
		logger.log(Level.INFO, "started network listener");
		gRunner.conn = conn;
	}

	public static void main(String[] args) {

		MainServer s = new MainServer();

	}
}
