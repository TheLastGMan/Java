package Application.Controls.Flight;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import Application.Controls.BaseTable;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public class FlightListUser extends BaseTable
{
	private static final long serialVersionUID = -6001209653500902028L;
	private int _userId = 0;

	/**
	 * Create the panel.
	 */
	public FlightListUser(int userId)
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
		_userId = userId;
		super.fillTableData();
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
		cols.add(new ColumnHeader("From", 0.30, false, false));
		cols.add(new ColumnHeader("To", 0.30, false, false));
		cols.add(new ColumnHeader("Departure", 0.10, false, false));
		cols.add(new ColumnHeader("Arrival", 0.10, false, false));
		return cols;
	}

	@Override
	public ArrayList<Object[]> FillTable()
	{
		QList<Service.Model.FlightView> flights = new Service.Repository.FlightPlan().loadFlightViewUser(0, _userId);
		ArrayList<Object[]> rows = flights.Select(fp -> new Object[] { fp.getIdent(), fp.getType(), fp.getSource(), fp.getDestination(), fp.getDepart(), fp.getArrive() }).ToList();
		return rows;
	}
}
