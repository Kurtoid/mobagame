package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class RequestPlayerBuyItemPacket extends Packet {
	public int itemID;

	public RequestPlayerBuyItemPacket(ByteBuffer chunkBuf) {
		readData(chunkBuf);
	}

	public RequestPlayerBuyItemPacket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_BUY_ITEM);
		buff.putInt(itemID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(5);
		itemID = buff.getInt();
	}

}
