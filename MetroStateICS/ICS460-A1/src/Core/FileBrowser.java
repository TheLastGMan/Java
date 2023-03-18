package Core;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * @author Ryan Gau
 * @version 1.0ICS460
 */
public class FileBrowser extends JPanel {
	private static final long serialVersionUID = 9140759509286588376L;
	private JFileChooser browser = new JFileChooser("./");
	private JTextField textFilePath;
	private JLabel lblName;
	private boolean checkFileExists = false;
	
	/**
	 * Create the panel.
	 */
	public FileBrowser() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 100 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);
		
		lblName = new JLabel("[Name]");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 0);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);
		
		textFilePath = new JTextField();
		textFilePath.setBackground(Color.WHITE);
		textFilePath.setEditable(false);
		GridBagConstraints gbc_textFilePath = new GridBagConstraints();
		gbc_textFilePath.insets = new Insets(0, 0, 5, 0);
		gbc_textFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFilePath.gridx = 0;
		gbc_textFilePath.gridy = 1;
		add(textFilePath, gbc_textFilePath);
		textFilePath.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (browser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						if (checkFileExists && !browser.getSelectedFile().exists()) {
							return;
						}
						
						textFilePath.setText(browser.getSelectedFile().getPath());
					}
					catch (Exception ex) {
						System.err.println("Browse Error: " + ex.getMessage());
					}
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnNewButton, gbc_btnNewButton);
	}
	
	// #Region Properties
	
	/**
	 * Get if a check for a file's existence should happen
	 *
	 * @return T/F if we should check
	 */
	public boolean getCheckFileExists() {
		return checkFileExists;
	}
	
	/**
	 * Sets if a check for a file's existence should happen
	 *
	 * @param value
	 *            T/F to check
	 */
	public void setCheckFileExists(boolean value) {
		checkFileExists = value;
	}
	
	/**
	 * Set Title
	 *
	 * @param text
	 *            String
	 */
	public void setText(String text) {
		lblName.setText(text);
	}
	
	/**
	 * get Text
	 *
	 * @return String
	 */
	public String getText() {
		return lblName.getText();
	}
	
	/**
	 * get File
	 *
	 * @return File
	 */
	public File getFile() {
		return new File(textFilePath.getText());
	}
	
	// #EndRegion
}
