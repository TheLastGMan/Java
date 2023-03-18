import java.io.Serializable;

/**
 * Represents an ordered pair of integer x- and y-coordinates that defines a
 * point in a two-dimensional plane.
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class Point implements Serializable {
	private static final long serialVersionUID = 7344053817157027325L;
	
	// #region private property backings
	private int xPosition;
	private int yPosition;
	// #endregion
	
	/**
	 * Create a new point
	 *
	 * @param x
	 *            X Coordinate
	 * @param y
	 *            Y Coordinate
	 */
	Point(int x, int y) {
		xPosition = x;
		yPosition = y;
	}
	
	/**
	 * Get X Position
	 *
	 * @return X
	 */
	public int getXPosition() {
		return xPosition;
	}
	
	/**
	 * Get Y Position
	 *
	 * @return Y
	 */
	public int getYPosition() {
		return yPosition;
	}
}
