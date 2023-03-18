package Core;

import java.util.EventObject;

/**
 * Message event arguments
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class MessageQueueDataEventArgs extends EventObject {
	private static final long serialVersionUID = 5989370233245614900L;
	public String message;
	
	/**
	 * @param source
	 */
	public MessageQueueDataEventArgs(Object source, String msg) {
		super(source);
		message = msg;
	}
}
