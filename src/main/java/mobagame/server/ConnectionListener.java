package mobagame.server;

import java.awt.image.DataBuffer;
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
import java.util.Iterator;

import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.Packet;
import mobagame.core.networking.packets.SignupPacket;
import mobagame.core.settings.SettingManager;
import mobagame.core.DebugSettings;
import mobagame.server.database.DatabaseConnectionManager;
import mobagame.server.database.PlayerAccount;
import mobagame.server.database.PlayerAccountDBO;

import javax.xml.crypto.Data;

public class ConnectionListener extends Thread {
	ByteBuffer chunkBuf;
	PlayerAccountDBO dbo;
	DatabaseConnectionManager db;
	IncomingPacketProcessor packetProc;
	SettingManager settingManager;

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

			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			packetProc = new IncomingPacketProcessor();
			packetProc.setServerEnabled(serverEnabled);
			packetProc.setDaemon(true);
			packetProc.start();

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
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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
			System.out.println(Arrays.toString(byteStore.toByteArray()));
			packetProc.addToQueue(chunkBuf);
		}

	}

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
	}

	public static void main(String[] args) {
		// test driver
		ConnectionListener cl = new ConnectionListener();
		cl.start();
	}
}
