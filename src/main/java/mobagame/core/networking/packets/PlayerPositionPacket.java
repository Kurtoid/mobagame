package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerPositionPacket extends Packet {
	public int x;
	public int y;
	public int playerID;

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
		x = buff.getInt(9);
		y = buff.getInt(13);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "PlayerPositionPacket{" +
				"x=" + x +
				", y=" + y +
				", playerID=" + playerID +
				'}';
	}
}
