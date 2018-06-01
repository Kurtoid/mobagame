package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerPositionPacket extends Packet {
	public double x;
	public double y;
	public int playerID;
	public int teamID;
	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 8 + 8 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_MOVEMENT);
		buff.putInt(playerID);
		buff.putDouble(x);
		buff.putDouble(y);
		buff.putInt(teamID);
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
		x = buff.getDouble(9);
		y = buff.getDouble(17);
		teamID = buff.getInt(25);
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
