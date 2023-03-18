/**
 *
 */
package UI;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class EqualConstraint implements IConstraint {
	/*
	 * (non-Javadoc)
	 * @see UI.IConstraint#execute(UI.Variable, UI.Variable)
	 */
	@Override
	public boolean execute(Variable target, Variable checkConstraintSource) {
		boolean didUpdate = false;
		
		// forward direction, go through target domain
		for (int i = target.Domain.Count() - 1; i >= 0; i--) {
			// local domain
			Domain targetDomain = target.Domain.Get(i);
			
			// if no matching equals constraint in the source against
			// checkConstraint, we don't equal any of them
			// remove domain from target
			if (!checkConstraintSource.Domain.Any(f -> f.Value.equals(targetDomain.Value))) {
				target.Domain.RemoveAt(i);
				didUpdate = true;
			}
		}
		
		// backward direction, go through check domain
		for (int i = checkConstraintSource.Domain.Count() - 1; i >= 0; i--) {
			// check domain
			Domain checkDomain = checkConstraintSource.Domain.Get(i);
			
			// if no matching equals constraint in the source against
			// checkConstraint, we don't equal any of them
			// remove domain from constraint source
			if (!target.Domain.Any(f -> f.Value.equals(checkDomain.Value))) {
				checkConstraintSource.Domain.RemoveAt(i);
				didUpdate = true;
			}
		}

		return didUpdate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see UI.IConstraint#getConstraintAction()
	 */
	@Override
	public String getConstraintAction() {
		return "=";
	}
}
