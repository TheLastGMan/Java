/**
 * Thread Statistics
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ThreadStatisticsSetup {
	// app specs, private data must be stored in a queue
	private static Queue<Dictionary<ColorType, Integer>> threadStatistics = new Queue<>();

	/**
	 * app specs: private data storage must be provided by ThreadStatisticsSetup
	 *
	 * @return Statistics Backing
	 */
	public synchronized static Dictionary<ColorType, Integer> RequestStatisticsStorage() {
		// create backing
		Dictionary<ColorType, Integer> backing = new Dictionary<>();
		threadStatistics.enqueue(backing);
		
		// set up initial value so we maintain enum order over all collections
		for (ColorType color : ColorType.values()) {
			backing.Add(color, 0);
		}
		return backing;
	}
	
	/**
	 * Print all results to the screen
	 */
	public static void print() {
		printTabularData();
		printCombinedStatistics();
	}
	
	/**
	 * Adds or updates the specified colors quantity
	 *
	 * @param color
	 *            Color to update
	 * @param quantity
	 *            Quantity to add
	 */
	public static void addOrUpdate(Dictionary<ColorType, Integer> statistics, ColorType color, int quantity) {
		if (!statistics.ContainsKey(color)) {
			// add color with initial value
			statistics.Add(color, quantity);
		}
		else {
			// mandatory try/catch around anything in java
			// that throws an exception, we know the conditions will be met
			// so we don't have to handle the exceptions in any fancy ways
			try {
				statistics.Set(color, statistics.Get(color) + quantity);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Print tabular statistics
	 */
	private static void printTabularData() {
		int tabularId = 0;
		for (Dictionary<ColorType, Integer> statistic : threadStatistics) {
			// move to next tabular id
			tabularId += 1;

			// total number of elements
			double tabulatorSum = 0;
			for (KeyValuePair<ColorType, Integer> kvp : statistic) {
				tabulatorSum += kvp.Value();
			}
			if (tabulatorSum == 0) {
				tabulatorSum = 1;
			}

			// output statistics
			for (KeyValuePair<ColorType, Integer> kvp : statistic) {
				double percentage = kvp.Value() / tabulatorSum * 100;
				System.out.println("Tabulator: " + tabularId + " Count " + kvp.Value() + " for color "
						+ kvp.Key().toString() + " = " + String.format("%.2f", percentage) + "%");
			}
		}
	}
	
	/**
	 * Print combined statistics
	 */
	private static void printCombinedStatistics() {
		// aggregate totals
		Dictionary<ColorType, Integer> combinedResults = combineResults(threadStatistics);

		// total number of elements
		double tabulatorSum = 0;
		for (KeyValuePair<ColorType, Integer> kvp : combinedResults) {
			tabulatorSum += kvp.Value();
		}
		if (tabulatorSum == 0) {
			tabulatorSum = 1;
		}

		// show totals
		System.out.println("==Totals==");
		for (KeyValuePair<ColorType, Integer> kvp : combinedResults) {
			double percentage = kvp.Value() / tabulatorSum * 100;
			System.out.println("Color " + kvp.Key().toString() + " composes " + String.format("%.2f", percentage)
					+ "% of the total");
		}
	}
	
	/**
	 * Combine Results of the processors
	 *
	 * @param processors
	 *            Collection of Processors
	 * @return Combined Results
	 */
	private static Dictionary<ColorType, Integer> combineResults(Iterable<Dictionary<ColorType, Integer>> statistics) {
		Dictionary<ColorType, Integer> result = new Dictionary<>();
		for (Dictionary<ColorType, Integer> statistic : statistics) {
			for (KeyValuePair<ColorType, Integer> kvp : statistic) {
				ThreadStatisticsSetup.addOrUpdate(result, kvp.Key(), kvp.Value());
			}
		}
		return result;
	}
}
