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
		try {
			new Thread(new ConnectionListener(null, 8666, worker)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("started network listener");
	}

	public static void main(String[] args) {
		new MainServer();

	}
}
