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
import java.awt.Point;
import java.util.Iterator;

/*
 * The controller orchestrates the drawing program. It receives
 * requests from the user via the view and then transmits them appropriately
 * to the model.
 * 
 */
public class Controller {
	private static Model model;
	private Line line;
	private Label label;
	private static Controller controller;
	private int pointCount;

	/**
	 * For singleton
	 */
	private Controller() {
	}

	/**
	 * Returns the instance of the controller
	 * 
	 * @return the instance
	 */
	public static Controller instance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	/**
	 * Sets the reference to the model
	 * 
	 * @param model
	 *            the model
	 */
	public static void setModel(Model model) {
		Controller.model = model;
	}

	/**
	 * Constructs a line and sends the info to the model.
	 * 
	 */
	public void makeLine() {
		line = new Line();
		pointCount = 0;
		model.addItem(line);
	}

	/**
	 * Stores one of the line endpoints.
	 * 
	 * @param point
	 *            one of the two points
	 */
	public void setLinePoint(Point point) {
		if (++pointCount == 1) {
			line.setPoint1(point);
		} else {
			line.setPoint2(point);
		}
		model.updateView();
	}

	/**
	 * Creates a label and informs the model.
	 * 
	 * @param point
	 *            the start point
	 */
	public void makeLabel(Point point) {
		label = new Label(point);
		model.addItem(label);
	}

	/**
	 * Receives a character and accumulates it. The model is asked to update the
	 * view.
	 * 
	 * @param character
	 *            the typed in character
	 */
	public void addCharacter(char character) {
		label.addCharacter(character);
		model.updateView();
	}

	/**
	 * A command to remove a character. The model will then update the view.
	 * 
	 */
	public void removeCharacter() {
		label.removeCharacter();
		model.updateView();
	}

	/**
	 * Given a point, see if any of the items contains it.
	 * 
	 * @param point
	 *            the point
	 */
	public void selectItem(Point point) {
		Iterator<Item> iterator = model.getItems();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item.includes(point)) {
				model.markSelected(item);
				break;
			}
		}
	}

	/**
	 * Processes the command to delete the selected items.
	 */
	public void deleteItems() {
		model.deleteSelectedItems();
	}

	/**
	 * Processes the command to open a file
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public void openFile(String fileName) {
		model.retrieve(fileName);
	}

	/**
	 * 
	 * Processes the command to close a file
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public void saveFile(String fileName) {
		model.save(fileName);
	}

}
