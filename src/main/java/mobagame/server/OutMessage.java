package mobagame.server;

import java.nio.ByteBuffer;

/**
 * represents a message to send to a client
 *
 * @author Kurt Wilson
 */
public class OutMessage {
	public OutMessage(long socketID2, ByteBuffer bytes) {
		socketID = socketID2;
		buff = bytes;
	}

	/**
	 * bytes to be sent to client
	 */
	public ByteBuffer buff;

	/**
	 * socket id of client
	 */
	public long socketID;
}
