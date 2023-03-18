package Application.Controls.Modules;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import Application.Model.ComboBoxItem;
import System.Linq.QList;

public class AirportComboBox extends JPanel
{
	private static final long serialVersionUID = 1038656530716322976L;
	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();
	private JComboBox<ComboBoxItem> comboBoxAirport;

	/**
	 * Create the panel.
	 */
	public AirportComboBox()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		comboBoxAirport = new JComboBox<>();
		comboBoxAirport.setMaximumRowCount(8);
		comboBoxAirport.setEditable(true);
		comboBoxAirport.getEditor().getEditorComponent().addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent event)
			{
				@SuppressWarnings("rawtypes")
				String entry = ((JTextComponent)((JComboBox)((Component)event.getSource()).getParent()).getEditor().getEditorComponent()).getText();
				fillStateDropdown(entry, false, -1);
			}
		});
		add(comboBoxAirport);

		// load initial states
		fillStateDropdown("", true, -1);
	}

	public int getAirportId()
	{
		return comboBoxAirport.getSelectedIndex() == -1 ? -1 : ((ComboBoxItem)comboBoxAirport.getSelectedItem()).Key;
	}

	public void setAirportId(int id)
	{
		fillStateDropdown("", true, id);
	}

	private void fillStateDropdown(String beginsWith, boolean autoSelect, int withId)
	{
		// clear states
		comboBoxAirport.removeAllItems();

		// load states that begin with filter
		//@formatter:off
		QList<ComboBoxItem> states = _AirportRepository.Table()
				.OrderBy(f -> f.Code)
				.Select(f -> new ComboBoxItem(f.Id, f.Code));
		//@formatter:on

		// check for filtering
		if (withId != -1 && states.Any(f -> f.Key == withId))
			states = states.Where(f -> f.Key == withId);
		else if (!beginsWith.equals(""))
			states = states.Where(f -> f.Value.startsWith(beginsWith));
		
		// fill combo box
		states.ForEach(f -> comboBoxAirport.addItem(f));

		// check for states
		if (autoSelect && comboBoxAirport.getItemCount() > 0)
			comboBoxAirport.setSelectedIndex(0);
	}
}
