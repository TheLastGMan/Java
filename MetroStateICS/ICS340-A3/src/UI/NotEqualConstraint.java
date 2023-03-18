/**
 *
 */
package UI;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class NotEqualConstraint implements IConstraint {
	/*
	 * (non-Javadoc)
	 * @see UI.IConstraint#execute(UI.Variable, UI.Variable)
	 */
	@Override
	public boolean execute(Variable target, Variable checkConstraintSource) {
		boolean didUpdate = false;
		
		// forward direction
		// if we have one domain, remove from constraint source
		// where domain does not equal ours
		if (target.Domain.Count() == 1) {
			for (int i = checkConstraintSource.Domain.Count() - 1; i >= 0; i--) {
				Domain checkDomain = checkConstraintSource.Domain.Get(i);
				if (checkDomain.Value.equals(target.Domain.Get(0).Value)) {
					checkConstraintSource.Domain.RemoveAt(i);
					didUpdate = true;
				}
			}
		}

		// backward direction
		// if constraint source has one domain
		// remove from target where domain does not equal
		if (checkConstraintSource.Domain.Count() == 1) {
			for (int i = target.Domain.Count() - 1; i >= 0; i--) {
				Domain targetDomain = target.Domain.Get(i);
				if (targetDomain.Value.equals(checkConstraintSource.Domain.Get(0).Value)) {
					target.Domain.RemoveAt(i);
					didUpdate = true;
				}
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
		return "!=";
	}
}
