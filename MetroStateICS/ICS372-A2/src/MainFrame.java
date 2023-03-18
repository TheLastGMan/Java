import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Entrance to application
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -8424734531682214227L;
	private static final String figuresFullFilePath = "figures";
	
	// #region Controls
	private JPanel contentPane;
	private ButtonGroup shapeGroup = new ButtonGroup();
	private ButtonGroup colorGroup = new ButtonGroup();
	private JRadioButton rdbtnShapeRect;
	private JRadioButton rdbtnShapeCirc;
	private JRadioButton rdbtnColorBlack;
	private JRadioButton rdbtnColorBrown;
	private JRadioButton rdbtnColorRed;
	private JRadioButton rdbtnColorGreen;
	private JRadioButton rdbtnColorBlue;
	private JRadioButton rdbtnColorYellow;
	private JRadioButton rdbtnColorOrange;
	private JRadioButton rdbtnColorPurple;
	private JRadioButton rdbtnColorGrey;
	private JRadioButton rdbtnColorWhite;
	private TextArea textAreaShapes;
	private JPanel panelCanvas;
	private JLabel lblDate;
	// #endregion
	
	// #region Canvas Items
	private Point point1;
	private Point point2;
	private int pointCount;
	// #endregion
	
	// #region private variables used in application
	private IO<Figure> ioFigureAccess = new IO<>();
	private ArrayList<Figure> figures = new ArrayList<>();
	// #endregion
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Create the frame.
	 * Layout generated with help of WindowBuilder
	 */
	public MainFrame() {
		setTitle("Figures GUI (Ryan Gau [ICS-372-A2])");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelContent = new JPanel();
		contentPane.add(panelContent);
		GridBagLayout gbl_panelContent = new GridBagLayout();
		gbl_panelContent.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelContent.rowHeights = new int[] { 0 };
		gbl_panelContent.columnWeights = new double[] { 24.0, 4.0, 2.0 };
		gbl_panelContent.rowWeights = new double[] { 1.0 };
		panelContent.setLayout(gbl_panelContent);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		panelContent.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 30 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		JPanel panelShapes = new JPanel();
		panelShapes.setBorder(new TitledBorder(null, "Shapes",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelShapes = new GridBagConstraints();
		gbc_panelShapes.insets = new Insets(5, 5, 0, 5);
		gbc_panelShapes.fill = GridBagConstraints.BOTH;
		gbc_panelShapes.gridx = 0;
		gbc_panelShapes.gridy = 0;
		panel.add(panelShapes, gbc_panelShapes);
		panelShapes.setLayout(new GridLayout(0, 1, 0, 0));
		
		rdbtnShapeRect = new JRadioButton("Rectangle");
		rdbtnShapeRect.addActionListener(e -> pointCount = 0);
		panelShapes.add(rdbtnShapeRect);
		
		rdbtnShapeCirc = new JRadioButton("Circle");
		rdbtnShapeCirc.addActionListener(e -> pointCount = 0);
		panelShapes.add(rdbtnShapeCirc);
		
		JPanel panelColors = new JPanel();
		panelColors.setBorder(new TitledBorder(null, "Shape Color",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelColors = new GridBagConstraints();
		gbc_panelColors.insets = new Insets(5, 5, 0, 5);
		gbc_panelColors.fill = GridBagConstraints.BOTH;
		gbc_panelColors.gridx = 0;
		gbc_panelColors.gridy = 1;
		panel.add(panelColors, gbc_panelColors);
		panelColors.setLayout(new GridLayout(0, 1, 0, 0));
		
		rdbtnColorBlack = new JRadioButton("Black");
		panelColors.add(rdbtnColorBlack);
		
		rdbtnColorBrown = new JRadioButton("Brown");
		panelColors.add(rdbtnColorBrown);
		
		rdbtnColorRed = new JRadioButton("Red");
		panelColors.add(rdbtnColorRed);
		
		rdbtnColorOrange = new JRadioButton("Orange");
		panelColors.add(rdbtnColorOrange);
		
		rdbtnColorYellow = new JRadioButton("Yellow");
		panelColors.add(rdbtnColorYellow);
		
		rdbtnColorGreen = new JRadioButton("Green");
		panelColors.add(rdbtnColorGreen);
		
		rdbtnColorBlue = new JRadioButton("Blue");
		panelColors.add(rdbtnColorBlue);
		
		rdbtnColorPurple = new JRadioButton("Violet");
		panelColors.add(rdbtnColorPurple);
		
		rdbtnColorGrey = new JRadioButton("Grey");
		panelColors.add(rdbtnColorGrey);
		
		rdbtnColorWhite = new JRadioButton("White");
		panelColors.add(rdbtnColorWhite);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(e -> closeApp());
		btnExit.setMnemonic('e');
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 5, 0, 5);
		gbc_btnExit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 2;
		panel.add(btnExit, gbc_btnExit);
		
		textAreaShapes = new TextArea();
		textAreaShapes.setBackground(Color.LIGHT_GRAY);
		textAreaShapes.setEditable(false);
		GridBagConstraints gbc_textAreaShapes = new GridBagConstraints();
		gbc_textAreaShapes.fill = GridBagConstraints.BOTH;
		gbc_textAreaShapes.gridx = 2;
		gbc_textAreaShapes.gridy = 0;
		panelContent.add(textAreaShapes, gbc_textAreaShapes);
		
		lblDate = new JLabel("[DATE]");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblDate.insets = new Insets(0, 5, 5, 0);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 0;
		panelContent.add(lblDate, gbc_lblDate);
		
		panelCanvas = new JPanel() {
			private static final long serialVersionUID = -6124545499040644886L;
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				// paint figures
				for (Figure f : figures) {
					f.draw(g);
				}
				
				if (true) {
					lblDate.repaint();
				}
				
				// redraw date
				lblDate.repaint();
			}
		};
		panelCanvas.setOpaque(true);
		panelCanvas.setBackground(Color.WHITE);
		panelCanvas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addPoint(e.getX(), e.getY());
			}
		});
		GridBagConstraints gbc_panelCanvas = new GridBagConstraints();
		gbc_panelCanvas.fill = GridBagConstraints.BOTH;
		gbc_panelCanvas.gridx = 0;
		gbc_panelCanvas.gridy = 0;
		panelContent.add(panelCanvas, gbc_panelCanvas);
		
		// end with WindowBuilder, pass off to my items
		mainExtension();
	}
	
	/**
	 * My extension to WindowBuilder generating layout
	 */
	private void mainExtension() {
		// assign groups
		// -colors
		colorGroup.add(rdbtnColorBlack);
		colorGroup.add(rdbtnColorBrown);
		colorGroup.add(rdbtnColorRed);
		colorGroup.add(rdbtnColorOrange);
		colorGroup.add(rdbtnColorYellow);
		colorGroup.add(rdbtnColorGreen);
		colorGroup.add(rdbtnColorBlue);
		colorGroup.add(rdbtnColorPurple);
		colorGroup.add(rdbtnColorGrey);
		colorGroup.add(rdbtnColorWhite);
		
		// -shapes
		shapeGroup.add(rdbtnShapeRect);
		shapeGroup.add(rdbtnShapeCirc);
		
		// set defaults
		rdbtnColorBlack.setSelected(true);
		rdbtnShapeRect.setSelected(true);
		
		// assign date
		String date = new SimpleDateFormat("dd-MMM-yyyy")
				.format(new GregorianCalendar().getTime());
		lblDate.setText(date);
		
		// import
		try {
			// read figures from file
			figures = ioFigureAccess.loadFigures(figuresFullFilePath);
			
			// add descriptions to notification area
			for (Figure f : figures) {
				textAreaShapes.append(f.toString() + System.lineSeparator());
			}
		}
		catch (Exception ex) {
			// error, inform
			JOptionPane.showMessageDialog(null,
					"Could not load shape data: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		}
	}
	
	/**
	 * Save figures and close application
	 */
	private void closeApp() {
		// export
		try {
			// save figures to file
			ioFigureAccess.saveFigures(figuresFullFilePath, figures);
		}
		catch (Exception ex) {
			// error, inform
			JOptionPane.showMessageDialog(null,
					"Could not save shape data: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		}
		
		// close
		dispose();
	}
	
	/**
	 * Adds a given point to the creation of a figure
	 *
	 * @param x
	 *            X Coordinate
	 * @param y
	 *            Y Coordinate
	 */
	private void addPoint(int x, int y) {
		// increase number of points we have
		pointCount += 1;
		
		// apply action based on number of points
		if (pointCount == 1) {
			point1 = new Point(x, y);
		}
		else {
			// create next point and add shape
			point2 = new Point(x, y);
			addFigure(point1, point2);
			
			// reset point count in preparation for next shape
			pointCount = 0;
		}
	}
	
	/**
	 * Adds the selected figure using the specified points
	 *
	 * @param p1
	 *            Point 1
	 * @param p2
	 *            Point 2
	 */
	private void addFigure(Point p1, Point p2) {
		// load figure
		Figure figure = selectedFigure(p1, p2);
		
		// apply color
		Color selColor = selectedColor();
		figure.setColor(selColor);
		
		// add to figure history
		textAreaShapes.append(figure.toString() + System.lineSeparator());
		figures.add(figure);
		
		// redraw
		panelCanvas.repaint();
	}
	
	/**
	 * Create the selected figure using the two clicked points
	 *
	 * @param p1
	 *            Point 1
	 * @param p2
	 *            Point 2
	 * @return Figure
	 */
	private Figure selectedFigure(Point p1, Point p2) {
		if (rdbtnShapeRect.isSelected()) {
			return new Rectangle(p1, p2);
		}
		else {
			return new Circle(p1, p2);
		}
	}
	
	/**
	 * Create the color for the selection
	 *
	 * @return Color
	 */
	private Color selectedColor() {
		if (rdbtnColorBlack.isSelected()) {
			return Color.BLACK;
		}
		else if (rdbtnColorBrown.isSelected()) {
			// brown
			return new Color(165, 64, 5);
		}
		else if (rdbtnColorRed.isSelected()) {
			return Color.RED;
		}
		else if (rdbtnColorOrange.isSelected()) {
			return Color.ORANGE;
		}
		else if (rdbtnColorYellow.isSelected()) {
			return Color.YELLOW;
		}
		else if (rdbtnColorGreen.isSelected()) {
			return Color.GREEN;
		}
		else if (rdbtnColorBlue.isSelected()) {
			return Color.BLUE;
		}
		else if (rdbtnColorPurple.isSelected()) {
			// Violet/Magenta
			return new Color(255, 0, 255);
		}
		else if (rdbtnColorGrey.isSelected()) {
			return Color.GRAY;
		}
		else {
			return Color.WHITE;
		}
	}
}
