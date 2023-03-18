/**
 *
 */
package UI;

import System.Linq.QList;

/**
 * Graph Map Interface
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface IGraphMap {
	/**
	 * Add an edge between two vertexes
	 *
	 * @param vertexFromName
	 *            From vertex name
	 * @param vertexToName
	 *            To vertex name
	 * @param edgeCost
	 *            Cost between vertexes
	 * @throws Exception
	 *             A vertex was not found, connected edge exists between
	 *             vertexes
	 */
	void addEdge(String vertexFromName, String vertexToName, long edgeCost) throws Exception;

	/**
	 * Update an edge between two vertexes
	 *
	 * @param vertexFromName
	 *            From vertex name
	 * @param vertexToName
	 *            To vertex name
	 * @param edgeCost
	 *            Cost between vertexes
	 * @throws Exception
	 *             A vertex was not found
	 */
	void updateEdge(String vertexFromName, String vertexToName, long edgeCost) throws Exception;

	/**
	 * Checks if there is a connected edge between the two vertexes
	 *
	 * @param vertexFromName
	 *            From vertex's Name
	 * @param vertexToName
	 *            To vertex's Name
	 * @return Null if there is not a connected edge, cost of edge otherwise
	 * @throws Exception
	 *             A vertex was not found
	 */
	Long edgeCostBetween(String vertexFromName, String vertexToName) throws Exception;
	
	/**
	 * Gives all connected vertexes to the specified vertex
	 *
	 * @param vertexName
	 *            From vertex's Name
	 * @return Collection of NodeDistanceInfo
	 */
	QList<NodeDistanceInfo> vertexsConnectedTo(String vertexName);

	/**
	 * Gives all connected vertexes leading to the specified vertex
	 *
	 * @param vertexName
	 *            To vertex's Name
	 * @return Collection of NodeDistanceInfo
	 */
	QList<NodeDistanceInfo> vertexsLeadingTo(String vertexName, boolean includeSameVertex);
	
	/**
	 * Does a vertex exist
	 *
	 * @param vertexName
	 *            Name of vertex
	 * @return T/F if vertex exists
	 */
	boolean vertexExists(String vertexName);
}
