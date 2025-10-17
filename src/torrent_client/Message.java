package src.torrent_client;

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

    void HaveMessage(int i) {
        this.piece_index = i;
    }

    @Override
    public byte getMessageType() { return HAVE; }

    @Override
    public int getMessageLength() { return 4; }

    @Override
    public byte[] getPayload() { return null; }
}

// other message type classes...