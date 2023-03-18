/**
 *
 */
package UI;

import System.Linq.QList;

/**
 * Knapsack Solver Interface
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface IKnapsackSolver {
	/**
	 * Name of solver
	 *
	 * @return String
	 */
	String Name();
	
	/**
	 * Solves the problem using the specified inputs
	 *
	 * @param maxKnapsackWeightUnits
	 *            Max Weight Units of Knapsack
	 * @param availableProducts
	 *            Collection of available products
	 * @return KansackResult Result
	 */
	KnapsackResult solveProblem(int maxKnapsackWeightUnits, QList<Product> availableProducts) throws Exception;
}
