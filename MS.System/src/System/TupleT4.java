package System;

/**
 * Represents a 4-tuple, or quadruple.
 *
 * @author Ryan Gau
 * @param <T1>
 *            The type of the tuple's first component.
 * @param <T2>
 *            The type of the tuple's second component.
 * @param <T3>
 *            The type of the tuple's third component.
 * @param <T4>
 *            The type of the tuple's fourth component.
 */
public class TupleT4<T1, T2, T3, T4> extends TupleT3<T1, T2, T3>
{
	private static final long serialVersionUID = 4345308902300213104L;
	public final T4 Item4;

	public TupleT4(T1 item1, T2 item2, T3 item3, T4 item4)
	{
		super(item1, item2, item3);
		Item4 = item4;
	}
}
