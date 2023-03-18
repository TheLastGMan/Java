package Client;

import Core.StreamReader;

/**
 * Packet processing information
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PacketSetupInfo {
	public final String Hostname;
	public final int Port;
	public final double PacketErrorPercent;
	public final int WindowSize;
	public final int DelayMs;
	public final int MessageSizeBytes;
	public final StreamReader Reader;
	
	public PacketSetupInfo(String hostname, int port, double packetErrorPercent, int windowSize, int delayMs,
			int messageSizeBytes, StreamReader reader) {
		Hostname = hostname;
		Port = port;
		PacketErrorPercent = packetErrorPercent;
		WindowSize = windowSize;
		DelayMs = delayMs;
		MessageSizeBytes = messageSizeBytes;
		Reader = reader;
	}
}
