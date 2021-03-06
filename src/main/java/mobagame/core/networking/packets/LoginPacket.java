package mobagame.core.networking.packets;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * sent from the client to a server to request authentication
 *
 * @author Kurt Wilson
 */
public class LoginPacket extends Packet {

	/**
	 * the username to attempt to log in as
	 */
	private String username;
	/**
	 * the (unhashed) password to use
	 */
	private String password;

	/**
	 * create a loginpacket, but not assign the bytes yet
	 *
	 * @param uname
	 *            username
	 * @param passwd
	 *            password
	 */
	public LoginPacket(String uname, String passwd) {
		username = uname;
		password = passwd;
	}

	@Override
	public String toString() {
		return "LoginPacket{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
	}

	/**
	 * create a LoginPacket for parsing
	 *
	 * @param bf
	 */
	public LoginPacket(ByteBuffer bf) {
		readData(bf);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = getNumBytes(MAX_USERNAME_LENGTH) + PACKET_SIZE_SIZE + PACKET_ID_SIZE
				+ getNumBytes(MAX_PASSWORD_LENGTH);
		ByteBuffer dataBuffer = ByteBuffer.allocate(dataSize);

		dataBuffer.position(0);
		dataBuffer.putInt(dataSize);

		setPacketType(dataBuffer, PK_ID_AUTH_LOGIN);

		setString(dataBuffer, username, 5, MAX_USERNAME_LENGTH);
		setString(dataBuffer, password, dataBuffer.position(), MAX_PASSWORD_LENGTH);

		// System.out.println(dataSize);
		// System.out.println(dataBuffer.getInt(0));
		// System.out.println(dataSize);
		// System.out.println(Arrays.toString(dataBuffer.array()));
		return dataBuffer;
	}

	@Override
	void readData(ByteBuffer buff) {
		username = getStringFromBuffer(buff, 5, MAX_USERNAME_LENGTH);
		password = getStringFromBuffer(buff, buff.position(), MAX_PASSWORD_LENGTH);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
