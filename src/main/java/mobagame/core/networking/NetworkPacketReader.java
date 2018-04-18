package mobagame.core.networking;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NetworkPacketReader {

	public static byte getPacketID(ByteBuffer bf) {
		return bf.get(0);
	}

	public static String processLoginPacket(ByteBuffer bf) {
		System.out.println("player connected");
		return getStringFromBuffer(bf, 1, NetworkPacketFactory.MAX_USERNAME_LENGTH);
	}

	public static String processSignupPacket(ByteBuffer bf) {
		System.out.println("Signup");
		String username = getStringFromBuffer(bf, 1, NetworkPacketFactory.MAX_USERNAME_LENGTH);
		String password = getStringFromBuffer(bf, bf.position(), NetworkPacketFactory.MAX_PASSWORD_LENGTH);
		String emailaddress = getStringFromBuffer(bf, bf.position(), NetworkPacketFactory.MAX_EMAIL_LENGTH);
		byte questionID = bf.get();
		String questionAnswer = getStringFromBuffer(bf, bf.position(), NetworkPacketFactory.MAX_SECURITY_QUESTION_SIZE);
		return username + "\t" + password + "\t" + emailaddress + "\t" + questionID + "\t" + questionAnswer;
	}

	private static String getStringFromBuffer(ByteBuffer bf, int position, int length) {
		byte[] bytes = new byte[length];
		bf.position(position);
		bf.get(bytes);
		String v = new String(bytes, StandardCharsets.UTF_8);
		return v.trim();
	}

}
