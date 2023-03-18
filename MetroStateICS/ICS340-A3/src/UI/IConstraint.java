/**
 *
 */
package UI;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public interface IConstraint {
	/**
	 * Execute the constraint algorithm
	 *
	 * @param target
	 *            Variable to prune domain
	 * @param source
	 *            Variable used to check which domain value to prune from target
	 * @return T/F if a Variable's domain was altered
	 */
	boolean execute(Variable target, Variable checkConstraintSource);

	/**
	 * Action of the constraint
	 * 
	 * @return key
	 */
	String getConstraintAction();
}
