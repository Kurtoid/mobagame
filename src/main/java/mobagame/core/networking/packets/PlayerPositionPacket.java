package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerPositionPacket extends Packet {
	int x;
	int y;
	int playerID;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_MOVEMENT);
		buff.putInt(playerID);
		buff.putInt(x);
		buff.putInt(y);
		return buff;
	}

	public PlayerPositionPacket() {
		// TODO Auto-generated constructor stub
	}

	public PlayerPositionPacket(ByteBuffer buff) {
		super();
		readData(buff);
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		playerID = buff.getInt(5);
		x = buff.getInt();
		y = buff.getInt();
	}

}
