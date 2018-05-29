package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * Sent from the client to the server to join a game with random (for now)
 * players
 *
 * @author Kurt Wilson
 */
public class NotifyPlayerEnterCharacterSelect extends Packet {
	public int teamID;
	public NotifyPlayerEnterCharacterSelect(ByteBuffer chunkBuf) {
		super();
		readData(chunkBuf);
	}


	public NotifyPlayerEnterCharacterSelect() {

	}


	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE+4;
		// player id and character, and, either a specific lobby, or -1 for random
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PLAYER_ENTER_CHARACTER_SELECT);
		buff.putInt(teamID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		teamID = buff.getInt();
	}

}
