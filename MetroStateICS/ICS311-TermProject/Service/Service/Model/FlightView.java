package Service.Model;

public class FlightView
{
	private int _Id = 0;
	private String _Ident = "";
	private String _Type = "";
	private String _Source = "";
	private String _Destination = "";
	private String _Depart = "";
	private String _Arrive = "";
	
	// Flight Plan Id
	public int getId()
	{
		return _Id;
	}
	
	public void setId(int value)
	{
		_Id = value;
	}
	
	// Aircraft Identifier
	public String getIdent()
	{
		return _Ident;
	}
	
	public void setIdent(String value)
	{
		_Ident = value;
	}
	
	// Aircraft Type
	public String getType()
	{
		return _Type;
	}
	
	public void setType(String value)
	{
		_Type = value;
	}
	
	// Aircraft From Airport
	public String getSource()
	{
		return _Source;
	}
	
	public void setSource(String value)
	{
		_Source = value;
	}
	
	// Aircraft Destination Airport
	public String getDestination()
	{
		return _Destination;
	}
	
	public void setDestination(String value)
	{
		_Destination = value;
	}
	
	// Departure Time
	public String getDepart()
	{
		return _Depart;
	}
	
	public void setDepart(String value)
	{
		_Depart = value;
	}
	
	// Arrivate Time
	public String getArrive()
	{
		return _Arrive;
	}
	
	public void setArrive(String value)
	{
		_Arrive = value;
	}
}
