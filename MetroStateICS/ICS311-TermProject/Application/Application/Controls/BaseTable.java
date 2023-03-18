package Application.Controls;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import Application.Model.ButtonColumn;
import Application.Model.ColumnHeader;
import System.Linq.QList;

public abstract class BaseTable extends BaseView
{
	private static final long serialVersionUID = -885940637566962711L;
	private QList<ColumnHeader> _ColumnInfoQuery = new QList<ColumnHeader>(new ArrayList<>());
	public JTable table;
	
	// #Region "Table Helpers"
	
	public abstract void CellClicked(int row, int col);
	
	public abstract int[] ButtonColumns();
	
	public abstract List<ColumnHeader> ColumnHeaders();
	
	public abstract ArrayList<Object[]> FillTable();
	
	// #EndRegion
	
	public BaseTable(String title)
	{
		super(title);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelWrapper.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				int row = table.rowAtPoint(arg0.getPoint());
				int col = table.columnAtPoint(arg0.getPoint());
				if (row >= 0 && col >= 0)
					CellClicked(row, col);
			}
		});
		table.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

		// load columns
		List<ColumnHeader> columns = ColumnHeaders();
		if (columns != null)
			_ColumnInfoQuery = new QList<>(columns);
		
		// add columns to table
		addColumns();
		table.getTableHeader().setReorderingAllowed(false);

		// set column info
		setTableColumnAttributes();

		// set button columns
		setButtonColumns();

		// fill the table
		fillTableData();
	}
	
	private void addColumns()
	{
		table.setModel(new DefaultTableModel(new Object[][] {}, _ColumnInfoQuery.Select(f -> f.Header).ToArray(String.class))
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return _ColumnInfoQuery.ElementAtOrDefault(column).Editable;
			}
		});
	}
	
	private void setTableColumnAttributes()
	{
		ColumnHeader[] cols = _ColumnInfoQuery.ToArray(ColumnHeader.class);
		int tableWidth = table.getWidth();
		for (int i = 0; i < cols.length; i++)
		{
			// width of cell
			int cellWidth = percentFromWidth(tableWidth, cols[i].Width);
			// table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
			// table.getColumnModel().getColumn(i).setMaxWidth(cellWidth);
			table.getColumnModel().getColumn(i).setMinWidth(cellWidth);
			
			// other attributes
			table.getColumnModel().getColumn(i).setResizable(cols[i].Resizeable);
			table.getColumnModel().getColumn(i).setCellEditor(null);
		}
	}
	
	private int percentFromWidth(int max, double value)
	{
		if (value < 0)
			return 0;
		if (value > 1)
			value = 1;
		
		return (int)(max * value);
	}
	
	private void setButtonColumns()
	{
		int[] cols = ButtonColumns();
		if (cols != null)
			for (int c : cols)
				new ButtonColumn(table, null, c);
	}
	
	protected void ClearTable()
	{
		// setup model
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		
		// clear table view
		tableModel.setRowCount(0);
	}
	
	protected void fillTableData()
	{
		// clear table view
		ClearTable();
		
		// setup model
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		
		// load rows
		ArrayList<Object[]> rows = FillTable();
		if (rows != null)
			for (Object[] r : rows)
				tableModel.addRow(r);
	}
}
