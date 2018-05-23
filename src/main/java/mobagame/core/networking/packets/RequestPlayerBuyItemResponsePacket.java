package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class RequestPlayerBuyItemResponsePacket extends Packet {
	public final static int SUCCESSFUL = 0;
	public static final int NO_INVENTORY_SPACE = 1;
	public static final int NOT_ENOUGH_GOLD = 2;
	public int status;
	public int itemID;

	public RequestPlayerBuyItemResponsePacket(ByteBuffer chunkBuf) {
		readData(chunkBuf);
	}

	public RequestPlayerBuyItemResponsePacket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_BUY_ITEM_RESPONSE);
		buff.putInt(status);
		buff.putInt(itemID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(5);
		status = buff.getInt();
		itemID = buff.getInt();
	}

}
