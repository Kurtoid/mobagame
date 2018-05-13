package mobagame.core.networking.packets;

import sun.security.pkcs.SigningCertificateInfo;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SignupResponsePacket extends Packet {
	final byte SUCCESSFUL = 0, FAILED_USERNAME = 1, FAILED_EMAIL = 2;
	byte status;

	public SignupResponsePacket() {

	}

	public SignupResponsePacket(byte status) {
		this.status = status;
	}
	public SignupResponsePacket(ByteBuffer buff){
		this();
		readData(buff);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 1;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_SIGNUP_RESPONSE);
		buff.put(status);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		status = buff.get(5);
	}
}
