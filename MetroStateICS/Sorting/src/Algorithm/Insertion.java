package Algorithm;


public class Insertion implements ISortable
{
	@Override
	public String Name()
	{
		return "Insertion";
	}

	@Override
	public SortResult Sort(int[] values)
	{
		if(values.length <= 1)
			return new SortResult(0, values);
		
		long start = System.currentTimeMillis();
		//move left to right
		for(int i = 1; i < values.length; i++)
		{
			int value = values[i];
			int j = i;
			while(j > 0 && values[j-1] > value)
				values[j] = values[--j];
			values[j] = value;
		}
		
		long end = System.currentTimeMillis();
		double elapsed = end - start;
		return new SortResult(elapsed, values);
	}
}
