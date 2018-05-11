package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * the first packet sent to a client from a server
 *
 * @author Kurt Wilson
 */
public class InitPacket extends Packet {

	public InitPacket(ByteBuffer chunkBuf) {
		super();
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		setPacketType(buff, PK_ID_INIT);
		buff.position(0);
		buff.putInt(dataSize);
		buff.position(0);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {

	}
}
