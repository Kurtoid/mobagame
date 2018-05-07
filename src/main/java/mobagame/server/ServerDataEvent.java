package mobagame.server;

import java.nio.channels.SocketChannel;

class ServerDataEvent {
	public ConnectionListener2 server;
	public SocketChannel socket;
	public byte[] data;
	
	public ServerDataEvent(ConnectionListener2 server, SocketChannel socket, byte[] data) {
		this.server = server;
		this.socket = socket;
		this.data = data;
	}
}