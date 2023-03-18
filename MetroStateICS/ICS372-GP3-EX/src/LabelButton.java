
/**
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 *            Redistribution and use with or without
 *            modification, are permitted provided that the following conditions
 *            are met:
 *            - the use is for academic purpose only
 *            - Redistributions of source code must retain the above copyright
 *            notice, this list of conditions and the following disclaimer.
 *            - Neither the name of Brahma Dathan or Sarnath Ramnath
 *            may be used to endorse or promote products derived
 *            from this software without specific prior written permission.
 *            The authors do not make any claims regarding the correctness of
 *            the code in this module
 *            and are not responsible for any loss or damage resulting from its
 *            use.
 */
import java.awt.Cursor;
import java.awt.event.*;

import javax.swing.*;

/**
 * The button to put labels. Processes the mouse movements and clicks calling
 * the appropriate methods of controller.
 */
class LabelButton extends JButton implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 4285968908158736240L;
	protected JPanel drawingPanel;
	protected View view;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private LabelCommand labelCommand;
	
	/**
	 * Creates the button for the label
	 *
	 * @param jFrame
	 *            the frame where the label is put
	 * @param jPanel
	 *            the panel within the frame
	 */
	public LabelButton(View jFrame, JPanel jPanel) {
		super("Label");
		keyHandler = new KeyHandler();
		addActionListener(this);
		view = jFrame;
		drawingPanel = jPanel;
		keyHandler = new KeyHandler();
	}
	
	/**
	 * Handle click for creating a new label
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		drawingPanel.addMouseListener(mouseHandler = new MouseHandler());
	}
	
	/**
	 * Handles mouse click so that the text can now be captured.
	 */
	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			view.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			if (labelCommand != null) {
				UndoManager.instance().endCommand(labelCommand);
			}
			labelCommand = new LabelCommand(View.mapPoint(event.getPoint()));
			UndoManager.instance().beginCommand(labelCommand);
			drawingPanel.addFocusListener(keyHandler);
			drawingPanel.requestFocusInWindow();
			drawingPanel.addKeyListener(keyHandler);
		}
	}
	
	/**
	 * Handles characters in the label
	 */
	private class KeyHandler extends KeyAdapter implements FocusListener {
		/**
		 * Handles printable characters
		 *
		 * @param event
		 *            the key event
		 */
		@Override
		public void keyTyped(KeyEvent event) {
			char character = event.getKeyChar();
			if (character >= 32 && character <= 126) {
				labelCommand.addCharacter(event.getKeyChar());
			}
		}
		
		/**
		 * Handles the enter and backspace keys
		 *
		 * @param event
		 *            the key event
		 */
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				drawingPanel.removeMouseListener(mouseHandler);
				drawingPanel.removeKeyListener(keyHandler);
				UndoManager.instance().endCommand(labelCommand);
				view.refresh();
			}
			else if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				labelCommand.removeCharacter();
			}
		}
		
		/**
		 * When the panel gets the focus, starts listening for key strokes.
		 */
		@Override
		public void focusGained(FocusEvent event) {
			drawingPanel.addKeyListener(this);
		}
		
		/**
		 * When the panel loses focus, wraps up the command. Stops listening to
		 * key strokes.
		 */
		@Override
		public void focusLost(FocusEvent event) {
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			drawingPanel.removeMouseListener(mouseHandler);
			UndoManager.instance().endCommand(labelCommand);
			drawingPanel.removeKeyListener(keyHandler);
			UndoManager.instance().endCommand(labelCommand);
			view.refresh();
		}
	}
}
