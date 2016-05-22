package gui;


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

import com.fazecast.jSerialComm.SerialPort;

public class searchPluggedInDevice implements ActionListener {
	ArrayList<String> foundDevices = new ArrayList<String>();
	SerialPort[] ports;
	JFrame window;
	JLabel description;
	JComboBox<String> listOfDevices;
	JButton cancel, connectToDevice, refreshComboBox;
	JPanel panel;
	JOptionPane errorMessage;

	public searchPluggedInDevice() {
		window = new JFrame("Ergebnis Ger�tesuche");

		window.setLayout(new GridLayout(2, 2));

		description = new JLabel("Folgende Ger�te wurden gefunden:");
		window.add(description);

		listOfDevices = new JComboBox<String>();
		fillComboBox();

		panel = new JPanel();
		panel.add(listOfDevices);

		refreshComboBox = new JButton("Aktualisieren");
		refreshComboBox.addActionListener(this);
		panel.add(refreshComboBox);

		window.add(panel);

		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		window.add(cancel);

		connectToDevice = new JButton("Verbinden");
		connectToDevice.addActionListener(this);
		window.add(connectToDevice);

		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		if ((JButton) e.getSource() == cancel) {
			window.dispose();
		}
		if ((JButton) e.getSource() == connectToDevice) {
			connectToChosenDevice();
		}

	}

	private void connectToChosenDevice() {
		SerialPort serialPort = ports[listOfDevices.getSelectedIndex()];
		if(serialPort.openPort()){
			System.out.println( serialPort.getDescriptivePortName() +" opened successfully.");
			new ConfigurationWindow(serialPort);
		}
		else {
			errorMessage = new JOptionPane("Port konnte nicht erreicht werden. Bitte nochmal versuchen.");
			System.out.println("Unable to open the port.");
			return;
		}
	}

}
