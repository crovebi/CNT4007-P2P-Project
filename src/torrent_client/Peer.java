package src.torrent_client;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

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
        // Make a copy of the input array to avoid null issues
        if (hasChunks != null) {
            this.hasChunks = new boolean[hasChunks.length];
            System.arraycopy(hasChunks, 0, this.hasChunks, 0, hasChunks.length);
        } else {
            this.hasChunks = new boolean[0];
        }
    }

    public int getPeerID() { return peerID; }
    public String getHostname() { return hostname; }
    public int getPort() { return port; }

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

    // Each peer must have its own server, so we create a it here and call it when needed
    // Use threads so it doesnt take forever
    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) { // Starts listening on port
                Socket socket = serverSocket.accept(); // Accept connection if found
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Read in bytes
                String message = in.readLine(); // convert message to string
                System.out.println("Received: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Each peer must also act as a client to connect other peer servers
    public void establishConnection(String serverPeerHost, int serverPeerPort, String message) {
        new Thread(() -> {
            try (Socket socket = new Socket(serverPeerHost, serverPeerPort)) { // Connects to other Peer's server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Sends message over connection
                out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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
