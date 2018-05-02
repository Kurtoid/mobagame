package mobagame.server;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.Packet;
import mobagame.core.networking.packets.SignupPacket;
import mobagame.server.database.PlayerAccountDBO;

public class IncomingPacketProcessor extends Thread {
	boolean serverEnabled;
	BlockingQueue<ByteBuffer> packets;

	public IncomingPacketProcessor() {
		packets = new LinkedBlockingQueue<ByteBuffer>();
	}
	public void setServerEnabled(boolean serverEnabled) {
		this.serverEnabled = serverEnabled;
	}
	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				ByteBuffer chunkBuf = packets.take();

				byte packetID = Packet.getPacketID(chunkBuf);
				if (packetID == Packet.PK_ID_AUTH_LOGIN) {
					// System.out.println(new LoginPacket(chunkBuf));
					LoginPacket p = new LoginPacket(chunkBuf);
					if (serverEnabled) {
						PlayerAccountDBO dbo = new PlayerAccountDBO();
						try {
							dbo.loginAccount(p.getUsername(), p.getPassword());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					System.out.println(p.toString());
				} else if (packetID == Packet.PK_ID_AUTH_SIGNUP) {
					// System.out.println(new SignupPacket(chunkBuf));
					SignupPacket packet = new SignupPacket(chunkBuf);
					System.out.println(packet);
					PlayerAccountDBO dbo = new PlayerAccountDBO();
					dbo.createAccount(packet.getUsername(), packet.getPassword(), packet.getEmailAddress(),
							packet.getSecurityQuestionID(), packet.getSecurityQuestionAnswer());

				} else if (packetID == Packet.PK_ID_INIT) {
					System.out.println("Connection init");
				} else {
					System.out.println("bad pkt");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	void addToQueue(ByteBuffer b) {
		packets.add(b);
	}
}
