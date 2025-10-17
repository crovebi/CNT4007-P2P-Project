package src.torrent_tracker;
import src.torrent_client.Peer;
import java.util.ArrayList;

public class Tracker {
    ArrayList<Peer> tracker = new ArrayList<>();

    public Tracker() {}

    ArrayList<Peer> getAvailablePeers() {
        return tracker;
    }

    ArrayList<Peer> getNeighboringPeers(Peer peer) {
        ArrayList<Peer> neighbors = new ArrayList<>();
        for (Peer p : tracker) { // filters out given peer from tracker
            if (!p.equals(peer)) {
                neighbors.add(p);
            }
        }
        return neighbors;
    }
}

// Tracker tracker = array
// When Peer Process runs, creates Peer objects and shoves into tracker
// Methods that link Peer objects here
// Request neighbors: return all peers in torrent that except input peer
//
