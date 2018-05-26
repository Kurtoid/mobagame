package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerUseItemResponsePacket extends Packet {
	public byte used;
	public int itemID;
	
	public PlayerUseItemResponsePacket() {
		// TODO Auto-generated constructor stub
	}

	public PlayerUseItemResponsePacket(ByteBuffer chunkBuf) {
		readData(chunkBuf);
	}

	@Override
	public ByteBuffer getBytes() {
		// TODO Auto-generated method stub
		int dataSize = PACKET_SIZE_SIZE+PACKET_ID_SIZE+1+4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_USE_ITEM_RESPONSE);
		buff.put(used);
		buff.putInt(itemID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		// TODO Auto-generated method stub
		buff.rewind();
		buff.position(5);
		used = buff.get();
		itemID = buff.getInt();
	}
}
