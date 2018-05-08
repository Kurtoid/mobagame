package mobagame.core.networking.packets;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * a debug packet to send random bullshit around
 * @author Kurt Wilson
 *
 */
public class SendRandomDataPacket extends Packet {

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.position(0);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_RANDOM_BS_PACKET);
		buff.putInt(new Random().nextInt(10));

		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {

	}

}
