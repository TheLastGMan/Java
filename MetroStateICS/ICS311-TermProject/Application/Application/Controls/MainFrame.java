package Application.Controls;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Application.Controls.Account.SignIn;
import Application.Controls.Airport.AirportList;
import Application.Controls.Flight.FlightList;
import Application.Controls.Flight.FlightListAdmin;
import Application.Controls.State.StateList;
import Application.Controls.User.UserEdit;
import Application.Controls.User.UserList;
import Application.Events.FPSEventService;
import Application.Events.IFPSEvents;
import Service.Model.User;

public class MainFrame extends JFrame implements IFPSEvents, Data.Events.IDBEvent
{
	private static final long serialVersionUID = -3332748369196358458L;
	private JPanel mainView;
	private JMenuItem mntmLogIn;
	private JMenuItem mntmSignOut;
	private int _UserId = 0;
	private JLabel lblMessage;
	private JMenu mnAccount;
	private JMenu mnManage;

	private final Service.Repository.User _UserRepository = new Service.Repository.User();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 *
	 * @throws java.lang.Exception
	 */
	public MainFrame() throws java.lang.Exception
	{
		initialize();
		lblMessage.setText("Welcome Pilot, to the FAA Flight Planning System");
		
		// add event listeners
		Data.Events.DBEventService.AddListener(this);
		FPSEventService.AddListener(this);
		
		// set up database
		Application.Setup.DBSetup();
		
		// show default control
		Logout();
	}
	
	private void initialize()
	{
		setTitle("FAA Flight Planing System (FPS)");
		setResizable(false);
		setBounds(860, 640, 860, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem homeItem = new JMenuItem("Home");
		homeItem.addActionListener((ActionEvent arg0) -> changePanel(new FlightList()));
		mnFile.add(homeItem);
		
		mntmLogIn = new JMenuItem("Sign In");
		mntmLogIn.addActionListener((ActionEvent arg0) -> changePanel(new SignIn()));
		mnFile.add(mntmLogIn);
		
		mntmSignOut = new JMenuItem("Sign Out");
		mntmSignOut.addActionListener((ActionEvent arg0) -> FPSEventService.Logout());
		mntmSignOut.setVisible(false);
		mnFile.add(mntmSignOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener((ActionEvent arg0) -> dispose());
		mnFile.add(mntmExit);
		
		mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener((ActionEvent arg0) -> changePanel(new UserEdit(_UserId)));
		mnAccount.add(mntmInfo);
		
		JMenuItem mntmFlightPlans = new JMenuItem("Flight Plans");
		mntmFlightPlans.addActionListener((ActionEvent arg0) -> changePanel(new Application.Controls.Flight.FlightListUser(_UserId)));
		mnAccount.add(mntmFlightPlans);
		
		mnManage = new JMenu("Manage");
		menuBar.add(mnManage);
		
		JMenuItem mntmUsers = new JMenuItem("Users");
		mntmUsers.addActionListener((ActionEvent arg0) -> changePanel(new UserList()));
		
		JMenuItem mntmAirports = new JMenuItem("Airports");
		mntmAirports.addActionListener((ActionEvent arg0) -> changePanel(new AirportList()));
		mnManage.add(mntmAirports);
		
		JMenuItem mntmStates = new JMenuItem("States");
		mntmStates.addActionListener((ActionEvent arg0) -> changePanel(new StateList()));
		
		JMenuItem mntmFlights = new JMenuItem("Flights");
		mntmFlights.addActionListener((ActionEvent arg0) -> changePanel(new FlightListAdmin()));
		mnManage.add(mntmFlights);
		mnManage.add(mntmStates);
		mnManage.add(mntmUsers);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		mainView = new JPanel();
		getContentPane().add(mainView, BorderLayout.CENTER);
		mainView.setLayout(new GridLayout(1, 1, 0, 0));
		
		lblMessage = new JLabel("[Message]");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMessage, BorderLayout.SOUTH);
	}
	
	private void changePanel(JPanel panel)
	{
		if (panel != null)
		{
			mainView.removeAll();
			mainView.add(panel, BorderLayout.CENTER);
			mainView.revalidate();
			mainView.repaint();
		}
	}

	@Override
	public void LoginSuccessful(Service.Model.User user)
	{
		// switch login/logout visibility
		mntmLogIn.setVisible(false);
		mntmSignOut.setVisible(true);

		// show user menu items available
		_UserId = user.Id;
		mnAccount.setVisible(true);

		// check for administration menu items
		if (user.IsAdmin)
			mnManage.setVisible(true);
		
		// show user screen
		changePanel(new Application.Controls.Flight.FlightListUser(_UserId));
	}

	@Override
	public void Logout()
	{
		// switch login/logout visibility
		mntmLogIn.setVisible(true);
		mntmSignOut.setVisible(false);

		// hide user menu items
		mnAccount.setVisible(false);
		mnManage.setVisible(false);

		// show general screen
		lblMessage.setText("Welcome, Pilot, to the FAA Flight Planning System");
		changePanel(new FlightList());
	}

	@Override
	public void Exception(String message)
	{
		// DB Exception
		lblMessage.setText("Error: " + message);
	}

	@Override
	public User CurrentUser()
	{
		return _UserRepository.Get(_UserId);
	}

	@Override
	public void ShowPanelOnMaster(JPanel panel)
	{
		changePanel(panel);
	}
}
