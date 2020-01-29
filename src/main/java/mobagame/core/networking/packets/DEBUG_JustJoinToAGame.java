package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class DEBUG_JustJoinToAGame extends Packet {
	public int playerID;
	public DEBUG_JustJoinToAGame(ByteBuffer chunkBuf) {
		super();
		readData(chunkBuf);
	}

	public DEBUG_JustJoinToAGame(int id) {
		super();
		playerID = id;
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE +4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_DEBUG_THROW_ME_IN_A_GAME);
		buff.putInt(playerID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
	}
}
