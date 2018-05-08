package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

/**
 * sent from the client to the server to indicate the user has pressed a key
 * 
 * @author Kurt Wilson
 *
 */
public class PlayerMovementRequest extends Packet {
	double dx;
	double dy;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 8 + 8;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PLAYER_REQUEST_MOVEMENT);
		buff.putDouble(dx);
		buff.putDouble(dy);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.position(5);
		dx = buff.getDouble();
		dy = buff.getDouble();
	}

	public static void main(String[] args) {
		PlayerMovementRequest r = new PlayerMovementRequest();
		r.dx = 5.2;
		r.dy = 10.7;
		r.readData(r.getBytes());
		System.out.println(r.dx);
		System.out.println(r.dy);
	}

}
