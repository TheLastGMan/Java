package Application.Controls.State;

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

public class StateList extends BaseTable
{
	private static final long serialVersionUID = 1199329955699212324L;
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();

	/**
	 * Create the panel.
	 */
	public StateList()
	{
		super("State List");

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new StateCreate());
			}
		});
		panel.add(btnCreate);
	}

	@Override
	public void CellClicked(int row, int col)
	{
		int stateId = (int)table.getModel().getValueAt(row, 0);
		if (col == 3)
			// edit
			FPSEventService.ShowPanelOnMaster(new StateEditx(stateId));
		else if (col == 4)
			// delete check
			if (JOptionPane.showConfirmDialog(null, "This will delete all information associated with this state." + System.lineSeparator() + "Are you sure?", "Delete",
					JOptionPane.WARNING_MESSAGE | JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
			_StateRepository.Delete(stateId);
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
		cols.add(new ColumnHeader("State Id", 0.15, false, false));
		cols.add(new ColumnHeader("Abbr", 0.15, false, false));
		cols.add(new ColumnHeader("Name", 0.50, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		return cols;
	}

	@Override
	public ArrayList<Object[]> FillTable()
	{
		//@formatter:off
		QList<Service.Model.State> table = _StateRepository.Table();
		return table
				.OrderBy(s -> s.Abbreviation)
				.Select(st -> new Object[] { st.Id, st.Abbreviation, st.Name, "Edit", "Delete" })
				.ToList();
		//@formatter:on
	}
}
