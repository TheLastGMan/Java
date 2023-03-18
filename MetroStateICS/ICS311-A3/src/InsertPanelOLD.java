import java.awt.*;
import javax.swing.*;
//This GUI interface is a place holder to be finished by students. 

public class InsertPanelOLD extends JPanel
{
	private static final long serialVersionUID = -3363401489579639076L;

	public InsertPanelOLD() {
		setBackground(Color.yellow);
		setPreferredSize(new Dimension(540, 500));
		JLabel isbnLabel = new JLabel("ISBN: ");
		JTextField isbnTextFld = new JTextField(10);
		add(isbnLabel);
		add(isbnTextFld);
	}
}
