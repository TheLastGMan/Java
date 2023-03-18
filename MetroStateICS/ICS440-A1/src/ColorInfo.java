/**
 *
 */

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class ColorInfo {
	/**
	 * Parse an integer value to a color type
	 *
	 * @param value
	 *            Value
	 * @return ColorType
	 */
	public static ColorType parseInt(int value) {
		switch (value) {
			case 0:
				return ColorType.Red;
			case 1:
				return ColorType.Brown;
			case 2:
				return ColorType.Yellow;
			case 3:
				return ColorType.Green;
			default:
				return ColorType.Blue;
		}
	}
}
