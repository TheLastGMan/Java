package Manager;

import java.util.Observable;

/**
 * @author Ryan Gau, Tenzin
 * @version 1.0
 */
public class Timer extends Observable {
	private Thread thread = new Thread(new TimerRunner(), "VehicleTimer");
	private int interval = 1000;

	public Timer() {
	}

	public Timer(int interval) {
		this.interval = interval;
	}

	public void start() {
		// clear the interrupted status we check to stop the timer
		thread.isInterrupted();

		// start the timer if it's not already running
		// and we have a valid time interval
		if (!thread.isAlive() && getInterval() > 0) {
			thread.start();
		}
	}

	public void stop() {
		thread.interrupt();
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int value) {
		// range check
		if (value <= 0) {
			value = 0;
		}

		// apply value
		interval = value;

		// on zero, stop
		if (value == 0) {
			stop();
		}
	}

	private void update() {
		super.setChanged();
		super.notifyObservers();
	}

	/**
	 * Internalized class so outside resource are not able to call the run
	 * method and block the thread they are on
	 *
	 * @author Ryan Gau
	 * @version 1.0
	 */
	private class TimerRunner implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					// sleep
					Thread.sleep(interval);

					// check if we were stopped
					if (Thread.interrupted()) {
						break;
					}

					// notify event
					update();
				}
				catch (Exception ex) {
					// soft error handling
				}
			}
		}
	}
}
