package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * Sent from the client to the server to join a game with random (for now)
 * players
 *
 * @author Kurt Wilson
 */
public class RequestEnterGamePacket extends Packet {
	public int playerID;
	public int characterID;
	public int gameID;

	public RequestEnterGamePacket(ByteBuffer chunkBuf) {
		super();
		readData(chunkBuf);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4 + 4;
		// player id and character, and, either a specific lobby, or -1 for random
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_ENTER_GAME);

		buff.putInt(playerID);
		buff.putInt(characterID);
		buff.putInt(gameID);

		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		playerID = buff.getInt(5);
		characterID = buff.getInt();
	}

}
