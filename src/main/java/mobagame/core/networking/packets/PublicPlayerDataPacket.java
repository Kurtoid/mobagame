package mobagame.core.networking.packets;

import mobagame.server.database.PlayerAccount;

import java.nio.ByteBuffer;

public class PublicPlayerDataPacket extends Packet{
	public PlayerAccount player;

	public PublicPlayerDataPacket(PlayerAccount p){
		player = p;
	}

	public PublicPlayerDataPacket(){

	}

	public PublicPlayerDataPacket(ByteBuffer buff){
		readData(buff);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 4 + getNumBytes(MAX_USERNAME_LENGTH) + 4;
		// playerID, username, level
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PUBLIC_PLAYER_DATA);

		buff.putInt(player.id);
		setString(buff, player.username, buff.position(), MAX_USERNAME_LENGTH);
		buff.putInt(player.level);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(0);
		player = new PlayerAccount();
		player.id = buff.getInt(5);
		player.username = getStringFromBuffer(buff, buff.position(), MAX_USERNAME_LENGTH);
		player.level = buff.getInt();
	}
}
