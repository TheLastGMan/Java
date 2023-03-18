import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

/**
 * Serves as a type for all figures in the simple
 * drawing program. Some implementation might be added
 * at a future date.
 *
 * @author Brahma Dathan
 * @author Ryan Gau
 * @version 1.1
 */
public abstract class Figure implements Serializable {
	private static final long serialVersionUID = 2432215686705409283L;
	private Color color;
	
	/**
	 * Get the shapes color
	 *
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Set the shapes color
	 *
	 * @param color
	 *            Color
	 */
	public void setColor(Color color) {
		// validate
		this.color = color;
	}
	
	/**
	 * Set the shapes color using the specified Red/Green/Blue values
	 *
	 * @param red
	 *            Red value [0, 255]
	 * @param green
	 *            Green value [0, 255]
	 * @param blue
	 *            Blue value [0, 255]
	 */
	public void setColor(short red, short green, short blue) {
		// validate
		red = (short)Math.min(255, Math.max(0, red));
		green = (short)Math.min(255, Math.max(0, green));
		blue = (short)Math.min(255, Math.max(0, blue));
		
		// create color
		Color c = new Color(red, green, blue);
		setColor(c);
	}
	
	/**
	 * Draws the figure using the given Graphics parameter
	 *
	 * @param graphics
	 *            the Graphics object for drawing the figure
	 */
	public void draw(Graphics graphics) {
		graphics.setColor(color);
		Graphics2D graphic2d = (Graphics2D)graphics;
		Shape shape = loadShape();
		graphic2d.fill(shape);
	}
	
	/**
	 * Load the shape to draw
	 *
	 * @return Shape to draw
	 */
	abstract Shape loadShape();
}
