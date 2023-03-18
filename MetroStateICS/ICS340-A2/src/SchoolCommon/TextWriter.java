/**
 *
 */
package SchoolCommon;

import java.io.*;

/**
 * Writes to a text file
 *
 * @author Ryan Gau
 * @version 1.1
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
		
		writer = new BufferedWriter(new FileWriter(output, !overwrite));
	}
	
	/**
	 * Writes a string to the file as a line
	 *
	 * @param value
	 *            String
	 * @throws IOException
	 *             Error
	 */
	public void writeLine(String value) throws IOException {
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
		writeLine(Long.toString(value));
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
		writeLine(Integer.toString(value));
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
