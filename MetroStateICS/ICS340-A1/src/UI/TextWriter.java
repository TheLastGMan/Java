/**
 *
 */
package UI;

import java.io.*;

/**
 * Writes to a text file
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class TextWriter implements AutoCloseable {
	private BufferedWriter writer;
	
	/**
	 * Initialize text writer
	 *
	 * @param output
	 *            Output File
	 * @param overwrite
	 *            Overwrite file
	 * @throws Exception
	 *             Error
	 */
	public TextWriter(File output, boolean overwrite) throws Exception {
		if (output == null) {
			throw new Exception("output must be specified");
		}
		
		// check if we should delete file first
		if (overwrite && output.exists()) {
			output.delete();
		}
		
		writer = new BufferedWriter(new FileWriter(output));
	}
	
	/**
	 * Writes a string to the file as a line
	 *
	 * @param value
	 *            String
	 * @throws IOException
	 *             Error
	 */
	public void writeLineString(String value) throws IOException {
		writer.write(value);
		writer.newLine();
	}
	
	/**
	 * Write a long value to the file as a string line
	 *
	 * @param value
	 *            Long
	 * @throws IOException
	 *             Error
	 */
	public void writeLineLong(long value) throws IOException {
		writeLineString(Long.toString(value));
	}
	
	/**
	 * Write a int value to the file as a string line
	 *
	 * @param value
	 *            Int
	 * @throws IOException
	 *             Error
	 */
	public void writeLineInt(int value) throws IOException {
		writeLineString(Integer.toString(value));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		writer.close();
	}
}
