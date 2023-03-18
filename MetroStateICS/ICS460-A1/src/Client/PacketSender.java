package Client;

import java.net.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.*;

import Core.*;

/**
 * Sends a file across the network
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PacketSender {
	private WorkerTask workerTask = new WorkerTask();
	private Thread workerThread = new Thread(workerTask);
	private final PacketSetupInfo packetInfo;
	private AtomicInteger lowestReceivedSequenceNumber = new AtomicInteger(0);
	private AtomicInteger activeSequenceNumber = new AtomicInteger(0);
	private boolean eofSent = false;
	private Object packetLock = new Object();
	private LinkedList<Integer> receivedSequences = new LinkedList<>();
	public int bufferSizeBytes = 32 * 1024; // 32KB
	
	/**
	 * All packet data sent event handler
	 */
	public final BaseEventHandler<CompletedEventArgs> PacketTxCompletedEventHandler = new BaseEventHandler<>();
	
	/**
	 * Packet message event
	 */
	public final BaseEventHandler<PacketMessageEventArgs> PacketMessageEventHandler = new BaseEventHandler<>();
	
	/**
	 * Setup
	 *
	 * @param packetSetupInfo
	 *            Packet Info
	 */
	public PacketSender(PacketSetupInfo packetSetupInfo) {
		packetInfo = packetSetupInfo;
	}
	
	/**
	 * start sending packets
	 */
	public synchronized void start() {
		stop();
		
		// start service
		Logging.debug("starting packet sender");
		workerThread = new Thread(workerTask);
		workerThread.setName("Packer Processor Service");
		workerThread.start();
	}
	
	/**
	 * stop sending packets
	 */
	public synchronized void stop() {
		Logging.debug("stopping packet sender");
		
		// request worker to stop
		workerTask.doRun.set(false);
		
		// wait for completion
		Logging.debug("waiting for packet sender to stop");
		while (workerTask.isRunning.get()) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				// do nothing
			}
		}
		workerThread.interrupt();
	}
	
	/**
	 * Insert sequence into received sequence numbers
	 *
	 * @param sequenceNumber
	 *            Sequence
	 */
	private void insertIntoWindowSequence(int sequenceNumber) {
		// insert lock object
		int index = 0;
		boolean insert = true;
		for (int value : receivedSequences) {
			if (sequenceNumber > value) {
				index++;
			}
			else if (sequenceNumber == value) {
				// duplicate
				insert = false;
				break;
			}
			else {
				// insert at index
				break;
			}
		}
		
		if (insert) {
			Logging.debug("adding window sequence: " + sequenceNumber + " | Index: " + index);
			receivedSequences.add(index, sequenceNumber);
		}
	}

	/**
	 * Checks if the sequence moves the lower bound of the window
	 *
	 * @param sequenceNumber
	 *            Sequence/ACK Number
	 * @return T/F if window moved
	 */
	private synchronized boolean movesWindow(int sequenceNumber) {
		Logging.debug("checking if sequence moves window: " + sequenceNumber);

		if (sequenceNumber == lowestReceivedSequenceNumber.get() + 1) {
			// trim all next numbers in sequence
			// lock during removal
			synchronized (receivedSequences) {
				while (!receivedSequences.isEmpty() && receivedSequences.peek() == (sequenceNumber + 1)) {
					sequenceNumber = receivedSequences.pop();
				}
			}

			// update to next sequence
			lowestReceivedSequenceNumber.set(sequenceNumber);

			// notify listeners to continue processing
			synchronized (packetLock) {
				packetLock.notifyAll();
			}
			
			Logging.debug("window moved: " + sequenceNumber);
			return true;
		}
		else {
			// check for known duplicate
			if (sequenceNumber < lowestReceivedSequenceNumber.get()) {
				return false;
			}

			// insert
			insertIntoWindowSequence(sequenceNumber);
			
			// inserting, so window didn't move
			return false;
		}
	}

	/**
	 * Worker Task for the packet sender
	 * Created as a runnable so we don't block the GUI
	 *
	 * @author Ryan Gau
	 * @version 1.0
	 */
	private class WorkerTask implements Runnable {
		public AtomicBoolean isRunning = new AtomicBoolean(false);
		public AtomicBoolean doRun = new AtomicBoolean(false);
		
		/**
		 * check if all tasks are completed
		 *
		 * @param pktTask
		 *            collection of PacketTasks
		 * @return T/F is all are completed
		 */
		private boolean allTasksCompleted(PacketTask[] pktTask) {
			for (PacketTask element : pktTask) {
				if (element.isComplete.get() == false) {
					return false;
				}
			}

			return true;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// reset state
			Logging.debug("packet sender process started");
			isRunning.set(true);
			doRun.set(true);
			eofSent = false;
			activeSequenceNumber.set(0);
			lowestReceivedSequenceNumber.set(0);

			// create worker tasks
			PacketTask[] pktTask = new PacketTask[packetInfo.WindowSize];
			Thread[] pktThread = new Thread[packetInfo.WindowSize];

			// continuously check for packets
			try {
				// setup tasks and start them
				Logging.debug("creating packet tasks: " + packetInfo.WindowSize);
				for (int i = 0; i < packetInfo.WindowSize; i++) {
					pktTask[i] = new PacketTask();
					pktThread[i] = new Thread(pktTask[i]);
					pktThread[i].start();
				}

				// wait for all tasks to finish
				while (doRun.get() && !allTasksCompleted(pktTask)) {
					try {
						Thread.sleep(1000);
					}
					catch (Exception ex) {
						// do nothing
					}
				}
			}
			catch (Exception ex) {
				// hard error, show it
				ex.printStackTrace();
			}
			finally {
				// shut down threads
				Logging.debug("shutting down packet tasks");
				for (int i = 0; i < packetInfo.WindowSize; i++) {
					pktTask[i].doRun.set(false);
				}

				// flush the packet buffer
				Logging.debug("flushing packet buffer");
				synchronized (packetLock) {
					packetLock.notifyAll();
				}

				// wait for completion (max 10s)
				Logging.debug("waiting up to 10s for packet tasks to complete");
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
					}
					catch (Exception ex) {
						// do nothing
					}

					// check if all completed
					if (allTasksCompleted(pktTask)) {
						break;
					}
				}

				// kill threads
				Logging.debug("killing packet threads");
				for (int i = 0; i < packetInfo.WindowSize; i++) {
					pktThread[i].interrupt();
				}
				
				// clear received window sequence numbers
				receivedSequences.clear();
			}

			// successful packet and EOF, show end message
			if (eofSent) {
				PacketTxCompletedEventHandler.raiseEvent(new CompletedEventArgs(this));
			}
			
			// mark completed
			doRun.set(false);
			isRunning.set(false);
			Logging.debug("packet sending service complete");
		}
	}
	
	/**
	 * Packet processing
	 * Allow multiple packets to be run at the same time, useful for different
	 * window sizes
	 *
	 * @author Ryan Gau
	 * @version 1.0
	 */
	private class PacketTask implements Runnable {
		public AtomicBoolean doRun = new AtomicBoolean(false);
		public AtomicBoolean isComplete = new AtomicBoolean(false);
		private DatagramSocket socket = null;
		
		/**
		 * Send and receive a packet response
		 *
		 * @param pkt
		 *            Packet to send
		 * @return Received Packet
		 * @throws Exception
		 *             Error
		 */
		private Packet sendReceivePacket(Packet pkt) throws Exception {
			Logging.debug("sendReceivePacket: Seq: " + pkt.seqno);
			
			PacketAction action = PacketAction.SENDING;
			while (true) {
				// check to apply error action
				PacketAckStatus status = PacketAckStatus.SENT;
				boolean simDropped = false;
				if (Common.checkRandomBelowLevel(packetInfo.PacketErrorPercent)) {
					if (Common.checkRandomBelowLevel(0.5)) {
						// drop packet
						Logging.debug("dropping packet: seq: " + pkt.seqno);
						status = PacketAckStatus.DROP;
						simDropped = true;
						continue;
					}
					else {
						// corrupt packet
						Logging.debug("corrupting packet: seq: " + pkt.seqno);
						pkt = new Packet(false, pkt.len, pkt.ackno, pkt.seqno, pkt.data);
						status = PacketAckStatus.ERROR;
					}
				}
				else {
					// default packet info
					pkt = new Packet(true, pkt.len, pkt.ackno, pkt.seqno, pkt.data);
				}
				
				// setup network
				try {
					// create a DatagramPacket
					byte[] pktData = Common.serialize(pkt);
					DatagramPacket dpkt = new DatagramPacket(pktData, pktData.length,
							InetAddress.getByName(packetInfo.Hostname), packetInfo.Port);
					
					// send packet
					Logging.debug("sending packet: seq: " + pkt.seqno + " | source port: " + socket.getLocalPort()
							+ " | host: " + dpkt.getAddress().getHostAddress() + " | port: " + dpkt.getPort());
					
					DatagramPacket responsePacket = new DatagramPacket(new byte[bufferSizeBytes], bufferSizeBytes);
					PacketMessageEventHandler
							.raiseEvent(new PacketMessageEventArgs(this, action, PacketStatus.NA, status, pkt));
					
					// if we don't simulate a dropped packed
					// send it
					if (!simDropped) {
						socket.send(dpkt);
					}
					
					// wait for non duplicate ACK response
					Packet response = null;
					while (true) {
						// receive packet
						socket.receive(responsePacket);
						response = Common.deserialize(responsePacket.getData(), Packet.class);
						Logging.debug("packet received: ACK: " + response.ackno);
						
						// if we match, it's not a duplicate, let it process
						if (pkt.seqno == response.ackno) {
							break;
						}
						
						// duplicate
						Logging.debug("packet received: ACK: " + response.ackno + " | Expected Seq: " + pkt.seqno
								+ " | receive again");
						PacketMessageEventHandler.raiseEvent(new PacketMessageEventArgs(this, PacketAction.ACK_RECEIVED,
								PacketStatus.DUPLICATE, PacketAckStatus.NA, pkt));
					}
					return response;
				}
				catch (SocketTimeoutException ste) {
					// no data received, send it again
					Logging.debug("packet timeout: seq: " + pkt.seqno);
					PacketMessageEventHandler.raiseEvent(new PacketMessageEventArgs(this, PacketAction.TIMEOUT,
							PacketStatus.NA, PacketAckStatus.SENT, pkt));
					action = PacketAction.RESENDING;
					continue;
				}
				catch (Exception ex) {
					// notify of error and wait for another
					// (most likely an invalid packet)
					// so, send it again
					Logging.error(ex.getMessage());
					ex.printStackTrace();
					continue;
				}
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				// save ports by binding once
				doRun.set(true);
				socket = new DatagramSocket(0);
				socket.setSoTimeout(packetInfo.DelayMs);
				
				// send packets
				while (doRun.get()) {
					// load next packet data
					// synchronize so we can associate the
					// next data with the next sequence number
					int nextSequenceNumber = 0;
					byte[] data = new byte[] {};
					synchronized (activeSequenceNumber) {
						data = packetInfo.Reader.readBytes(packetInfo.MessageSizeBytes);
						nextSequenceNumber = activeSequenceNumber.incrementAndGet();
						
						// check exit condition
						if (data.length == 0) {
							if (eofSent) {
								isComplete.set(true);
								break;
							}
							eofSent = true;
						}
					}
					Logging.debug("loaded next seq: " + nextSequenceNumber);
					
					// Keep within window of lowest received sequence number
					while (nextSequenceNumber >= (lowestReceivedSequenceNumber.get() + packetInfo.WindowSize)) {
						// outside of bounds, wait for new window area
						synchronized (packetLock) {
							packetLock.wait();
						}
					}
					
					// send data (length + 12 [header])
					Packet pkt = new Packet(true, (short)(data.length + 12), nextSequenceNumber, nextSequenceNumber,
							data);
					
					// handle packet transport
					// keep sending until no longer corrupt
					// or no longer requested to run
					while (doRun.get()) {
						// send packet and wait for result
						Packet pktResp = sendReceivePacket(pkt);
						
						// determine state
						boolean corrupted = !pktResp.checksumValid();
						boolean movesWindow = movesWindow(pktResp.ackno);
						
						// pick correct status
						PacketStatus status = PacketStatus.RECEIVED;
						if (corrupted) {
							status = PacketStatus.ERROR;
						}
						else if (movesWindow) {
							status = PacketStatus.MOVE_WINDOW;
						}
						
						// show message
						PacketMessageEventHandler.raiseEvent(new PacketMessageEventArgs(this, PacketAction.ACK_RECEIVED,
								status, PacketAckStatus.SENT, pkt));
						
						// only continue if it's not corrupted
						if (!corrupted) {
							break;
						}
					}
				}
			}
			catch (Exception ex) {
				// hard error, stop
				ex.printStackTrace();
			}
			finally {
				// close socket
				socket.close();
				receivedSequences.clear();
				isComplete.set(true);
			}
		}
	}
}
