package Server;

import java.util.EventObject;

import Core.*;

/**
 * Packet info to show
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PacketInfoEventArgs extends EventObject {
	private static final long serialVersionUID = 3115158790829762704L;
	
	public final PacketAction PacketAction;
	public final boolean IsDuplicate;
	public final int PacketNumber;
	public final PacketCondition PacketCondition;
	public final PacketAckStatus PacketAckStatus;
	public final String SentAtTime;
	
	public PacketInfoEventArgs(Object source, PacketAction action, boolean duplicate, int pktNumber,
			PacketCondition condition, PacketAckStatus ackInfo) {
		super(source);
		PacketAction = action;
		IsDuplicate = duplicate;
		PacketNumber = pktNumber;
		PacketCondition = condition;
		PacketAckStatus = ackInfo;
		SentAtTime = Common.currentDateStamp();
	}
}
