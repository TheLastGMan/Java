package System;

import java.io.Serializable;

/**
 * Represents a 1-tuple, or singleton.
 *
 * @author Ryan Gau
 * @param <T1>
 *            The type of the tuple's only component.
 */
public class TupleT1<T1> implements Serializable
{
	private static final long serialVersionUID = 4345308902300213101L;
	public final T1 Item1;

	public TupleT1(T1 item1)
	{
		Item1 = item1;
	}
}
