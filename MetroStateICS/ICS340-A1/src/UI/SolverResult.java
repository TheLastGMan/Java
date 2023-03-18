/**
 *
 */
package UI;

/**
 * Stores result from solved equation
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class SolverResult {
	public final String SolverName;
	public final KnapsackResult Result;
	public final long MillisecondsTaken;
	
	public SolverResult(String name, KnapsackResult result, long milliseconds) {
		SolverName = name;
		Result = result;
		MillisecondsTaken = milliseconds;
	}
}
