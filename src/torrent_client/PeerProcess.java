package src.torrent_client;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class PeerProcess {
    int peerID;
    String hostname;
    int port;
    boolean hasFile;

    public PeerProcess(int peerID){
        this.peerID = peerID;

    }
    private static Map<Integer, Peer> readPeerInfoConfig(String filePath, int chunkCount) {
        Map<Integer, Peer> peers = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 4) continue;

                int peerId = Integer.parseInt(parts[0]);
                String hostName = parts[1];
                int port = Integer.parseInt(parts[2]);
                boolean hasFile = parts[3].equals("1");

                boolean[] hasChunks = new boolean[chunkCount];
                for(boolean b : hasChunks) b = b || hasFile;
                peers.put(peerId, new Peer(peerId, hostName, port, hasChunks));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peers;
    }
}
