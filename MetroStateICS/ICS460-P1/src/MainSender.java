import java.io.File;
import java.net.*;

/**
 * Main Sender (Client)
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class MainSender {
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
		File inFile = new File(args[0]);
		if (!inFile.exists()) {
			System.err.println("Input file must exist: " + inFile.getAbsoluteFile());
			return;
		}
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
		try (StreamReader reader = new StreamReader(inFile)) {
			// open sender
			int packetCount = 1;
			int byteOffset = 0;
			try (DatagramSocket socket = new DatagramSocket(0)) {
				socket.setSoTimeout(10000);
				while (true) {
					try {
						// keep it simple, make packet data 512 bytes each
						byte[] data = reader.readBytes(512);
						DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(hostName),
								portNumber);
						
						// write status message
						int endOffset = byteOffset + data.length - 1;
						if (endOffset < byteOffset) {
							endOffset = byteOffset;
						}
						System.out.println(packetCount + "-" + byteOffset + "-" + endOffset);
						packetCount += 1;
						byteOffset += data.length;
						
						// send packet
						socket.send(packet);
						
						// check exit condition
						if (data.length == 0) {
							break;
						}
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
		
		System.out.println("Sender closing");
	}
}
