/**
 *
 */
package UI;

import java.io.File;
import java.util.Arrays;

import SchoolCommon.*;
import System.Collections.Generic.List;

/**
 * Program helper utilities
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ProgramHelper {
	
	/**
	 * Loaded constraints
	 */
	private static List<IConstraint> DefinedConstraints = new List<>(
			Arrays.asList(new IConstraint[] { new EqualConstraint(), new NotEqualConstraint() }));

	public static List<Variable> parseVariableFile(File variableFile) throws Exception {
		// validate
		if (variableFile == null || !variableFile.exists()) {
			throw new Exception("Variable file must exist");
		}

		// read variables from file
		List<Variable> variables = new List<>();
		try (TextReader rdr = new TextReader(variableFile)) {
			String line = "";
			while ((line = rdr.readLine()) != null) {
				// one variable per line, starts with "v"
				// trim this off, don't need it
				if (!line.toLowerCase().startsWith("v")) {
					throw new Exception("unknown variable format: expecting line starting with 'v': " + line);
				}

				// add to variable list
				variables.Add(new Variable(line));
			}
		}
		catch (Exception ex) {
			throw new Exception("unable to read variable file: " + ex.getMessage());
		}
		return variables;
	}
	
	public static List<Domain> parseDomainFile(File domainFile) throws Exception {
		// validate
		if (domainFile == null || !domainFile.exists()) {
			throw new Exception("Domain file must exist");
		}

		// read domain from file
		List<Domain> domain = new List<>();
		try (TextReader rdr = new TextReader(domainFile)) {
			String line = "";
			while ((line = rdr.readLine()) != null) {
				// one domain per line, starts with "d"
				// trim this off, don't need it
				if (!line.toLowerCase().startsWith("d")) {
					throw new Exception("unknown domain format: expecting line starting with 'd': " + line);
				}

				// add to variable list
				domain.Add(new Domain(line));
			}
		}
		catch (Exception ex) {
			throw new Exception("unable to read domain file: " + ex.getMessage());
		}
		return domain;
	}

	public static List<Constraint> parseConstraintFile(File constriantFile, List<Variable> variables) throws Exception {
		// validate
		if (constriantFile == null || !constriantFile.exists()) {
			throw new Exception("Constraint file must exist");
		}
		if (variables == null) {
			throw new Exception("Variables must be set");
		}
		
		// read constraints from file
		List<Constraint> constraints = new List<>();
		try (TextReader rdr = new TextReader(constriantFile)) {
			String line = "";
			while ((line = rdr.readLine()) != null) {
				String[] parts = line.split("\\s+");
				if (parts.length != 4) {
					throw new Exception("invalid constraint line: " + line);
				}

				// parse id of constraint
				Integer id = Common.tryParseInt(parts[0]);
				if (id == null) {
					throw new Exception("invalid id: " + line);
				}
				
				// variable
				Variable lhand = loadVariable(parts[1], variables);
				Variable rhand = loadVariable(parts[3], variables);

				// load constraint
				IConstraint constraint = DefinedConstraints
						.FirstOrDefault(f -> f.getConstraintAction().equals(parts[2]));
				if (constraint == null) {
					throw new Exception("Unknown constraint type: " + parts[2]);
				}
				
				// put it all together
				Constraint cons = new Constraint(id, line, constraint, lhand, rhand);
				constraints.Add(cons);
			}
		}
		catch (Exception ex) {
			throw new Exception("unable to read constraint file: " + ex.getMessage());
		}
		return constraints;
	}
	
	private static Variable loadVariable(String key, List<Variable> variables) throws Exception {
		if (key.startsWith("d(") && key.endsWith(")")) {
			// domain constraint
			String varName = key.substring(2, key.length() - 1);
			if (!varName.startsWith("v")) {
				throw new Exception("invalid variable: " + varName);
			}
			
			// find variable
			Variable v = variables.FirstOrDefault(f -> f.Name.equals(varName));
			if (v == null) {
				throw new Exception("could not find variable: " + varName);
			}
			return v;
		}
		else if (key.startsWith("d")) {
			// unary constraint
			Variable v = new UnaryVariable("v" + key);
			v.Domain.Add(new Domain(key));
			return v;
		}
		else {
			throw new Exception("unknown domain constraint: " + key);
		}
	}

	private static File activeOutputFile = null;

	/**
	 * Write a new line to the output file
	 *
	 * @param outputFile
	 *            File to save the model to
	 * @param line
	 *            Line to write
	 */
	public static void writeResult(File outputFile, String line) throws Exception {
		if (outputFile == null) {
			throw new Exception("Output File must be specified");
		}

		activeOutputFile = outputFile;
		try (TextWriter tw = new TextWriter(outputFile, true)) {
			tw.writeLine(line);
		}
		catch (Exception ex) {
			throw new Exception("Error while writing file: " + ex.getMessage());
		}
	}

	/**
	 * Append result to the active output file
	 *
	 * @param line
	 *            Line to write
	 * @throws Exception
	 *             Error
	 */
	public static void appendResult(String line) throws Exception {
		if (activeOutputFile == null || !activeOutputFile.exists()) {
			throw new Exception("Output file must exist, please use writeResult first to set the output file");
		}
		
		try (TextWriter tw = new TextWriter(activeOutputFile, false)) {
			tw.writeLine(line);
		}
		catch (Exception ex) {
			throw new Exception("Error while writing file: " + ex.getMessage());
		}
	}
}
