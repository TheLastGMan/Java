import java.io.*;

/**
 * Reads from a file
 *
 * @author Ryan Gau
 * @version 1.1ICS460P1
 */
public class StreamReader implements AutoCloseable {
	private InputStream reader;
	
	/**
	 * Initialize text reader
	 *
	 * @param input
	 *            Input File
	 * @throws Exception
	 *             Error
	 */
	public StreamReader(File input) throws Exception {
		if (input == null) {
			throw new Exception("input must be specified");
		}
		if (!input.exists()) {
			throw new Exception("input file must exist");
		}

		reader = new FileInputStream(input);
	}

	/**
	 * Reads up to the specified length from the file
	 *
	 * @param length
	 *            Max length to read, less if end of file reached
	 * @return byte array of content
	 * @throws IOException
	 *             Error
	 */
	public synchronized byte[] readBytes(int length) throws IOException {
		byte[] buffer = new byte[length];
		int readLength = reader.read(buffer);
		
		if (readLength <= 0) {
			// end of stream
			return new byte[] {};
		}
		else if (readLength != length) {
			// resize buffer
			byte[] nBuffer = new byte[readLength];
			System.arraycopy(buffer, 0, nBuffer, 0, readLength);
			return nBuffer;
		}
		
		// return full length buffer
		return buffer;
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
