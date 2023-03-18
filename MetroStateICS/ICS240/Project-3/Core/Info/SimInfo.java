package Core.Info;

public class SimInfo implements Cloneable
{
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

	// Arrival rate percent (0-100)
	public byte ArrivalRatePercent()
	{
		return _ArrivalRatePercent;
	}
	
	public void setArrivalRatePercent(byte value)
	{
		// soft validate
		if (value > 100)
			value = 100;
		_ArrivalRatePercent = value;
	}
	
	private byte _ArrivalRatePercent = 45;

	// Departure rate percent (0-100)
	public byte DepartureRatePercent()
	{
		return _DepartureRatePercent;
	}
	
	public void setDepartureRatePercent(byte value)
	{
		// soft validate
		if (value > 100)
			value = 100;
		_DepartureRatePercent = value;
	}
	
	private byte _DepartureRatePercent = 20;

	// Time to land (mins)
	public byte MinsToLand()
	{
		return _MinsToLand;
	}
	
	public void setMinsToLand(byte value)
	{
		// soft validate
		if (value < 0)
			value = 0;
		_MinsToLand = value;
	}
	
	private byte _MinsToLand = 2;

	// Time to land (mins)
	public byte MinsToTakeoff()
	{
		return _MinsToTakeoff;
	}
	
	public void setMinsToTakeoff(byte value)
	{
		// soft validate
		if (value < 0)
			value = 0;
		_MinsToTakeoff = value;
	}
	
	private byte _MinsToTakeoff = 3;

	// Mins of fuel left
	public short MinsTimeRemaining()
	{
		return _MinsTimeRemaining;
	}
	
	public void setMinsTimeRemaining(short value)
	{
		// soft validate
		if (value < 0)
			value = 0;
		_MinsTimeRemaining = value;
	}
	
	private short _MinsTimeRemaining = 10;

	// Runways available at airport
	public byte RunwaysAvailable()
	{
		return _RunwaysAvailable;
	}
	
	public void setRunwaysAvailable(byte value)
	{
		// soft validate
		if (value < 0)
			value = 0;
		_RunwaysAvailable = value;
	}
	
	private byte _RunwaysAvailable = 1;

	// Simulation time in mins
	public short SimulationTime()
	{
		return _SimulationTime;
	}
	
	public void setSimulationTime(short value)
	{
		// soft validate
		if (value < 0)
			value = 0;
		_SimulationTime = value;
	}
	
	private short _SimulationTime = 600;
}
