package Core.Info;

public class AutoSim implements Cloneable
{
	public AutoSimMode Mode;
	public byte Increment;
	public int Simulations;

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return this;
	}
}
