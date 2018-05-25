package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class PlayerStatusReport extends Packet {
	public int playerHealth;
	public int playerGold;
	public int playerMana;
	public int playerID;

	public PlayerStatusReport() {
	}

	public PlayerStatusReport(ByteBuffer buff) {
		readData(buff);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4 + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_STATUS_REPORT);
		buff.putInt(playerID);
		buff.putInt(playerHealth);
		buff.putInt(playerMana);
		buff.putInt(playerGold);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
		playerHealth = buff.getInt();
		playerMana = buff.getInt();
		playerGold = buff.getInt();
	}

}
