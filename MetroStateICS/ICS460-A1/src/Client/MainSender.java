package Client;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import Core.*;

/**
 * Sender GUI for custom packet sender
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class MainSender extends JFrame {
	private static final long serialVersionUID = 3115158790829762690L;
	
	private MessageQueue messageQueue = new MessageQueue();
	private PacketSender activePacketSender = null;
	
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
	private JLabel lblWindowSize;
	private JSpinner spinnerWindowSize;
	private JLabel lblTimeoutMs;
	private JSpinner spinnerTimeoutMs;
	private JLabel lblMessageSizeBytes;
	private JSpinner spinnerMessageSizeBytes;
	private JLabel lblStatusDelay;
	private JSpinner spinnerStatusMessageDelay;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainSender frame = new MainSender();
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
	public MainSender() throws Exception {
		setTitle("ICS460 - Project (Sender)");
		// setup event listeners
		setupListeners();
		
		// create display
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(875, 640, 875, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 200, 250, 0 };
		gbl_panel.rowHeights = new int[] { 30, 30, 30, 30, 0, 0, 0, 30, 30 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		lblHostname = new JLabel("Server Hostname : ");
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
		
		lblPortNumber = new JLabel("Server Port : ");
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
		
		lblWindowSize = new JLabel("Window Size : ");
		GridBagConstraints gbc_lblWindowSize = new GridBagConstraints();
		gbc_lblWindowSize.anchor = GridBagConstraints.EAST;
		gbc_lblWindowSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblWindowSize.gridx = 0;
		gbc_lblWindowSize.gridy = 3;
		panel.add(lblWindowSize, gbc_lblWindowSize);
		
		spinnerWindowSize = new JSpinner();
		spinnerWindowSize.setModel(new SpinnerNumberModel(1, 1, Runtime.getRuntime().availableProcessors() * 2, 1));
		GridBagConstraints gbc_spinnerWindowSize = new GridBagConstraints();
		gbc_spinnerWindowSize.anchor = GridBagConstraints.WEST;
		gbc_spinnerWindowSize.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerWindowSize.gridx = 1;
		gbc_spinnerWindowSize.gridy = 3;
		panel.add(spinnerWindowSize, gbc_spinnerWindowSize);
		
		lblTimeoutMs = new JLabel("Timeout (ms) : ");
		GridBagConstraints gbc_lblTimeoutMs = new GridBagConstraints();
		gbc_lblTimeoutMs.anchor = GridBagConstraints.EAST;
		gbc_lblTimeoutMs.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeoutMs.gridx = 0;
		gbc_lblTimeoutMs.gridy = 4;
		panel.add(lblTimeoutMs, gbc_lblTimeoutMs);
		
		spinnerTimeoutMs = new JSpinner();
		spinnerTimeoutMs.setModel(new SpinnerNumberModel(2000, 500, 120000, 25));
		GridBagConstraints gbc_spinnerTimeoutMs = new GridBagConstraints();
		gbc_spinnerTimeoutMs.anchor = GridBagConstraints.WEST;
		gbc_spinnerTimeoutMs.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerTimeoutMs.gridx = 1;
		gbc_spinnerTimeoutMs.gridy = 4;
		panel.add(spinnerTimeoutMs, gbc_spinnerTimeoutMs);
		
		lblMessageSizeBytes = new JLabel("Message Size Bytes (MSS) : ");
		GridBagConstraints gbc_lblMessageSizeBytes = new GridBagConstraints();
		gbc_lblMessageSizeBytes.anchor = GridBagConstraints.EAST;
		gbc_lblMessageSizeBytes.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessageSizeBytes.gridx = 0;
		gbc_lblMessageSizeBytes.gridy = 5;
		panel.add(lblMessageSizeBytes, gbc_lblMessageSizeBytes);
		
		spinnerMessageSizeBytes = new JSpinner();
		spinnerMessageSizeBytes.setModel(new SpinnerNumberModel(500, 1, 1460, 1));
		GridBagConstraints gbc_spinnerMessageSizeBytes = new GridBagConstraints();
		gbc_spinnerMessageSizeBytes.anchor = GridBagConstraints.WEST;
		gbc_spinnerMessageSizeBytes.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerMessageSizeBytes.gridx = 1;
		gbc_spinnerMessageSizeBytes.gridy = 5;
		panel.add(spinnerMessageSizeBytes, gbc_spinnerMessageSizeBytes);
		
		lblStatusDelay = new JLabel("Status Message Delay (ms) : ");
		GridBagConstraints gbc_lblStatusDelay = new GridBagConstraints();
		gbc_lblStatusDelay.anchor = GridBagConstraints.EAST;
		gbc_lblStatusDelay.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatusDelay.gridx = 0;
		gbc_lblStatusDelay.gridy = 6;
		panel.add(lblStatusDelay, gbc_lblStatusDelay);
		
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
		gbc_spinnerStatusMessageDelay.gridy = 6;
		panel.add(spinnerStatusMessageDelay, gbc_spinnerStatusMessageDelay);
		
		fileBrowser = new FileBrowser();
		fileBrowser.setText("Input File");
		fileBrowser.setCheckFileExists(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 7;
		panel.add(fileBrowser, gbc_textField);
		
		lblCompIndicator = new JLabel("");
		GridBagConstraints gbc_lblCompIndicator = new GridBagConstraints();
		gbc_lblCompIndicator.insets = new Insets(0, 0, 0, 5);
		gbc_lblCompIndicator.gridx = 0;
		gbc_lblCompIndicator.gridy = 8;
		panel.add(lblCompIndicator, gbc_lblCompIndicator);
		
		btnBindListener = new JButton("Send File");
		btnBindListener.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendFile();
				}
				catch (Exception ex) {
					displayMessage("Error: " + ex.getMessage());
				}
			}
		});
		GridBagConstraints gbc_btnBindListener = new GridBagConstraints();
		gbc_btnBindListener.gridwidth = 2;
		gbc_btnBindListener.gridx = 0;
		gbc_btnBindListener.gridy = 8;
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
	 * Sends the file across the network
	 *
	 * @throws Exception
	 *             Error
	 */
	private void sendFile() throws Exception {
		// quick validate
		if (fileBrowser.getFile().getPath().length() == 0) {
			displayMessage("Input file must be specified");
			return;
		}
		else if (textFieldHostName.getText().length() == 0) {
			displayMessage("Hostname must be specified");
			return;
		}
		
		// startup sender
		if (activePacketSender != null) {
			activePacketSender.stop();
			activePacketSender.PacketMessageEventHandler.removeHandler(packetMessage());
			activePacketSender.PacketTxCompletedEventHandler.removeHandler(packetSendComplete());
		}
		
		// setup packet sender info
		PacketSetupInfo info = new PacketSetupInfo(textFieldHostName.getText(), (int)spinnerPortNumber.getValue(),
				((double)spinnerErrorPercent.getValue()) / 100, (int)spinnerWindowSize.getValue(),
				(int)spinnerTimeoutMs.getValue(), (int)spinnerMessageSizeBytes.getValue(),
				new StreamReader(fileBrowser.getFile()));
		
		// assign sender and map events
		activePacketSender = new PacketSender(info);
		activePacketSender.PacketMessageEventHandler.addHandler(packetMessage());
		activePacketSender.PacketTxCompletedEventHandler.addHandler(packetSendComplete());
		
		messageQueue.clear();
		textPaneMessage.setText("");
		lblCompIndicator.setText("");
		activePacketSender.start();
		displayMessage("Sending packets");
	}

	/**
	 * Set up basic event listeners
	 */
	private void setupListeners() {
		// process packet data
		Logging.debug("setting event listeners");
		messageQueue.DataMessage.addHandler((eventArgs) -> {
			textPaneMessage.setText(eventArgs.message + System.lineSeparator() + textPaneMessage.getText());
		});
	}
	
	/**
	 * Handle packet messages
	 *
	 * @return Event-based method
	 */
	private Consumer<PacketMessageEventArgs> packetMessage() {
		return (pkt) -> {
			StringBuffer sb = new StringBuffer(64);
			switch (pkt.Action) {
				case SENDING:
				case RESENDING:
					// packet action
					sb.append((pkt.Action == PacketAction.SENDING) ? "SENDing" : "ReSend.");
					sb.append(" | ");
					
					// sequence number
					sb.append(Common.numberToString(pkt.Packet.seqno, 10));
					sb.append(" | ");
					
					// byte sequence carried [start offset]:[end offset]
					int startOffset = (pkt.Packet.seqno - 1) * (int)spinnerMessageSizeBytes.getValue();
					int endOffset = startOffset + pkt.Packet.data.length - 1;
					if (endOffset < startOffset) {
						// EOF packet
						sb.append(" END OF TRANSMISSION ");
					}
					else {
						sb.append(Common.numberToString(startOffset, 10));
						sb.append(":");
						sb.append(Common.numberToString(endOffset, 10));
					}
					sb.append(" | ");
					
					// sent time time
					sb.append(pkt.SentAtTime);
					sb.append(" | ");
					
					// packet condition
					switch (pkt.AckStatus) {
						case DROP:
							sb.append("DROP");
							break;
						case SENT:
							sb.append("SENT");
							break;
						case ERROR:
							sb.append("ERR ");
							break;
						case DELAYED:
							sb.append("DLYD");
							break;
						default:
							sb.append(" UNK");
							break;
					}
					
					break;
				case ACK_RECEIVED:
					// packet action
					sb.append("AckRcvd | ");
					
					// sequence number
					sb.append(Common.numberToString(pkt.Packet.seqno, 10));
					sb.append(" | ");
					
					// acknowledgement status
					// RECEIVED, DUPLICATE, DUPLICATE, ERROR, MOVE_WINDOW, NA
					switch (pkt.Status) {
						case DUPLICATE:
							sb.append("DuplAck");
							break;
						case ERROR:
							sb.append("ErrAck.");
							break;
						case MOVE_WINDOW:
							sb.append("MoveWnd");
							break;
						case RECEIVED:
							sb.append("Okay");
							break;
						default:
							sb.append("UNK");
							break;
					}
					break;
				case TIMEOUT:
					// packet action
					sb.append("TimeOut | ");
					
					// sequence number
					sb.append(Common.numberToString(pkt.Packet.seqno, 10));
					
					break;
				default:
					sb.append("UNKNOWN PACKET ACTION");
					break;
			}
			
			// show generated message
			displayMessage(sb.toString());
		};
	}
	
	/**
	 * Handles file TX complete event
	 *
	 * @return Event-based method
	 */
	private Consumer<CompletedEventArgs> packetSendComplete() {
		return (args) -> {
			lblCompIndicator.setText("X");
			displayMessage("Transmission Complete");
		};
	}
	
	private void displayMessage(String message) {
		Logging.info(message);
		messageQueue.addMessage(message);
	}
}
