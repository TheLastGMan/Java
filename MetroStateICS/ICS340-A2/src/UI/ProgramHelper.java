/**
 *
 */
package UI;

import java.io.File;
import java.util.*;

import SchoolCommon.*;

/**
 * Program helper utilities
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ProgramHelper {
	/**
	 * Parse the specified file into a graph map
	 *
	 * @param matrixFile
	 *            Matrix File
	 *            - {node_name_0}[\t...{node_name_n}]
	 *            {node name}\t{value_0}[\t...{value_n}]
	 * @throws Exception
	 *             Error
	 */
	public static IGraphMap readMatrixFile(File matrixFile, long edgeCostInitialValue) throws Exception {
		// validate
		if (matrixFile == null || !matrixFile.exists()) {
			throw new Exception("Matrix File must exist");
		}
		
		// read file
		try (TextReader tr = new TextReader(matrixFile)) {
			// read all lines of the file, easier to work on the matrix
			List<String[]> lines = new ArrayList<>();
			String line = null;
			while ((line = tr.readLine()) != null) {
				lines.add(line.split("\\t"));
			}
			
			// validate
			if (lines.size() == 0) {
				throw new Exception("No Content");
			}
			else if (lines.size() == 1) {
				throw new Exception("No Edge Costs Found");
			}
			else if (lines.get(0).length <= 1) {
				throw new Exception("No Vertexs found in header: Content: " + String.join(", ", lines.get(0)));
			}
			
			// vertex names
			List<String> toVertexs = new ArrayList<>();
			List<String> fromVertexs = new ArrayList<>();
			
			// create to and from vertexs
			String[] header = lines.get(0);
			for (int x = 1; x < header.length; x++) {
				toVertexs.add(header[x]);
			}
			for (int y = 1; y < lines.size(); y++) {
				fromVertexs.add(lines.get(y)[0]);
			}
			
			// add edge costs to graph
			IGraphMap graph = new VertexAdjacencyMatrix(fromVertexs, toVertexs, edgeCostInitialValue);
			for (int y = 1; y < lines.size(); y++) {
				String[] row = lines.get(y);
				
				// validate row columns
				if (row.length != header.length) {
					throw new Exception("Invalid number of row columns found: " + row.length + " | Expected: "
							+ header.length + " | Line Number: " + y);
				}
				
				// add in edge costs from row
				for (int x = 1; x < row.length; x++) {
					// check for a valid edge cost
					Long edgeCost = Common.tryParseLong(row[x]);
					if (row[x].equals("")) {
						// default empty to initial value, saves some space in
						// writing test files
						edgeCost = edgeCostInitialValue;
					}
					else if (edgeCost == null) {
						throw new Exception("Invalid edge cost: " + row[x] + " | Column Header: " + header[x]
								+ " | Line Number: " + y);
					}
					
					// add edge (rows are from vertex, header is to vertex)
					graph.addEdge(row[0], header[x], edgeCost);
				}
			}
			return graph;
		}
		catch (Exception ex) {
			throw new Exception("Couldn't read Matrix File: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * Parse the specified file into Heuristic information about the matrix
	 *
	 * @param heuristicFile
	 *            Heuristic File {nodeName}\t{value}
	 * @return Dictionary(of String, Long)
	 * @throws Exception
	 *             Error
	 */
	public static HeuristicMap readHeuristicFile(File heuristicFile) throws Exception {
		// validate
		if (heuristicFile == null || !heuristicFile.exists()) {
			throw new Exception("Heuristic File must exist");
		}
		
		// read file
		HeuristicMap heuristics = new HeuristicMap();
		try (TextReader tr = new TextReader(heuristicFile)) {
			String line = null;
			long lineNumber = 1;
			
			// parse each line while we have them
			while ((line = tr.readLine()) != null) {
				String[] parts = line.split("\\t"); // row by tab
				// Expected format: {NodeName}\t{HeuristicValue}
				if (parts.length != 2) {
					throw new Exception("Invalid line: Expected 2 Columns | Found: " + parts.length + " | Line Number: "
							+ lineNumber + " | Content: {" + line + "}");
				}
				
				// load values
				String nodeName = parts[0];
				long value = Long.parseLong(parts[1]);
				
				// check if node already exists
				if (heuristics.ContainsKey(nodeName)) {
					throw new Exception("Duplicate Node: " + nodeName + " | Line: " + lineNumber);
				}
				heuristics.Add(nodeName, value);
				
				// move to next line
				lineNumber += 1;
			}
		}
		catch (Exception ex) {
			throw new Exception("Couldn't read Heuristic File: " + ex.getMessage(), ex);
		}
		return heuristics;
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
