package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * packet that describes how a player moved. This is sent from the server to the
 * client to report how a player moved.
 * 
 * @author Kurt Wilson
 *
 */
public class PlayerMovement extends Packet {

	int playerID;
	double x;
	double y;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 8 + 8;
		// playerID, x, y
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_MOVE_REPORT);
		buff.putDouble(x);
		buff.putDouble(y);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(5);
		playerID = buff.getInt();
		x = buff.getDouble();
		y = buff.getDouble();
	}

}
