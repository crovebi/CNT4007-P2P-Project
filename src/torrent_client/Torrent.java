package src.torrent_client;

import java.util.Map;
import src.torrent_tracker.Tracker;

public class Torrent {
    public static void main(String[] args) {
        String config = "src/project_config_file_large/PeerInfo.cfg";
        Tracker tracker = new Tracker();

        Map<Integer, Peer> peers = PeerProcess.readPeerInfoConfig(config, 10);
        if (peers.isEmpty()) {
            System.err.println("No peers loaded! Check your config path: " + config);
            return;
        }

        for (Peer peer : peers.values()) {
            tracker.addPeer(peer);
        }

        Peer p1 = tracker.getPeerByID(1001);
        Peer p2 = tracker.getPeerByID(1002);
        p1.start();
        p2.start();

        try { // give time to start
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        p1.establishConnection(p2.getHostname(), p2.getPort(), "Hello from peer " + p1.getPeerID());
    }
}