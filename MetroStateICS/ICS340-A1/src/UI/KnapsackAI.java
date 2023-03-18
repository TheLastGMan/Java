/**
 *
 */
package UI;

import java.util.*;

import System.Linq.QList;

/**
 * Knapsack AI, tries to solve using full recursion
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class KnapsackAI implements IKnapsackSolver {
	@Override
	public String Name() {
		return "Recursive";
	}
	
	@Override
	public KnapsackResult solveProblem(int maxKnapsackWeightUnits, QList<Product> availableProducts) throws Exception {
		// setup
		IntermediateResult initialResult = new IntermediateResult();

		// solve, order products by their weight to speed up time to solve
		IntermediateResult bestKnapsack = solve(maxKnapsackWeightUnits,
				availableProducts.OrderBy(f -> f.UnitWeight()).ThenBy(f -> f.Id()).ToList(), initialResult, true, 0);

		// map to result and give
		KnapsackResult result = new KnapsackResult(maxKnapsackWeightUnits, totalRecursiveCalls,
				bestKnapsack.SelectedProducts);
		return result;
	}
	
	// solve intermediate problem and return number of recursive calls
	private int totalRecursiveCalls = 0;
	
	/**
	 * Recursively solves problem
	 *
	 * @param maxWeightUnits
	 *            Maximum Weight (Units)
	 * @param availableProducts
	 *            Collection of products we may use
	 * @param midSolve
	 *            Intermediate Result used to track changes
	 * @param rootLevel
	 *            If we are root, we ignore weight checks and same products
	 * @param baseIndex
	 *            Base index is used so we don't double check previous products
	 *            as prior searches have already looked ahead to us,
	 *            saves us from duplicate results
	 * @return IntermediateResult
	 */
	private IntermediateResult solve(int maxWeightUnits, List<Product> availableProducts, IntermediateResult midSolve,
			boolean rootLevel, int baseIndex) {
		// temp best result
		IntermediateResult bestResult = midSolve;
		totalRecursiveCalls += 1;

		// we start at i = max, so we can remove it from available products
		// an then can add it back in at the end
		for (int i = baseIndex; i < availableProducts.size(); i++) {
			// store local reference to product
			Product p = availableProducts.get(i);

			// for first level, skip same product id's, already checked
			if (rootLevel && i > 0 && p.Id() == availableProducts.get(i - 1).Id()) {
				continue;
			}
			
			// weight check if not root level, as we want to look at all options
			long newWeight = midSolve.CumulativeWeightUnits + p.UnitWeight();
			if (newWeight > maxWeightUnits || newWeight < midSolve.CumulativeWeightUnits) {
				/*
				 * we are overweight or an overflow occurred
				 * since we know the products are ordered by weight ASC
				 * this failure will hold true for all future products
				 * allowing us to exit here and save time
				 */
				break;
			}

			// setup info for next iteration
			availableProducts.remove(i);
			IntermediateResult iterInfo = new IntermediateResult();
			iterInfo.SelectedProducts.addAll(midSolve.SelectedProducts);
			iterInfo.SelectedProducts.add(p);
			iterInfo.CumulativeValueUnits = midSolve.CumulativeValueUnits + p.UnitValue();
			iterInfo.CumulativeWeightUnits = newWeight;

			// solve
			IntermediateResult iterationResult = solve(maxWeightUnits, availableProducts, iterInfo, false, i);

			// restore available products to original state
			availableProducts.add(i, p);

			// compare iteration result to our best result
			if (iterationResult.compareTo(bestResult) > 0) {
				bestResult = iterationResult;
			}
		}

		// give best result
		return bestResult;
	}
	
	private class IntermediateResult implements Comparable<IntermediateResult> {
		public List<Product> SelectedProducts = new ArrayList<>();
		public long CumulativeValueUnits;
		public long CumulativeWeightUnits;
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(IntermediateResult o) {
			long localUnits = CumulativeValueUnits;
			long otherUnits = o.CumulativeValueUnits;
			
			if (localUnits < otherUnits) {
				return -1;
			}
			// precedence to high units or higher depth
			else if (localUnits > otherUnits) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
	}
}
