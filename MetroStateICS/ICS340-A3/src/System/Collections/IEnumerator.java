/**
 *
 */
package System.Collections;

import java.util.Iterator;

/**
 * @author rgau1
 * @version 1.0
 */
public interface IEnumerator<T> extends Iterator<T>
{
	void Reset();
}
