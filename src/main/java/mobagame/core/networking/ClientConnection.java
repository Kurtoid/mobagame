package mobagame.core.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientConnection extends Thread {

	private Socket socket;
	DataInputStream socketIn;
	DataOutputStream socketOut;
	public ClientConnection(Socket sock) throws IOException {
		socket = sock;
		socketIn = new DataInputStream(socket.getInputStream());
		socketOut = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		super.run();
		System.out.println("starting runner");
		long lastRespondTime = System.currentTimeMillis();
		boolean running = true;
		while (running) {
			try {
				if (socketIn.available() >= 4) { // size of int, which represents size of transmission

					// https://stackoverflow.com/questions/1176135/socket-send-and-receive-byte-array
					int length = socketIn.readInt();
					length -= NetworkPacketFactory.PACKET_SIZE_SIZE;
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
					ByteBuffer bf = ByteBuffer.wrap(buf, 0, length);
					byte packetID = NetworkPacketReader.getPacketID(bf);
					if (packetID == NetworkPacketFactory.PK_ID_AUTH_LOGIN) {
						System.out.println(NetworkPacketReader.processLoginPacket(bf));
					} else if (packetID == NetworkPacketFactory.PK_ID_AUTH_SIGNUP) {
						System.out.println(NetworkPacketReader.processSignupPacket(bf));
					} else {
						System.out.println("bad pkt");
					}
					lastRespondTime = System.currentTimeMillis();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - lastRespondTime > 1000 * 10) {
				System.out.println("no transmissions, closing");
				running = false;
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("socket closed");
	}

	public int doLogin(String username, String password) {
		byte[] data = NetworkPacketFactory.makeLoginPacket(username, password);
		try {
			socketOut.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public int doSignup(String username, String password, String emailAddress, byte securityQuestionID,
			String securityQuestionAnswer) {
		byte[] data = NetworkPacketFactory.makeSignupPacket(username, password, emailAddress, securityQuestionID,
				securityQuestionAnswer);
		try {
			socketOut.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

}
