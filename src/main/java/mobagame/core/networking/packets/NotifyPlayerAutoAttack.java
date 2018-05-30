package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class NotifyPlayerAutoAttack extends Packet {
	int playerID;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PLAYER_AUTO_ATTACK);
		buff.putInt(playerID);
		return null;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
	}
}
