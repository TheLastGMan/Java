import java.io.*;
import Generic.*;

public class BSTProgram
{
	public static void main(String args[])
	{
		// read file
		try
		{
			// init
			String file = "c:/users/rgau1/desktop/music.txt"; // args[0]; //debug, override file input
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			// process
			BST<BstFileNode> bst = new BST<BstFileNode>("\r\n");
			bst.AutoBalance = false;
			String line = null;
			while ((line = br.readLine()) != null)
			{
				String[] parts = line.split("\\s+");
				for (String p : parts)
				{
					// remove non letter characters and insert as lower case so we can group them by the word
					p = p.replaceAll("[^a-zA-Z\']", "");
					bst.Insert(new BstFileNode(p.toLowerCase()));
				}
			}
			bst.Balance();
			
			// display
			System.out.println(bst.toString());
			
			// cleanup
			br.close();
		}
		catch (FileNotFoundException fex)
		{
			System.out.println("Unable to open file: " + args[0]);
		}
		catch (Exception ex)
		{
			System.out.println("Process Error: " + ex.getMessage());
		}
	}
}
