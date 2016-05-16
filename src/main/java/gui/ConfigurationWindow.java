package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.fazecast.jSerialComm.SerialPort;

public class ConfigurationWindow implements ActionListener {
	SerialPort chosenPort;
	JFrame window;
	JButton configurate, simulate;
	JLabel deviceLabel, device,  measurementLabel, measurement, statusLabel, status;
	public ConfigurationWindow(SerialPort serialport) {
		chosenPort = serialport;
		window =  new JFrame("Konfiguration von " +chosenPort.getDescriptivePortName());
		window.setLayout(new GridLayout(4, 2));
		
		deviceLabel = new JLabel("Verbundenes Gerät");
		window.add(deviceLabel);
		
		device = new JLabel(chosenPort.getDescriptivePortName());
		window.add(device);
		
		measurementLabel = new JLabel("Aktueller Messwert");
		window.add(measurementLabel);
		
		measurement = new JLabel(getCurrentReading());
		window.add(measurement);
		
		statusLabel = new JLabel("Status");
		window.add(statusLabel);
		
		status = new JLabel(getStatus());
		window.add(status);
		
		configurate = new JButton("Konfiguration vornehmen");
		configurate.addActionListener(this);
		window.add(configurate);
		
		simulate = new JButton("Simulation");
		simulate.addActionListener(this);
		window.add(simulate);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);
	
	}
	private String getStatus() {
		// TODO Auto-generated method stub
		String getReading = "00000101";
		chosenPort.writeBytes(getReading.getBytes(), 8);
		return "Läuft bei uns!";
	}
	private String getCurrentReading() {
		// TODO Auto-generated method stub
		return "775";
	}
	public void actionPerformed(ActionEvent e) {
		if((JButton) e.getSource() == configurate){
			new ChangeConfigurationWindow();
		}
		if((JButton) e.getSource() == simulate){
			new SimulationWindow();
		}
		
	}

}
