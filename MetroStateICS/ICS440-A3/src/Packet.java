import java.util.*;

/**
 * Contain a packet that traverses the network
 *
 * @author Professor
 * @version 1.0
 */
public class Packet {
	// The final router
	private int destination;
	// The origin router
	private int source;
	// The path this packet takes
	private LinkedList<Integer> path = new LinkedList<>();
	
	/**
	 * Instantiate a packet, given source and destination
	 *
	 * @param s
	 * @param d
	 */
	Packet(int s, int d) {
		source = s;
		destination = d;
	}
	
	/**
	 * Added for ease of debugging
	 * Ryan Gau
	 *
	 * @return Collection of router hops
	 */
	public List<Integer> getPath() {
		return path;
	}
	
	/**
	 * Return the packet's source.
	 *
	 * @return
	 */
	int getSource() {
		return source;
	}
	
	/**
	 * Return the packet's destination.
	 *
	 * @return
	 */
	int getDestination() {
		return destination;
	}
	
	/**
	 * Record the current location as the packet traverses the network.
	 *
	 * @param router
	 */
	void Record(int router) {
		path.add(router);
	}
	
	/**
	 * Print the route the packet took through the network.
	 */
	void Print() {
		System.out.println("Packet source=" + source + " destination=" + destination);
		System.out.print("    path: ");
		for (int i = 0; i < path.size(); i++) {
			System.out.print(" " + path.get(i));
		}
		System.out.println();
	}
}
