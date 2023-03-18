import java.io.*;

/**
 * Writes to a file
 *
 * @author Ryan Gau
 * @version 1.1ICS460P1
 */
public class StreamWriter implements AutoCloseable {
	private OutputStream writer;

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
	public StreamWriter(File output, boolean overwrite) throws Exception {
		if (output == null) {
			throw new Exception("output must be specified");
		}

		// check if we should delete file first
		if (overwrite && output.exists()) {
			output.delete();
		}

		writer = new FileOutputStream(output, !overwrite);
	}

	/**
	 * Writes a binary stream to the file
	 *
	 * @param buffer
	 *            Stream to write
	 * @throws IOException
	 *             Error
	 */
	public synchronized void writeBytes(byte[] buffer) throws IOException {
		writer.write(buffer);
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
