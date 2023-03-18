/**
 *
 */
package UI;

/**
 * Node Distance Information
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class NodeDistanceInfo {
	// fields
	public final String VertexName;
	public final long Cost;
	
	// ctor
	public NodeDistanceInfo(String vertexName, long cost) {
		VertexName = vertexName;
		Cost = cost;
	}
}
