package mobagame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import mobagame.core.networking.ClientConnection;

public class ConnectionListener extends Thread {

	boolean running = true;

	@Override
	public void run() {
		super.run();
		try {
			// TODO: convert to settings API when we've made it
			ServerSocket ss = new ServerSocket(8666);
			while (running) {
				Socket s = ss.accept();
				System.out.println("connection");
				new ClientConnection(s).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		// test driver
		ConnectionListener cl = new ConnectionListener();
		cl.start();
	}
}
