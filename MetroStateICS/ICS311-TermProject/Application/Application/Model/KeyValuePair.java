package Application.Model;

public class KeyValuePair<K, V>
{
	public final K Key;
	public V Value;
	
	public KeyValuePair(K key, V value)
	{
		Key = key;
		Value = value;
	}
}
