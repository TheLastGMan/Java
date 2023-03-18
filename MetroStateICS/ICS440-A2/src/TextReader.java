import java.io.*;

/**
 * Reads a text file
 *
 * @author Ryan Gau
 * @version 1.1
 */
public class TextReader implements AutoCloseable {
	private BufferedReader reader;
	
	/**
	 * Initialize text reader
	 *
	 * @param input
	 *            Input File
	 * @throws Exception
	 *             Error
	 */
	public TextReader(File input) throws Exception {
		if (input == null) {
			throw new Exception("input must be specified");
		}
		if (!input.exists()) {
			throw new Exception("input file must exist");
		}
		
		reader = new BufferedReader(new FileReader(input));
	}
	
	/**
	 * Read the next line from the file
	 *
	 * @return String
	 * @throws IOException
	 *             Error
	 */
	public String readLine() throws IOException {
		return reader.readLine();
	}
	
	/**
	 * Read the next line and parses it as a Long value
	 *
	 * @return Long
	 * @throws IOException
	 *             Error
	 */
	public long readLineLong() throws IOException {
		return Long.parseLong(readLine());
	}
	
	/**
	 * Read the next line and parses it as a Int value
	 *
	 * @return Int
	 * @throws IOException
	 *             Error
	 */
	public int readLineInt() throws IOException {
		return Integer.parseInt(readLine());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		reader.close();
	}
}
