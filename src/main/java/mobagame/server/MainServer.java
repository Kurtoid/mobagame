package mobagame.server;

import java.io.IOException;

public class MainServer {
	ConnectionListener l;
	MasterGameRunner gRunner;

	public MainServer() {
		System.out.println("Starting server");
		System.out.println("initializing objects");
		gRunner = new MasterGameRunner();
		ResponseWorker worker = new ResponseWorker();
		// required, TODO: make this neater later
		worker.runner = gRunner;
		new Thread(worker).start();
		System.out.println("started message parser");
		ConnectionListener conn = null;
		try {
			conn = new ConnectionListener(null, 8666, worker);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(conn).start();
		System.out.println("started network listener");
		gRunner.conn = conn;
	}

	public static void main(String[] args) {
		new MainServer();

	}
}
