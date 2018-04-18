package mobagame.core.networking;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NetworkPacketFactory {

	public static int BYTES_PER_CHARACTER = 1;

	public static int MAX_USERNAME_LENGTH = 16;
	public static byte PK_ID_AUTH_LOGIN = 0x01;

	public static int PACKET_ID_SIZE = 1;
	public static int PACKET_SIZE_SIZE = 4;
	// login:
	// byte length, byte type, String(16) uname, String(64) password

	public static int MAX_PASSWORD_LENGTH = 64;
	public static int MAX_EMAIL_LENGTH = 254;
	public static int MAX_SECURITY_QUESTION_SIZE = 254;

	public static byte PK_ID_AUTH_SIGNUP = 0x03;
	// byte length, byte type, String username, String password, String
	// emailAddress, int securityQuestionID, String securityQuestionAnswer

	public static int getNumBytes(String s) {
		return s.length() * BYTES_PER_CHARACTER;
	}

	public static int getNumBytes(int n) {
		return n * BYTES_PER_CHARACTER;
	}

	/**
	 * Sets a bytebuffer's packet type assumes bf length at least 2
	 *
	 * @param bf
	 * @param packet_type
	 */
	private static void setPacketType(ByteBuffer bf, byte packet_type) {
		bf.position(PACKET_SIZE_SIZE);
		bf.put(packet_type);
	}

	private static void setString(ByteBuffer bf, String s, int position, int length) {

		if (s.length() > length) {
			s = s.substring(0, length);
		}

		byte[] textBytes = s.getBytes(StandardCharsets.UTF_8);
		byte[] filledBytes = new byte[length];
		System.arraycopy(textBytes, 0, filledBytes, 0, textBytes.length);
		// System.out.println(Arrays.toString(filledBytes));
		bf.position(position);
		bf.put(filledBytes);

	}

	private static byte[] bufferToArray(ByteBuffer bf, int datasize) {
		byte[] sendBytes = new byte[datasize];
		bf.position(0);
		bf.get(sendBytes);
		return sendBytes;
	}

	public static byte[] makeLoginPacket(String username, String password) {
		int datasize = getNumBytes(MAX_USERNAME_LENGTH) + PACKET_SIZE_SIZE + PACKET_ID_SIZE + getNumBytes(MAX_PASSWORD_LENGTH);
		ByteBuffer dataBuffer = ByteBuffer.allocate(datasize);

		dataBuffer.position(0);
		dataBuffer.putInt(datasize);

		setPacketType(dataBuffer, PK_ID_AUTH_LOGIN);

		setString(dataBuffer, username, 5, MAX_USERNAME_LENGTH);
		setString(dataBuffer, username, dataBuffer.position(), MAX_PASSWORD_LENGTH);

//		System.out.println(datasize);
		// System.out.println(Arrays.toString(sendBytes));
		return bufferToArray(dataBuffer, datasize);
	}

	public static byte[] makeSignupPacket(String username, String password, String emailAddress,
			byte securityQuestionID, String securityQuestionAnswer) {
		int datasize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + getNumBytes(MAX_USERNAME_LENGTH)
				+ getNumBytes(MAX_PASSWORD_LENGTH) + getNumBytes(MAX_EMAIL_LENGTH) + 1
				+ getNumBytes(MAX_SECURITY_QUESTION_SIZE);

		ByteBuffer dataBuffer = ByteBuffer.allocate(datasize);

		dataBuffer.position(0);
		dataBuffer.putInt(datasize);

		setPacketType(dataBuffer, PK_ID_AUTH_SIGNUP);

		// these can be done consecutively without position(int) because setString
		// increments position
		setString(dataBuffer, username, dataBuffer.position(), MAX_USERNAME_LENGTH);
		setString(dataBuffer, password, dataBuffer.position(), MAX_PASSWORD_LENGTH);
		setString(dataBuffer, emailAddress, dataBuffer.position(), MAX_EMAIL_LENGTH);
		dataBuffer.put(securityQuestionID);
		setString(dataBuffer, securityQuestionAnswer, dataBuffer.position(), MAX_SECURITY_QUESTION_SIZE);

//		System.out.println(Arrays.toString(bufferToArray(dataBuffer, datasize)));

		return bufferToArray(dataBuffer, datasize);
	}

}
