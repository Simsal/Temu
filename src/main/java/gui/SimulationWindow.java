package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fazecast.jSerialComm.SerialPort;

public class SimulationWindow implements ActionListener {

	JFrame window;
	JPanel firstGrid;
	JLabel simulationStateLabel, simulationState, actualConfiguration, simulation, deviceNameLabel, deviceName,
			descriptionLabel, description, measurementStartLabel, measurementStart, unityMeasurementStartLabel,
			unityMeasurementStart, measurementEndingLabel, measurementEnding, unityMeasurementEndingLabel,
			unityMeasurementEnding, actualMeasurementLabel, actualMeasurement, emptyLabel1, emptyLabel2, emptyLabel3,
			emptyLabel4, emptyLabel5, emptyLabel6, emptyLabel7;
	JTextField simulatedMeasurementValue;
	JButton cancel, simulate;

	public SimulationWindow(SerialPort chosenPort) {

		window = new JFrame("Simulation" + chosenPort.getDescriptivePortName());
		window.setLayout(new GridLayout(9, 3));

		firstGrid = new JPanel();
		simulationStateLabel = new JLabel("Simulation");
		firstGrid.add(simulationStateLabel);

		simulationState = new JLabel("");
		firstGrid.add(simulationState);

		window.add(firstGrid);

		actualConfiguration = new JLabel("Aktuelle Konfiguration");
		window.add(actualConfiguration);

		simulation = new JLabel("Simulation");
		window.add(simulation);

		deviceNameLabel = new JLabel("Device-Name");
		window.add(deviceNameLabel);

		deviceName = new JLabel("");
		window.add(deviceName);

		emptyLabel1 = new JLabel("");
		window.add(emptyLabel1);

		descriptionLabel = new JLabel("Beschreibung");
		window.add(descriptionLabel);

		description = new JLabel("");
		window.add(description);

		emptyLabel2 = new JLabel("");
		window.add(emptyLabel2);

		measurementStart = new JLabel("Messbereichsanfang");
		window.add(measurementStart);

		measurementStartLabel = new JLabel("");
		window.add(measurementStartLabel);

		emptyLabel3 = new JLabel("");
		window.add(emptyLabel3);

		unityMeasurementStartLabel = new JLabel("Einheit");
		window.add(unityMeasurementStartLabel);

		unityMeasurementStart = new JLabel("");
		window.add(unityMeasurementStart);

		emptyLabel4 = new JLabel("");
		window.add(emptyLabel4);

		measurementEndingLabel = new JLabel("Messbereichsende");
		window.add(measurementEndingLabel);

		measurementEnding = new JLabel("");
		window.add(measurementEnding);

		emptyLabel5 = new JLabel("");
		window.add(emptyLabel5);

		unityMeasurementEndingLabel = new JLabel("Einheit");
		window.add(unityMeasurementEndingLabel);

		unityMeasurementEnding = new JLabel("");
		window.add(unityMeasurementEnding);

		emptyLabel6 = new JLabel("");
		window.add(emptyLabel6);

		actualMeasurementLabel = new JLabel("Aktueller Messwert");
		window.add(actualMeasurementLabel);

		actualMeasurement = new JLabel("");
		window.add(actualMeasurement);

		simulatedMeasurementValue = new JTextField();
		window.add(simulatedMeasurementValue);

		emptyLabel7 = new JLabel("");
		window.add(emptyLabel7);

		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		window.add(cancel);

		simulate = new JButton("Messwert simulieren");
		simulate.addActionListener(this);
		window.add(simulate);

		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);
	}

	private void sendSimulationData() {
		// Daten an resource schicken
		actualMeasurement.setText("1070");
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ((JButton) e.getSource() == cancel) {
			window.dispose();
		}
		if ((JButton) e.getSource() == simulate) {
			sendSimulationData();
		}
		
	}

}
