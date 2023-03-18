package Server;

import java.util.*;
import java.util.concurrent.atomic.*;

import Core.*;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class ServerPacketProcessor {
	// buffer of packets
	private LinkedList<Packet> packetBuffer = new LinkedList<>();
	private Object packetSync = new Object();

	// next sequential sequence number to process
	private AtomicInteger nextSeqNumberToProcess = new AtomicInteger(1);

	// worker task related
	private WorkerTask workerTask = new WorkerTask();
	private Thread workerThread = new Thread(workerTask);

	/**
	 * Get the packet buffer
	 *
	 * @return List(Of Packet)
	 */
	public List<Packet> getPacketBuffer() {
		return new ArrayList<>(packetBuffer);
	}

	/**
	 * Next sequential data packet received to process event handler
	 */
	public final BaseEventHandler<ProcessPacketDataEventArgs> ProcessPacketEventHandler = new BaseEventHandler<>();

	/**
	 * Packet info event handler
	 */
	public final BaseEventHandler<PacketInfoEventArgs> PacketInfoEventHandler = new BaseEventHandler<>();

	/**
	 * All packet data received event handler
	 */
	public final BaseEventHandler<CompletedEventArgs> PacketRxCompletedEventHandler = new BaseEventHandler<>();

	/**
	 * Adds a packet to the buffer
	 *
	 * @param pkt
	 *            Packet
	 * @param isDropped
	 *            Was the packet dropped
	 */
	public void addPacket(Packet pkt) {
		Logging.debug("add packet to buffer: " + pkt.toString());
		boolean isDuplicate = false;
		boolean isOutOfSequence = false;

		if (pkt.seqno < nextSeqNumberToProcess.get()) {
			// already processed
			Logging.debug("packet already processed");
			isDuplicate = true;
			isOutOfSequence = true;
		}
		else {
			// check to insert into buffer
			synchronized (packetBuffer) {
				boolean doInsert = true;
				boolean doUpdate = false;
				int index = 0;
				for (Packet pks : packetBuffer) {
					if (pkt.seqno < pks.seqno) {
						break;
					}
					else if (pkt.seqno == pks.seqno) {
						isDuplicate = true;
						doInsert = false;

						// equal, check if new one doesn't have error
						if (pkt.checksumValid()) {
							Logging.debug("Seq: " + pkt.seqno + " | duplicate packet | non corrupted | updating");
							doUpdate = true;
						}
						else {
							Logging.debug("Seq: " + pkt.seqno + " | duplicate packet: no action required");
						}

						break;
					}

					// move to next slot
					index += 1;
				}

				// insert or update
				if (doInsert) {
					Logging.debug("Seq: " + pkt.seqno + " | Inserting");
					packetBuffer.add(index, pkt);
				}
				else if (doUpdate) {
					Logging.debug("Seq: " + pkt.seqno + " | Updating");
					packetBuffer.set(index, pkt);
				}
			}
		}

		// parse packet conditions
		PacketCondition condition = PacketCondition.RECEIVED;
		if (!pkt.checksumValid()) {
			condition = PacketCondition.CORRUPT;
		}
		else if (isOutOfSequence) {
			condition = PacketCondition.OUT_OF_SEQUENCE;
		}

		PacketAckStatus ackStatus = PacketAckStatus.SENT;
		if (!pkt.checksumValid()) {
			ackStatus = PacketAckStatus.ERROR;
		}

		// notify of packet received
		PacketInfoEventHandler.raiseEvent(
				new PacketInfoEventArgs(this, PacketAction.RECEIVED, isDuplicate, pkt.seqno, condition, ackStatus));

		// notify listeners we have another packet in the buffer
		synchronized (packetSync) {
			packetSync.notify();
		}
	}

	/**
	 * start the packet processor
	 */
	public synchronized void start() {
		stop();

		// start service
		Logging.debug("starting server packet processor");
		workerThread = new Thread(workerTask);
		workerThread.setName("Packer Processor Service");
		workerThread.start();
	}

	/**
	 * stop the packer processor
	 */
	public synchronized void stop() {
		Logging.debug("stopping server packet processor");

		// request worker to stop
		workerTask.doRun.set(false);
		synchronized (packetSync) {
			packetSync.notify();
		}

		// wait for completion
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
	 * Process in order packets
	 *
	 * @return T/F if End of Stream
	 * @throws InterruptedException
	 */
	private boolean processPackets() {
		while (true) {
			// load the next valid packet
			Packet pks = null;
			synchronized (packetBuffer) {
				if (!packetBuffer.isEmpty() && packetBuffer.peek().checksumValid()
						&& packetBuffer.peek().seqno == nextSeqNumberToProcess.get()) {
					pks = packetBuffer.poll();
				}
			}

			// check for no action
			if (pks == null) {
				Logging.debug("no valid packets to process");
				return false;
			}

			// process packet
			if (pks.data.length == 0) {
				Logging.debug(pks.seqno + " | no data, end of stream");
				PacketRxCompletedEventHandler.raiseEvent(new CompletedEventArgs(this));
				return true;
			}
			else {
				Logging.debug(pks.seqno + " | processing packet");
				ProcessPacketEventHandler.raiseEvent(new ProcessPacketDataEventArgs(this, pks.data));
			}

			// move to next expected sequence number
			nextSeqNumberToProcess.incrementAndGet();
		}
	}

	private class WorkerTask implements Runnable {
		public AtomicBoolean isRunning = new AtomicBoolean(false);
		public AtomicBoolean doRun = new AtomicBoolean(false);

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// reset state
			Logging.debug("server packet process started");
			nextSeqNumberToProcess = new AtomicInteger(1);
			doRun.set(true);
			isRunning.set(true);

			// continuously check for packets
			try {
				while (doRun.get()) {
					// process packets until the queue is empty
					Logging.debug("check for packets to process");
					if (processPackets()) {
						Logging.debug("worker task: end of stream detected");
						break;
					}
					
					// wait for packet
					Logging.debug("waiting for packet notification");
					synchronized (packetSync) {
						packetSync.wait();
					}
				}
			}
			catch (Exception ex) {
				// hard error, show it
				ex.printStackTrace();
			}
			finally {
				// clear buffer on exit
				// sync as we have a get method on it
				synchronized (packetBuffer) {
					packetBuffer.clear();
				}
				
				// mark completed
				isRunning.set(false);
			}
		}
	}
}
