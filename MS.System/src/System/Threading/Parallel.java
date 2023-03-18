/**
 *
 */
package System.Threading;

import java.util.function.Consumer;

import System.Collections.Generic.List;
import System.Linq.*;
import System.PLinq.ParallelEnumerable;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class Parallel
{
	public static void For(int startIndex, int length, Consumer<Integer> action)
	{
		Enumerable.Range(startIndex, length).AsParallel().ForAll(idx -> action.accept(idx));
	}
	
	public static void For(long startIndex, long length, Consumer<Long> action)
	{
		List<Long> lst = new List<>();
		while (length-- > 0)
			lst.Add(startIndex++);
		
		new ParallelEnumerable<>(lst).ForAll(idx -> action.accept(idx));
	}
	
	public static <T> void ForEach(IEnumerable<T> collection, int startIndex, Consumer<T> action)
	{
		new List<>(collection).Skip(startIndex).AsParallel().ForAll(action);
	}
	
	public static <T> void ForEach(IEnumerable<T> collection, int startIndex, int endIndex, Consumer<T> action)
	{
		new List<>(collection).Skip(startIndex).Take(endIndex - startIndex).AsParallel().ForAll(action);
	}
	
	public static <T> void ForEach(IEnumerable<T> collection, Consumer<T> action)
	{
		new List<>(collection).AsParallel().ForAll(action);
	}
}
