import java.io.Serializable;
import java.util.*;

/**
 * Represents a collection of keys and values.
 * Based on Microsoft's System.Collections.Generic.Dictionary
 *
 * @author Ryan Gau
 * @version 1.0-ICS440A1
 * @param TKey
 *            The type of the keys in the dictionary.
 * @param TValue
 *            The type of the values in the dictionary.
 */
public class Dictionary<TKey extends Comparable<TKey>, TValue>
		implements Iterable<KeyValuePair<TKey, TValue>>, Serializable {

	private static final long serialVersionUID = -5511211048713190342L;
	private HashMap<TKey, TValue> _hashtable = null;
	
	public Dictionary() {
		this(0);
	}
	
	public Dictionary(int capacity) {
		// validate
		if (capacity < 0) {
			capacity = 0;
		}
		
		// setup
		_hashtable = new HashMap<>(capacity);
	}
	
	/**
	 * Gets the number of key/value pairs contained in the Dictionary(Of TKey,
	 * TValue).
	 *
	 * @return The number of key/value pairs contained in the Dictionary(Of
	 *         TKey, TValue).
	 */
	public int Count() {
		return _hashtable.size();
	}

	/**
	 * Gets the value associated with the specified key.
	 *
	 * @param key
	 *            The key of the value to get
	 * @return value associated with the specified key. if the key is not found,
	 *         and an exception is thrown
	 * @throws Exception
	 *             Error
	 */
	public TValue Get(TKey key) throws Exception {
		TValue value = _hashtable.get(key);
		if (value == null) {
			throw new Exception("Key not found");
		}
		
		return _hashtable.get(key);
	}
	
	public void Set(TKey key, TValue value) throws Exception {
		if (key == null) {
			throw new Exception("Key must be specified");
		}
		if (value == null) {
			throw new Exception("Value must be speicifed");
		}
		
		// add or update
		if (ContainsKey(key)) {
			_hashtable.replace(key, value);
		}
		else {
			Add(key, value);
		}
	}
	
	/**
	 * Adds the specified key and value to the dictionary.
	 *
	 * @param key
	 *            The key of the element to add.
	 * @param value
	 *            The value of the element to add. The value can be null for
	 *            reference types.
	 */
	public void Add(TKey key, TValue value) {
		_hashtable.put(key, value);
	}

	/**
	 * Removes all keys and values from the Dictionary(Of TKey, TValue).
	 */
	public void Clear() {
		_hashtable.clear();
	}

	/**
	 * Determines whether the Dictionary(Of TKey, TValue) contains the specified
	 * key.
	 *
	 * @param key
	 *            The key to locate
	 * @return T/F if the dictionary contains the specified key
	 */
	public boolean ContainsKey(TKey key) {
		return _hashtable.containsKey(key);
	}
	
	/**
	 * Determines whether the Dictionary(Of TKey, TValue) contains a specific
	 * value.
	 *
	 * @param value
	 *            The value to locate, can be null for reference types
	 * @return T/F if the dictionary contains an element with the specified
	 *         value
	 */
	public boolean ContainsValue(TValue value) {
		return _hashtable.containsValue(value);
	}
	
	/**
	 * @return Creates a shallow copy of the current Object
	 * @throws Exception
	 *             Error
	 */
	@SuppressWarnings("unchecked")
	public Dictionary<TKey, TValue> MemberwiseClone() throws Exception {
		return (Dictionary<TKey, TValue>)super.clone();
	}
	
	/**
	 * Removes the value with the specified key from the Dictionary(Of TKey,
	 * TValue).
	 *
	 * @param key
	 *            The key of the element to remove.
	 */
	public boolean Remove(TKey key) {
		return _hashtable.remove(key) != null;
	}

	/**
	 * Gets the value associated with the specified key.
	 *
	 * @param key
	 *            The key of the value to get.
	 * @return returns the value for the key, if the key is found;
	 *         otherwise, returns null
	 */
	public TValue TryGetValue(TKey key) {
		return _hashtable.get(key);
	}
	
	/**
	 * Returns an iterator that can go through the Dictionary(Of TKey, TValue)
	 */
	@Override
	public Iterator<KeyValuePair<TKey, TValue>> iterator() {
		return new DictionaryIterator();
	}
	
	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#SyncRoot()
	 */
	public Object SyncRoot() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#IsSynchronized()
	 */
	public boolean IsSynchronized() {
		return false;
	}

	private class DictionaryIterator implements Iterator<KeyValuePair<TKey, TValue>> {
		private Iterator<TKey> baseIterator = _hashtable.keySet().iterator();
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return baseIterator.hasNext();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public KeyValuePair<TKey, TValue> next() {
			TKey current = baseIterator.next();
			return new KeyValuePair<>(current, TryGetValue(current));
		}
	}
}
