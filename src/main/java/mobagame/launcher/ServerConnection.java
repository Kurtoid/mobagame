package mobagame.launcher;

import mobagame.core.networking.packets.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerConnection extends Thread {
    SocketChannel c;
    private BlockingQueue<Packet> packetsToSend;
    boolean running = true;

    public void initConnect(String hostname, int port) throws IOException {
        SocketChannel echoSocket = SocketChannel.open();
        echoSocket.connect(new InetSocketAddress(hostname, port));
    }

    public ServerConnection() {
        packetsToSend = new LinkedBlockingDeque<Packet>();
    }

    public void queuePacket(Packet p) {
        packetsToSend.add(p);
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Starting client-server thread");
        try {
            while (running) {
                Packet p = packetsToSend.take();
                c.write(p.getBytes());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
