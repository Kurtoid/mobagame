package mobagame.server.tests;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import mobagame.core.networking.packets.LoginPacket;
import mobagame.core.networking.packets.SignupPacket;

public class UserLoginTester {
    public static void main(String[] args) {
        try {
            SocketChannel echoSocket = SocketChannel.open();
            echoSocket.connect(new InetSocketAddress("localhost", 8666));

            LoginPacket p = new LoginPacket("Kurtoid", "Password");
            // echoSocket.write(new InitPacket().getBytes());
            ByteBuffer buff = p.getBytes();
            buff.flip();
            System.out.println(Arrays.toString(buff.array()));

            echoSocket.write(buff);
            SignupPacket sp = new SignupPacket("Kurtoid", "PASS", "Kurt4wilson@gmail.com", (byte) 4, "gdbefhblw");
            buff = sp.getBytes();
            buff.flip();
            System.out.println(Arrays.toString(buff.array()));
            echoSocket.write(buff);
            // c.doLogin("Kurtoid", "Password");
            // c.doSignup("Kurtoid", "PASS", "Kurt4wilson@gmail.com", (byte) 4,
            // "gdbefhblw");
            Thread.sleep(3000);
            echoSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
