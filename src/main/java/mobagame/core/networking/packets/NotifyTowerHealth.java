package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class NotifyTowerHealth extends Packet {
	public int towerID;
	public int health;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_TOWER_HEALTH);
		buff.putInt(towerID);
		buff.putInt(health);
		return buff;
	}

	public NotifyTowerHealth() {
		// TODO Auto-generated constructor stub
	}

	public NotifyTowerHealth(ByteBuffer buff) {
		super();
		readData(buff);
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		towerID = buff.getInt();
		health = buff.getInt();
	}

	
}
