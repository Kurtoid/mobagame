package mobagame.server.tests;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import mobagame.core.networking.packets.LoginPacket;
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
		ServerConnection sc;
		try {
			sc = ServerConnection.getInstance("localhost", 8666);
			Thread t = new Thread(sc);
			t.setDaemon(true);
			t.start();
			RspHandler h = new RspHandler();
			sc.send(new LoginPacket("Kurtoid", "PASS").getBytes().array(), h);
			h.waitForResponse();
			System.out.println(Arrays.toString(h.getResponse().array()));
			
			sc.send(new SignupPacket("test1", "PASSWORD", "example@example.com", (byte) 3, "poop").getBytes().array(), null);
			
			h = new RspHandler();
			sc.send(new LoginPacket("test1", "PASSWORD").getBytes().array(), h);
			h.waitForResponse();
			System.out.println(Arrays.toString(h.getResponse().array()));

			/*
			 * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } //
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
