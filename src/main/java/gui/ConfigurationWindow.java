package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;

public class ConfigurationWindow implements ActionListener {
	SerialPort chosenPort;
	JFrame window;
	JButton configurate, simulate, tempButton;
	JLabel deviceLabel, device,  measurementLabel, measurement, statusLabel, status;
	JPanel tempPanel;
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
		
		tempPanel = new JPanel();
		tempPanel.add(simulate);
//		window.add(simulate);
		
		tempButton = new JButton("auslesen");
		tempButton.addActionListener(this);
		tempPanel.add(tempButton);
		
		window.add(tempPanel);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);
	
	}
	private String getStatus() {
		// TODO Auto-generated method stub
		String getReading = "00000101";
		chosenPort.readBytes(getReading.getBytes(), 8);
		return "Läuft bei uns!";
	}
	private String getCurrentReading() {
		// TODO Auto-generated method stub
		return "775";
	}
	public void actionPerformed(ActionEvent e) {
		if((JButton) e.getSource() == configurate){
			new ChangeConfigurationWindow(chosenPort);
		}
		if((JButton) e.getSource() == simulate){
			new SimulationWindow(chosenPort);
		}
		
		//Delete
		if((JButton) e.getSource() == tempButton){
			Scanner data = new Scanner(chosenPort.getInputStream());
			System.out.println("start");
			System.out.println(data.hasNextLine());
			while(data.hasNextLine()){
				try{
					System.out.println(data.nextLine());
					}
				catch(Exception f){}
		}
		}
	}

}
