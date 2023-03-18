package Core.Info;

public class SimResult
{
	//Planes that crashed (how control would ever this happen is another story)
	public short Rerouted()
	{
		return _Rerouted;
	}
	public void setRerouted(short value)
	{
		//soft validate
		if(value < 0)
			value = 0;
		_Rerouted = value;
	}
	private short _Rerouted;
	
	//Airport arrivals
	public short Arrivals()
	{
		return _Arrivals;
	}
	public void setArrivals(short value)
	{
		//soft validate
		if(value < 0)
			value = 0;
		_Arrivals = value;
	}
	private short _Arrivals;
	
	//Airport departures
	public short Departures()
	{
		return _Departures;
	}
	public void setDepartures(short value)
	{
		//soft validate
		if(value < 0)
			value = 0;
		_Departures = value;
	}
	private short _Departures;
	
	//Average wait for departure
	public double AveDepartureWaitTime()
	{
		return _AveDepartureWaitTime;
	}
	public void setAveDepartureWaitTime(double value)
	{
		//soft validate
		if(value < 0)
			value = 0;
		_AveDepartureWaitTime = value;
	}
	private double _AveDepartureWaitTime;
	
	//Average wait for arrival
	public double AveArrivalWaitTime()
	{
		return _AveArrivalWaitTime;
	}
	public void setAveArrivalWaitTime(double value)
	{
		//soft validate
		if(value < 0)
			value = 0;
		_AveArrivalWaitTime = value;
	}
	private double _AveArrivalWaitTime;
}
