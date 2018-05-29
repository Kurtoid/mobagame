package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

import mobagame.core.game.Game;
import mobagame.core.game.InGamePlayer;

public class RequestEnterGameResponsePacket extends Packet {
	static final int STATUS_FAILED = 1;
	static final int STATUS_ACCEPT = 1;

	int status;
	public int gameID = -1;
	public int playerID;

	public RequestEnterGameResponsePacket(Game g, InGamePlayer p) {
		status = STATUS_ACCEPT;
		gameID = g.getGameID();
		playerID = p.getPlayerID();
	}

	public RequestEnterGameResponsePacket() {
		// TODO Auto-generated constructor stub
	}

	public RequestEnterGameResponsePacket(ByteBuffer pkt) {
		readData(pkt);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4+4;
		// status, and lobbyID
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_ENTER_GAME_REPONSE);
		buff.putInt(status);
		buff.putInt(gameID);
		buff.putInt(playerID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		status = buff.getInt();
		gameID = buff.getInt();
		playerID = buff.getInt();
		System.out.println("gameResp " + status + " " + gameID + " " + playerID);

	}

}
