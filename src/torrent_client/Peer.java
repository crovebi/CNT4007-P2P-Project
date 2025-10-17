package src.torrent_client;
import java.util.ArrayList;

public class Peer {
    int peerID;
    String hostname;
    int port;
    boolean[] hasChunks;
    ArrayList<Peer> neighbors;

    public Peer(int peerID, String hostname, int port, boolean[] hasChunks) {
        this.peerID = peerID;
        this.hostname = hostname;
        this.port = port;
        System.arraycopy(hasChunks, 0, this.hasChunks, 0, hasChunks.length);
    }

    void setNeighbors(ArrayList<Peer> neighbors){
        this.neighbors = neighbors;
    }

    ArrayList<boolean[]> getChunkListFromNeighbors(ArrayList<Peer> neighbors) {
        ArrayList<boolean[]> chunkList = new ArrayList<>();
        for (Peer neighbor : neighbors) {
            chunkList.add(neighbor.hasChunks);
        }
        return chunkList;
    }
}

//

// Peers have a peerID, hostname, port number and a boolean to check if they have the file
// Peers store Pieces, each with a unique identifier
// Peers manage their socket connections up to a given limit
// Randomly makes connections

// Tracker sends new list of peers
// Tracker contains unchoking interval, list of peers in torrent
// Returns to current peer

// Peer is going to request each of the neighboring peers for a list of chunks that they
// If they have a different chunk, successfully connect
// Else, looks for new neighbors

// Peer can send chunks, peer can receive chunks
//

// ORDER OF SUCCESS
// 1.) Send file to Peer
// 2.) Send chunks to peer
// 3.) Send chunks to multiple peers

// int[] neighbors = tracker.getNeighbors(Peer);
// Peer.setNeighbors(neighbor)
// boolean[][]
