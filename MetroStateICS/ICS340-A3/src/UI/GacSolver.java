/**
 *
 */
package UI;

import java.io.File;

import SchoolCommon.*;
import System.Collections.Generic.*;
import System.Timers.Stopwatch;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class GacSolver {
	/**
	 * Wrapper to solve the assignment problem
	 *
	 * @param matrixFile
	 *            Matrix File
	 * @param heuristicFile
	 *            Heuristic File
	 * @throws Exception
	 *             Error
	 */
	public static void solveProblem(File variableFile, File domainFile, File constraintFile) throws Exception {
		// read variable file
		List<Variable> variables = ProgramHelper.parseVariableFile(variableFile);
		
		// read domain file
		List<Domain> domain = ProgramHelper.parseDomainFile(domainFile);
		
		// map domain to variables
		for (Variable v : variables) {
			v.Domain.AddRange(domain);
		}
		
		// read constraint file
		List<Constraint> constraints = ProgramHelper.parseConstraintFile(constraintFile, variables);

		// solve
		Stopwatch sw = Stopwatch.startNew();
		String outputFile = Common.fileDirectory(variableFile.getPath()) + "/" + "result.txt";
		ProgramHelper.writeResult(new File(outputFile), "GAC Algorithm Trace");
		Solve(constraints);
		sw.stop();

		// write final results
		Logging.info("----- RESULTS -----");
		for (Variable v : variables) {
			Logging.info(v.DomainOutput());
		}
	}
	
	/**
	 * Solve GAC
	 *
	 * @param constraints
	 *            Collection(Of Constraint) to work against
	 * @throws Exception
	 *             Error
	 */
	public static void Solve(List<Constraint> constraints) throws Exception {
		// validate
		if (constraints == null) {
			throw new Exception("constraints must be specified");
		}

		// set up constraint tracking sets
		Queue<Constraint> activeConstraints = new Queue<>(constraints);
		List<Constraint> processedConstraints = new List<>(constraints.Count());

		// loop until no updates are made from all constraints
		while (activeConstraints.Any()) {
			// load and move constraint to used
			Constraint activeConstraint = activeConstraints.Dequeue();
			processedConstraints.Add(activeConstraint);

			// Log info
			Logging.info("Active Constraint: " + activeConstraint.Description);
			Logging.debug(activeConstraint.Target.DomainOutput() + " "
					+ activeConstraint.DefinedConstraint.getConstraintAction() + " "
					+ activeConstraint.CheckConstraintSource.DomainOutput());
			Logging.info("TDA List {");
			for (Constraint c : activeConstraints) {
				Logging.info("  " + c.Description);
			}
			Logging.info("}");

			// apply constraint
			if (activeConstraint.CheckConstraints()) {
				// show updated domain
				Logging.info("Domain Updated: " + activeConstraint.Target.DomainOutput() + " | "
						+ activeConstraint.CheckConstraintSource.DomainOutput());

				// updates, check for empty sets
				if (activeConstraint.HasEmptyDomain()) {
					// failure
					Logging.info("-------------------------------");
					Logging.info("!!!  Empty Domain Detected  !!!");
					Logging.info("-------------------------------");
					break;
				}
				else {
					// add back in used constraints to check again
					Logging.debug("domain was updated, adding back in processed constraints.");
					processedConstraints.forEach(f -> activeConstraints.Enqueue(f));
					processedConstraints.Clear();
				}
			}
			else {
				Logging.info("No Updates");
			}
		}
	}
}
