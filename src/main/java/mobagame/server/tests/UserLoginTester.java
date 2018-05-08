package mobagame.server.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import mobagame.core.networking.packets.DisconnectPacket;
import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.LoginStatusPacket;
import mobagame.core.networking.packets.SignupPacket;

/**
 * For internal testing use, do not distribute
 *
 * @author Kurt Wilson
 *
 */
public class UserLoginTester {
	public static void main(String[] args) {
		try {
			SocketChannel echoSocket = SocketChannel.open();
			echoSocket.connect(new InetSocketAddress("localhost", 8666));
			echoSocket.configureBlocking(false);
			SignupPacket sp = new SignupPacket("Kurtoid", "PASS", "Kurt4wilson@gmail.com", (byte) 4, "gdbefhblw");
			ByteBuffer buff = sp.getBytes();
			buff.flip();
			System.out.println(Arrays.toString(buff.array()));
			echoSocket.write(buff);

			LoginPacket p = new LoginPacket("Kurtoid", "PASS");
			// echoSocket.write(new InitPacket().getBytes());
			buff = p.getBytes();
			buff.flip();
			System.out.println(Arrays.toString(buff.array()));
			echoSocket.write(buff);

			// c.doLogin("Kurtoid", "Password");
			// c.doSignup("Kurtoid", "PASS", "Kurt4wilson@gmail.com", (byte) 4,
			// "gdbefhblw");
			System.out.println("waiting for resposne");
			boolean gotResponse = false;
			LoginStatusPacket status = null;
			while (!gotResponse) {
				// System.out.println("running repsonse loop");
				ByteArrayOutputStream byteStore = new ByteArrayOutputStream();
				ByteBuffer chunkBuf = ByteBuffer.allocate(512);
				chunkBuf.clear();
				chunkBuf.position(0);
				while (echoSocket.read(chunkBuf) > 0) {
					byteStore.write(chunkBuf.array());
					System.out.println(Arrays.toString(chunkBuf.array()));
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
					// System.out.println("nope");
				}
				if (length > 0) {
					// System.out.println(chunkBuf.get(5));
					// System.out.println(length);
					gotResponse = true;
					status = new LoginStatusPacket();
					status.readData(chunkBuf);
					System.out.println("got response");
				}
			}
			if (status != null) {
				System.out.println(status.success ? "Logged in" : "Not logged in");
			} else {
				System.out.println("an error occured");
			}

			Thread.sleep(3000);
			buff = new DisconnectPacket().getBytes();
			buff.flip();
			echoSocket.write(buff);
			Thread.sleep(1000);
			echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
