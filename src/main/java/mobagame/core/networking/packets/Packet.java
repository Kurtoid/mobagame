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

	public static final int MAX_USERNAME_LENGTH = 16;
	public static final int MAX_PASSWORD_LENGTH = 64;
	public static final byte PK_ID_INIT = 01;
	public static final byte PK_ID_AUTH_LOGIN = 02;
	public static final int MAX_EMAIL_LENGTH = 254;
	public static final int MAX_SECURITY_QUESTION_SIZE = 254;

	public static final byte PK_ID_AUTH_SIGNUP = 03;
	public static final byte PK_ID_AUTH_STATUS = 04;
	public static final byte PK_ID_CONN_DISCONNECT = 05;
	public static final byte PK_ID_PLAYER_REQUEST_ENTER_GAME = 06;
	public static final byte PK_ID_PLAYER_REQUEST_ENTER_GAME_REPONSE = 07;
	public static final byte PK_ID_PLAYER_MOVE_REPORT = 8;
	public static final byte PK_ID_PLAYER_REQUEST_MOVEMENT = 9;
	public static final byte PK_ID_SIGNUP_RESPONSE = 10;
	public static final byte PK_ID_PUBLIC_PLAYER_DATA = 11;
	public static final byte PK_ID_PLAYER_MOVEMENT = 12;
	public static final byte PK_ID_NOTIFY_PLAYER_JOINED = 13;
	public static final byte PK_ID_NOTIFY_PLAYER_DISCONNECT = 14;
	public static final byte PK_ID_PLAYER_REQUEST_BUY_ITEM = 15;
	public static final byte PK_ID_PLAYER_REQUEST_BUY_ITEM_RESPONSE = 16;
	public static final byte PK_ID_PLAYER_REQUEST_SELL_ITEM = 17;
	public static final byte PK_ID_PLAYER_REQUEST_SELL_ITEM_RESPONSE = 18;
	public static final byte PK_ID_PLAYER_STATUS_REPORT = 19;
	public static final byte PK_ID_PLAYER_USE_ITEM_REQUEST = 20;
	public static final byte PK_ID_PLAYER_USE_ITEM_RESPONSE = 21;


	public static final byte PK_ID_RANDOM_BS_PACKET = 69;//?

	public static int BYTES_PER_CHARACTER = 1;
	public static int PACKET_ID_SIZE = 1;
	public static int PACKET_SIZE_SIZE = 4;

	/**
	 * with a set of bytes, parse it out and assign the results to the specific
	 * packet class variables
	 *
	 * @param buff the data
	 */
	abstract void readData(ByteBuffer buff);

	/**
	 * utility method: what type is the packet?
	 *
	 * @param bf packet data
	 * @return type of packet
	 */
	public static byte getPacketID(ByteBuffer bf) {
		bf.position(4);
		return bf.get();
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
	 * @param bf       the original packet
	 * @param position where the string starts
	 * @param length   how long the string <i>could be</i>
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
	 * @param bf          the packet data
	 * @param packet_type the type to assign to it
	 */
	protected void setPacketType(ByteBuffer bf, byte packet_type) {
		bf.position(4);
		bf.put(packet_type);
	}

	/**
	 * add a string to a byte array
	 *
	 * @param bf       the packet data
	 * @param s        the string
	 * @param position where it should be
	 * @param length   how long it could be
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
