package Application.Controls.Airport;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Application.Controls.BaseTable;
import Application.Events.FPSEventService;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public class AirportList extends BaseTable
{
	private static final long serialVersionUID = -477851091088110400L;
	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();

	/**
	 * Create the panel.
	 */
	public AirportList()
	{
		super("Airports");

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new AirportCreate());
			}
		});
		panel.add(btnCreate);
	}

	@Override
	public void CellClicked(int row, int col)
	{
		int airportId = (int)table.getModel().getValueAt(row, 0);
		if (col == 3)
			// edit
			FPSEventService.ShowPanelOnMaster(new AirportEdit(airportId));
		else if (col == 4)
			// delete check
			if (JOptionPane.showConfirmDialog(null, "This will delete all information associated with this airport." + System.lineSeparator() + "Are you sure?", "Delete",
					JOptionPane.WARNING_MESSAGE | JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
			_AirportRepository.Delete(airportId);
			fillTableData();
			}
	}

	@Override
	public int[] ButtonColumns()
	{
		return new int[] { 3, 4 };
	}

	@Override
	public List<ColumnHeader> ColumnHeaders()
	{
		ArrayList<ColumnHeader> cols = new ArrayList<ColumnHeader>();
		cols.add(new ColumnHeader("Airport Id", 0.15, false, false));
		cols.add(new ColumnHeader("Code", 0.15, false, false));
		cols.add(new ColumnHeader("City, State", 0.50, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		return cols;
	}

	@Override
	public ArrayList<Object[]> FillTable()
	{
		QList<Service.Model.AirportSummary> airports = new Service.Repository.Airport().SummaryView();
		ArrayList<Object[]> rows = airports.Select(view -> new Object[] { view.Id, view.Code, view.City + ", " + view.StateAbbreviation, "Edit", "Delete" }).ToList();
		return rows;
	}
}
