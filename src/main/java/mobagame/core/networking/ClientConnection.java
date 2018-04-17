package mobagame.core.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientConnection extends Thread {

	public static byte PK_ID_AUTH_LOGIN = 0x01;
	// login:
	// byte length, byte type, String(16) uname, TODO: password

	private Socket socket;
	DataInputStream socketIn;
	public Map<String, Integer> intKeys;

	public ClientConnection(Socket sock) throws IOException {
		socket = sock;
		socketIn = new DataInputStream(socket.getInputStream());
		intKeys = new HashMap<>();
	}

	@Override
	public void run() {
		super.run();
		System.out.println("starting runner");
		while (socket.isConnected()) {
			try {
				if (socketIn.available() > 0) {
					// https://stackoverflow.com/questions/1176135/socket-send-and-receive-byte-array
					int length = socketIn.read();
					System.out.println(length);
					// TODO: how slow is this??
					byte[] buf = new byte[length];
					socketIn.readFully(buf);
					// int pos = 0;
					// while (pos < length) {
					// int n = socketIn.read(buf, pos, length - pos);
					// if (n < 0)
					// break;
					// pos += n;
					// }
					System.out.println(Arrays.toString(buf));
					ByteBuffer bf = ByteBuffer.wrap(buf, 0, length);
					byte packetID = bf.get(0);
					if (packetID == PK_ID_AUTH_LOGIN) {
						System.out.println("player connected");
						byte[] uNameBytes = new byte[16];
						bf.position(1);
						bf.get(uNameBytes);
						String v = new String(uNameBytes, StandardCharsets.UTF_8);
						System.out.println(v.trim());
						System.out.println(System.currentTimeMillis());

					} else {
						System.out.println("bad pkt");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
