/**
 *
 */
package UI;

import SchoolCommon.Logging;
import System.Collections.Dictionary;

/**
 * Heuristic Mapping
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class HeuristicMap extends Dictionary<String, Long> {
	private static final long serialVersionUID = 3512021052497313983L;
	
	/**
	 * Make both gets default the value if the vertex is not found
	 */
	@Override
	public Long Get(String vertexName) {
		// check if it exists
		if (super.ContainsKey(vertexName)) {
			return super.TryGetValue(vertexName);
		}
		
		// otherwise, log warning and give default
		Logging.warning("No Heuristic value for vertex name: " + vertexName + " | defaulting to 1");
		return (long)1;
	}
	
	/**
	 * Make both gets default the value if the vertex is not found
	 */
	@Override
	public Long TryGetValue(String vertexName) {
		return Get(vertexName);
	}
}
