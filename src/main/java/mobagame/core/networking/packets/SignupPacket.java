package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * Packet sent from client to server to request a new user to be created
 * @author Kurt Wilson
 *
 */
public class SignupPacket extends Packet {
	String username;
	String password;
	String emailAddress;
	byte securityQuestionID;
	String securityQuestionAnswer;
	@Override
	public String toString() {
		return "SignupPacket{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", securityQuestionID=" + securityQuestionID +
				", securityQuestionAnswer='" + securityQuestionAnswer + '\'' +
				'}';
	}

	public SignupPacket(String username, String password, String emailAddress, byte securityQuestionID,
						String securityQuestionAnswer) {
		super();
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.securityQuestionID = securityQuestionID;
		this.securityQuestionAnswer = securityQuestionAnswer;
	}

	public SignupPacket(ByteBuffer bf) {
		readData(bf);
	}

	@Override
	public ByteBuffer getBytes() {
		int datasize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + getNumBytes(MAX_USERNAME_LENGTH)
				+ getNumBytes(MAX_PASSWORD_LENGTH) + getNumBytes(MAX_EMAIL_LENGTH) + 1
				+ getNumBytes(MAX_SECURITY_QUESTION_SIZE);

		ByteBuffer dataBuffer = ByteBuffer.allocate(datasize);

		dataBuffer.position(0);
		dataBuffer.putInt(datasize);

		setPacketType(dataBuffer, PK_ID_AUTH_SIGNUP);

		// these can be done consecutively without position(int) because setString
		// increments position
		setString(dataBuffer, username, 5, MAX_USERNAME_LENGTH);
		setString(dataBuffer, password, dataBuffer.position(), MAX_PASSWORD_LENGTH);
		setString(dataBuffer, emailAddress, dataBuffer.position(), MAX_EMAIL_LENGTH);
		dataBuffer.put(securityQuestionID);
		setString(dataBuffer, securityQuestionAnswer, dataBuffer.position(), MAX_SECURITY_QUESTION_SIZE);

		// System.out.println(Arrays.toString(bufferToArray(dataBuffer, datasize)));

		return dataBuffer;
	}

	@Override
	void readData(ByteBuffer bf) {
		username = getStringFromBuffer(bf, 5, MAX_USERNAME_LENGTH);
		password = getStringFromBuffer(bf, bf.position(), MAX_PASSWORD_LENGTH);
		emailAddress = getStringFromBuffer(bf, bf.position(), MAX_EMAIL_LENGTH);
		securityQuestionID = bf.get();
		securityQuestionAnswer = getStringFromBuffer(bf, bf.position(), MAX_SECURITY_QUESTION_SIZE);

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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public byte getSecurityQuestionID() {
		return securityQuestionID;
	}

	public void setSecurityQuestionID(byte securityQuestionID) {
		this.securityQuestionID = securityQuestionID;
	}

	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
}
