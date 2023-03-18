package Server;

import java.util.EventObject;

import Core.Packet;

/**
 * Process packet event arguments
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ProcessPacketEventArgs extends EventObject {
	private static final long serialVersionUID = 3115158790829762700L;
	public final Packet packet;

	/**
	 * Setup Packet processing event arguments
	 *
	 * @param source
	 *            Event Source
	 */
	public ProcessPacketEventArgs(Object source, Packet pkt) {
		super(source);
		packet = pkt;
	}
}
