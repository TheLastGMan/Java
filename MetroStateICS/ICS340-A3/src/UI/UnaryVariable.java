/**
 *
 */
package UI;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class UnaryVariable extends Variable {
	/**
	 * Init Variable
	 *
	 * @param name
	 *            Name
	 */
	public UnaryVariable(String name) {
		super(name);
	}

	/**
	 * Unary variable not backed by an input variable
	 * Always considered as having a value
	 */
	@Override
	public boolean HasEmptyDomain() {
		return false;
	}
}
