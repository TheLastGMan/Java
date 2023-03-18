package Core;

/**
 * Action taken on a packet
 *
 * @author Ryan Gau
 * @version 1.0
 */
public enum PacketAction {
	SENDING, RESENDING, RECEIVED, ACK_RECEIVED, TIMEOUT
}
