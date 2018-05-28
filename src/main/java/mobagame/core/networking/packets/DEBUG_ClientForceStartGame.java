package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * Sent from the client to the server to join a game with random (for now)
 * players
 *
 * @author Kurt Wilson
 */
public class DEBUG_ClientForceStartGame extends Packet {
	public int lobbyID;
	public DEBUG_ClientForceStartGame(ByteBuffer chunkBuf) {
		super();
		readData(chunkBuf);
	}


	public DEBUG_ClientForceStartGame() {

	}


	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE +4;
		// player id and character, and, either a specific lobby, or -1 for random
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_DEBUG_CLIENT_FORCE_START_GAME);
buff.putInt(lobbyID);
		return buff;
	}

	@Override void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		lobbyID=buff.getInt();
	}

}
