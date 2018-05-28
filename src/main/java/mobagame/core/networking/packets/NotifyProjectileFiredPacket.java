package mobagame.core.networking.packets;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

public class NotifyProjectileFiredPacket extends Packet {
	public Point2D.Double target;
	public Point2D.Double firedFrom;
	public double speed;
	public int teamIDFiredFrom;

	public NotifyProjectileFiredPacket(ByteBuffer pkt) {
		super();
		readData(pkt);
	}

	public NotifyProjectileFiredPacket() {

	}

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_SIZE_SIZE + PACKET_ID_SIZE + 8 * 2 + 8 * 2 + 4 + 8;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_NOTIFY_PROJECTILE_FIRED);
		buff.putDouble(firedFrom.getX());
		buff.putDouble(firedFrom.getY());
		buff.putDouble(target.getX());
		buff.putDouble(target.getY());
		buff.putInt(teamIDFiredFrom);
		buff.putDouble(speed);
		return buff;
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		buff.position(5);
		firedFrom = new Point2D.Double(buff.getDouble(), buff.getDouble());
		target = new Point2D.Double(buff.getDouble(), buff.getDouble());
		teamIDFiredFrom = buff.getInt();
		speed = buff.getDouble();
	}
}
