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

public class StateComboBox extends JPanel
{
	private static final long serialVersionUID = 4823197684499463775L;
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();
	private JComboBox<ComboBoxItem> comboBoxState;
	
	/**
	 * Create the panel.
	 */
	public StateComboBox()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		comboBoxState = new JComboBox<>();
		comboBoxState.setMaximumRowCount(8);
		comboBoxState.setEditable(true);
		comboBoxState.getEditor().getEditorComponent().addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent event)
			{
				@SuppressWarnings("rawtypes")
				String entry = ((JTextComponent)((JComboBox)((Component)event.getSource()).getParent()).getEditor().getEditorComponent()).getText();
				fillStateDropdown(entry, false, -1);
			}
		});
		add(comboBoxState);
		
		// load initial states
		fillStateDropdown("", true, -1);
	}
	
	public int getStateId()
	{
		return comboBoxState.getSelectedIndex() == -1 ? -1 : ((ComboBoxItem)comboBoxState.getSelectedItem()).Key;
	}
	
	public void setStateId(int id)
	{
		fillStateDropdown("", true, id);
	}
	
	private void fillStateDropdown(String beginsWith, boolean autoSelect, int withId)
	{
		// clear states
		comboBoxState.removeAllItems();
		
		// load states that begin with filter
		//@formatter:off
		QList<ComboBoxItem> states = _StateRepository.Table()
				.OrderBy(f -> f.Abbreviation)
				.Select(f -> new ComboBoxItem(f.Id, f.Name));
		//@formatter:on
		
		// check for filtering
		if (withId != -1 && states.Any(f -> f.Key == withId))
			states = states.Where(f -> f.Key == withId);
		else if (!beginsWith.equals(""))
			states = states.Where(f -> f.Value.startsWith(beginsWith));
		
		// fill combo box
		states.ForEach(f -> comboBoxState.addItem(f));
		
		// check for states
		if (autoSelect && comboBoxState.getItemCount() > 0)
			comboBoxState.setSelectedIndex(0);
	}
}
