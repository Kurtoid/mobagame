package mobagame.core.networking.packets;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

public class NotifyProjectileFiredPacket extends Packet {
	public int teamIDFiredFrom;
	public int projectileID;
	public NotifyProjectileFiredPacket(ByteBuffer pkt) {
		super();
		readData(pkt);
	}

	public NotifyProjectileFiredPacket() {

	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PROJECTILE_FIRED);
		buff.putInt(teamIDFiredFrom);
		buff.putInt(projectileID);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		teamIDFiredFrom = buff.getInt();
		projectileID = buff.getInt();
	}


	public static void main(String[] args) {
		NotifyProjectileFiredPacket pkt = new NotifyProjectileFiredPacket();
		pkt.teamIDFiredFrom = 1;
		pkt.projectileID = 5;
		NotifyProjectileFiredPacket pkt2 = new NotifyProjectileFiredPacket(pkt.getBytes());
		System.out.println(pkt.toString());
		System.out.println(pkt2.toString());
	}
}
