package mobagame.core.networking.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class Packet {
    public abstract ByteBuffer getBytes();

    public static int MAX_USERNAME_LENGTH = 16;
    public static int MAX_PASSWORD_LENGTH = 64;
    public static byte PK_ID_INIT = 0x01;
    public static byte PK_ID_AUTH_LOGIN = 0x02;
    public static int MAX_EMAIL_LENGTH = 254;
    public static int MAX_SECURITY_QUESTION_SIZE = 254;

    public static byte PK_ID_AUTH_SIGNUP = 0x03;

    public static int BYTES_PER_CHARACTER = 1;
    public static int PACKET_ID_SIZE = 1;
    public static int PACKET_SIZE_SIZE = 4;

    abstract void readData(ByteBuffer buff);

    public static byte getPacketID(ByteBuffer bf) {
        return bf.get(4);
    }

    public int getNumBytes(int n) {
        return n * BYTES_PER_CHARACTER;
    }

    protected String getStringFromBuffer(ByteBuffer bf, int position, int length) {
        byte[] bytes = new byte[length];
        bf.position(position);
        bf.get(bytes);
        String v = new String(bytes, StandardCharsets.UTF_8);
        return v.trim();
    }

    protected void setPacketType(ByteBuffer bf, byte packet_type) {
        bf.position(4);
        bf.put(packet_type);
    }

    protected void setString(ByteBuffer bf, String s, int position, int length) {

        if (s.length() > length) {
            s = s.substring(0, length);
        }

        byte[] textBytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] filledBytes = new byte[length];
        System.arraycopy(textBytes, 0, filledBytes, 0, textBytes.length);
        // System.out.println(Arrays.toString(filledBytes));
        bf.position(position);
        bf.put(filledBytes);

    }

}
