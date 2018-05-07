package mobagame.launcher.networking;

import java.nio.ByteBuffer;

public class RspHandler {

	private byte[] rsp = null;
	boolean responseready = false;

	public synchronized boolean handleResponse(byte[] rsp) {
		this.rsp = rsp;
		this.notify();
		return true;
	}

	public synchronized void waitForResponse() {
		while (this.rsp == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		System.out.println(new String(this.rsp));
		responseready = true;
	}

	public ByteBuffer getResponse() {
		if (responseready)
			return ByteBuffer.wrap(rsp);
		else {
			return null;
		}
	}

}
