/**
 *
 */
package UI;

import java.util.List;

import System.Linq.QList;

/**
 * Knapsack Result Model
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class KnapsackResult {
	private int _maxWeightUnits;
	private long _totalValueUnits;
	private int _totalWeightUnits;
	private int _recursiveCalls;
	private QList<Product> _products;

	public KnapsackResult(int maxWeightUnits, int recursiveCalls, List<Product> products) throws Exception {
		// validate
		if (products == null) {
			throw new Exception("products must be specified");
		}

		// set fields
		_maxWeightUnits = maxWeightUnits;
		_recursiveCalls = recursiveCalls;
		_products = new QList<>(products);
		calculate();
	}

	private void calculate() {
		// calculate sums for related fields
		_products.ForEach(p -> {
			_totalValueUnits += p.UnitValue();
			_totalWeightUnits += p.UnitWeight();
		});
	}

	/**
	 * Max Weight of Knapsack
	 *
	 * @return Int
	 */
	public int maxWeight() {
		return _maxWeightUnits;
	}

	/**
	 * Total Value of Knapsack in Units
	 *
	 * @return Long
	 */
	public long totalValueUnits() {
		return _totalValueUnits;
	}

	/**
	 * Total Weight of Knapsack in Units
	 *
	 * @return Int
	 */
	public int totalWeightUnits() {
		return _totalWeightUnits;
	}

	/**
	 * Number of Recursive Calls made
	 *
	 * @return Int
	 */
	public int recursiveCalls() {
		return _recursiveCalls;
	}
	
	/**
	 * Collection of products in Knapsack
	 *
	 * @return List(Of Product)
	 */
	public QList<Product> products() {
		return _products;
	}
}
