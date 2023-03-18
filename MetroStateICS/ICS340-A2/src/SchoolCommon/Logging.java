/**
 *
 */
package SchoolCommon;

/**
 * @author rgau1
 * @version 1.0
 */
public class Logging {
	public static void debug(String message) {
		write("DEBUG: " + message, false);
	}

	public static void info(String message) {
		write("INFO: " + message, false);
	}
	
	public static void warning(String message) {
		write("WARN: " + message, false);
	}

	public static void error(String message) {
		write(message, true);
	}
	
	private static void write(String message, boolean isError) {
		if (isError) {
			System.err.println(message);
		}
		else {
			System.out.println(message);
		}
	}
}
