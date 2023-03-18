/**
 *
 */
package UI;

import java.util.*;

/**
 * A* Vector Result
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class AStarResult implements Comparator<AStarResult>, Comparable<AStarResult> {
	// Common Fields
	public final long Estimate;
	public final long PathCost;
	public final List<String> Path = new ArrayList<>();
	
	/**
	 * Initialize with cost info
	 *
	 * @param estimate
	 *            Estimate
	 * @param pathCost
	 *            Cost of the path to get here
	 */
	public AStarResult(long pathCost, long estimate) {
		PathCost = pathCost;
		Estimate = estimate;
	}

	/**
	 * Vector Name
	 *
	 * @return Last Vector's Name
	 */
	public String getVertexName() {
		return Path.size() > 0 ? Path.get(Path.size() - 1) : "";
	}
	
	/**
	 * Prior Vector Name
	 *
	 * @return prior Vector's Name
	 */
	public String getPriorVertexName() {
		return Path.size() > 1 ? Path.get(Path.size() - 2) : "";
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AStarResult o1, AStarResult o2) {
		// Precedence Estimate -> PathCost -> VertexName
		int estCpx = ((Long)o1.Estimate).compareTo(o2.Estimate);
		if (estCpx == 0) {
			int pthCstCpx = ((Long)o1.PathCost).compareTo(o2.PathCost);
			if (pthCstCpx == 0) {
				return o1.getVertexName().compareTo(o2.getVertexName());
			}
			return pthCstCpx;
		}
		return estCpx;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AStarResult o) {
		return compare(this, o);
	}
}
