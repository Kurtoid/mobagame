package mobagame.server;

import mobagame.core.game.InGamePlayer;

import java.io.IOException;
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
