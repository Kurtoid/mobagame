package mobagame.server.tests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import mobagame.core.networking.ClientConnection;
import mobagame.core.networking.NetworkPacketFactory;

public class UserLoginPacket {
	public static void main(String[] args) {
		try {
		Socket echoSocket = new Socket("localhost", 8666);
		ClientConnection c = new ClientConnection(echoSocket);
		c.doLogin("Kurtoid", "Password");
		c.doSignup("Kurtoid", "PASS", "Kurt4wilson@gmail.com", (byte) 4, "gdbefhblw");

		echoSocket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
