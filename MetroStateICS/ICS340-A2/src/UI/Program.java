/**
 *
 */
package UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import SchoolCommon.*;
import System.Linq.QList;
import System.Timers.Stopwatch;

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
	private FileBrowser matrixFileBrowser;
	private FileBrowser heuristicFileBrowser;

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
		setTitle("ICS340 - RyanGau - Program C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 100 };
		gbl_contentPane.rowHeights = new int[] { 30, 30, 15, 30, 15 };
		gbl_contentPane.columnWeights = new double[] { 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);

		matrixFileBrowser = new FileBrowser();
		matrixFileBrowser.setText("Matrix File");
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(matrixFileBrowser, gbc_panel);
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;

		heuristicFileBrowser = new FileBrowser();
		heuristicFileBrowser.setText("Heuristic File");
		GridBagConstraints gbc_hpanel = new GridBagConstraints();
		gbc_hpanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_hpanel.insets = new Insets(0, 0, 5, 0);
		gbc_hpanel.gridx = 0;
		gbc_hpanel.gridy = 1;
		contentPane.add(heuristicFileBrowser, gbc_hpanel);

		JButton btnNewButton = new JButton("Solve");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// load file locations
				File matrixFile = matrixFileBrowser.getFile();
				File heuristicFile = heuristicFileBrowser.getFile();

				// validate before solving
				if (matrixFile != null && matrixFile.exists() && heuristicFile != null & heuristicFile.exists()) {
					try {
						solveProblem(matrixFile, heuristicFile);
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
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnNewButton, gbc_btnNewButton);

		lblResult = new JLabel("");
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 5, 0);
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 4;
		contentPane.add(lblResult, gbc_lblResult);
	}

	/**
	 * Wrapper to solve the assignment problem
	 *
	 * @param matrixFile
	 *            Matrix File
	 * @param heuristicFile
	 *            Heuristic File
	 * @throws Exception
	 *             Error
	 */
	private void solveProblem(File matrixFile, File heuristicFile) throws Exception {
		// validate
		if (matrixFile == null || !matrixFile.exists()) {
			throw new Exception("Matrix File must exist");
		}
		if (heuristicFile == null || !heuristicFile.exists()) {
			throw new Exception("Heuristic File must exist");
		}
		
		// read in matrix and heuristic file
		// spec, non connected vertexes will have connection cost of -1
		IGraphMap graph = ProgramHelper.readMatrixFile(matrixFile, -1);
		HeuristicMap heuristics = ProgramHelper.readHeuristicFile(heuristicFile);
		
		// create output file header
		String outFileName = Common.nameBeforeToken(matrixFile.getName(), "_") + "_out.txt";
		ProgramHelper.writeResult(new File(outFileName),
				"vertex,{priority queue vertex 1},{priority queue vertex 2},{priority queue vertex n}");

		// solve
		lblResult.setText("Solving...");
		Stopwatch sw = Stopwatch.startNew();
		// spec: can be either/or {G,Z}
		QList<String> goalVertexNames = new QList<>(new ArrayList<>(Arrays.asList("G", "Z")));
		AStarResult result = AStarSolver.Solve(graph, heuristics, "S", goalVertexNames, -1);
		sw.stop();
		
		// write result
		System.out.println("------------------");
		System.out.println("----- RESULT -----");
		String bestPath = "Path: " + String.join(" => ", result.Path);
		System.out.println(bestPath);
		ProgramHelper.appendResult(bestPath);
		String bestCost = "Cost: " + result.PathCost;
		System.out.println(bestCost);
		ProgramHelper.appendResult(bestCost);
		System.out.println("Time (ms): " + sw.elapsedMilliseconds());
	}
}
