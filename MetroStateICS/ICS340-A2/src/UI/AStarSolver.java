/**
 *
 */
package UI;

import java.util.*;

import SchoolCommon.Logging;
import System.Linq.QList;

/**
 * Solves a graph using A*
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class AStarSolver {
	/**
	 * Solves a graph using A* based algorithm
	 *
	 * @param graphMap
	 *            Map of the graph
	 * @param heuristics
	 *            Heuristics of each Vertex
	 * @param startVertexName
	 *            Name of the Starting Vertex
	 * @param goalVertexName
	 *            Name of the Goal Vertex
	 * @param keepCostsAbove
	 *            Vertexes are considered connected if the edge cost between two
	 *            Vertexes are above this value
	 * @return List of the node names for the path taken from the start Vertex
	 *         to the goal Vertex
	 */
	public static AStarResult Solve(IGraphMap graphMap, HeuristicMap heuristics, String startVertexName,
			QList<String> goalVertexNames, long keepCostsAbove) throws Exception {
		// validate null inputs
		if (graphMap == null) {
			throw new Exception("Graph Map must be specified");
		}
		if (heuristics == null) {
			throw new Exception("Heuristics must be specified");
		}
		if (startVertexName == null) {
			throw new Exception("Starting Vertex Name must be specified");
		}
		if (goalVertexNames == null || goalVertexNames.Count() == 0) {
			throw new Exception("Goal Vertex(s) Name must be specified");
		}

		// validate there is a start
		if (!graphMap.vertexExists(startVertexName)) {
			throw new Exception("Start Vertex not found: " + startVertexName);
		}

		// valid goal node(s)
		if (goalVertexNames == null || !goalVertexNames.Any()) {
			throw new Exception("Goal Vertex(s) must be specified");
		}

		// check for missing goal nodes, log warning
		QList<String> foundGoalVertexs = goalVertexNames.Where(v -> graphMap.vertexExists(v));
		if (foundGoalVertexs.Count() != goalVertexNames.Count()) {
			Logging.warning(
					"Goal Vertex(s) Not Found: " + String.join(", ", goalVertexNames.Except(foundGoalVertexs, f -> f)));
		}

		// remove isolated goal nodes
		QList<String> reachableGoalVertexs = foundGoalVertexs.Where(v -> graphMap.vertexsLeadingTo(v, false).Any());
		if (!reachableGoalVertexs.Any()) {
			throw new Exception("No Goal Vertexs have an edge leading to them");
		}

		/*
		 * Now that validation is done
		 * A* Search Algorithm
		 */

		// start solving by adding starting Vertex
		List<String> closedSet = new ArrayList<>();
		PriorityQueue<AStarResult> searchSet = new PriorityQueue<>();
		searchSet.add(new AStarResult(0, heuristics.Get(startVertexName)));
		searchSet.peek().Path.add(startVertexName);

		// keep expanding until we reach the goal or run out of paths
		AStarResult bestResult = new AStarResult(0, 0);
		long iterationCount = 0;
		while (!searchSet.isEmpty()) {
			// increment iteration counter
			iterationCount += 1;

			// current Vertex
			AStarResult currentVertex = searchSet.poll();

			// debug processing
			Logging.debug(iterationCount + " | Expanding: " + currentVertex.getVertexName() + " | Path Cost: "
					+ currentVertex.PathCost + " | Estimate: " + currentVertex.Estimate);

			// if we are expanding a goal node, return the best path
			if (reachableGoalVertexs.Contains(currentVertex.getVertexName())) {
				bestResult = currentVertex;
				ProgramHelper.appendResult(currentVertex.getVertexName() + ",Goal Vertex Reached");
				break;
			}

			// add this node to the closed set, we're expanding it
			closedSet.add(currentVertex.getVertexName());

			// remove other vectors from the open set with the same name
			// this is the lowest, don't need to search them
			searchSet.removeIf(f -> f.getVertexName().equals(currentVertex.getVertexName()));

			// add in Vertexes connected to the active Vertex
			// if they are not in the closed set
			// compute p(v) + h(v) | path + heuristic
			// create new Vertex info and add to priority queue
			searchSet.addAll(graphMap.vertexsConnectedTo(currentVertex.getVertexName()).Select(v -> {
				long newPathCost = currentVertex.PathCost + v.Cost;
				long VertexHeuristic = heuristics.Get(v.VertexName);

				// next Vertex info
				AStarResult nextVertex = new AStarResult(newPathCost, newPathCost + VertexHeuristic);
				nextVertex.Path.addAll(currentVertex.Path);
				nextVertex.Path.add(v.VertexName);

				// debug
				Logging.debug("Found: " + v.VertexName + " | Path Cost: " + newPathCost + " | Heuristic: "
						+ VertexHeuristic + " | Estimate: " + nextVertex.Estimate);

				// add new Vertex
				return nextVertex;
			}).Where(v -> !(new QList<>(closedSet).Contains(v.getVertexName()))).ToList());

			// Print priority queue
			List<String> Vertexs = new QList<>(searchSet).OrderBy(f -> f)
					.Select(f -> "n/" + f.getVertexName() + "|f/" + f.Estimate + "|via/" + f.getPriorVertexName())
					.ToList();
			Logging.debug("Priority Queue: ver/" + currentVertex.getVertexName() + " = {"
					+ String.join("} => {", Vertexs) + "}");
			ProgramHelper.appendResult(currentVertex.getVertexName() + ",{" + String.join("},{", Vertexs) + "}");
		}

		// no path found that leads to a goal Vertex
		Logging.debug("Iterations: " + iterationCount);
		return bestResult;
	}
}
