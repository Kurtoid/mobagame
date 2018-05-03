package mobagame.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import mobagame.core.settings.SettingManager;
import mobagame.server.database.DatabaseConnectionManager;
import mobagame.server.database.PlayerAccountDBO;

/**
 * (For now) the main server thread It runs until exited, and recieves client
 * connections. When started, it starts another processing thread, which handles
 * incoming and outgoing packets
 *
 * @author Kurt Wilson
 */
public class ConnectionListener extends Thread {
	/**
	 * keep this as a field so it doesnt have to be re-allocated constantly
	 */
	ByteBuffer chunkBuf;
	PlayerAccountDBO dbo;
	DatabaseConnectionManager db;
	IncomingPacketProcessor packetProc;
	SettingManager settingManager;
	Map<SelectionKey, Long> socketMap;
	Map<Long, Queue<OutMessage>> messages;
	long nextSocketID = 0;

	/**
	 * Creates a new server instance.
	 */
	public ConnectionListener() {
		try {
			settingManager = new SettingManager();
			settingManager.openFile(Paths.get("default_server_settings.conf"));
			settingManager.readSettings();
			boolean serverEnabled = Boolean
					.parseBoolean(settingManager.getSetting("server.databaseenabled").getValue());
			if (serverEnabled) {
				try {
					db = DatabaseConnectionManager.getInstance();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbo = new PlayerAccountDBO();
			chunkBuf = ByteBuffer.allocate(512);
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			messages = new HashMap<>();

			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			packetProc = new IncomingPacketProcessor(this);
			packetProc.setServerEnabled(serverEnabled);
			packetProc.setDaemon(true);
			socketMap = new HashMap<>();
			// setDaemon(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ServerSocketChannel serverSocketChannel;
	boolean running = true;
	private Selector selector;

	@Override
	public void run() {
		super.run();
		packetProc.start();
		try {
			// TODO: convert to settings API when we've made it

			serverSocketChannel.socket().bind(new InetSocketAddress(8666));
			while (running) {

				Iterator<SelectionKey> iter;
				SelectionKey key;
				while (serverSocketChannel.isOpen()) {
					selector.select();
					iter = this.selector.selectedKeys().iterator();
					while (iter.hasNext()) {
						key = iter.next();
						iter.remove();
						if (!key.isValid()) {
							continue;
						}
						if (key.isAcceptable())
							this.handleAccept(key);
						if (key.isReadable())
							this.handleRead(key);
						if (key.isWritable()) {
							this.handleWrite(key);
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * called by main server thread checks if there are queued packets for this
	 * socket
	 *
	 * @param key
	 */
	private void handleWrite(SelectionKey key) {
		if (socketMap.containsKey(key)) {
			long keyID = socketMap.get(key);
			OutMessage m = messages.get(keyID).poll();
			if (m != null) {
				SocketChannel ch = (SocketChannel) key.channel();
				try {
					ch.write(m.buff);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * called by main server thread checks if a socket has an incoming message, and
	 * dispaches it to the processing thread
	 *
	 * @param key
	 * @throws IOException
	 */
	private void handleRead(SelectionKey key) throws IOException {
		SocketChannel ch = (SocketChannel) key.channel();
		ByteArrayOutputStream byteStore = new ByteArrayOutputStream();
		chunkBuf.clear();
		chunkBuf.position(0);
		while (ch.read(chunkBuf) > 0) {
			byteStore.write(chunkBuf.array());
			chunkBuf.clear();
			chunkBuf.position(0);
		}
		chunkBuf = ByteBuffer.allocate(byteStore.size());
		chunkBuf.put(byteStore.toByteArray());
		chunkBuf.flip();
		int length = 0;
		try {
			length = chunkBuf.getInt();
		} catch (BufferUnderflowException e) {
			ch.close();
		}
		if (length > 0) {
			System.out.println(socketMap.size());
			long keyID = socketMap.get(key);
			System.out.println(Arrays.toString(byteStore.toByteArray()));
			packetProc.addToQueue(new OutMessage(keyID, chunkBuf));
		}

	}

	/**
	 * called by main thread accepts incoming connections
	 *
	 * @param key
	 * @throws IOException
	 */
	private void handleAccept(SelectionKey key) throws IOException {
		System.out.println("Accept connection #");
		// For an accept to be pending the channel must be a server socket channel.
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

		// Accept the connection and make it non-blocking
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		socketChannel.register(this.selector, SelectionKey.OP_READ);
		socketMap.put(key, nextSocketID);
		messages.put(nextSocketID, new LinkedList<>());
		nextSocketID++;
	}

	public static void main(String[] args) {
		// test driver
		ConnectionListener cl = new ConnectionListener();
		cl.start();
	}
}
