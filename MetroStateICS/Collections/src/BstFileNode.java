import Generic.IBSTNode;

public class BstFileNode implements IBSTNode<BstFileNode>
{
	public BstFileNode(String value)
	{
		_word = value;
	}
	
	private final String _word;
	
	public String Word()
	{
		return _word;
	}
	
	private int _count = 1;
	
	public int Count()
	{
		return _count;
	}
	
	@Override
	public String toString()
	{
		return Word() + " (" + Count() + ")";
	}
	
	@Override
	public void DuplicateDetected()
	{
		_count++;
	}
	
	@Override
	public int compareTo(BstFileNode o)
	{
		return Word().compareTo(o.Word());
	}
}
