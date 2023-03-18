package Core;

import java.util.EventObject;

/**
 * Last data packet event arguments
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class CompletedEventArgs extends EventObject {
	private static final long serialVersionUID = 3115158790829762702L;
	
	/**
	 * @param source
	 *            Event Source
	 */
	public CompletedEventArgs(Object source) {
		super(source);
	}
}
