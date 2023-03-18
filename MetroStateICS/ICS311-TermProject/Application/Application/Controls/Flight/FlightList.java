package Application.Controls.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Application.Controls.BaseTable;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public class FlightList extends BaseTable
{
	private static final long serialVersionUID = -4475680229401547011L;
	private static final Service.Repository.FlightPlan _FlightPlanRepository = new Service.Repository.FlightPlan();
	
	// load general flight view
	public FlightList()
	{
		super("Active Flight Plans");
		
		// Create refresh timer, interval of 1 min, load latest flights
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				fillTableData();
			}
		}, 60 * 1000 /* initial run */, 60 * 1000 /* interval */);
	}
	
	@Override
	public void CellClicked(int row, int col)
	{}
	
	@Override
	public int[] ButtonColumns()
	{
		return new int[] {};
	}
	
	@Override
	public List<ColumnHeader> ColumnHeaders()
	{
		// "Ident", "Type", "From", "To", "Departure", "Arrival"
		ArrayList<ColumnHeader> cols = new ArrayList<>();
		cols.add(new ColumnHeader("Ident", 0.10, false, false));
		cols.add(new ColumnHeader("Type", 0.10, false, false));
		cols.add(new ColumnHeader("From", 0.25, false, false));
		cols.add(new ColumnHeader("To", 0.25, false, false));
		cols.add(new ColumnHeader("Departure", 0.15, false, false));
		cols.add(new ColumnHeader("Arrival", 0.15, false, false));
		return cols;
	}
	
	@Override
	public ArrayList<Object[]> FillTable()
	{
		// add flights to table view
		QList<Service.Model.FlightView> fvs = _FlightPlanRepository.loadFlightView(30);
		ArrayList<Object[]> rows = fvs.Select(fv -> new Object[] { fv.getIdent(), fv.getType(), fv.getSource(), fv.getDestination(), fv.getDepart(), fv.getArrive() }).ToList();
		return rows;
	}
}
