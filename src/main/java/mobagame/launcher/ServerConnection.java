package mobagame.launcher;

import mobagame.core.networking.packets.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Used by the client, and represents a single connection to a server
 * @author Kurt Wilson
 *
 */
class ServerConnection extends Thread {
	private SocketChannel c;
	private BlockingQueue<Packet> packetsToSend;
	boolean running = true;

	/**
	 * connects to a server at hostname:port, and keeps it alive
	 * @param hostname
	 * @param port
	 * @throws IOException if it cant connect
	 */
	public void initConnect(String hostname, int port) throws IOException {
		c = SocketChannel.open();
		c.connect(new InetSocketAddress(hostname, port));
	}

	public ServerConnection() {
		packetsToSend = new LinkedBlockingDeque<Packet>();
	}

	public void queuePacket(Packet p) {
		packetsToSend.add(p);
	}

	@Override
	public void run() {
		super.run();
		System.out.println("Starting client-server thread");
		try {
			while (running) {
				Packet p = packetsToSend.take();
				// TODO: remove this lag-causing message
				System.out.println("Sending");
				ByteBuffer b = p.getBytes();
				b.flip();
				c.write(b);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
