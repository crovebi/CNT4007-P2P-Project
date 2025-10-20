package src.torrent_client;

import java.nio.ByteBuffer;
import java.util.BitSet;

public abstract class Message {
    public byte CHOKE = 0;
    public byte UNCHOKE = 1;
    public byte INTERESTED = 2;
    public byte NOT_INTERESTED = 3;
    public byte HAVE = 4;
    public byte BITFIELD = 5;
    public byte REQUEST = 6;
    public byte PIECE = 7;

    public abstract byte getMessageType();
    public abstract int getMessageLength();
    public abstract byte[] getPayload();

    public byte[] toByteArray() {
        byte type = getMessageType();
        byte[] payload = getPayload();
        int length = payload == null ? 1 : 1 + payload.length;

        ByteBuffer buffer = ByteBuffer.allocate(length + 4);
        buffer.putInt(length);      // 4-bytes
        buffer.put(type);           // 1-byte

        if (payload != null) {
            buffer.put(payload);    // n-bytes
        }

        return buffer.array();
    }
}

// Zero-payload messages

class ChokeMessage extends Message {
    @Override
    public byte getMessageType() { return CHOKE; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}

class UnchokeMessage extends Message {
    @Override
    public byte getMessageType() { return UNCHOKE; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}

class InterestedMessage extends Message {
    @Override
    public byte getMessageType() { return INTERESTED; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}

class NotInterestedMessage extends Message {
    @Override
    public byte getMessageType() { return NOT_INTERESTED; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}

// Payload messages

class HaveMessage extends Message {
    int piece_index;

    HaveMessage(int piece_index) { this.piece_index = piece_index; }

    int getPieceIndex() { return piece_index; }

    @Override
    public byte getMessageType() { return HAVE; }

    @Override
    public int getMessageLength() { return 5; }     // type + piece index (4-bytes)

    @Override
    public byte[] getPayload() {
//        return ByteBuffer.allocate(4)
        return null;
    }
}

class BitfieldMessage extends Message {
    BitSet bitfield;
    int num_pieces;

    BitfieldMessage(BitSet bitfield, int num_pieces) {
        this.bitfield = bitfield;
        this.num_pieces = num_pieces;
    }

    @Override
    public byte getMessageType() { return BITFIELD; }

    @Override
    public int getMessageLength() { return 1 + getPayload().length; }     // type + bitfield

    @Override
    public byte[] getPayload() {
        int num_bytes = (num_pieces + 7) / 8;   // round to nearest byte
        byte[] payload = new byte[num_bytes];

        // big-endian filling byte array
        for (int i = 0; i < num_pieces; i++) {
            if (bitfield.get(i)) {
                int byte_index = i / 8;
                int bit_index = 7 - (i % 8);   // big endian (high to low)
                payload[byte_index] |= (1 << bit_index);
            }
        }

        return payload;
    }
}

// TODO
class RequestMessage extends Message {
    @Override
    public byte getMessageType() { return REQUEST; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}

// TODO
class PieceMessage extends Message {
    @Override
    public byte getMessageType() { return PIECE; }

    @Override
    public int getMessageLength() { return 1; }

    @Override
    public byte[] getPayload() { return null; }
}









