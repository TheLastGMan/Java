package Application.Controls.User;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Application.Controls.BaseTable;
import Application.Events.FPSEventService;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public class UserList extends BaseTable
{
	private static final long serialVersionUID = -7341219167224863911L;
	private static Service.Repository.User _UserRepository = new Service.Repository.User();
	
	/**
	 * Create the panel.
	 */
	public UserList()
	{
		super("User Accounts");
	}
	
	@Override
	public void CellClicked(int row, int col)
	{
		int pilotId = (int)table.getModel().getValueAt(row, 0);
		if (col == 4)
			// edit
			FPSEventService.ShowPanelOnMaster(new UserEditAdmin(pilotId));
		else if (col == 5)
			// delete check
			if (JOptionPane.showConfirmDialog(null, "This will delete all users data in the system." + System.lineSeparator() + "Are you sure?", "Delete",
					JOptionPane.INFORMATION_MESSAGE | JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
			_UserRepository.Delete(pilotId);
			fillTableData();
			}
	}
	
	@Override
	public int[] ButtonColumns()
	{
		return new int[] { 4, 5 };
	}
	
	@Override
	public List<ColumnHeader> ColumnHeaders()
	{
		// "Pilot Id", "Username", "First Name", "Last Name", ""
		ArrayList<ColumnHeader> cols = new ArrayList<>();
		cols.add(new ColumnHeader("Pilot Id", 0.10, false, false));
		cols.add(new ColumnHeader("Username", 0.20, false, false));
		cols.add(new ColumnHeader("First Name", 0.25, false, false));
		cols.add(new ColumnHeader("Last Name", 0.25, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		cols.add(new ColumnHeader("", 0.10, false, false));
		return cols;
	}
	
	@Override
	public ArrayList<Object[]> FillTable()
	{
		ArrayList<Service.Model.UserSummary> users = new Service.Repository.User().UserSummary();
		ArrayList<Object[]> rows = new QList<>(users).Select(us -> new Object[] { us.PilotId, us.Username, us.FirstName, us.LastName, "Edit", "Delete" }).ToList();
		return rows;
	}
}
