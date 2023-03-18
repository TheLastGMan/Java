import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Represents a filled Rectangle when drawn
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class Rectangle extends Figure {
	private static final long serialVersionUID = 3411352556217485112L;
	
	// #region private property backings
	private Point upperLeftPoint;
	private short width;
	private short height;
	// #endregion
	
	/**
	 * Create new instance using two points
	 *
	 * @param point1
	 *            Coordinate 1
	 * @param point2
	 *            Coordinate 2
	 */
	Rectangle(Point point1, Point point2) {
		// 0,0 is upper left
		// find upper point
		Point upperPoint = point1;
		Point lowerPoint = point2;
		if (upperPoint.getYPosition() > lowerPoint.getYPosition()) {
			// swap
			upperPoint = point2;
			lowerPoint = point1;
		}
		
		// check for upper left
		upperLeftPoint = upperPoint;
		Point lowerRightPoint = lowerPoint;
		
		if (upperPoint.getXPosition() > lowerPoint.getXPosition()) {
			// swap x positions
			int tempX = upperLeftPoint.getXPosition();
			upperLeftPoint = new Point(lowerRightPoint.getXPosition(),
					upperLeftPoint.getYPosition());
			lowerRightPoint = new Point(tempX, lowerRightPoint.getYPosition());
		}
		
		// set dimensions
		width = (short)Math.abs(
				lowerRightPoint.getXPosition() - upperLeftPoint.getXPosition());
		height = (short)Math.abs(
				lowerRightPoint.getYPosition() - upperLeftPoint.getYPosition());
	}
	
	/**
	 * Get the upper point of the rectangle
	 *
	 * @return Upper Point
	 */
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	
	/**
	 * Get the width of the Rectangle
	 *
	 * @return Width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the Rectangle
	 *
	 * @return Height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Tells super how to generate this shape
	 */
	@Override
	Shape loadShape() {
		// x, y, width, height
		Shape s = new Rectangle2D.Float(getUpperLeftPoint().getXPosition(),
				getUpperLeftPoint().getYPosition(), getWidth(), getHeight());
		return s;
	}
	
	/**
	 * Overrides toString of super, contains friendly output
	 */
	@Override
	public String toString() {
		//@formatter:off
		return "Rectangle [x=" + getUpperLeftPoint().getXPosition()
				+ ", y=" + getUpperLeftPoint().getYPosition()
				+ ", width=" + getWidth()
				+ ", height=" + getHeight()
				+ ", color=" + getColor().toString()
				+ "]";
		//@formatter:on
	}
}
