package Core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import Core.Info.*;

public class Exporter
{
	private static final String _CsvHeader = "Arrival Rate %,Departure Rate %,Time to Land (min),Time to Takeoff (min),Time until Reroute (min),Runways,Simulation Time (min),Landings,Takeoffs,Reroutes,Approach Wait Ave. (min),Departure Wait Ave. (min)";
	
	public boolean SaveToFile(ArrayList<SimMultiResult> simResults, String fileName)
	{
		try
		{
			FileWriter fr = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fr);
			
			br.write(_CsvHeader);
			for(SimMultiResult result : simResults)
				br.write("\r\n" + simInfoToCsvLine(result.SimInfo) + "," + simResultToCsvLine(result.SimResult));
			
			br.flush();
			br.close();
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	
	public boolean SaveToFile(SimInfo info, SimResult simResult, String fileName)
	{
		try
		{
			FileWriter fr = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fr);
			
			br.write(_CsvHeader);
			br.write("\r\n" + simInfoToCsvLine(info) + "," + simResultToCsvLine(simResult));
			
			br.flush();
			br.close();
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	
	private String simInfoToCsvLine(SimInfo info)
	{
		if(info == null)
			return "";
		
		return String.format("%1$s,%2$s,%3$s,%4$s,%5$s,%6$s,%7$s", info.ArrivalRatePercent(), info.DepartureRatePercent(), info.MinsToLand(), info.MinsToTakeoff(), info.MinsTimeRemaining(), info.RunwaysAvailable(), info.SimulationTime());
	}
	
	private String simResultToCsvLine(SimResult result)
	{
		if(result == null)
			return "";
		
		return String.format("%1$s,%2$s,%3$s,%4$s,%5$s", result.Arrivals(), result.Departures(), result.Rerouted(), result.AveArrivalWaitTime(), result.AveDepartureWaitTime());
	}
	
}
