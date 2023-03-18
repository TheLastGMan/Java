import java.io.File;
import java.net.*;

/**
 * Main Receiver (Server)
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class MainReceiver {
	private final static int bufferSize = 32 * 1024;
	
	/**
	 * Main Entry Point
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// parse inputs
		// {output file}, {server host name}, {server port}
		if (args.length != 3) {
			System.err.println("expecting 3 arguments: {output file}, {server host name}, {server port}");
		}

		// validate arguments
		File outFile = new File(args[0]);
		String hostName = args[1];
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[2]);
			if (portNumber <= 0 || portNumber >= 65536) {
				throw new Exception("Invalid port number");
			}
		}
		catch (Exception ex) {
			System.err.print("Invalid port number, must be a positive while number [1, 65535]");
			return;
		}
		
		// open up stream writer
		try (StreamWriter writer = new StreamWriter(outFile, true)) {
			// open listener
			int packetCount = 1;
			int byteOffset = 0;
			try (DatagramSocket socket = new DatagramSocket(portNumber, InetAddress.getByName(hostName))) {
				socket.setSoTimeout(10000);
				while (true) {
					// wait for data
					try {
						DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize);
						socket.receive(packet);
						
						// resize to match received data
						byte[] newBuff = new byte[packet.getLength()];
						System.arraycopy(packet.getData(), 0, newBuff, 0, newBuff.length);
						
						// write status message
						int endOffset = byteOffset + newBuff.length - 1;
						if (endOffset < byteOffset) {
							endOffset = byteOffset;
						}
						System.out.println(packetCount + "-" + byteOffset + "-" + endOffset);
						packetCount += 1;
						byteOffset += newBuff.length;
						
						// check exit condition
						if (newBuff.length == 0) {
							break;
						}
						
						// write data
						writer.writeBytes(newBuff);
					}
					catch (SocketTimeoutException ste) {
						// do nothing
					}
					catch (Exception ex) {
						System.err.println("Error: " + ex.getMessage());
						ex.printStackTrace();
					}
				}
			}
			catch (Exception ex) {
				System.err.println("Socket Error: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		catch (Exception ex) {
			System.err.println("File Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		System.out.println("Receiver closing");
	}
}
