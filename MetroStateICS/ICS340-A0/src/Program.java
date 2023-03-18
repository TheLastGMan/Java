import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class Program extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 4959131182449781932L;
	private JPanel contentPane;
	JTextPane textPaneFilePath;
	private JFileChooser browser = new JFileChooser("./");
	private JLabel lblMaxValue;
	private JLabel lblMinValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Program() {
		setTitle("ICS340 - Ryan Gau - Program 0");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSourceFile = new JLabel("Source File");
		lblSourceFile.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblSourceFile = new GridBagConstraints();
		gbc_lblSourceFile.insets = new Insets(0, 0, 5, 0);
		gbc_lblSourceFile.gridx = 0;
		gbc_lblSourceFile.gridy = 0;
		contentPane.add(lblSourceFile, gbc_lblSourceFile);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 100 };
		gbl_panel.rowHeights = new int[] { 30, 30 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		Button buttonBrowse = new Button("Browse");
		buttonBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (browser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						if (browser.getSelectedFile().exists()) {
							textPaneFilePath.setText(browser.getSelectedFile().getPath());
						}
					}
					catch (Exception ex) {
						System.err.println("Browse Error: " + ex.getMessage());
					}
				}
			}
		});
		GridBagConstraints gbc_buttonBrowse = new GridBagConstraints();
		gbc_buttonBrowse.insets = new Insets(0, 0, 5, 0);
		gbc_buttonBrowse.gridx = 0;
		gbc_buttonBrowse.gridy = 1;
		panel.add(buttonBrowse, gbc_buttonBrowse);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 429, 0 };
		gbl_panel_2.rowHeights = new int[] { 16, 23, 16, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);
		
		lblMinValue = new JLabel("");
		GridBagConstraints gbc_lblMinValue = new GridBagConstraints();
		gbc_lblMinValue.anchor = GridBagConstraints.NORTH;
		gbc_lblMinValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMinValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblMinValue.gridx = 0;
		gbc_lblMinValue.gridy = 1;
		panel_2.add(lblMinValue, gbc_lblMinValue);
		
		textPaneFilePath = new JTextPane();
		textPaneFilePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPaneFilePath.setEditable(false);
		GridBagConstraints gbc_textPaneFilePath = new GridBagConstraints();
		gbc_textPaneFilePath.anchor = GridBagConstraints.NORTH;
		gbc_textPaneFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPaneFilePath.insets = new Insets(0, 0, 5, 0);
		gbc_textPaneFilePath.gridx = 0;
		gbc_textPaneFilePath.gridy = 0;
		panel_2.add(textPaneFilePath, gbc_textPaneFilePath);

		lblMaxValue = new JLabel("");
		GridBagConstraints gbc_lblMaxValue = new GridBagConstraints();
		gbc_lblMaxValue.anchor = GridBagConstraints.NORTH;
		gbc_lblMaxValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMaxValue.gridx = 0;
		gbc_lblMaxValue.gridy = 2;
		panel_2.add(lblMaxValue, gbc_lblMaxValue);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPane.add(panel_1, gbc_panel_1);
		
		Button buttonFindMinMax = new Button("Find Min/Max values");
		buttonFindMinMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textPaneFilePath.getText().length() > 0) {
					processFile(new File(textPaneFilePath.getText()));
				}
			}
		});
		panel_1.add(buttonFindMinMax);
	}

	/**
	 * Read the specified file and find min/max values, write to new file
	 *
	 * @param fullFilePath
	 *            File to read
	 */
	private void processFile(File fullFilePath) {
		/*
		 * 2. Reads the selected text file. The text file for my test will
		 * consist
		 * of a number of rows, each containing a single integer,
		 * small enough to fit into a long.
		 */
		// important bit
		long min = 0;
		long max = 0;

		// read file
		BufferedReader br = null;
		try {
			// setup
			boolean firstLine = true;
			long lineNumber = 1;
			br = new BufferedReader(new FileReader(fullFilePath));
			String line = "";
			
			// read line by line so we don't have to store entire file in memory
			while ((line = br.readLine()) != null) {
				Long value = tryParseLong(line);
				if (value != null) {
					if (firstLine) {
						// first line, assign both min/max values
						firstLine = false;
						min = value;
						max = value;
					}
					else if (value > max) {
						max = value;
					}
					else if (value < min) {
						min = value;
					}
				}
				else {
					// parse error
					System.err.println("Unable to parse: " + line + " to long: Line: " + Long.toString(lineNumber));
				}
				
				// end line
				lineNumber += 1;
			}
		}
		catch (Exception ex) {
			// read error
			logError("File Read Error: " + fullFilePath + " | Ex: " + ex.getMessage());
			return;
		}
		finally {
			try {
				br.close();
			}
			catch (IOException e) {
			}
		}
		
		/*
		 * 3. Writes the maximum integer on the first line of the output file
		 * and the minimum
		 * integer on the second line of the output file. Specifically, the
		 * output text file will
		 * be in the same directory as the input text file, where the naming
		 * convention is as
		 * follows: If the input file is abc.txt, the output file is
		 * abc_maxmin.txt.
		 */
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(nameWithoutExtension(fullFilePath.getPath()) + "_maxmin.txt"));
			bw.write(Long.toString(max));
			bw.newLine();
			bw.write(Long.toString(min));
		}
		catch (Exception ex) {
			// write error
			logError("File Write Error: " + fullFilePath);
			return;
		}
		finally {
			try {
				bw.close();
			}
			catch (IOException e) {
			}
		}

		// show values to screen
		lblMinValue.setText("Min: " + Long.toString(min));
		lblMaxValue.setText("Max: " + Long.toString(max));

		// show completed message
		JOptionPane.showMessageDialog(null, "Process completed");
	}

	/**
	 * Safely parse string to long
	 * (unable to return boolean value since
	 * primitive types can't be passed by reference)
	 *
	 * @param value
	 *            String value to parse
	 * @return Null if unable to parse, value otherwise
	 */
	private Long tryParseLong(String value) {
		try {
			return Long.parseLong(value);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Find the name of the file without an extension
	 *
	 * @param name
	 *            String to parse
	 * @return Name without its extension
	 */
	private String nameWithoutExtension(String name) {
		int pos = name.lastIndexOf(".");
		if (pos != -1) {
			return name.substring(0, pos);
		}
		return name;
	}
	
	/**
	 * Logs an error message
	 *
	 * @param message
	 *            Message
	 */
	private void logError(String message) {
		System.err.println(message);
		JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
	}
}
