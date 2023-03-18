import Generic.IBSTNode;

public class BSTComparatorInt implements IBSTNode<BSTComparatorInt>
{
	public BSTComparatorInt(int value)
	{
		_value = value;
	}
	
	private final int _value;
	
	public Integer Value()
	{
		return _value;
	}
	
	@Override
	public void DuplicateDetected()
	{}
	
	@Override
	public String toString()
	{
		return Value().toString();
	}
	
	@Override
	public int compareTo(BSTComparatorInt o)
	{
		return Value().compareTo(o.Value());
	}
}
