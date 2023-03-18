package Core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Holds a queue of messages on a time delay
 *
 * @author Ryan
 * @version 1.0
 */
public class MessageQueue {
	private AtomicLong sleepTimeMilliseconds = new AtomicLong(1000);
	private ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private WorkerTask workerTask = new WorkerTask();
	private Thread workerThread = new Thread(workerTask);
	private Object workerSync = new Object();

	public BaseEventHandler<MessageQueueDataEventArgs> DataMessage = new BaseEventHandler<>();

	public MessageQueue() {
		this(1000);
	}

	public MessageQueue(long interval) {
		setSleepTimeMilliseconds(interval);
		workerThread.start();
	}

	/**
	 * Set the sleep time
	 *
	 * @param ms
	 *            Milliseconds to delay between message checks
	 */
	public void setSleepTimeMilliseconds(long ms) {
		Logging.debug("updating message queue delay: " + ms);
		sleepTimeMilliseconds.set(ms);
	}

	/**
	 * Clear any remaining messages
	 */
	public void clear() {
		messageQueue.clear();
	}

	/**
	 * Add a message to the queue,
	 * converts null input to empty string
	 *
	 * @param message
	 *            Message
	 */
	public void addMessage(String message) {
		// do not allow null messages
		if (message == null) {
			message = "";
		}
		
		// add to queue and notify we have data to process
		messageQueue.add(message);
		synchronized (workerSync) {
			workerSync.notify();
		}
	}

	private class WorkerTask implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					while (!messageQueue.isEmpty()) {
						// load next available message
						String data = messageQueue.poll();
						
						// send notification if we have a message
						if (data != null) {
							DataMessage.raiseEvent(new MessageQueueDataEventArgs(this, data));
						}
						
						// delay by amount specified
						try {
							// delay message alerts
							Thread.sleep(sleepTimeMilliseconds.get());
						}
						catch (Exception ex) {
							// do nothing
						}
					}
					
					// nothing to process wait for next message
					synchronized (workerSync) {
						workerSync.wait();
					}
				}
			}
			catch (Exception ex) {
				// hard error, alert
				Logging.error("MessageQueue Error: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
