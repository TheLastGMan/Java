import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Input/Output object reader
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class IO<T extends Serializable> {
	@SuppressWarnings("unchecked")
	public ArrayList<T> loadFigures(String fullFilePath) throws Exception {
		// result
		ArrayList<T> result = new ArrayList<>();
		
		// if file is not found, do nothing
		File fileInfo = new File(fullFilePath);
		if (fileInfo.exists()) {
			try {
				// create file/object stream
				FileInputStream streamIn = new FileInputStream(fullFilePath);
				ObjectInputStream objectInputStream = new ObjectInputStream(
						streamIn);
				
				// read
				result = (ArrayList<T>)objectInputStream.readObject();
				
				// close
				objectInputStream.close();
				streamIn.close();
			}
			catch (Exception ex) {
				fileInfo.delete();
				throw ex;
			}
		}
		
		return result;
	}
	
	public void saveFigures(String fullFilePath, ArrayList<T> objects)
			throws Exception {
		// create file/object stream
		FileOutputStream fout = new FileOutputStream(fullFilePath);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		
		// write
		oos.writeObject(objects);
		
		// close stream
		oos.close();
		fout.close();
	}
}
