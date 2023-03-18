/**
 *
 */
package UI;

import java.util.*;

import System.Collections.Dictionary;
import System.Linq.QList;

/**
 * Store the graph as an Adjacency Matrix
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class VertexAdjacencyMatrix implements IGraphMap {
	// Adjacency Matrix
	private final long[][] VertexMatrix;
	
	// Not connected cost
	private final long edgeNotConnectedCost;
	
	// Node Names to Index lookups
	public final Dictionary<String, Integer> VertexMapFrom = new Dictionary<>();
	public final Dictionary<String, Integer> VertexMapTo = new Dictionary<>();
	
	// Reverse mappings of index to node name
	private final Dictionary<Integer, String> VertexMapFromIndex = new Dictionary<>();
	private final Dictionary<Integer, String> VertexMapToIndex = new Dictionary<>();

	/**
	 * Initialize Matrix Adjacency
	 *
	 * @param fromVertexNames
	 *            Collection of Node's From Names
	 * @param toVertexNames
	 *            Collection of Node's To Names
	 * @param notConnectedCost
	 *            Edge's not connected cost
	 * @throws Exception
	 *             Error
	 */
	public VertexAdjacencyMatrix(List<String> fromVertexNames, List<String> toVertexNames, long notConnectedCost)
			throws Exception {
		// map from Vertexes
		for (int i = 0; i < fromVertexNames.size(); i++) {
			VertexMapFrom.Add(fromVertexNames.get(i), i);
			VertexMapFromIndex.Add(i, fromVertexNames.get(i));
		}

		// map to Vertexes
		for (int i = 0; i < toVertexNames.size(); i++) {
			VertexMapTo.Add(toVertexNames.get(i), i);
			VertexMapToIndex.Add(i, toVertexNames.get(i));
		}

		// assign not connected cost
		edgeNotConnectedCost = notConnectedCost;

		// declare matrix size and assign initial values
		VertexMatrix = new long[fromVertexNames.size()][toVertexNames.size()];
		for (int x = 0; x < fromVertexNames.size(); x++) {
			for (int y = 0; y < toVertexNames.size(); y++) {
				VertexMatrix[x][y] = notConnectedCost;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#addEdge(java.lang.String, java.lang.String,
	 * long)
	 */
	@Override
	public void addEdge(String vertexFromName, String vertexToName, long edgeCost) throws Exception {
		// load indices of Vertexes in matrix
		Integer fromIndex = VertexMapFrom.TryGetValue(vertexFromName);
		Integer toIndex = VertexMapTo.TryGetValue(vertexToName);
		
		// check if they exist
		if (fromIndex == null) {
			throw new Exception("From Vertex not found: " + vertexFromName);
		}
		if (toIndex == null) {
			throw new Exception("To Vertex not found: " + vertexToName);
		}
		
		// check for duplicate
		if (VertexMatrix[fromIndex][toIndex] > edgeNotConnectedCost) {
			throw new Exception("Edge already added: " + vertexFromName + " => " + vertexToName);
		}

		// don't connect a vertex to itself with a cost of zero
		if (vertexFromName.equals(vertexToName) && edgeCost == 0) {
			edgeCost = edgeNotConnectedCost;
		}

		// add edge
		VertexMatrix[fromIndex][toIndex] = edgeCost;
	}
	
	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#updateEdge(String vertexFromName, String vertexToName,
	 * long edgeCost)
	 */
	@Override
	public void updateEdge(String vertexFromName, String vertexToName, long edgeCost) throws Exception {
		// load indices of Vertexes in matrix
		Integer fromIndex = VertexMapFrom.TryGetValue(vertexFromName);
		Integer toIndex = VertexMapTo.TryGetValue(vertexToName);

		// check if they exist
		if (fromIndex == null) {
			throw new Exception("From Vertex not found: " + vertexFromName);
		}
		if (toIndex == null) {
			throw new Exception("To Vertex not found: " + vertexToName);
		}

		// don't connect a vertex to itself with a cost of zero
		if (vertexFromName.equals(vertexToName) && edgeCost == 0) {
			edgeCost = edgeNotConnectedCost;
		}
		
		// update
		VertexMatrix[fromIndex][toIndex] = edgeCost;
	}

	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#edgeCostBetween(String vertexFromName, String
	 * vertexToName)
	 */
	@Override
	public Long edgeCostBetween(String vertexFromName, String vertexToName) throws Exception {
		// load indices of Vertexes in matrix
		Integer fromIndex = VertexMapFrom.TryGetValue(vertexFromName);
		Integer toIndex = VertexMapTo.TryGetValue(vertexToName);
		
		// check if they exist
		if (fromIndex == null) {
			throw new Exception("From Vertex not found: " + vertexFromName);
		}
		if (toIndex == null) {
			throw new Exception("To Vertex not found: " + vertexToName);
		}
		
		// return connected value between the two Vertexes
		long cost = VertexMatrix[fromIndex][toIndex];
		return (cost > edgeNotConnectedCost) ? cost : null;
	}

	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#VertexsConnectedTo(java.lang.String)
	 */
	@Override
	public QList<NodeDistanceInfo> vertexsConnectedTo(String vertexName) {
		// setup
		List<NodeDistanceInfo> connectedVertexs = new ArrayList<>();
		
		// check for existence of a Vertex
		Integer fromIndex = VertexMapFrom.TryGetValue(vertexName);
		if (fromIndex != null) {
			// check connected Vertexes
			for (int i = 0; i < VertexMapTo.Count(); i++) {
				long cost = VertexMatrix[fromIndex][i];

				// add only connected Vertexes
				if (cost > edgeNotConnectedCost) {
					connectedVertexs.add(new NodeDistanceInfo(VertexMapToIndex.TryGetValue(i), cost));
				}
			}
		}

		return new QList<>(connectedVertexs);
	}
	
	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#VertexsLeadingTo(java.lang.String)
	 */
	@Override
	public QList<NodeDistanceInfo> vertexsLeadingTo(String vertexName, boolean includeSameVertex) {
		// setup
		List<NodeDistanceInfo> connectedVertexs = new ArrayList<>();
		
		// check for existence of a Vertex
		Integer toIndex = VertexMapTo.TryGetValue(vertexName);
		if (toIndex != null) {
			// check connected Vertexes
			for (int i = 0; i < VertexMapFrom.Count(); i++) {
				// from Vertex info
				String fromVertexName = VertexMapFromIndex.TryGetValue(i);
				long cost = VertexMatrix[i][toIndex];
				
				// add only connected Vertexes
				if (cost > edgeNotConnectedCost) {
					// check for same Vertex
					if (fromVertexName.equals(vertexName) && !includeSameVertex) {
						continue;
					}
					
					connectedVertexs.add(new NodeDistanceInfo(fromVertexName, cost));
				}
			}
		}
		
		return new QList<>(connectedVertexs);
	}

	/*
	 * (non-Javadoc)
	 * @see UI.IGraphMap#VertexExists(java.lang.String)
	 */
	@Override
	public boolean vertexExists(String vertexName) {
		return VertexMapTo.ContainsKey(vertexName) || VertexMapFrom.ContainsKey(vertexName);
	}
}
