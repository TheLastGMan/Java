package System;

/**
 * Represents a 3-tuple, or triple.
 *
 * @author Ryan Gau
 * @param <T1>
 *            The type of the tuple's first component.
 * @param <T2>
 *            The type of the tuple's second component.
 * @param <T3>
 *            The type of the tuple's third component.
 */
public class TupleT3<T1, T2, T3> extends TupleT2<T1, T2>
{
	private static final long serialVersionUID = 4345308902300213103L;
	public final T3 Item3;

	public TupleT3(T1 item1, T2 item2, T3 item3)
	{
		super(item1, item2);
		Item3 = item3;
	}
}
