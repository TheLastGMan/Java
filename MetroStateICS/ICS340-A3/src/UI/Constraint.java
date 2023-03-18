/**
 *
 */
package UI;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class Constraint {
	public final int Id;
	public final String Description;
	public final IConstraint DefinedConstraint;
	public final Variable Target;
	public final Variable CheckConstraintSource;
	
	/**
	 * Init constraint
	 * 
	 * @param id
	 *            Id
	 * @param description
	 *            Full description of constraint
	 * @param definedConstraint
	 *            constraint type backing
	 * @param target
	 *            Target variable
	 * @param checkConstraintSource
	 *            check constraint variable
	 */
	public Constraint(int id, String description, IConstraint definedConstraint, Variable target,
			Variable checkConstraintSource) {
		Id = id;
		Description = description;
		DefinedConstraint = definedConstraint;
		Target = target;
		CheckConstraintSource = checkConstraintSource;
	}

	/**
	 * Check constraints and prune domains
	 */
	public boolean CheckConstraints() {
		return DefinedConstraint.execute(Target, CheckConstraintSource);
	}
	
	/**
	 * Does constraint have an empty domain
	 *
	 * @return T/F if empty set found
	 */
	public boolean HasEmptyDomain() {
		return Target.HasEmptyDomain() || CheckConstraintSource.HasEmptyDomain();
	}
}
