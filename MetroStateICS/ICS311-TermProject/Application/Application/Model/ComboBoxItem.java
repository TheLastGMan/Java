package Application.Model;

public class ComboBoxItem extends KeyValuePair<Integer, String>
{
	
	public ComboBoxItem(Integer key, String value)
	{
		super(key, value);
	}
	
	@Override
	public String toString()
	{
		return Value;
	}
}
