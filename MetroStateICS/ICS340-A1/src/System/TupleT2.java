package System;

/**
 * Represents a 2-tuple, or pair.
 * 
 * @author Ryan Gau
 * @param <T1>
 *            The type of the tuple's first component.
 * @param <T2>
 *            The type of the tuple's second component.
 */
public class TupleT2<T1, T2> extends TupleT1<T1>
{
	public final T2 Item2;
	
	public TupleT2(T1 item1, T2 item2)
	{
		super(item1);
		Item2 = item2;
	}
}
