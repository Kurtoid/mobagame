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
import mobagame.core.networking.packets.SendRandomDataPacket;
import mobagame.core.networking.packets.SignupPacket;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

/**
 * For internal testing use, do not distribute
 *
 * @author Kurt Wilson
 *
 */
public class UserLoginTester2 {
	public static void main(String[] args) {
		ServerConnection server = null;
		try {
			server = ServerConnection.getInstance("localhost", 8666);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SendRandomDataPacket p = new SendRandomDataPacket();
		// echoSocket.write(new InitPacket().getBytes());
		ByteBuffer buff = p.getBytes();
		buff.flip();
		// System.out.println(Arrays.toString(buff.array()));
		RspHandler h = new RspHandler();
		h.start();
		try {
			server.send(buff.array(), h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.waitForResponse();
		System.out.println((h.getResponse(SendRandomDataPacket.class)));
		System.out.println((h.getResponse(SendRandomDataPacket.class)));
		System.out.println((h.getResponse(SendRandomDataPacket.class)));

		h = new RspHandler();
		try {
			server.send(buff.array(), h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.waitForResponse();
		System.out.println((h.getResponse()));
		System.out.println((h.getResponse()));
		System.out.println((h.getResponse()));

		System.exit(0);
	}
}
