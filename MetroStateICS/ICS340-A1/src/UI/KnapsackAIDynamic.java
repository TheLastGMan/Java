/**
 *
 */
package UI;

import java.util.*;

import System.Linq.QList;

/**
 * Knapsack AI, solve using dynamic recursion
 * -----
 * Modified formula from:
 * http://www.geeksforgeeks.org/dynamic-programming-set-10-0-1-knapsack-problem
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class KnapsackAIDynamic implements IKnapsackSolver {
	@Override
	public String Name() {
		return "Dynamic";
	}
	
	@Override
	public KnapsackResult solveProblem(int maxKnapsackWeightUnits, QList<Product> availableProducts) throws Exception {
		// solve
		return solve(maxKnapsackWeightUnits,
				availableProducts.OrderBy(f -> f.UnitWeight()).ThenBy(f -> f.Id()).ToList());
	}

	public KnapsackResult solve(int maxWeight, List<Product> products) throws Exception {
		// table for storage
		KnapsackResult solved[][] = new KnapsackResult[products.size() + 1][maxWeight + 1];
		
		// store recursive calls so we can show how many time we calculated
		// something
		int totalCalculations = 0;
		
		// solve bottom up
		// go through each product
		for (int i = 0; i <= products.size(); i++) {
			// loop through weights
			for (int w = 0; w <= maxWeight; w++) {
				// increase call count
				totalCalculations += 1;

				if (i > 0 && w > 0) {
					// current product (less 1 as 0 can't be solved)
					Product p = products.get(i - 1);
					
					// check if we can add to collection
					if (p.UnitWeight() <= w) {
						// add product to result at weight slot less our weight
						// val[i-1] + solved[i-1][w-weight[i-1]]
						List<Product> prevProds = solved[i - 1][w - p.UnitWeight()].products().ToList();
						prevProds.add(p);
						KnapsackResult newRes = new KnapsackResult(maxWeight, 1, prevProds);

						// update with best result, based on formula
						// max(val from above, solved[i-1][w]
						KnapsackResult comparedTo = solved[i - 1][w];
						solved[i][w] = newRes.totalValueUnits() >= comparedTo.totalValueUnits() ? newRes : comparedTo;
					}
					else {
						// use previous best result
						solved[i][w] = solved[i - 1][w];
					}
				}
				else {
					// base case
					solved[i][w] = new KnapsackResult(maxWeight, totalCalculations, new ArrayList<Product>());
				}
			}
		}
		
		// give best result
		KnapsackResult bestResult = solved[products.size()][maxWeight];
		return new KnapsackResult(maxWeight, totalCalculations, bestResult.products().ToList());
	}
}
