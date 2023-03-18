package App;
import org.junit.Test;
import Core.Simulator;
import Core.Info.SimInfo;
import Core.Info.SimResult;

public class SimulateTest {

	@Test
	public void Simulate()
	{
		SimInfo si = new SimInfo();
		
		System.out.println("Landing Time: " + si.MinsToLand());
		System.out.println("Takeoff Time: " + si.MinsToTakeoff());
		System.out.println("Arrival %   : " + si.ArrivalRatePercent());
		System.out.println("Departure % : " + si.DepartureRatePercent());
		System.out.println("Max delay   : " + si.MinsTimeRemaining());
		System.out.println("Runways     : " + si.RunwaysAvailable());
		System.out.println("Sim Time    : " + si.SimulationTime() + " min");
		System.out.println("-------------");
		
		Simulator s = new Simulator();
		SimResult sr = s.Simulate(si);
		
		System.out.println("Takeoffs : " + sr.Departures());
		System.out.println("Landings : " + sr.Arrivals());
		System.out.println("Rerouted : " + sr.Rerouted());
		System.out.println("Ave Takeoff Delay : " + sr.AveDepartureWaitTime() + " min");
		System.out.println("Ave Landing Delay : " + sr.AveArrivalWaitTime() + " min");
	}
	
	@Test
	public void SimulateMultiple()
	{
		
	}

}
