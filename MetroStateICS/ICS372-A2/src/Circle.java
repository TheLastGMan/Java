import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Represents a filled Circle when drawn
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class Circle extends Figure {
	private static final long serialVersionUID = 3411352556217485113L;
	
	// #region private property backings
	private int radius;
	private Point center;
	// #endregion
	
	/**
	 * Create new instance of the circle
	 *
	 * @param center
	 *            Center point
	 * @param radius
	 *            Radius
	 */
	Circle(Point centerPoint, Point radiusPoint) {
		// figure out radius c = sqrt(a^2 + b^2) | ^ = to the power of
		int xSquared = (int)Math.pow(
				centerPoint.getXPosition() - radiusPoint.getXPosition(), 2);
		int ySquared = (int)Math.pow(
				centerPoint.getYPosition() - radiusPoint.getYPosition(), 2);
		int cRadius = (int)Math.sqrt(xSquared + ySquared);
		radius = cRadius;
		
		// assign point
		center = centerPoint;
	}
	
	/**
	 * Get the radius of the circle
	 *
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Get the diameter of the circle
	 *
	 * @return Diameter
	 */
	public int getDiameter() {
		return getRadius() * 2;
	}
	
	/**
	 * Get the center point of the circle
	 *
	 * @return Center Point
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Get the upper left hand corner of the circle's bounding box
	 *
	 * @return
	 */
	private Point getUpperLeft() {
		return new Point(getCenter().getXPosition() - getRadius(),
				getCenter().getYPosition() - getRadius());
	}
	
	/**
	 * Tells super how to generate this shape
	 */
	@Override
	Shape loadShape() {
		// x, y, width, height
		Point upperLeftPoint = getUpperLeft();
		Shape s = new Ellipse2D.Float(upperLeftPoint.getXPosition(),
				upperLeftPoint.getYPosition(), getDiameter(), getDiameter());
		return s;
	}
	
	/**
	 * Overrides toString of super, contains friendly output
	 */
	@Override
	public String toString() {
		//@formatter:off
		return "Circle [x=" + getCenter().getXPosition()
				+ ", y=" + getCenter().getYPosition()
				+ ", radius=" + getRadius()
				+ ", color=" + getColor().toString()
				+ "]";
		//@formatter:on
	}
}
