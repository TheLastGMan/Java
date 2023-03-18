/**
 *
 */
package UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import SchoolCommon.*;

/**
 * Main Program
 * Any package that starts with System.*
 * see reference: http://apps.rpgcor.com/java/
 * example: QList<T>, Stopwatch
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Program extends JFrame {
	private static final long serialVersionUID = 4452189799754769657L;
	private JPanel contentPane;
	private JLabel lblResult;
	private FileBrowser variableFileBrowser;
	private FileBrowser domainFileBrowser;
	private FileBrowser constraintFileBrowser;

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
		setResizable(false);
		setFont(new Font("Cambria", Font.PLAIN, 12));
		setTitle("ICS340 - RyanGau - Program D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 100 };
		gbl_contentPane.rowHeights = new int[] { 30, 30, 30, 30, 30, 15 };
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);

		// Variable File
		variableFileBrowser = new FileBrowser();
		variableFileBrowser.setText("Variable File");
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(variableFileBrowser, gbc_panel);
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;

		// Domain File
		domainFileBrowser = new FileBrowser();
		domainFileBrowser.setText("Domain File");
		GridBagConstraints gbc_hpanel = new GridBagConstraints();
		gbc_hpanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_hpanel.insets = new Insets(0, 0, 5, 0);
		gbc_hpanel.gridx = 0;
		gbc_hpanel.gridy = 1;
		contentPane.add(domainFileBrowser, gbc_hpanel);

		// Constraint File
		constraintFileBrowser = new FileBrowser();
		constraintFileBrowser.setText("Constraint File");
		GridBagConstraints gbx_hpanel = new GridBagConstraints();
		gbx_hpanel.fill = GridBagConstraints.HORIZONTAL;
		gbx_hpanel.gridy = 2;
		gbx_hpanel.gridx = 0;
		gbc_hpanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_hpanel.insets = new Insets(0, 0, 5, 0);
		gbc_hpanel.gridx = 0;
		gbc_hpanel.gridy = 2;
		contentPane.add(constraintFileBrowser, gbx_hpanel);
		
		JButton btnNewButton = new JButton("Solve");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// load file locations
				File variableFile = variableFileBrowser.getFile();
				File domainFile = domainFileBrowser.getFile();
				File constraintFile = constraintFileBrowser.getFile();

				// validate before solving
				if (variableFile != null && variableFile.exists() && domainFile != null && domainFile.exists()
						&& constraintFile != null && constraintFile.exists()) {
					try {
						GacSolver.solveProblem(variableFile, domainFile, constraintFile);
						lblResult.setText("Success: Computation Complete, check output file.");
					}
					catch (Exception ex) {
						Logging.error(ex.getMessage());
						lblResult.setText("Error: " + ex.getMessage());
					}
				}
				else {
					lblResult.setText("Fields are required.");
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(btnNewButton, gbc_btnNewButton);

		lblResult = new JLabel("");
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 5, 0);
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 5;
		contentPane.add(lblResult, gbc_lblResult);
	}
}
