/**
 * Processes program requests
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class RequestProcessor implements Runnable {
	private final Queue<Integer> backing;
	private static final ThreadLocal<Dictionary<ColorType, Integer>> statistics = new ThreadLocal<Dictionary<ColorType, Integer>>() {
		@Override
		protected Dictionary<ColorType, Integer> initialValue() {
			return ThreadStatisticsSetup.RequestStatisticsStorage();
		}
	};
	
	public RequestProcessor(Queue<Integer> collection) {
		backing = collection;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			// get next value from the queue
			Integer nextValue = backing.poll();

			// check for a valid value from the queue
			if (nextValue != null) {
				// parse to a color type and add it to the statistics
				ColorType color = ColorInfo.parseInt(nextValue.intValue());
				ThreadStatisticsSetup.addOrUpdate(statistics.get(), color, 1);
			}
			else {
				// queue is empty, exit
				break;
			}
		}
	}
}
