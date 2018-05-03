package mobagame.server.tests;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.SignupPacket;

/**
 * For internal testing use, do not distribute
 * @author Kurt Wilson
 *
 */
public class UserLoginTester {
	public static void main(String[] args) {
		try {
			SocketChannel echoSocket = SocketChannel.open();
			echoSocket.connect(new InetSocketAddress("localhost", 8666));

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
			Thread.sleep(3000);
			echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
