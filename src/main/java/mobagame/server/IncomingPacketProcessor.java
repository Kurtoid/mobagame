package mobagame.server;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.LoginStatusPacket;
import mobagame.core.networking.packets.Packet;
import mobagame.core.networking.packets.SignupPacket;
import mobagame.server.database.PlayerAccount;
import mobagame.server.database.PlayerAccountDBO;

/**
 * Handles new messages from clients runs as a thread, and waits for new
 * additions to BlockingQueue&lt;OutMessage&gt; packets
 *
 * @author Kurt Wilson
 */
public class IncomingPacketProcessor extends Thread {
	boolean serverEnabled;
	BlockingQueue<OutMessage> packets;
	ConnectionListener l;

	/**
	 * initializes class
	 *
	 * @param l
	 */
	public IncomingPacketProcessor(ConnectionListener l) {
		packets = new LinkedBlockingQueue<>();
		this.l = l;
	}

	/**
	 * sets whether the thread should try to use
	 * {@link mobagame.server.database.DatabaseConnectionManager} or not
	 *
	 * @param serverEnabled
	 */
	public void setServerEnabled(boolean serverEnabled) {
		this.serverEnabled = serverEnabled;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				OutMessage m = packets.take();
				ByteBuffer chunkBuf = m.buff;
				byte packetID = Packet.getPacketID(chunkBuf);
				if (packetID == Packet.PK_ID_AUTH_LOGIN) {
					// System.out.println(new LoginPacket(chunkBuf));
					LoginPacket p = new LoginPacket(chunkBuf);
					if (serverEnabled) {
						PlayerAccountDBO dbo = new PlayerAccountDBO();
						try {
							PlayerAccount player = dbo.loginAccount(p.getUsername(), p.getPassword());
							LoginStatusPacket loginPak = new LoginStatusPacket();
							loginPak.success = player != null;
							l.messages.get(m.socketID).add(new OutMessage(m.socketID, loginPak.getBytes()));
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * adds a message to process to send to clients
	 *
	 * @param bytes
	 */
	void addToQueue(OutMessage bytes) {
		packets.add(bytes);
	}
}
