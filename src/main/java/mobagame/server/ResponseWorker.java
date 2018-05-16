package mobagame.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import mobagame.core.networking.packets.*;
import mobagame.server.database.PlayerAccount;
import mobagame.server.database.PlayerAccountDBO;

public class ResponseWorker implements Runnable {
	MasterGameRunner runner;
	private List queue = new LinkedList();
	public Queue<Packet> gameEvents = new LinkedBlockingQueue<Packet>();

	public void processData(ConnectionListener server, SocketChannel socket, byte[] data, int count) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		synchronized (queue) {
			queue.add(new ServerDataEvent(server, socket, dataCopy));
			queue.notify();
		}
	}

	public void run() {
		ServerDataEvent dataEvent;

		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (ServerDataEvent) queue.remove(0);
			}
			ByteBuffer chunkBuf = ByteBuffer.wrap(dataEvent.data);
			byte packetID = Packet.getPacketID(chunkBuf);
			System.out.println(packetID);
			if (packetID == Packet.PK_ID_AUTH_LOGIN) {
				System.out.println("login");
				// System.out.println(new LoginPacket(chunkBuf));
				LoginPacket p = new LoginPacket(chunkBuf);
				handleLoginPacket(p, dataEvent);
			} else if (packetID == Packet.PK_ID_AUTH_SIGNUP) {
				// System.out.println(new SignupPacket(chunkBuf));
				System.out.println("Signup");
				SignupPacket packet = new SignupPacket(chunkBuf);
				handleSignupPacket(packet, dataEvent);
			} else if (packetID == Packet.PK_ID_INIT) {
				System.out.println("Connection init");
				handleInitPacket(new InitPacket(chunkBuf), dataEvent);
			} else if (packetID == Packet.PK_ID_CONN_DISCONNECT) {
				System.out.println("disconnect");
				handleDisconnectPacket(new DisconnectPacket(chunkBuf), dataEvent);
			} else if (packetID == Packet.PK_ID_RANDOM_BS_PACKET) {
				System.out.println("BULLSHIT MODE");
				handleBullshitPacket(new SendRandomDataPacket(chunkBuf), dataEvent);
			}else if(packetID == Packet.PK_ID_PLAYER_REQUEST_ENTER_GAME){
				handleRequestEnterGameapacket(new RequestEnterGamePacket(chunkBuf), dataEvent);
			} else {
				System.out.println("bad pkt");
			}

			// Return to sender
			// dataEvent.server.send(dataEvent.socket, dataEvent.data);
		}
	}

	private void handleRequestEnterGameapacket(RequestEnterGamePacket requestEnterGamePacket, ServerDataEvent dataEvent) {
		runner.assignGame(requestEnterGamePacket.playerID);
	}

	private void handleBullshitPacket(SendRandomDataPacket packet, ServerDataEvent dataEvent) {
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
	}

	private void handleDisconnectPacket(DisconnectPacket disconnectPacket, ServerDataEvent dataEvent) {
	}

	private void handleInitPacket(InitPacket initPacket, ServerDataEvent dataEvent) {
	}

	private void handleSignupPacket(SignupPacket packet, ServerDataEvent dataEvent) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		SignupResponsePacket response = new SignupResponsePacket();
//		SignupResponsePacket resp = new SignupResponsePacket(SignupResponsePacket.FA);
		try {
			dbo.createAccount(packet.getUsername(), packet.getPassword(), packet.getEmailAddress(),
					packet.getSecurityQuestionID(), packet.getSecurityQuestionAnswer());
			response.status = SignupResponsePacket.SUCCESSFUL;
		} catch (SQLException e) {
			e.printStackTrace();
			PlayerAccount tmp = null;
			try{
				tmp = dbo.getAccountByEmail(packet.getEmailAddress());
			} catch (SQLException e1) {
				System.out.println("not email");
				// skip
			}
			if(tmp!=null){
				System.out.println("bad email");
				response.status = SignupResponsePacket.FAILED_EMAIL;
			}
			tmp = null;
			try{
				tmp = dbo.getAccountByUsername(packet.getUsername());
			} catch (SQLException e1) {
				System.out.println("not username");
				// skip
			}
			if(tmp!=null){
				System.out.println("bad username");
				response.status = SignupResponsePacket.FAILED_USERNAME;
			}
		}
		dataEvent.server.send(dataEvent.socket, response.getBytes().array());
	}

	void handleLoginPacket(LoginPacket p, ServerDataEvent dataEvent){
		// if (serverEnabled) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		PlayerAccount player = null;
		try {
			player = dbo.loginAccount(p.getUsername(), p.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoginStatusPacket loginPak = new LoginStatusPacket();
		loginPak.success = player != null;
		dataEvent.server.send(dataEvent.socket, loginPak.getBytes().array());
		if(player!=null){
			dataEvent.server.send(dataEvent.socket, new PublicPlayerDataPacket(player).getBytes().array());
		}
		// }

	}
}