package mobagame.launcher.networking;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

import mobagame.core.networking.packets.*;

public class RspHandler extends Thread {

	private BlockingQueue<ByteBuffer> responses;
	private BlockingQueue<Packet> packets;
	static RspHandler instance;

	/**
	 * called by the server thread
	 * dont use this anywhere else
	 *
	 * @param rsp
	 * @return whether the connection should be closed or not
	 */
	public synchronized boolean handleResponse(byte[] rsp) {
		System.out.println("handle response");
		responses.add(ByteBuffer.wrap(rsp));
		this.notify();
		return false;
	}

	/**
	 * waits for at least one response to have been processed
	 */
	public synchronized void waitForResponse() {
		System.out.println("waiting for response");
		while (packets.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.print("resp recieved");
	}

	/**
	 * waits for at least one response to have been processed, or <i>timeout</i> milliseconds
	 *
	 * @param timeout
	 * @throws TimeoutException if it times out
	 */
	public synchronized void waitForResponse(long timeout) throws TimeoutException {
		long currentTime = System.currentTimeMillis();
		System.out.println("waiting for response");
		while (packets.isEmpty()) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
			}
			if (System.currentTimeMillis() - currentTime > timeout) {
				throw new TimeoutException("the server took too long");
			}
		}
		System.out.print("resp recieved");
	}

	/**
	 * waits for <i>amount</i> responses, or <i>timeout</i> milliseconds
	 *
	 * @param amount
	 * @param timeout
	 * @throws TimeoutException
	 */
	public synchronized void waitForResponse(int amount, long timeout) throws TimeoutException {
		long currentTime = System.currentTimeMillis();
//		System.out.println("waiting for response " + amount);
		while (packets.size() < amount) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
			}
			if (System.currentTimeMillis() - currentTime > timeout) {
				throw new TimeoutException("the server took too long");
			}
		}
//		System.out.println("resp recieved");
	}

	/**
	 * dont call this directly
	 */
	@Override
	public void run() {
		super.run();
		while (true) {
			ByteBuffer b = null;
			try {
				b = responses.take();
//				System.out.println("proccessing response");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("arrayin: " + Arrays.toString(b.array()));
			while (b.remaining() > 0) {
//				System.out.println("remaining " + b.toString());
				int len = b.getInt();
//				System.out.println("len " + len);
				if (len > 0) {
					ByteBuffer pkt = ByteBuffer.allocate(len);
					pkt.putInt(len);
					byte[] tmp = new byte[len - 4];
					b.get(tmp);
					pkt.put(tmp);
//					System.out.println("cut array " + Arrays.toString(pkt.array()));
					switch (Packet.getPacketID(pkt)) {
						case Packet.PK_ID_RANDOM_BS_PACKET:
//							System.out.println("Bullshit");
							addToPackets(new SendRandomDataPacket(pkt));
							break;
						case Packet.PK_ID_SIGNUP_RESPONSE:
//							System.out.println("SignupResponse");
							addToPackets(new SignupResponsePacket(pkt));
							break;
						case Packet.PK_ID_AUTH_STATUS:
//							System.out.println("LoginStatusPacket");
							addToPackets(new LoginStatusPacket(pkt));
							break;
						case Packet.PK_ID_PUBLIC_PLAYER_DATA:
//							System.out.println("playerdata");
							addToPackets(new PublicPlayerDataPacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_REQUEST_ENTER_GAME_REPONSE:
//							System.out.println("request game resposne");
							addToPackets(new RequestEnterGameResponsePacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_REQUEST_ENTER_LOBBY_REPONSE:
//							System.out.println("request enter lobby response");
							addToPackets(new RequestEnterLobbyResponsePacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_MOVE_REPORT:
//							System.out.println("Player movement report");
							addToPackets(new PlayerPositionPacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_MOVEMENT:
//							System.out.println("player_movement");
							addToPackets(new PlayerPositionPacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_PLAYER_JOINED:
//							System.out.println("player joined");
							addToPackets(new NotifyPlayerJoinedGamePacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_PLAYER_DISCONNECT:
//							System.out.println("Player disconnected");
							addToPackets(new NotifyPlayerDisconnectedPacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_REQUEST_BUY_ITEM_RESPONSE:
//							System.out.println("Buy item response");
							addToPackets(new RequestPlayerBuyItemResponsePacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_REQUEST_SELL_ITEM_RESPONSE:
//							System.out.println("sell item response");
							addToPackets(new RequestPlayerSellItemResponsePacket(pkt));
							break;
						case Packet.PK_ID_PLAYER_STATUS_REPORT:
//							System.out.println("status report");
							addToPackets(new PlayerStatusReport(pkt));
							break;
						case Packet.PK_ID_PLAYER_USE_ITEM_RESPONSE:
//							System.out.println("used item");
							addToPackets(new PlayerUseItemResponsePacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_PROJECTILE_FIRED:
//							System.out.println("projectile fired");
							addToPackets(new NotifyProjectileFiredPacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_PLAYER_ENTER_CHARACTER_SELECT:
//							System.out.println("Character select enter");
							addToPackets(new NotifyPlayerEnterCharacterSelect(pkt));
							break;
						case Packet.PK_ID_PROJECTILE_MOVEMENT:
//							System.out.println("projectile movement");
							addToPackets(new ProjectilePositionPacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_PROJECTILE_REMOVED:
//							System.out.println("projectile removed");
							addToPackets(new NotifyProjectileRemovedPacket(pkt));
							break;
						case Packet.PK_ID_NOTIFY_TOWER_HEALTH:
							System.out.println("tower health");
							addToPackets(new NotifyTowerHealth(pkt));
						default:
							System.out.println("unknown packet " + Packet.getPacketID(pkt));
							break;
					}
				}
			}
		}
	}

	/**
	 * adds a packet to the queue, and notifys any waits
	 *
	 * @param p
	 */
	private synchronized void addToPackets(Packet p) {
		System.out.println(p.getClass().getName() + " added");
		packets.add(p);
		this.notify();
	}

	/**
	 * gets any next avalible response
	 *
	 * @return
	 */
	public synchronized Packet getResponse() {
		System.out.println("get response called");
		try {
			return packets.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * gets a response of a specified type
	 *
	 * @param c
	 * @return
	 */
	public synchronized Packet getResponse(Class c) {
		Iterator<Packet> i = packets.iterator();
//		System.out.println(packets.size() + " to search");
		while (i.hasNext()) {
			Packet tmp = i.next();
//			System.out.println("looking at " + tmp.getClass().getName());
			if (c.isInstance(tmp)) {
				i.remove();
//				System.out.println(tmp.getClass().getName() + " found");
				return tmp;
			}
		}
//		System.out.println("Cant find packet type " + c.getName());
		return null;
	}

	private RspHandler() {
		System.out.println("construct RspHandler");
		responses = new LinkedBlockingQueue<ByteBuffer>();
		packets = new LinkedBlockingQueue<>();
		start();
	}

	public static RspHandler getInstance() {
		if (instance == null) {
			instance = new RspHandler();
		}
		return instance;
	}

	public int getWaitingPackets() {
		return packets.size();
	}

	public void clear() {
		responses = new LinkedBlockingQueue<ByteBuffer>();
		packets = new LinkedBlockingQueue<>();
	}
}
