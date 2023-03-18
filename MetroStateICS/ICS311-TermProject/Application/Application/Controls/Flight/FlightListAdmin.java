package Application.Controls.Flight;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Application.Controls.BaseTable;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public class FlightListAdmin extends BaseTable
{
	private static final long serialVersionUID = -5428190630470870528L;
	private static final Service.Repository.FlightPlan _FlightPlanService = new Service.Repository.FlightPlan();
	
	/**
	 * Create the panel.
	 */
	public FlightListAdmin()
	{
		super("Flight Plans");
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new FlightCreate());
			}
		});
		add(btnCreate, BorderLayout.SOUTH);
	}
	
	@Override
	public void CellClicked(int row, int col)
	{
		int planId = (int)table.getModel().getValueAt(row, 0);
		
		if (col == 7)
			Application.Events.FPSEventService.ShowPanelOnMaster(new FlightEdit(planId));
		else if (col == 8)
			// delete check
			if (JOptionPane.showConfirmDialog(null, "This will delete all information associated with this Flight Plan." + System.lineSeparator() + "Are you sure?", "Delete",
					JOptionPane.WARNING_MESSAGE | JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
			_FlightPlanService.Delete(planId);
			fillTableData();
			}
	}
	
	@Override
	public int[] ButtonColumns()
	{
		return new int[] { 7, 8 };
	}
	
	@Override
	public List<ColumnHeader> ColumnHeaders()
	{
		// "Ident", "Type", "From", "To", "Departure", "Arrival"
		ArrayList<ColumnHeader> cols = new ArrayList<>();
		cols.add(new ColumnHeader("Id", 0, false, false));
		cols.add(new ColumnHeader("Ident", 0.10, false, false));
		cols.add(new ColumnHeader("Type", 0.10, false, false));
		cols.add(new ColumnHeader("From", 0.20, false, false));
		cols.add(new ColumnHeader("To", 0.20, false, false));
		cols.add(new ColumnHeader("Departure", 0.15, false, false));
		cols.add(new ColumnHeader("Arrival", 0.15, false, false));
		cols.add(new ColumnHeader("", 0.05, false, false));
		cols.add(new ColumnHeader("", 0.05, false, false));
		return cols;
	}
	
	@Override
	public ArrayList<Object[]> FillTable()
	{
		QList<Service.Model.FlightView> flights = new Service.Repository.FlightPlan().loadFlightViewAdmin(0);
		ArrayList<Object[]> rows = flights
				.Select(fp -> new Object[] { fp.getId(), fp.getIdent(), fp.getType(), fp.getSource(), fp.getDestination(), fp.getDepart(), fp.getArrive(), "Edit", "Delete" })
				.ToList();
		return rows;
	}
}
