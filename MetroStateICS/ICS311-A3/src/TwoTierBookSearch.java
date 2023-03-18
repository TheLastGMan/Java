import java.awt.*;
import javax.swing.*;

public class TwoTierBookSearch
{
	public static void main(String[] args) {
		LogoPanel logoPanel = new LogoPanel();
		JFrame frame = new JFrame("Two Tier Book Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(logoPanel, BorderLayout.NORTH);
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Data Insertion Page", new InsertPanel());
		tp.addTab("Data Retrival Page", new RetrievalPanel());
		frame.getContentPane().add(tp, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}
