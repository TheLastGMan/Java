/**
 *
 */
package UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import System.Linq.QList;
import System.Timers.Stopwatch;

/**
 * Main Program
 * Any package that starts with System.*
 * see reference: http://apps.rpgcor.com/java/
 * example: QList<T>, Stopwatch
 * MergeSort*
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Program extends JFrame {
	private static final long serialVersionUID = 4452189799754769657L;
	private JPanel contentPane;
	private JLabel lblResult;
	private JSpinner spinnerMaxWeightUnits;
	private FileBrowser fileBrowser;

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
		setFont(new Font("Cambria", Font.PLAIN, 12));
		setResizable(false);
		setTitle("ICS340 - RyanGau - Program B");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 100, 0 };
		gbl_contentPane.rowHeights = new int[] { 30, 30, 30, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		contentPane.setLayout(gbl_contentPane);

		fileBrowser = new FileBrowser();
		fileBrowser.setText("Source File");
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(fileBrowser, gbc_panel);

		JLabel lblNewLabel = new JLabel("Maximum Knapsack Weight (Units)");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		spinnerMaxWeightUnits = new JSpinner();
		spinnerMaxWeightUnits.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerMaxWeightUnits = new GridBagConstraints();
		gbc_spinnerMaxWeightUnits.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerMaxWeightUnits.fill = GridBagConstraints.BOTH;
		gbc_spinnerMaxWeightUnits.gridx = 0;
		gbc_spinnerMaxWeightUnits.gridy = 2;
		contentPane.add(spinnerMaxWeightUnits, gbc_spinnerMaxWeightUnits);

		JButton btnNewButton = new JButton("Solve");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = fileBrowser.getFile();
				if (file != null && file.exists()) {
					try {
						int maxWeight = (int)spinnerMaxWeightUnits.getValue();
						solveKnapsackProblem(file, maxWeight);
						lblResult.setText("Success: Computation Complete, check output file.");
					}
					catch (Exception ex) {
						lblResult.setText("Error: " + ex.getMessage());
					}

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

	/**
	 * Wrapper to solve the knapsack problem
	 *
	 * @param file
	 *            Input File
	 * @param maxWeightUnits
	 *            Maximum weight of knapsack
	 * @throws Exception
	 *             Error
	 */
	private void solveKnapsackProblem(File file, int maxWeightUnits) throws Exception {
		// validate
		if (file == null) {
			throw new Exception("file must be specified");
		}

		// read in file
		QList<Product> products = KnapsackHelper.readFile(file);

		// solve
		lblResult.setText("Solving...");
		IKnapsackSolver solvers[] = { new KnapsackAIDynamic() };
		java.util.List<SolverResult> results = new ArrayList<>();
		for (IKnapsackSolver solver : solvers) {
			try {
				Stopwatch sw = new Stopwatch();
				sw.start();
				KnapsackResult result = solver.solveProblem(maxWeightUnits, products);
				sw.stop();
				results.add(new SolverResult(solver.Name(), result, sw.elapsedMilliseconds()));
			}
			catch (Exception ex) {
				throw new Exception("Unable to solve: " + solver.Name() + " -> " + ex.getMessage());
			}
		}

		// write result
		String outFileName = KnapsackHelper.nameWithoutExtension(file.getPath()) + "-output.txt";
		KnapsackHelper.writeFile(maxWeightUnits, new File(outFileName), results);
	}
}
