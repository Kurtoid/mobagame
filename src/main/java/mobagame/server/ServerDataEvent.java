package mobagame.server;

import java.nio.channels.SocketChannel;

class ServerDataEvent {
	public ConnectionListener server;
	public SocketChannel socket;
	public byte[] data;
	
	public ServerDataEvent(ConnectionListener server, SocketChannel socket, byte[] data) {
		this.server = server;
		this.socket = socket;
		this.data = data;
	}
}