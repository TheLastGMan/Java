package Client;

import java.util.EventObject;

import Core.*;

/**
 * Packet message event arguments
 *
 * @author Ryan
 * @version 1.0
 */
public class PacketMessageEventArgs extends EventObject {
	private static final long serialVersionUID = 201679078738080701L;

	public final PacketAction Action;
	public final PacketStatus Status;
	public final PacketAckStatus AckStatus;
	public final Packet Packet;
	public final String SentAtTime;

	public PacketMessageEventArgs(Object source, PacketAction action, PacketStatus status, PacketAckStatus ackStatus,
			Packet packet) {
		super(source);

		Action = action;
		Status = status;
		AckStatus = ackStatus;
		Packet = packet;
		SentAtTime = Common.currentDateStamp();
	}
}
