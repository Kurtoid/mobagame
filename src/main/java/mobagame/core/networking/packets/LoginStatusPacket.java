package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * the response to a {@link mobagame.core.networking.packets.LoginPacket} sent
 * by a sever, to a client
 *
 * @author Kurt Wilson
 */
public class LoginStatusPacket extends Packet {
	public boolean success = false;

	@Override
	public ByteBuffer getBytes() {
		int datasize = PACKET_SIZE_SIZE + PACKET_SIZE_SIZE + 1;
		ByteBuffer buff = ByteBuffer.allocate(datasize);
		buff.position(0);
		buff.putInt(datasize);

		setPacketType(buff, PK_ID_AUTH_STATUS);

		buff.position(5);
		buff.put((byte) (success ? 0x01 : 0x00));
		return buff;
	}

	@Override
	public void readData(ByteBuffer buff) {
		success = buff.get(5) > 0 ? true : false;
	}

}
