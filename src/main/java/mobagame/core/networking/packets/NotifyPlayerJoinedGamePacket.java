package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class NotifyPlayerJoinedGamePacket extends Packet{
	public int playerID;
	public int teamID;
	public NotifyPlayerJoinedGamePacket(ByteBuffer buff){
		readData(buff);
	}
	public NotifyPlayerJoinedGamePacket(){

	}
	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PLAYER_JOINED);
		buff.putInt(playerID);
		buff.putInt(teamID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
		teamID = buff.getInt();

	}
}
