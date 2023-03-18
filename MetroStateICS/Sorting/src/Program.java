import java.util.Arrays;
import Algorithm.*;

public class Program
{
	public static void main(String args[])
	{
		//setup
		int[] values = {30, 10, 15, 50, 99, 75, 35, 65, 31, 14, 18, 90, 47 };
		
		//Display
		System.out.println("Array Sorting Algorithms");
		System.out.println("");
		
		//Loop through methods
		ISortable[] methods = { new Insertion(), new Bubble(), new Shell(), new Merge() };
		for(ISortable method : methods)
		{
			System.out.println(method.Name());
			try
			{
				SortResult result = method.Sort(values.clone());
				System.out.println("Time (ms): " + result.ElapsedMs);
				System.out.println("Input:  " + String.join(", ", Arrays.toString(values)));
				System.out.println("Output: " + String.join(", ", Arrays.toString(result.Values)));
				
				//just so we know array has different values each time
				values[0] = values[0] + 10;
			}
			catch (Exception ex)
			{
				System.out.println("Error: " + ex.getMessage());
			}
			System.out.println("");
		}
	}
}
