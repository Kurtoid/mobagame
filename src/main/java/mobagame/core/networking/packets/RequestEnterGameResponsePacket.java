package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

import mobagame.core.game.Game;

public class RequestEnterGameResponsePacket extends Packet {
	static final int STATUS_FAILED = 1;
	static final int STATUS_ACCEPT = 1;

	int status;
	public int gameID = -1;

	public RequestEnterGameResponsePacket(Game g) {
		status = STATUS_ACCEPT;
		gameID = g.getGameID();
	}

	public RequestEnterGameResponsePacket() {
		// TODO Auto-generated constructor stub
	}

	public RequestEnterGameResponsePacket(ByteBuffer pkt) {
		readData(pkt);
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4;
		// status, and gameID
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_ENTER_GAME_REPONSE);
		buff.putInt(status);
		buff.putInt(gameID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		status = buff.getInt(5);
		gameID = buff.getInt(5 + 4);
		System.out.println("gameResp " + status + " " + gameID);

	}

}
