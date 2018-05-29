package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class ProjectilePositionPacket extends Packet {
	public double x;
	public double y;
	public int projectileID;

	@Override
	public ByteBuffer getBytes() {
		int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 8 + 8;
		ByteBuffer buff = ByteBuffer.allocate(dataSize);
		buff.putInt(dataSize);
		setPacketType(buff, PK_ID_PROJECTILE_MOVEMENT);
		buff.putInt(projectileID);
		buff.putDouble(x);
		buff.putDouble(y);
		return buff;
	}

	public ProjectilePositionPacket() {
		// TODO Auto-generated constructor stub
	}

	public ProjectilePositionPacket(ByteBuffer buff) {
		super();
		readData(buff);
	}

	@Override
	void readData(ByteBuffer buff) {
		buff.rewind();
		projectileID = buff.getInt(5);
		x = buff.getDouble(9);
		y = buff.getDouble(17);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "ProjectilePositionPacket{" +
				"x=" + x +
				", y=" + y +
				", projectileID=" + projectileID +
				'}';
	}
}
