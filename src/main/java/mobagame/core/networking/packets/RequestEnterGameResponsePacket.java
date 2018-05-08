package mobagame.core.networking.packets;

import java.nio.ByteBuffer;

public class RequestEnterGameResponsePacket extends Packet {
    static final int STATUS_FAILED = 1;
    static final int STATUS_ACCEPT = 1;

    int status;
    int gameID = -1;

    @Override
    public ByteBuffer getBytes() {
        int dataSize = PACKET_ID_SIZE + PACKET_SIZE_SIZE + 4 + 4;
        // status, and gameID
        ByteBuffer buff = ByteBuffer.allocate(dataSize);
        setPacketType(buff, PK_ID_PLAYER_REQUEST_ENTER_GAME_REPONSE);
        buff.putInt(status);
        buff.putInt(gameID);
        return buff;
    }

    @Override
    void readData(ByteBuffer buff) {
        buff.rewind();
        status = buff.getInt(5);
        gameID = buff.getInt();
    }

}
