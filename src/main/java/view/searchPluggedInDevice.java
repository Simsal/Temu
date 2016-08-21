package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;

public class searchPluggedInDevice implements ActionListener {
	ArrayList<String> foundDevices = new ArrayList<String>();
	SerialPort[] ports;
	JFrame window;
	JLabel description, description1, plugDevice, startConnection;
	JComboBox<String> listOfDevices;
	JButton connectToDevice, refreshComboBox;
	JPanel topPanel, bottomPanel, centerPanel, panel;
	JOptionPane errorMessage;

	public searchPluggedInDevice() {
		window = new JFrame("Konfigurationssoftware T-MU");

		window.setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		description1 = new JLabel("Derzeit ist kein Gerät verbunden. Stellen Sie eine Verbindung zu Ihrem Gerät her.");
		description1.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.add(description1, BorderLayout.PAGE_START);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		
		plugDevice = new JLabel("1. Gerät anschliesen.");
		plugDevice.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(plugDevice);
		
		startConnection = new JLabel("2. Verbindungssuche über Software starten.");
		startConnection.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.add(startConnection);
		
		topPanel.add(centerPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();

		description = new JLabel("Folgende Geräte wurden gefunden:");
		description.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.add(description);

		listOfDevices = new JComboBox<String>();
		fillComboBox();

		panel = new JPanel();
		panel.add(listOfDevices);

		refreshComboBox = new JButton("Aktualisieren");
		refreshComboBox.addActionListener(this);
		panel.add(refreshComboBox);

		bottomPanel.add(panel);

		connectToDevice = new JButton("Verbinden");
		connectToDevice.addActionListener(this);
		bottomPanel.add(connectToDevice);
		
		window.add(topPanel, BorderLayout.PAGE_START);
		window.add(bottomPanel, BorderLayout.SOUTH);
		window.getRootPane().setDefaultButton(connectToDevice);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

	}

	public void findDevices() {
		ports = SerialPort.getCommPorts();

		for (SerialPort port : ports) {
			foundDevices.add(port.getDescriptivePortName());
		}
	}
	
	public void fillComboBox(){
		listOfDevices.removeAllItems();
		foundDevices.clear();
		findDevices();
		for (String device : foundDevices) {
			listOfDevices.addItem(device);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if ((JButton) e.getSource() == refreshComboBox) {
			fillComboBox();
		}
		if ((JButton) e.getSource() == connectToDevice) {
			connectToChosenDevice();
		}

	}

	private void connectToChosenDevice() {
		SerialPort serialPort = ports[listOfDevices.getSelectedIndex()];
		if(serialPort.openPort()){
			System.out.println( serialPort.getDescriptivePortName() +" opened successfully.");
			new OverviewWindow(serialPort);
		}
		else {
			JOptionPane.showMessageDialog(new JOptionPane(), "Port konnte nicht erreicht werden. Bitte nochmal versuchen.");
			return;
		}
	}

}
