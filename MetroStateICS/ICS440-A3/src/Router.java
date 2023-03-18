import java.util.LinkedList;

/**
 * This runnable routes packets as they traverse the network.
 *
 * @author Professor, Ryan Gau
 * @version 1.0
 */
public class Router implements Runnable {
	private LinkedList<Packet> list = new LinkedList<>();
	private int routes[];
	private Router routers[];	// why do we need this here
									// it's a static field in routing
	private int routerNum;
	private boolean end = false;
	
	public Router(int rts[], Router rtrs[], int num) {
		routes = rts;
		routers = rtrs;
		routerNum = num;
	}

	/**
	 * Add a packet to this router. Add some details on how this works
	 *
	 * @param p
	 *            Packet
	 */
	public void addWork(Packet p) {
		// synchronize on the object so only one thread may add at a time
		synchronized (list) {
			list.add(p);
		}
		
		// synchronize on ourself so we own the thread to
		// notify the process there are more packets to process
		synchronized (this) {
			notify();
		}
	}

	/**
	 * indicates to Router that,
	 * once there are no more packets in the network,
	 * it should return
	 */
	public synchronized void end() {
		// mark the end is in sight
		end = true;

		// notify to process packets once more
		notify();
	}

	/**
	 * indicates they're are no more active packets in the pipeline
	 */
	public synchronized void networkEmpty() {
		// notify to process packets once more
		notify();
	}
	
	/**
	 * Process packets. Add some details on how this works.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				// process current queue until it's empty
				processPackets();
				
				// exit if marked to end and no more packets in network
				if (end && routing.getPacketCount() == 0) {
					break;
				}
				
				// synchronize on ourself so we own the thread to
				// wait for next available packet to process
				synchronized (this) {
					this.wait();
				}
			}
		}
		catch (Exception ex) {
			// hard error, show it
			ex.printStackTrace();
		}
	}

	/**
	 * Process the packet queue until it's empty
	 */
	private void processPackets() {
		// process packets until queue is empty
		Packet pkt = null;
		while ((pkt = safeListPoll()) != null) {

			/*
			 * without an exposed packet path
			 * unable to check if already processed this
			 * since provided code doesn't offer this
			 * commenting it out
			 * ---------------------------
			 * // check if we already processed this
			 * if (pkt.getPath().contains(routerNum)) {
			 * // we already processed this
			 * // drop it
			 * routing.decPacketCount();
			 * continue;
			 * }
			 */
			
			// mark packet with this router
			pkt.Record(routerNum);
			
			// determine next hop
			if (pkt.getDestination() == routerNum) {
				// reached destination, remove it from the packet count
				routing.decPacketCount();
			}
			else if (pkt.getDestination() >= routers.length) {
				// destination router unreachable, what do we do?
				// drop packet for safety
				routing.decPacketCount();
			}
			/*
			 * else if (pkt.getPath().size() >= 255) {
			 * // another case, without an exposed hop count
			 * // could get an unsuitable routing path
			 * // since provided code does not exposed it, commenting it out
			 * //---------------------------------------------------------------
			 * // max hop count reached, what do we do?
			 * // drop packet for safety
			 * }
			 */
			else {
				// forward packet to next router
				int nextHop = routes[pkt.getDestination()];
				Router rtr = routers[nextHop];
				rtr.addWork(pkt);
			}
		}
	}
	
	/**
	 * Poll the queue for the next available packet from the queue
	 *
	 * @return Next Packet or NULL if no more remain
	 */
	private Packet safeListPoll() {
		// for safety in case of concurrent calls
		// synchronize removals
		Packet pkt;
		synchronized (list) {
			pkt = list.poll();
		}
		return pkt;
	}
}
