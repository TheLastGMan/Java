import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class InsertPanel extends JPanel
{
	private static final long serialVersionUID = -5904840788364847897L;
	private JTextField txtboxISBN;
	private JTextField txtboxAuthor;
	private JLabel lblTitle;
	private JTextField txtboxTitle;
	private final JLabel lblPrice = new JLabel("Price:");
	private JButton btnNewButton;
	private JTextPane textAreaSQL;
	private JSpinner spinnerPrice;
	
	public InsertPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{225, 225, 0};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 60, 120};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblISBN = new GridBagConstraints();
		gbc_lblISBN.fill = GridBagConstraints.BOTH;
		gbc_lblISBN.insets = new Insets(0, 0, 5, 5);
		gbc_lblISBN.gridx = 0;
		gbc_lblISBN.gridy = 0;
		add(lblISBN, gbc_lblISBN);
		lblISBN.setLabelFor(txtboxISBN);
		
		txtboxISBN = new JTextField();
		txtboxISBN.setColumns(24);
		GridBagConstraints gbc_txtboxISBN = new GridBagConstraints();
		gbc_txtboxISBN.fill = GridBagConstraints.BOTH;
		gbc_txtboxISBN.insets = new Insets(0, 0, 5, 0);
		gbc_txtboxISBN.gridx = 1;
		gbc_txtboxISBN.gridy = 0;
		add(txtboxISBN, gbc_txtboxISBN);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.fill = GridBagConstraints.BOTH;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 1;
		add(lblAuthor, gbc_lblAuthor);
		lblAuthor.setLabelFor(txtboxAuthor);
		
		txtboxAuthor = new JTextField();
		GridBagConstraints gbc_txtboxAuthor = new GridBagConstraints();
		gbc_txtboxAuthor.fill = GridBagConstraints.BOTH;
		gbc_txtboxAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_txtboxAuthor.gridx = 1;
		gbc_txtboxAuthor.gridy = 1;
		add(txtboxAuthor, gbc_txtboxAuthor);
		txtboxAuthor.setColumns(64);
		
		lblTitle = new JLabel("Title:");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 2;
		add(lblTitle, gbc_lblTitle);
		lblTitle.setLabelFor(txtboxTitle);
		
		txtboxTitle = new JTextField();
		GridBagConstraints gbc_txtboxTitle = new GridBagConstraints();
		gbc_txtboxTitle.fill = GridBagConstraints.BOTH;
		gbc_txtboxTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtboxTitle.gridx = 1;
		gbc_txtboxTitle.gridy = 2;
		add(txtboxTitle, gbc_txtboxTitle);
		txtboxTitle.setColumns(64);
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.fill = GridBagConstraints.BOTH;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 3;
		add(lblPrice, gbc_lblPrice);
		
		/*---------------------
		 *  Generate Button 
		 *  -------------------*/
		btnNewButton = new JButton("Generate Insert Statment");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//validate
				ArrayList<String> errors = new ArrayList<String>();
				if(txtboxISBN.getText().trim().isEmpty())
					errors.add("ISBN");
				if(txtboxAuthor.getText().trim().isEmpty())
					errors.add("Author");
				if(txtboxTitle.getText().trim().isEmpty())
					errors.add("Title");
				
				//check validation errors
				if(!errors.isEmpty())
				{
					textAreaSQL.setText("Missing values for: {" + String.join(", ", errors) + "}.");
				}
				else
				{
					//Generate Insert Statement
					String sql = String.format("INSERT INTO %1$s\r\nVALUES('%2$s', '%3$s', '%4$s', %5$3.2f);", "books", sqlSafeish(txtboxISBN.getText()), sqlSafeish(txtboxAuthor.getText()), sqlSafeish(txtboxTitle.getText()), ((float)spinnerPrice.getValue()));
					textAreaSQL.setText(sql);
				}
			}
		});
		
		spinnerPrice = new JSpinner();
		lblPrice.setLabelFor(spinnerPrice);
		spinnerPrice.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(9999.99), new Float(0.01)));
		GridBagConstraints gbc_spinnerPrice = new GridBagConstraints();
		gbc_spinnerPrice.anchor = GridBagConstraints.WEST;
		gbc_spinnerPrice.fill = GridBagConstraints.BOTH;
		gbc_spinnerPrice.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPrice.gridx = 1;
		gbc_spinnerPrice.gridy = 3;
		add(spinnerPrice, gbc_spinnerPrice);
		btnNewButton.setMnemonic('G');
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		add(btnNewButton, gbc_btnNewButton);
		
		textAreaSQL = new JTextPane();
		textAreaSQL.setEditable(false);
		GridBagConstraints gbc_textAreaSQL = new GridBagConstraints();
		gbc_textAreaSQL.ipady = 10;
		gbc_textAreaSQL.ipadx = 10;
		gbc_textAreaSQL.gridwidth = 2;
		gbc_textAreaSQL.fill = GridBagConstraints.BOTH;
		gbc_textAreaSQL.gridx = 0;
		gbc_textAreaSQL.gridy = 5;
		add(textAreaSQL, gbc_textAreaSQL);
	}
	
	private String sqlSafeish(String input)
	{
		return input.replace('\'', ' ').trim();
	}
}
