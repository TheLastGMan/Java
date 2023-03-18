import java.util.Scanner;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class ConsoleReader {
	/**
	 * Read an integer from the console
	 *
	 * @param inputText
	 * @return
	 */
	public static int readInt(String inputText) {
		Integer result = null;
		String rawInput = "";
		do {
			try {
				rawInput = readLine(inputText);
				result = Integer.valueOf(rawInput);
			}
			catch (Exception e) {
				System.out.println("Invalid integer: " + rawInput);
			}
		} while (result == null);
		return result;
	}
	
	/***
	 * Read an integer within an inclusive range from the console
	 *
	 * @param inputText
	 * @param minInclusive
	 * @param maxInclusive
	 * @return
	 */
	public static int readInt(String inputText, int minInclusive, int maxInclusive) {
		while (true) {
			int value = readInt(inputText);
			
			// check range
			if (value < minInclusive || value > maxInclusive) {
				System.out.println("Value must be between [" + minInclusive + ", " + maxInclusive + "]");
				continue;
			}
			
			// give result
			return value;
		}
	}
	
	/**
	 * Read a line of text from the console
	 *
	 * @param inputText
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readLine(String inputText) {
		System.out.print(inputText + ": ");
		return new Scanner(System.in).nextLine();
	}
}
