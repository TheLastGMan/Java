import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogoPanel extends JPanel
{
	private static final long serialVersionUID = 484885367235964405L;
	private URL urllogo; 
    private ImageIcon logo; 
    private JLabel imageLabel; 
    private JLabel textLabel;

    public LogoPanel() {
    	urllogo = getClass().getResource("logo.jpg");
    	logo = new ImageIcon(urllogo);
    	imageLabel = new JLabel(logo);
    	textLabel = new JLabel("2-Tier Architecture Application");
		textLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
    	setBackground(Color.orange);
    	setPreferredSize(new Dimension(540, 100));
        add(imageLabel);
        add(textLabel);
    }
}
