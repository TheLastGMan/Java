/**
 *
 */
package System.Collections;

import java.io.Serializable;
import java.util.*;

import System.Linq.Enumerable;

/**
 * Manages a compact array of bit values, which are represented as Booleans, where true indicates that the bit is on (1)
 * and false indicates the bit is off (0).
 * Based on Microsoft's System.Collections.BitArray
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class BitArray extends ICollection<Boolean> implements Serializable // ICloneable
{
	private static final long serialVersionUID = -6555236136251465753L;
	private BitSet _backing;

	// #Region Constructors

	public BitArray(BitArray value)
	{
		this(value.ToArray(Boolean.class));
	}

	public BitArray(Boolean values[])
	{
		// set up backing and set initial values
		_backing = new BitSet(values.length);
		for (int i = 0; i < values.length; i++)
			_backing.set(i, values[i]);
	}

	public BitArray(int numberOfBits)
	{
		this(numberOfBits, false);
	}

	public BitArray(int numberOfBits, boolean defaultValue)
	{
		this(Enumerable.Repeat(defaultValue, numberOfBits).ToArray(Boolean.class));
	}

	/**
	 * Convert into a Java BitSet
	 *
	 * @return
	 */
	public BitSet ToBitset()
	{
		return _backing;
	}

	// #EndRegion

	/**
	 * Get the value of the specified bit
	 *
	 * @param bit
	 * @return
	 */
	public boolean Get(int bit)
	{
		return _backing.get(bit);
	}

	/**
	 * Set the specified bit to the designated value
	 *
	 * @param bit
	 * @param value
	 */
	public void Set(int bit, boolean value)
	{
		_backing.set(bit, value);
	}

	/**
	 * Gets the number of elements in the BitArray
	 *
	 * @return Length
	 */
	public int Length()
	{
		return _backing.length();
	}

	/**
	 * Performs the bitwise AND operation between the elements of the current BitArray object and the corresponding
	 * elements in the specified array. The current BitArray object will be modified to store the result of the bitwise
	 * AND operation.
	 *
	 * @param value
	 * @return
	 */
	public BitArray And(BitArray value)
	{
		_backing.and(value.ToBitset());
		return this;
	}

	/**
	 * Returns an enumerator that iterates through the BitArray.
	 *
	 * @return
	 */
	public IEnumerator<Boolean> GetEnumerator()
	{
		return new BitArrayEnumerator();
	}

	/**
	 * Inverts all the bit values in the current BitArray, so that elements set to true are changed to false, and
	 * elements set to false are changed to true.
	 *
	 * @return
	 */
	public BitArray Not()
	{
		_backing.xor(new BitArray(Enumerable.Repeat(true, Length()).ToArray(Boolean.class)).ToBitset());
		return this;
	}

	/**
	 * Performs the bitwise OR operation between the elements of the current BitArray object and the corresponding
	 * elements in the specified array. The current BitArray object will be modified to store the result of the bitwise
	 * OR operation.
	 *
	 * @param value
	 * @return
	 */
	public BitArray Or(BitArray value)
	{
		_backing.or(value.ToBitset());
		return this;
	}

	/**
	 * Sets all bits in the BitArray to the specified value.
	 *
	 * @param value
	 */
	public void SetAll(boolean value)
	{
		_backing = new BitArray(Length(), value).ToBitset();
	}

	/**
	 * Performs the bitwise exclusive OR operation between the elements of the current BitArray object against the
	 * corresponding elements in the specified array. The current BitArray object will be modified to store the result
	 * of the bitwise exclusive OR operation.
	 *
	 * @param value
	 * @return
	 */
	public BitArray Xor(BitArray value)
	{
		_backing.xor(value.ToBitset());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#Count()
	 */
	@Override
	public int Count()
	{
		return _backing.size();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#SyncRoot()
	 */
	@Override
	public Object SyncRoot()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#IsSynchronized()
	 */
	@Override
	public boolean IsSynchronized()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Linq.IEnumerable#iterator()
	 */
	@Override
	public Iterator<Boolean> iterator()
	{
		return GetEnumerator();
	}

	private class BitArrayEnumerator implements IEnumerator<Boolean>
	{
		private int index = 0;

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			return index != _backing.size();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Boolean next()
		{
			return _backing.get(index++);
		}

		/*
		 * (non-Javadoc)
		 * @see System.Collections.Generic.IEnumerator#Reset()
		 */
		@Override
		public void Reset()
		{
			index = 0;
		}
	}
}
