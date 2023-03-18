package Server;

import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import Core.*;

/**
 * Packet service
 * Listens for packets received and submits it for processing
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ServerPacketListener {
	public double errorPercentage = 0;
	public String hostName = "localhost";
	public int port = 3141;
	public int bufferSizeBytes = 32 * 1024; // 32KB

	// service thread related
	ServiceTask serviceTask = new ServiceTask();
	Thread serviceThread = new Thread(serviceTask);

	/**
	 * Packet received event handler
	 */
	public final BaseEventHandler<ProcessPacketEventArgs> PacketReceivedEventHandler = new BaseEventHandler<>();

	/**
	 * Packet info event handler
	 */
	public final BaseEventHandler<PacketInfoEventArgs> PacketInfoEventHandler = new BaseEventHandler<>();
	
	/**
	 * Starts the service
	 *
	 * @throws Exception
	 */
	public synchronized void start() {
		// stop service just in case
		stop();

		// start service
		Logging.debug("starting packet service");
		serviceThread = new Thread(serviceTask);
		serviceThread.setName("Packet Reader Service");
		serviceThread.start();
	}

	/**
	 *
	 */
	public synchronized void stop() {
		Logging.debug("stopping packet service");

		// signal service to stop
		serviceTask.doRun.set(false);

		// wait for completion
		while (serviceTask.isRunning.get()) {
			try {
				Thread.sleep(100);
			}
			catch (Exception ex) {// do nothing
			}
		}
		serviceThread.interrupt();
	}

	private void sendPacket(DatagramSocket socket, DatagramPacket message, int ackNumber, boolean validChecksum,
			boolean drop) throws Exception {
		// set up return packet
		Packet txPacket = new Packet(validChecksum, (short)8, ackNumber, 0, new byte[] {});
		byte[] packetData = Common.serialize(txPacket);
		DatagramPacket txMessage = new DatagramPacket(packetData, packetData.length, message.getAddress(),
				message.getPort());
		
		// random error percentage
		PacketAckStatus ackStatus = PacketAckStatus.SENT;
		if (drop) {
			ackStatus = PacketAckStatus.DROP;
		}
		else if (drop) {
			ackStatus = PacketAckStatus.ERROR;
		}

		// send packet info
		PacketInfoEventHandler.raiseEvent(
				new PacketInfoEventArgs(this, PacketAction.SENDING, false, ackNumber, PacketCondition.NA, ackStatus));

		if (!drop) {
			// send packet
			Logging.debug("sending ACK: " + ackNumber + " | source port: " + socket.getLocalPort() + " | host: "
					+ message.getAddress().getHostAddress() + " | port: " + message.getPort());
			socket.send(txMessage);
		}
	}

	/**
	 * File Receiver Service
	 *
	 * @author Ryan Gau
	 * @version 1.0
	 */
	private class ServiceTask implements Runnable {
		private AtomicBoolean isRunning = new AtomicBoolean(false);
		private AtomicBoolean doRun = new AtomicBoolean(false);
		private DatagramSocket serviceSocket = null;

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// reset state
			isRunning.set(true);
			doRun.set(true);

			try {
				// setup socket, 10s timeout per request
				serviceSocket = new DatagramSocket(port, InetAddress.getByName(hostName));
				serviceSocket.setSoTimeout(10000);

				// process packets
				while (doRun.get()) {
					// wait for packet
					try {
						DatagramPacket clientPacket = new DatagramPacket(new byte[bufferSizeBytes], bufferSizeBytes);
						serviceSocket.receive(clientPacket);

						// pass off to worker thread
						Thread worker = new Thread(new WorkerTask(serviceSocket, clientPacket));
						worker.setName("Server Worker: " + worker.getName());
						worker.start();
					}
					catch (SocketTimeoutException ste) {
						// no data received, wait for another
						continue;
					}
					catch (Exception ex) {
						// notify of error and wait for another
						// (most likely an invalid packet)
						Logging.error(ex.getMessage());
						ex.printStackTrace();
						continue;
					}
				}
			}
			catch (Exception ex) {
				// hard error, stop
				Logging.error("Service Error: " + ex.getMessage());
				ex.printStackTrace();
			}
			finally {
				serviceSocket.close();
			}

			// mark completed
			isRunning.set(false);
		}
	}

	/**
	 * File Receiver Packet Worker
	 *
	 * @author Ryan Gau
	 * @version 1.0
	 */
	private class WorkerTask implements Runnable {
		private final DatagramSocket serviceSocket;
		private final DatagramPacket clientPacket;

		public WorkerTask(DatagramSocket socket, DatagramPacket packet) {
			serviceSocket = socket;
			clientPacket = packet;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				// parse packet from stream
				Packet pkt = Common.deserialize(clientPacket.getData(), Packet.class);
				Logging.debug("packet received: " + pkt.seqno);

				// process packet
				ProcessPacketEventArgs request = new ProcessPacketEventArgs(this, pkt);
				PacketReceivedEventHandler.raiseEvent(request);

				// simulate network conditions setup
				boolean simDropped = false;
				boolean simCorrupt = false;
				if (Common.checkRandomBelowLevel(errorPercentage)) {
					if (Common.checkRandomBelowLevel(0.5)) {
						simDropped = true;
					}
					else {
						simCorrupt = true;
					}
				}

				// Selective Repeat, Acknowledge sequence number received
				// determine response code
				if (simCorrupt || !pkt.checksumValid()) {
					// no simulated NAC response
					// return error acknowledgement or corrupt or
					// invalid packet length
					// sendPacket(serviceSocket, clientPacket,
					// request.packet.seqno, false, false);
				}
				else if (simDropped) {
					// dropped, do not return an acknowledgement
					Logging.debug("simulating dropped packet: " + pkt.seqno);
					sendPacket(serviceSocket, clientPacket, request.packet.seqno, true, true);
				}
				else {
					// return positive acknowledgement
					sendPacket(serviceSocket, clientPacket, request.packet.seqno, true, false);
				}
			}
			catch (Exception ex) {
				// do nothing, same as dropping the packet
				Logging.error("Service Worker Task Error: " + ex.getMessage());
			}
		}
	}
}
