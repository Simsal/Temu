package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.fazecast.jSerialComm.SerialPort;

public class searchPluggedInDevice {
	String [] foundDevices;
	JFrame window;
	JLabel description;
	JComboBox<String> listOfDevices;
	JButton cancel, connectToDevice, refreshComboBox;
	
	public searchPluggedInDevice() {
		window = new JFrame("Ergebnis Gerätesuche");
		
		window.setLayout(new GridLayout(2, 2));
		
		description = new JLabel("Folgende Geräte wurden gefunden:");
		window.add(description);
		
		listOfDevices = new JComboBox<String>();
		findDevices();
		for(String device : foundDevices){
			listOfDevices.addItem(device);
		}
		
		
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);
		
	}
	public void findDevices(){
		SerialPort[] ports = SerialPort.getCommPorts();
		
		int i = 0;
//		for(SerialPort port : ports){
//			foundDevices[i] = port.getDescriptivePortName();
//			i++;
//		}
	}
	
	public static void main(String[] args) {
		new searchPluggedInDevice();
	}
}
