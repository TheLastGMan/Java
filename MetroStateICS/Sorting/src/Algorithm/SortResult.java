package Algorithm;

public class SortResult
{
	public double ElapsedMs = -1;
	public int[] Values;
	
	SortResult(double ms, int[] values)
	{
		ElapsedMs = ms;
		Values = values;
	}
}
