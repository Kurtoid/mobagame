package mobagame.server;

import java.nio.channels.SocketChannel;

class ServerDataEvent {
	public ConnectionListener server;
	public SocketChannel socket;
	public byte[] data;
	int connectionID;
	public ServerDataEvent(ConnectionListener server, SocketChannel socket, byte[] data, int connectionID) {
		this.server = server;
		this.socket = socket;
		this.data = data;
		this.connectionID = connectionID;
	}
}