package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerUseItemRequestPacket extends Packet {
	public int itemID;
	
	public PlayerUseItemRequestPacket() {
		// TODO Auto-generated constructor stub
	}

	public PlayerUseItemRequestPacket(ByteBuffer chunkBuf) {
		readData(chunkBuf);
	}

	@Override
	public ByteBuffer getBytes() {
		// TODO Auto-generated method stub
		int dataSize = PACKET_SIZE_SIZE+PACKET_ID_SIZE+4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_USE_ITEM_REQUEST);
		buff.putInt(itemID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		// TODO Auto-generated method stub
		buff.rewind();
		buff.position(5);
		itemID = buff.getInt();
	}

}
