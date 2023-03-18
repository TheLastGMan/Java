package Algorithm;

import java.util.Stack;

public class Shell implements ISortable
{
	@Override
	public String Name() 
	{
		return "Shell";
	}

	@Override
	public SortResult Sort(int[] values)
	{
		//if 0 length, no need to sort
		if(values.length < 2)
			return new SortResult(0, values);
		
		long start = System.currentTimeMillis();
		values = sort(values);
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		return new SortResult(elapsed, values);
	}
	
	private int[] sort(int[] values)
	{
		//don't waste time if only none or one value
		if(values.length < 2)
			return values;
		
		Stack<Integer> gaps = sequence(values.length);
		while(!gaps.isEmpty())
		{
			int gap = gaps.pop();
			for(int i = gap; i < values.length; i++)
			{
				//shift values until their correct position is met
				int j = i;
				int temp = values[i];
				for(j = i; j >= gap && values[j - gap] > temp; j-= gap)
					values[j] = values[j - gap];
				values[j] = temp;
			}
		}
		
		return values;
	}
	
	private Stack<Integer> sequence(int length)
	{
		Stack<Integer> result = new Stack<Integer>();
		
		int index = 1;
		int lastSeq = 1;
		
		//generate sequences while result allows us to compare 2 values, so half of the length
		while(lastSeq < ((int)(length / 2)))
		{
			result.push(lastSeq);
			
			//Papernov & Stasevich, 1965 formula for sequence generation 2^k + 1
			lastSeq = (int)Math.pow(2, index++) + 1;
		}
		
		return result;
	}
}
