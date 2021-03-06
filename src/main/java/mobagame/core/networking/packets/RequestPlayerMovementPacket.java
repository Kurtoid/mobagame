package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * sent from the client to the server to indicate the user has pressed a key
 *
 * @author Kurt Wilson
 *
 */
public class RequestPlayerMovementPacket extends Packet {
	public double x;
	public double y;

	public RequestPlayerMovementPacket(ByteBuffer chunkBuf) {
		readData(chunkBuf);
	}
	public RequestPlayerMovementPacket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 8 + 8;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_MOVEMENT);
		buff.putDouble(x);
		buff.putDouble(y);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(5);
		x = buff.getDouble();
		y = buff.getDouble();
	}

	public static void main(String[] args) {
		RequestPlayerMovementPacket r = new RequestPlayerMovementPacket();
		r.x = 5;
		r.y = 10;
		r.readData(r.getBytes());
		System.out.println(r.x);
		System.out.println(r.y);
	}

}
