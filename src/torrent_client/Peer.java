package src.torrent_client;
import java.util.ArrayList;
<<<<<<< Updated upstream
=======
import java.io.*;
import java.net.*;
import java.util.*;
>>>>>>> Stashed changes

public class Peer {
    int peerID;
    String hostname;
    int port;
    boolean[] hasChunks;
    ArrayList<Peer> neighbors;
    Map<Integer, Socket> sockets = new HashMap<>();
    Socket currentSocket;
    PrintWriter out;

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
<<<<<<< Updated upstream
=======

    // Each peer must have its own server, so we create a it here and call it when needed
    // Use threads so it doesnt take forever
    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) { // Starts listening on port

                while (true) {
                    Socket socket = serverSocket.accept(); // Accept connection if found

                    new Thread(() -> {
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) { // Read in bytes)
                            String message;
                            while ((message = in.readLine()) != null) {
                                System.out.println("Peer " + peerID + " received: " + message);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Each peer must also act as a client to connect other peer servers
    public void establishConnection(int peerConnectionId, String serverPeerHost, int serverPeerPort) {
        new Thread(() -> {
            try { // Connects to other Peer's server
                if (sockets != null && !sockets.containsKey(peerConnectionId)) {
                    currentSocket = new Socket(serverPeerHost, serverPeerPort);
                    sockets.put(peerConnectionId, currentSocket);
                    out = new PrintWriter(currentSocket.getOutputStream(), true);
                } else {currentSocket = sockets.get(peerConnectionId);
                    out = new PrintWriter(currentSocket.getOutputStream(), true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            if (out != null) {
                out.println(message);
                System.out.println("Message: "+ message +" sent ");
            }
        }).start();
    }
>>>>>>> Stashed changes
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
