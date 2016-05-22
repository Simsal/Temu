package gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Startfenster {
	JFrame window;
	JLabel description1, plugDevice, startConnection;
	JPanel centerPanel;
	JButton searchDevices; 
	
	public Startfenster(String name) {
		window = new JFrame(name);
		window.setLayout(new BorderLayout());
		
		description1 = new JLabel("Derzeit ist kein Ger�t verbunden. Stellen Sie eine Verbindung zu Ihrem Ger�t her.");
		window.add(description1, BorderLayout.PAGE_START);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		
		plugDevice = new JLabel("1. Ger�t anschliesen.");
		centerPanel.add(plugDevice);
		
		startConnection = new JLabel("2. Verbindungssuche �ber Software starten.");
		centerPanel.add(startConnection);
		
		window.add(centerPanel, BorderLayout.CENTER);
		
		searchDevices = new JButton("Ger�t suchen");
		searchDevices.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new searchPluggedInDevice();
				
			}
		});
		window.add(searchDevices, BorderLayout.PAGE_END);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setSize(800, 300);
		window.pack();
		window.setVisible(true);
	}
	
	//delete after test
	public static void main(String[] args) {
		new Startfenster("Hallo");
	}

}
