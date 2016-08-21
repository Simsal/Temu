package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ConfigurationInformation;

import com.fazecast.jSerialComm.SerialPort;

import controller.CommunicationInterface;
import controller.CommunicationMethodsImplementation;

public class SimulationWindow implements ActionListener {

	JFrame window;
	JPanel firstGrid;
	JLabel simulationStateLabel, simulationState, actualConfiguration,
			simulation, deviceNameLabel, deviceName, descriptionLabel,
			description, measurementStartLabel, measurementStart,
			unityMeasurementStartLabel, unityMeasurementStart,
			measurementEndingLabel, measurementEnding, actualMeasurementLabel,
			actualMeasurement, emptyLabel0, emptyLabel1, emptyLabel2,
			emptyLabel3, emptyLabel4, emptyLabel5, emptyLabel6, emptyLabel7,
			emptyLabel8, emptyLabel9, emptyLabel10, emptyLabel11;
	JTextField simulatedMeasurementValue;
	JButton cancel, startSimulation, stopSimulation;

	SerialPort connectedPort;
	CommunicationInterface communicationInterface = new CommunicationMethodsImplementation();

	ConfigurationInformation localModel = new ConfigurationInformation();

	public SimulationWindow(SerialPort chosenPort) {

		localModel = communicationInterface.readConfiguration(chosenPort);

		connectedPort = chosenPort;

		window = new JFrame("Simulation von "
				+ connectedPort.getDescriptivePortName());
		window.setLayout(new GridLayout(8, 4));

		firstGrid = new JPanel();

		simulationStateLabel = new JLabel(
				"<html><b style='float:left'>Simulation: </b></html>");
		firstGrid.add(simulationStateLabel);

		simulationState = new JLabel(
				"<html><p style='color:#04B431'>Nicht aktiv</p></html>");
		simulationState.setBorder(new EmptyBorder(10, 10, 10, 10));
		firstGrid.add(simulationState);

		window.add(firstGrid);

		actualConfiguration = new JLabel("Aktuelle Konfiguration");
		Font font = actualConfiguration.getFont();
		font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT,
				TextAttribute.WEIGHT_BOLD));
		actualConfiguration.setFont(font);
		window.add(actualConfiguration);

		simulation = new JLabel("Simulation");
		simulation.setFont(font);
		window.add(simulation);

		emptyLabel0 = new JLabel("");
		window.add(emptyLabel0);

		descriptionLabel = new JLabel("Beschreibung");
		descriptionLabel.setFont(font);
		descriptionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(descriptionLabel);

		description = new JLabel(connectedPort.getDescriptivePortName());
		window.add(description);

		emptyLabel2 = new JLabel("");
		window.add(emptyLabel2);

		ImageIcon image2 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel2 = new JLabel(image2, JLabel.CENTER);
		infoLabel2.setToolTipText("Verbundenes Gerät.");
		window.add(infoLabel2);

		measurementStart = new JLabel("Messbereichsanfang [°C]");
		measurementStart.setFont(font);
		measurementStart.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(measurementStart);

		measurementStartLabel = new JLabel(
				localModel.getMeasurementValueStart());
		window.add(measurementStartLabel);

		emptyLabel3 = new JLabel("");
		window.add(emptyLabel3);

		ImageIcon image3 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel3 = new JLabel(image3, JLabel.CENTER);
		infoLabel3.setToolTipText("Konfigurierter Messbereichsanfang in °C.");
		window.add(infoLabel3);

		measurementEndingLabel = new JLabel("Messbereichsende [°C]");
		measurementEndingLabel.setFont(font);
		measurementEndingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(measurementEndingLabel);

		measurementEnding = new JLabel(localModel.getMeasurementValueEnd());
		window.add(measurementEnding);

		emptyLabel5 = new JLabel("");
		window.add(emptyLabel5);

		ImageIcon image5 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel5 = new JLabel(image5, JLabel.CENTER);
		infoLabel5.setToolTipText("Konfiguriertes Messbereichsende in °C.");
		window.add(infoLabel5);

		actualMeasurementLabel = new JLabel("Aktueller Messwert [°C]");
		actualMeasurementLabel.setFont(font);
		actualMeasurementLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(actualMeasurementLabel);

		actualMeasurement = new JLabel(
				communicationInterface.getCurentMeasurementValue(connectedPort));
		window.add(actualMeasurement);

		simulatedMeasurementValue = new JTextField();
		window.add(simulatedMeasurementValue);

		ImageIcon image7 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel7 = new JLabel(image7, JLabel.CENTER);
		infoLabel7
				.setToolTipText("<html>Links wird der empfangene Messwert angezeigt. <br> "
						+ "In das Textfeld kann der zu simulierende Messwert eingegeben werden.<br>"
						+ "Es wird über die Stromschnittstelle keine Diagnoseinformation übertragen.</html>");
		window.add(infoLabel7);

		emptyLabel7 = new JLabel("");
		window.add(emptyLabel7);

		emptyLabel8 = new JLabel("");
		window.add(emptyLabel8);

		emptyLabel9 = new JLabel("");
		window.add(emptyLabel9);

		emptyLabel10 = new JLabel("");
		window.add(emptyLabel10);

		emptyLabel11 = new JLabel("");
		window.add(emptyLabel11);

		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		window.add(cancel);

		startSimulation = new JButton("Messwert simulieren");
		startSimulation.addActionListener(this);
		window.add(startSimulation);

		stopSimulation = new JButton("Simulation stoppen");
		stopSimulation.addActionListener(this);
		stopSimulation.setEnabled(false);
		window.add(stopSimulation);

		window.getRootPane().setDefaultButton(startSimulation);

		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if ((JButton) e.getSource() == cancel) {
			window.dispose();
		}
		if ((JButton) e.getSource() == startSimulation) {
			if (simulatedMeasurementValue.getText().isEmpty()) {
				JOptionPane.showMessageDialog(new JOptionPane(),
						"Bitte geben Sie einen Wert zum simulieren an.");
			} else {
				try {
					communicationInterface.newSimulation(connectedPort,
							simulatedMeasurementValue.getText());
					simulationState
							.setText("<html><p style='color:#F22C2C'>Aktiv</p></html>");
					simulatedMeasurementValue.setEnabled(false);
					startSimulation.setEnabled(false);
					stopSimulation.setEnabled(true);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(new JOptionPane(),
							"Bitte verwenden sie nur Zahlen.");
				}

			}

		}
		if ((JButton) e.getSource() == stopSimulation) {
			if (communicationInterface.stopSimulation(connectedPort)) {
				simulatedMeasurementValue.setEnabled(true);
				startSimulation.setEnabled(true);
				stopSimulation.setEnabled(false);
				simulationState
						.setText("<html><p style='color:#04B431'>Nicht aktiv</p></html>");
			} else {
				JOptionPane.showMessageDialog(new JOptionPane(),
						"Fehler beim Beenden der Simulation!");
			}

		}

	}

}
