package Algorithm;

public interface ISortable
{
	String Name();
	
	/**
	 * Simplistic input
	 * could use generic type implementation along with a comparator to determine value gt, lt or eq
	 * @param values
	 * @return
	 */
	SortResult Sort(int[] values);
}
