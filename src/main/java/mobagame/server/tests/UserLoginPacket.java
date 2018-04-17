package mobagame.server.tests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import mobagame.core.networking.ClientConnection;

public class UserLoginPacket {
	public static void main(String[] args) {
		try {
			Socket echoSocket;

			echoSocket = new Socket("localhost", 8666);
			DataOutputStream dataOut = new DataOutputStream(echoSocket.getOutputStream());
			ByteBuffer dataBuffer = ByteBuffer.allocate(300);

			byte datasize = 0;

			dataBuffer.position(1);
			dataBuffer.put(ClientConnection.PK_ID_AUTH_LOGIN);
			datasize += 1;

			dataBuffer.position(2);
			String uName;
			Scanner input = new Scanner(System.in);
			uName = input.next();
			if(uName.length()>16) {
				uName = uName.substring(0, 16);
			}

			byte[] uNameBytes = uName.getBytes(StandardCharsets.UTF_8);
			byte[] uNameFilledBytes = new byte[16];
			System.arraycopy(uNameBytes, 0, uNameFilledBytes, 0, uNameBytes.length);
			System.out.println(Arrays.toString(uNameFilledBytes));
			dataBuffer.put(uNameFilledBytes);
			datasize += uNameFilledBytes.length;

			System.out.println(datasize);
			dataBuffer.position(0);
			dataBuffer.put((byte) (datasize));

			byte[] sendBytes = new byte[datasize + 1];
			dataBuffer.position(0);
			dataBuffer.get(sendBytes);
			System.out.println(Arrays.toString(sendBytes));

			dataOut.write(sendBytes);
			dataOut.flush();
			System.out.println(System.currentTimeMillis());
			echoSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
