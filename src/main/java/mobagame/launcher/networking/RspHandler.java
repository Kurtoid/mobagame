package mobagame.launcher.networking;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mobagame.core.networking.packets.Packet;
import mobagame.core.networking.packets.SendRandomDataPacket;

public class RspHandler extends Thread {

	private BlockingQueue<ByteBuffer> responses;
	private BlockingQueue<Packet> packets;

	public synchronized boolean handleResponse(byte[] rsp) {
		System.out.println("handle response");
		responses.add(ByteBuffer.wrap(rsp));
		this.notify();
		return false;
	}

	public synchronized void waitForResponse() {
		System.out.println("waiting for response");
		while (responses.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.print("resp recieved ");
		System.out.println(responses.peek());
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			ByteBuffer b = null;
			try {
				b = responses.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Arrays.toString(b.array()));
			while (b.remaining() > 0) {
				System.out.println(b.toString());
				int len = b.getInt();
				System.out.println(len);
				if (len > 0) {
					ByteBuffer pkt = ByteBuffer.allocate(len);
					pkt.putInt(len);
					byte[] tmp = new byte[len - 4];
					b.get(tmp);
					pkt.put(tmp);
					System.out.println(Arrays.toString(pkt.array()));
					if (Packet.getPacketID(pkt) == Packet.PK_ID_RANDOM_BS_PACKET) {
						System.out.println("Bullshit");
					}
				}
			}
		}
	}

	public Packet getResponse() {
		System.out.println("get response");
		try {
			return packets.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Packet getResponse(Class c) {
		Iterator<Packet> i = packets.iterator();
		while(i.hasNext()) {
			Packet p = i.next();
			if(p.getClass().isInstance(c)) {
				i.remove();
				return p;
			}
		}
		return null;
	}

	public RspHandler() {
		System.out.println("construct RspHandler");
		responses = new LinkedBlockingQueue<ByteBuffer>();
		packets = new LinkedBlockingQueue<>();
	}
}
