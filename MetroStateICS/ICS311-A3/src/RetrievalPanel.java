import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class RetrievalPanel extends JPanel
{
	private static final long serialVersionUID = 5778548417783006411L;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private JComboBox<String> searchTypeCmb;
	private JTextField searchTermField;
	private JButton submitBtn;
	private JTextArea textArea;
	
	public RetrievalPanel() {		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(540, 500));
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0, 2));
		JLabel searchTypeLbl = new JLabel("Choose Search Type: ");
		searchTypeCmb = new JComboBox<String>();
		searchTypeCmb.addItem("Title");
		searchTypeCmb.addItem("ISBN");
		JLabel searchTermLbl = new JLabel("Enter Search Term: ");
		searchTermField = new JTextField();
		submitBtn = new JButton("Get Books");
		ButtonListener buttonListener = new ButtonListener();
		submitBtn.addActionListener(buttonListener);
		p.add(searchTypeLbl);
		p.add(searchTypeCmb);
		p.add(searchTermLbl);
		p.add(searchTermField);
		p.add(submitBtn);
		textArea = new JTextArea(20, 20);
		
		add(p, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == submitBtn) {
				String queryString = "";
				String outputString = "";
				String selectedItem = (String) searchTypeCmb.getSelectedItem();
				String searchTerm = searchTermField.getText();
				String dash = "\n=========================================================\n";
				textArea.setText("");
				
				if ("Title".equals(selectedItem)) {
					queryString = "select author, title from books where title like '%"
							+ searchTerm + "%'";
				}
				else if ("ISBN".equals(selectedItem)) {
					queryString = "select author, title from books where ISBN = '"
							+ searchTerm + "'";
				}
				
				try {
					connection = DriverManager.getConnection(
							"jdbc:mysql://localhost/books", "root", "admin");
					statement = connection.createStatement();
					resultSet = statement.executeQuery(queryString);
					ResultSetMetaData metaData = resultSet.getMetaData();
					int numberOfColumns = metaData.getColumnCount();
					outputString = "Result from Books Table of Books Database\n\n";
					
					for(int i=1; i<=numberOfColumns;i++){
						outputString = outputString + metaData.getColumnName(i) + "\t\t";
					}
					
					outputString += dash;
					
					while(resultSet.next()){
						for(int i=1; i<=numberOfColumns;i++){
							outputString = outputString + resultSet.getObject(i) + "\t\t";
						}
						outputString += "\n";
					}
					
					textArea.append(outputString);
					connection.close();
				}
				catch (SQLException sqlException){
					sqlException.printStackTrace();
				}
			}
		}
	}
}
