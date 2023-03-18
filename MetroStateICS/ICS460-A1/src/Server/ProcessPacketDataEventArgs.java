package Server;

import java.util.EventObject;

/**
 * Next packet data in sequence
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ProcessPacketDataEventArgs extends EventObject {
	private static final long serialVersionUID = 3115158790829762700L;
	public final byte[] dataStream;
	
	/**
	 * Set up Packet event arguments
	 *
	 * @param source
	 *            Source of the event
	 * @param stream
	 *            Data stream
	 */
	public ProcessPacketDataEventArgs(Object source, byte[] stream) {
		super(source);
		dataStream = stream;
	}
}
