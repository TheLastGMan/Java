package Algorithm;

import java.util.Arrays;

public class Merge implements ISortable
{
	@Override
	public String Name()
	{
		return "Merge";
	}
	
	@Override
	public SortResult Sort(int[] values) {
		if(values.length <= 1)
			return new SortResult(0, values);
		
		long start = System.currentTimeMillis();
		values = splitMergeArray(values);
		long end = System.currentTimeMillis();
		double elapsed = end - start;
		return new SortResult(elapsed, values);
	}
	
	/**
	 * Algorithm entry point
	 * @param values
	 * @return
	 */
	int[] splitMergeArray(int[] values)
	{
		if(values.length <= 1)
			return values;
		
		//local items
		int half = (int)(values.length / 2);
		
		//create parts
		int[] left = Arrays.copyOfRange(values, 0, half);
		int[] right = Arrays.copyOfRange(values, half, values.length);
		
		//process
		left = splitMergeArray(left);
		right = splitMergeArray(right);
		values = mergeArray(left, right);
		
		return values;
	}
	
	/**
	 * Merge to arrays in ascending order (assumes left/right has already been sorted in ascending order)
	 * @param left
	 * @param right
	 * @return
	 */
	int[] mergeArray(int[] left, int[] right)
	{
		//create combined output and index counters
		int[] output = new int[left.length + right.length];
		int lcnt = 0;
		int rcnt = 0;
		
		//compare arrays to each other until either is empty, then use other, assign to output array index
		for(int i = 0; i < output.length; i++)
			if(lcnt < left.length && rcnt < right.length)
				output[i] = (left[lcnt] < right[rcnt]) ? left[lcnt++] : right[rcnt++];
			else if(lcnt < left.length)
				output[i] = left[lcnt++];
			else
				output[i] = right[rcnt++];
		
		return output;
	}
}
