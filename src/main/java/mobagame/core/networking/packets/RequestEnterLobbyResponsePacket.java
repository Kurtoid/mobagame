package mobagame.core.networking.packets;

import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Lobby;

import java.nio.ByteBuffer;

public class RequestEnterLobbyResponsePacket extends Packet {
	static final int STATUS_FAILED = 1;
	static final int STATUS_ACCEPT = 1;

	int status;
	public int lobbyID = -1;
	public int playerID;

	public RequestEnterLobbyResponsePacket(Lobby g, InGamePlayer p) {
		status = STATUS_ACCEPT;
		lobbyID = g.getLobbyID();
		playerID = p.getPlayerID();
	}

	public RequestEnterLobbyResponsePacket() {
		// TODO Auto-generated constructor stub
	}

	public RequestEnterLobbyResponsePacket(ByteBuffer pkt) {
		readData(pkt);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4+4;
		// status, and lobbyID
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_ENTER_LOBBY_REPONSE);
		buff.putInt(status);
		buff.putInt(lobbyID);
		buff.putInt(playerID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		status = buff.getInt(5);
		lobbyID = buff.getInt(5 + 4);
		playerID = buff.getInt(5+4*2);
		System.out.println("gameResp " + status + " " + lobbyID + " " + playerID);

	}
}
