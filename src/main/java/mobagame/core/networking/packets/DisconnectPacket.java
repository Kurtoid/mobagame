package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * The last thing a client sends to the server
 *
 * @author Kurt Wilson
 *
 */
public class DisconnectPacket extends Packet {


	@Override
	public ByteBuffer getBytes() {
		ByteBuffer buff = ByteBuffer.allocate(PACKET_SIZE_SIZE + PACKET_ID_SIZE);
		buff.putInt(PACKET_SIZE_SIZE+PACKET_ID_SIZE);
		setPacketType(buff, PK_ID_CONN_DISCONNECT);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {

	}

}
