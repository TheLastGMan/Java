/**
 *
 */
package UI;

import java.io.File;
import java.util.ArrayList;

import System.Linq.*;

/**
 * Helper Methods for the Knapsack problem
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class KnapsackHelper {

	private class FileFields {
		public static final int ProductName = 0;
		public static final int Value = 1;
		public static final int Weight = 2;
		public static final int Quantity = 3;
		
		public static final int COUNT = 4;
	}

	/**
	 * Parse the input file to a collection of products
	 *
	 * @param file
	 *            Input file
	 * @return QList(Of Product)
	 * @throws Exception
	 *             Error
	 */
	public static QList<Product> readFile(File file) throws Exception {
		java.util.List<Product> products = new ArrayList<>();
		
		int lineNumber = 1;
		try (TextReader reader = new TextReader(file)) {
			// product id
			int id = 1;
			
			String line = "";
			while ((line = reader.readLineString()) != null) {
				// specification, fields split by space
				// any spaces in string fields have
				// been replaced by an underscore
				// {name|value|weight|quantity}
				
				// split line into parts by spaces
				String parts[] = line.split("\\s+");
				
				// validate field count
				if (parts.length != FileFields.COUNT) {
					throw new Exception("Invalid Line, expecting " + Integer.toString(FileFields.COUNT)
							+ " split by space fields, found: " + Integer.toString(parts.length));
				}
				
				// product name
				String prodName = parts[FileFields.ProductName];
				
				// product value
				long prodValue = Long.parseLong(parts[FileFields.Value]);
				
				// product weight
				int prodWeight = Integer.parseInt(parts[FileFields.Weight]);

				// product quantity
				int qty = Integer.parseInt(parts[FileFields.Quantity]);
				lineNumber += 1;
				
				// flatten product view, don't store quantity per item
				// less overhead during solving, don't have to track quantity
				while (qty-- > 0) {
					Product product = new Product(id, prodName, prodValue, prodWeight);
					products.add(product);
				}
				
				// next product id
				id += 1;
			}
		}
		catch (Exception ex) {
			throw new Exception(
					"Unable to read file: Line: " + Integer.toString(lineNumber) + " | Inner Ex: " + ex.getMessage());
		}
		
		return new QList<>(products);
	}

	/**
	 * Writes results to the specified output file
	 *
	 * @param maxWeightUnits
	 *            Max weight of knapsack
	 * @param file
	 *            Output File
	 * @param results
	 *            Solved Results
	 * @throws Exception
	 *             Error
	 */
	public static void writeFile(long maxWeightUnits, File file, java.util.List<SolverResult> results)
			throws Exception {
		try (TextWriter writer = new TextWriter(file, false)) {
			// The maximum weight of the knapsack.
			writer.writeLineLong(maxWeightUnits);
			System.out.println("Max Weight Units: " + Long.toString(maxWeightUnits));

			/*
			 * for each solver:
			 * The total value of the knapsack
			 * The total weight of the knapsack
			 * The set of items chosen
			 * The number of recursive calls in your [recursive] program
			 */
			for (SolverResult solveResult : results) {
				// DEBUG: Log To Console, save looking at a file
				System.out.println("====================");
				System.out.println("Solver: " + solveResult.SolverName);
				System.out.println("Time (ms): " + Long.toString(solveResult.MillisecondsTaken));
				System.out.println("--------------------");

				// write information
				KnapsackResult result = solveResult.Result;
				System.out.println("Total Value Units: " + result.totalValueUnits());
				writer.writeLineLong(result.totalValueUnits());
				System.out.println("Total Weight Units: " + result.totalWeightUnits());
				writer.writeLineLong(result.totalWeightUnits());

				// group by products id so we can show the
				// quantity of each item on a single line
				if (result.products().Any()) {
					for (Grouping<Integer, Product> gp : result.products().GroupBy(f -> f.Id())
							.OrderByDescending(f -> f.Count()).ThenBy(f -> f.Key)) {
						// store product reference and write info line to file
						Product p = gp.FirstOrDefault(); // since we grouped by,
														 // this will always be
														 // set
						String prodInfo = Integer.toString(gp.Count()) + "x " + p.Name() + " @ "
								+ Long.toString(p.UnitValue());
						System.out.println(prodInfo);
						writer.writeLineString(prodInfo);
					}
				}

				System.out.println("Calls: " + Integer.toString(result.recursiveCalls()));
				writer.writeLineInt(result.recursiveCalls());
				System.out.println("<><><><><><><><><><>");
			}
		}
		catch (Exception ex) {
			throw new Exception("Unable to write file: " + ex.getMessage());
		}
	}
	
	/**
	 * Find the name of the file without an extension
	 *
	 * @param name
	 *            String to parse
	 * @return Name without its extension
	 */
	public static String nameWithoutExtension(String name) {
		int pos = name.lastIndexOf(".");
		if (pos != -1) {
			return name.substring(0, pos);
		}
		return name;
	}
}
