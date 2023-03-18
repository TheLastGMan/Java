/**
 *
 */
package UI;

import System.Collections.Generic.List;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class Variable {
	public final String Name;
	public final List<Domain> Domain = new List<>();

	/**
	 * Init Variable
	 * 
	 * @param name
	 *            Name
	 */
	public Variable(String name) {
		Name = name;
	}
	
	/**
	 * Does variable have an empty domain
	 * 
	 * @return T/F if empty
	 */
	public boolean HasEmptyDomain() {
		return !Domain.Any();
	}

	/**
	 * Friendly output of variable with domain values
	 * 
	 * @return String
	 */
	public String DomainOutput() {
		return Name + " {" + String.join(", ", Domain.Select(f -> f.Value)) + "}";
	}
}
