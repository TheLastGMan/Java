package Server;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import Core.*;

/**
 * Receiver GUI for custom packet reader
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class MainReceiver extends JFrame {
	private static final long serialVersionUID = 3115158790829762691L;
	
	private StreamWriter dataWriter = null;
	private ServerPacketProcessor packetReader = new ServerPacketProcessor();
	private ServerPacketListener packetService = new ServerPacketListener();
	private MessageQueue messageQueue = new MessageQueue();
	
	private JPanel contentPane;
	private JTextPane textPaneMessage;
	private JScrollPane scrollPane;
	private JLabel lblHostname;
	private JLabel lblPortNumber;
	private JSpinner spinnerPortNumber;
	private JTextField textFieldHostName;
	private JButton btnBindListener;
	private FileBrowser fileBrowser;
	private JLabel lblStatusMessage;
	private JSpinner spinnerErrorPercent;
	private JLabel lblCompIndicator;
	private JLabel lblStatusMessageDelay;
	private JSpinner spinnerStatusMessageDelay;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainReceiver frame = new MainReceiver();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 *
	 * @throws Exception
	 */
	public MainReceiver() throws Exception {
		setTitle("ICS460 - Project (Receiver)");
		// setup event listeners
		setupListeners();
		
		// create display
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(640, 640, 640, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 200, 250, 0 };
		gbl_panel.rowHeights = new int[] { 30, 30, 30, 0, 30, 30 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		lblHostname = new JLabel("Listen Hostname : ");
		GridBagConstraints gbc_lblHostname = new GridBagConstraints();
		gbc_lblHostname.anchor = GridBagConstraints.EAST;
		gbc_lblHostname.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostname.gridx = 0;
		gbc_lblHostname.gridy = 0;
		panel.add(lblHostname, gbc_lblHostname);
		
		textFieldHostName = new JTextField();
		textFieldHostName.setText("localhost");
		GridBagConstraints gbc_textFieldHostName = new GridBagConstraints();
		gbc_textFieldHostName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldHostName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostName.gridx = 1;
		gbc_textFieldHostName.gridy = 0;
		panel.add(textFieldHostName, gbc_textFieldHostName);
		textFieldHostName.setColumns(10);
		
		lblPortNumber = new JLabel("Listen Port : ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblPortNumber, gbc_lblNewLabel_1);
		
		spinnerPortNumber = new JSpinner();
		spinnerPortNumber.setModel(new SpinnerNumberModel(31415, 1, 65535, 1));
		GridBagConstraints gbc_spinnerPortNumber = new GridBagConstraints();
		gbc_spinnerPortNumber.anchor = GridBagConstraints.WEST;
		gbc_spinnerPortNumber.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPortNumber.gridx = 1;
		gbc_spinnerPortNumber.gridy = 1;
		panel.add(spinnerPortNumber, gbc_spinnerPortNumber);
		
		btnBindListener = new JButton("Update Packet Listener");
		btnBindListener.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					receiveFile();
				}
				catch (Exception ex) {
					displayMessage("Error: " + ex.getMessage());
				}
			}
		});
		
		lblStatusMessage = new JLabel("Packet Error Percentage : ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblStatusMessage, gbc_lblNewLabel_2);
		
		spinnerErrorPercent = new JSpinner();
		spinnerErrorPercent.setModel(new SpinnerNumberModel(0.0, 0.0, 99.0, 1.0));
		GridBagConstraints gbc_spinnerErrorPercent = new GridBagConstraints();
		gbc_spinnerErrorPercent.anchor = GridBagConstraints.WEST;
		gbc_spinnerErrorPercent.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerErrorPercent.gridx = 1;
		gbc_spinnerErrorPercent.gridy = 2;
		panel.add(spinnerErrorPercent, gbc_spinnerErrorPercent);
		
		lblStatusMessageDelay = new JLabel("Status Message Delay (ms) : ");
		GridBagConstraints gbc_lblStatusMessageDelay = new GridBagConstraints();
		gbc_lblStatusMessageDelay.anchor = GridBagConstraints.EAST;
		gbc_lblStatusMessageDelay.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatusMessageDelay.gridx = 0;
		gbc_lblStatusMessageDelay.gridy = 3;
		panel.add(lblStatusMessageDelay, gbc_lblStatusMessageDelay);
		
		spinnerStatusMessageDelay = new JSpinner();
		spinnerStatusMessageDelay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				messageQueue.setSleepTimeMilliseconds((long)spinnerStatusMessageDelay.getValue());
			}
		});
		spinnerStatusMessageDelay
				.setModel(new SpinnerNumberModel(new Long(50), new Long(1), new Long(5000), new Long(1)));
		GridBagConstraints gbc_spinnerStatusMessageDelay = new GridBagConstraints();
		gbc_spinnerStatusMessageDelay.anchor = GridBagConstraints.WEST;
		gbc_spinnerStatusMessageDelay.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerStatusMessageDelay.gridx = 1;
		gbc_spinnerStatusMessageDelay.gridy = 3;
		panel.add(spinnerStatusMessageDelay, gbc_spinnerStatusMessageDelay);
		
		fileBrowser = new FileBrowser();
		fileBrowser.setText("Output File");
		fileBrowser.setCheckFileExists(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		panel.add(fileBrowser, gbc_textField);
		
		lblCompIndicator = new JLabel("");
		GridBagConstraints gbc_lblCompIndicator = new GridBagConstraints();
		gbc_lblCompIndicator.insets = new Insets(0, 0, 0, 5);
		gbc_lblCompIndicator.gridx = 0;
		gbc_lblCompIndicator.gridy = 5;
		panel.add(lblCompIndicator, gbc_lblCompIndicator);
		GridBagConstraints gbc_btnBindListener = new GridBagConstraints();
		gbc_btnBindListener.gridwidth = 2;
		gbc_btnBindListener.gridx = 0;
		gbc_btnBindListener.gridy = 5;
		panel.add(btnBindListener, gbc_btnBindListener);
		
		textPaneMessage = new JTextPane();
		textPaneMessage.setEditable(false);
		textPaneMessage.setFont(new Font("Courier New", Font.BOLD, 18));
		scrollPane = new JScrollPane(textPaneMessage);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * @throws Exception
	 */
	private void receiveFile() throws Exception {
		// quick validate
		if (fileBrowser.getFile().getPath().length() == 0) {
			displayMessage("Output file must be specified");
			return;
		}
		else if (textFieldHostName.getText().length() == 0) {
			displayMessage("Hostname must be specified");
			return;
		}
		
		// close any existing file access
		if (dataWriter != null) {
			dataWriter.close();
		}

		// startup listener on new thread so we don't block this one
		dataWriter = new StreamWriter(fileBrowser.getFile(), true);
		packetService.port = (int)spinnerPortNumber.getValue();
		packetService.hostName = textFieldHostName.getText();
		packetService.errorPercentage = ((double)spinnerErrorPercent.getValue()) / 100;
		lblCompIndicator.setText("");
		textPaneMessage.setText("");
		startServices();
		displayMessage("Listening for packets");
	}
	
	private void startServices() {
		// setup packet processor
		Logging.debug("starting services");
		messageQueue.clear();
		packetReader.start();
		packetService.start();
	}
	
	private void setupListeners() {
		// process packet data
		Logging.debug("setting event listeners");
		packetReader.ProcessPacketEventHandler.addHandler((eventArgs) -> {
			Logging.debug("packet data event received");
			try {
				dataWriter.writeBytes(eventArgs.dataStream);
			}
			catch (IOException ex) {
				displayMessage("ERROR: " + ex.getMessage());
			}
		});
		
		// receive of data completed
		packetReader.PacketRxCompletedEventHandler.addHandler((eventArgs) -> {
			Logging.debug("packet receive complete event received");
			try {
				dataWriter.close();
				lblCompIndicator.setText("X");
				displayMessage("Receive Complete");
			}
			catch (Exception ex) {
				displayMessage("ERROR: " + ex.getMessage());
			}
		});
		
		// packet info received
		packetService.PacketInfoEventHandler.addHandler(packetMessage());
		packetReader.PacketInfoEventHandler.addHandler(packetMessage());
		
		// packet received
		packetService.PacketReceivedEventHandler.addHandler((eventArgs) -> {
			packetReader.addPacket(eventArgs.packet);
		});
		
		// message received from time-delay queue
		messageQueue.DataMessage.addHandler((eventArgs) -> {
			textPaneMessage.setText(eventArgs.message + System.lineSeparator() + textPaneMessage.getText());
		});
	}
	
	/**
	 * Handle packet messages
	 *
	 * @return Event-based method
	 */
	private Consumer<PacketInfoEventArgs> packetMessage() {
		return (eventArgs) -> {
			Logging.debug("packet info event received");
			StringBuffer sb = new StringBuffer(64);
			
			switch (eventArgs.PacketAction) {
				case RECEIVED:
					// status
					sb.append(eventArgs.IsDuplicate ? "DUPL" : "RECV");
					sb.append("     | ");
					
					// sequence number
					sb.append(Common.numberToString(eventArgs.PacketNumber, 10));
					sb.append(" | ");
					
					// received time
					sb.append(eventArgs.SentAtTime);
					sb.append(" | ");

					// packet condition
					switch (eventArgs.PacketCondition) {
						case CORRUPT:
							sb.append("CRPT");
							break;
						case OUT_OF_SEQUENCE:
							sb.append("!Seq");
							break;
						case RECEIVED:
							sb.append("RECV");
							break;
						default:
							sb.append("+UNK");
							break;
					}
					
					break;
				case SENDING:
					// status
					sb.append("SEND ACK");
					sb.append(" | ");
					
					// ACK number
					sb.append(Common.numberToString(eventArgs.PacketNumber, 10));
					sb.append(" | ");
					
					// sent time milliseconds
					sb.append(eventArgs.SentAtTime);
					sb.append(" | ");
					
					// ACK status
					switch (eventArgs.PacketAckStatus) {
						case DROP:
							sb.append("DROP");
							break;
						case SENT:
							sb.append("SENT");
							break;
						case ERROR:
							sb.append("ERR ");
							break;
						default:
							sb.append(" UNK");
							break;
					}
					
					break;
				default:
					sb.append("UNKNOWN ACTION");
					break;
			}
			
			displayMessage(sb.toString());
		};
	}
	
	private void displayMessage(String message) {
		Logging.info(message);
		messageQueue.addMessage(message);
	}
}
