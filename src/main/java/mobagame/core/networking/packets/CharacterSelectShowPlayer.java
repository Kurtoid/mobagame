package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class CharacterSelectShowPlayer extends Packet{
	public int playerID;
	public String playerUsername;
	public int teamID;
	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + getNumBytes(MAX_USERNAME_LENGTH)+4+4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_CHARACTER_SELECT_SHOW_PLAYER);
		buff.putInt(playerID);
		setString(buff, playerUsername, buff.position(), MAX_USERNAME_LENGTH);
		buff.putInt(teamID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
		playerUsername = getStringFromBuffer(buff, buff.position(), MAX_USERNAME_LENGTH);
		teamID = buff.getInt();
	}
}
