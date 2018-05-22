package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class NotifyPlayerDisconnectedPacket extends Packet {
	public static final int MANUAL_DISCONNECT = 0;
	static final int CONNECTION_LOST = 1;
	static final int PLAYER_KICKED = 2;
	public int playerID;
	public int disconnectReason;
	public NotifyPlayerDisconnectedPacket(ByteBuffer pkt) {
		readData(pkt);
	}

	public NotifyPlayerDisconnectedPacket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PLAYER_DISCONNECT);
		buff.putInt(playerID);
		buff.putInt(disconnectReason);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		playerID = buff.getInt();
		disconnectReason = buff.getInt();
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "NotifyPlayerDisconnectedPacket [playerID=" + playerID + ", disconnectReason=" + disconnectReason + "]";
	}

}
