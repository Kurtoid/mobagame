package mobagame.core.networking.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * represents a message sent between a client and server not actually a packet
 * in tcp, but pretty much behaves like and is treated like one each packet type
 * is a subclass of this.
 *
 * @author Kurt Wilson
 */
public abstract class Packet {
	public abstract ByteBuffer getBytes();

	public static int MAX_USERNAME_LENGTH = 16;
	public static int MAX_PASSWORD_LENGTH = 64;
	public static byte PK_ID_INIT = 0x01;
	public static byte PK_ID_AUTH_LOGIN = 0x02;
	public static int MAX_EMAIL_LENGTH = 254;
	public static int MAX_SECURITY_QUESTION_SIZE = 254;

	public static byte PK_ID_AUTH_SIGNUP = 0x03;
	public static byte PK_ID_AUTH_STATUS = 0x04;
	public static byte PK_ID_CONN_DISCONNECT =  0x05;

	public static int BYTES_PER_CHARACTER = 1;
	public static int PACKET_ID_SIZE = 1;
	public static int PACKET_SIZE_SIZE = 4;

	/**
	 * with a set of bytes, parse it out and assign the results to the specific
	 * packet class variables
	 *
	 * @param buff
	 *            the data
	 */
	abstract void readData(ByteBuffer buff);

	/**
	 * utility method: what type is the packet?
	 *
	 * @param bf
	 *            packet data
	 * @return type of packet
	 */
	public static byte getPacketID(ByteBuffer bf) {
		return bf.get(4);
	}

	/**
	 * utility method to find how many bytes are in a string BYTES_PER_CHARCATER
	 * changes if we ever switch from UTF-8 //TODO: should accept a string
	 * instead...
	 *
	 * @param n
	 * @return number of bytes to represent the characters
	 */
	public int getNumBytes(int n) {
		return n * BYTES_PER_CHARACTER;
	}

	/**
	 * read a packet and pull a string from it
	 *
	 * @param bf
	 *            the original packet
	 * @param position
	 *            where the string starts
	 * @param length
	 *            how long the string <i>could be</i>
	 * @return
	 */
	protected String getStringFromBuffer(ByteBuffer bf, int position, int length) {
		byte[] bytes = new byte[length];
		bf.position(position);
		bf.get(bytes);
		String v = new String(bytes, StandardCharsets.UTF_8);
		return v.trim();
	}

	/**
	 * given a packet, set it's type with a given packet type constant
	 *
	 * @param bf
	 *            the packet data
	 * @param packet_type
	 *            the type to assign to it
	 */
	protected void setPacketType(ByteBuffer bf, byte packet_type) {
		bf.position(4);
		bf.put(packet_type);
	}

	/**
	 * add a string to a byte array
	 *
	 * @param bf
	 *            the packet data
	 * @param s
	 *            the string
	 * @param position
	 *            where it should be
	 * @param length
	 *            how long it could be
	 */
	protected void setString(ByteBuffer bf, String s, int position, int length) {

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

}
