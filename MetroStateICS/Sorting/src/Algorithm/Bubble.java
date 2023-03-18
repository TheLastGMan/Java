package Algorithm;

public class Bubble implements ISortable
{
	@Override
	public String Name()
	{
		return "Bubble";
	}

	@Override
	public SortResult Sort(int[] values)
	{
		if(values.length <= 1)
			new SortResult(0, values);
		
		long start = System.currentTimeMillis();
		for(int i = 1; i < values.length; i++)
		{
			boolean swapped = false;
			for(int j = 1; j < values.length; j++)
				if(values[j] < values[j-1])
				{
					//swap
					int tmp = values[j];
					values[j] = values[j-1];
					values[j-1] = tmp;
					
					//mark swapped
					swapped = true;
				}
			
			//if we didn't swap, no need to continue
			if(!swapped)
				break;
		}
		
		long end = System.currentTimeMillis();
		double elapsed = end - start;
		return new SortResult(elapsed, values);
	}
}
