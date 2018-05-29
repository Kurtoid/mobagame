package mobagame.core.networking.packets;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

public class NotifyProjectileRemovedPacket extends Packet {
	public int teamIDFiredFrom;
	public int projectileID;
	public NotifyProjectileRemovedPacket(ByteBuffer pkt) {
		super();
		readData(pkt);
	}

	public NotifyProjectileRemovedPacket() {

	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 4 + 4;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PROJECTILE_REMOVED);
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
		NotifyProjectileRemovedPacket pkt = new NotifyProjectileRemovedPacket();
		pkt.teamIDFiredFrom = 1;
		pkt.projectileID = 5;
		NotifyProjectileRemovedPacket pkt2 = new NotifyProjectileRemovedPacket(pkt.getBytes());
		System.out.println(pkt.toString());
		System.out.println(pkt2.toString());
	}
}
