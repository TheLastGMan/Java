package Core;

import java.util.*;
import Core.Info.*;

public class Simulator
{
	public ArrayList<SimMultiResult> SimulateMultiple(SimInfo info, AutoSim auto)
	{
		if(info == null || auto == null)
			return null;
		
		ArrayList<SimMultiResult> result = new ArrayList<SimMultiResult>();
		while(auto.Simulations-- != 0)
		{
			SimInfo simCopy = (SimInfo)info.clone();
			result.add(new SimMultiResult(Simulate(simCopy), simCopy));
			
			//update simulation info base on sim settings
			switch(auto.Mode)
			{
				case Arrival:
					info.setArrivalRatePercent((byte)(info.ArrivalRatePercent() + auto.Increment));
					break;
				case Departure:
					info.setDepartureRatePercent((byte)(info.DepartureRatePercent() + auto.Increment));
					break;
				case ArrivalTime:
					info.setMinsToLand((byte)(info.MinsToLand() + auto.Increment));
					break;
				case DepartureTime:
					info.setMinsToTakeoff((byte)(info.MinsToTakeoff() + auto.Increment));
					break;
				case RerouteTime:
					info.setMinsTimeRemaining((short)(info.MinsTimeRemaining() + auto.Increment));
					break;
				case Runway:
					info.setRunwaysAvailable((byte)(info.RunwaysAvailable() + auto.Increment));
					break;
				default:
					break;
			}
		}
		
		return result;
	}
	
	public SimResult Simulate(SimInfo info)
	{
		//validate
		if(info.SimulationTime() <= 0)
			return null;
		
		//setup
		Queue<AirplaneSimInfo> approachArrivals = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> towerDepartures = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> landingQueue = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> takeoffQueue = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> landings = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> takeoffs = new LinkedList<AirplaneSimInfo>();
		Queue<AirplaneSimInfo> rerouted = new LinkedList<AirplaneSimInfo>();
		
		//Progress States
		//Landing (units elapsed) -> Landing
		//Takeoff (units elapsed) -> Takeoffs
		//Arrivals (update wait and time left) -> Rerouted
		//Departures (update wait)
		//generate new planes based on percentages
		//assign runway slot(s) [arrivals > departures]
		
		short simUnits = info.SimulationTime();
		do
		{
			//update arrival/departures wait for planes in sequence
			for(int i = approachArrivals.size(); i != 0; i--)
			{
				AirplaneSimInfo airplane = approachArrivals.poll();
				airplane.WaitUnits++;
				
				//if no time remaining, reroute them to their filed alternate airport
				if(--airplane.TimeUnitsRemaining == 0)
					rerouted.add(airplane);
				else
					approachArrivals.add(airplane);
			}
			for(AirplaneSimInfo airplane : towerDepartures)
				airplane.WaitUnits++;
			
			//update progress of those taking off and landing
			for(int i = 0; i < landingQueue.size(); i++)
			{
				AirplaneSimInfo airplane = landingQueue.poll();
				
				//if land time elapsed, transfer them over to ground control
				if(--airplane.ActionUnitsRemaining == 0)
					landings.add(airplane);
				else
					landingQueue.add(airplane);
			}
			for(int i = 0; i < takeoffQueue.size(); i++)
			{
				AirplaneSimInfo airplane = takeoffQueue.poll();
				
				//if take-off time elapsed (successful takeoff), transfer them over to departure control
				if(--airplane.ActionUnitsRemaining == 0)
					takeoffs.add(airplane);
				else
					takeoffQueue.add(airplane);
			}
			
			//generate random arrival/departures (assuming this covers both scheduled IFR and unscheduled VFR traffic)
			byte rand = (byte)new Random().nextInt(100);
			if(rand < info.ArrivalRatePercent())
			{
				AirplaneSimInfo asia = new AirplaneSimInfo();
				asia.ActionUnitsRemaining = info.MinsToLand();
				asia.TimeUnitsRemaining = info.MinsTimeRemaining();
				approachArrivals.add(asia);
			}
			rand = (byte)new Random().nextInt(100);
			if(rand < info.DepartureRatePercent())
			{
				AirplaneSimInfo asid = new AirplaneSimInfo();
				asid.ActionUnitsRemaining = info.MinsToTakeoff();
				towerDepartures.add(asid);
			}
			
			//assign runway slots (landings have priority)
			//landing assignments (transferred over to tower and cleared to land)
			int openSlots = info.RunwaysAvailable() - (landingQueue.size() + takeoffQueue.size());
			int landingSlots = Math.min(openSlots, approachArrivals.size());
			openSlots -= landingSlots;
			while(landingSlots-- != 0)
			{
				AirplaneSimInfo requestLanding = approachArrivals.poll();
				approachArrivals.remove(0);
				landingQueue.add(requestLanding);
			}
			//takeoff assignments (cleared for takeoff)
			int takeoffSlots = Math.min(openSlots, towerDepartures.size());
			openSlots -= takeoffSlots;
			while(takeoffSlots-- != 0)
			{
				AirplaneSimInfo requestTakeoff = towerDepartures.poll();
				towerDepartures.remove(0);
				takeoffQueue.add(requestTakeoff);
			}
		} while (--simUnits != 0);
		
		//create result statistics
		SimResult result = new SimResult();
		
		result.setArrivals((short)landings.size());
		result.setDepartures((short)takeoffs.size());
		result.setRerouted((short)rerouted.size());
		result.setAveArrivalWaitTime(round((airplaneTotalWait(landings) + airplaneTotalWait(landingQueue)) / (double)(landings.size() + landingQueue.size()), (byte)4));
		result.setAveDepartureWaitTime(round((airplaneTotalWait(takeoffs) + airplaneTotalWait(takeoffQueue)) / (double)(takeoffs.size() + takeoffQueue.size()), (byte)4));
		
		return result;
	}
	
	private double round(double value, byte places)
	{
		//soft validate
		if(places < 0)
			places = 0;
		
		double power = Math.pow(10,  places);
		return Math.round(value * power) / power;
	}
	
	private int airplaneTotalWait(Queue<AirplaneSimInfo> airplanes)
	{
		int sum = 0;
		for(int i = 0; i < airplanes.size(); i ++)
		{
			AirplaneSimInfo airplane = airplanes.poll();
			sum += airplane.WaitUnits;
			airplanes.add(airplane);
		}
		return sum;
	}
}
