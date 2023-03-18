/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.awt.Graphics;

/**
 * A UI that uses the swing package
 *
 */
public class NewSwingUI implements UIContext {
	private Graphics graphics;
	private static NewSwingUI swingUI;

	/**
	 * For the singleton pattern
	 */
	private NewSwingUI() {
	}

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static NewSwingUI getInstance() {
		if (swingUI == null) {
			swingUI = new NewSwingUI();
		}
		return swingUI;
	}

	/**
	 * The Graphics object for drawing
	 * 
	 * @param graphics
	 *            the Graphics object
	 */
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	/**
	 * Draws a label
	 * 
	 * @param label
	 *            the label
	 */
	@Override
	public void draw(Label label) {
		if (label.getStartingPoint() != null) {
			if (label.getText() != null) {
				graphics.drawString(label.getText(), (int) label
						.getStartingPoint().getX(), (int) label
						.getStartingPoint().getY());
			}
		}
		int length = graphics.getFontMetrics().stringWidth(label.getText());
		graphics.drawString("_",
				(int) label.getStartingPoint().getX() + length, (int) label
						.getStartingPoint().getY());
	}

	/**
	 * Draws a line
	 * 
	 * @param line
	 *            the line to be drawn
	 */
	@Override
	public void draw(Line line) {
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		if (line.getPoint1() != null) {
			i1 = Math.round((float) (line.getPoint1().getX()));
			i2 = Math.round((float) (line.getPoint1().getY()));
			if (line.getPoint2() != null) {
				i3 = Math.round((float) (line.getPoint2().getX()));
				i4 = Math.round((float) (line.getPoint2().getY()));
			} else {
				i3 = i1;
				i4 = i2;
			}
			graphics.drawLine(i1, i2, i3, i4);
		}
	}

	/**
	 * Captures undefined items
	 * 
	 * @param item
	 *            the item
	 */
	@Override
	public void draw(Item item) {
		System.out.println("Cant draw unknown Item \n");
	}
}