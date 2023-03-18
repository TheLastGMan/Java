/**
 *
 */
package SchoolCommon;

/**
 * Common utilities
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Common {
	/**
	 * Find the name of the file without an extension
	 *
	 * @param name
	 *            String to parse
	 * @return Name without its extension
	 */
	public static String nameWithoutExtension(String name) {
		return nameBeforeToken(name, ".");
	}
	
	/**
	 * Find the name of the file before the specified token
	 *
	 * @param name
	 *            String to parse
	 * @param token
	 *            last index token to find
	 * @return Name before the specified token
	 */
	public static String nameBeforeToken(String name, String token) {
		int pos = name.lastIndexOf(token);
		if (pos != -1) {
			return name.substring(0, pos);
		}
		return name;
	}
	
	/**
	 * Find the directory of the file
	 *
	 * @param name
	 *            String to parse
	 * @return File's Directory
	 */
	public static String fileDirectory(String name) {
		int pos = name.lastIndexOf("/");
		if (pos == -1) {
			pos = name.lastIndexOf("\\");
		}

		if (pos != -1) {
			return name.substring(0, pos);
		}
		return "";
	}
	
	/**
	 * Safely parse a string to a Byte (sbyte/char in c#.net)
	 * (unable to return boolean value since
	 * primitive types can't be passed by reference)
	 *
	 * @param value
	 *            String value to parse
	 * @return Null if unable to parse, value otherwise
	 */
	public static Byte tryParseByte(String value) {
		try {
			return Byte.parseByte(value);
		}
		catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Safely parse a string to a Short
	 * (unable to return boolean value since
	 * primitive types can't be passed by reference)
	 *
	 * @param value
	 *            String value to parse
	 * @return Null if unable to parse, value otherwise
	 */
	public static Short tryParseShort(String value) {
		try {
			return Short.parseShort(value);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Safely parse a string to an integer
	 * (unable to return boolean value since
	 * primitive types can't be passed by reference)
	 *
	 * @param value
	 *            String value to parse
	 * @return Null if unable to parse, value otherwise
	 */
	public static Integer tryParseInt(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Safely parse a string to a long
	 * (unable to return boolean value since
	 * primitive types can't be passed by reference)
	 *
	 * @param value
	 *            String value to parse
	 * @return Null if unable to parse, value otherwise
	 */
	public static Long tryParseLong(String value) {
		try {
			return Long.parseLong(value);
		}
		catch (Exception ex) {
			return null;
		}
	}
}
