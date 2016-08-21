package view;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.ConfigurationInformation;

import com.fazecast.jSerialComm.SerialPort;

import controller.CommunicationInterface;
import controller.CommunicationMethodsImplementation;

public class OverviewWindow implements ActionListener {
	SerialPort chosenPort;
	JFrame window;
	JButton configurate, simulate, refresh, stopConfiguration;
	JLabel deviceLabel, device, measurementLabel, measurement, statusLabel,
			status, emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4,
			emptyLabel5;
	JPanel tempPanel;

	ConfigurationInformation localModel;
	CommunicationInterface communicationInterface = new CommunicationMethodsImplementation();

	public OverviewWindow(SerialPort serialport) {
		chosenPort = serialport;
		window = new JFrame("Konfiguration von "
				+ chosenPort.getDescriptivePortName());
		window.setLayout(new GridLayout(6, 3));

		deviceLabel = new JLabel("<html><b>Verbundenes Gerät</b></html>");
		deviceLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(deviceLabel);

		device = new JLabel(chosenPort.getDescriptivePortName());
		window.add(device);

		ImageIcon image1 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel1 = new JLabel(image1, JLabel.CENTER);
		infoLabel1
				.setToolTipText("Mit diesem Gerät ist derzeit eine Verbindung hergestellt. ");
		window.add(infoLabel1);

		measurementLabel = new JLabel(
				"<html><b>Aktueller Messwert [°C]</b></html>");
		measurementLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(measurementLabel);

		measurement = new JLabel(getCurrentReading());
		window.add(measurement);

		ImageIcon image2 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel2 = new JLabel(image2, JLabel.CENTER);
		infoLabel2
				.setToolTipText("Der gemessene Temperaturwert. Kann über den Button 'Werte aktualisieren' aktualisiert werden.");
		window.add(infoLabel2);

		statusLabel = new JLabel("<html><b>Status</b></html>");
		statusLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(statusLabel);

		status = new JLabel(getStatus());

		window.add(status);

		ImageIcon image3 = new ImageIcon(
				"src/main/resources/images/questionmark.jpg");
		JLabel infoLabel3 = new JLabel(image3, JLabel.CENTER);
		infoLabel3
				.setToolTipText("<html>Der ausgelesene Fehlerstatus. Kann über den Button 'Werte aktualisieren' aktualisiert werden.<br>"
						+ "Mögliche Fehlerstati: OK, Kabelbruch Sensor, Kurzschluss Sensor, Messbereichsüber-/unterschreitung</html>");

		window.add(infoLabel3);

		emptyLabel1 = new JLabel("");
		window.add(emptyLabel1);

		emptyLabel2 = new JLabel("");
		window.add(emptyLabel2);

		emptyLabel3 = new JLabel("");
		window.add(emptyLabel3);

		configurate = new JButton("Konfiguration vornehmen");
		configurate.addActionListener(this);
		window.add(configurate);

		simulate = new JButton("Simulation");
		simulate.addActionListener(this);

		window.add(simulate);

		refresh = new JButton("Werte aktualisieren");
		refresh.addActionListener(this);
		window.add(refresh);

		emptyLabel4 = new JLabel("");
		window.add(emptyLabel4);

		emptyLabel5 = new JLabel("");
		window.add(emptyLabel5);

		stopConfiguration = new JButton("Konfigurationsprogramm schließen");
		stopConfiguration.addActionListener(this);
		window.add(stopConfiguration);

		window.pack();
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setVisible(true);

	}

	private String getStatus() {
		String statusValue = communicationInterface.getStatus(chosenPort);

		return statusValue;
	}

	private String getCurrentReading() {
		return communicationInterface.getCurentMeasurementValue(chosenPort);
	}

	public void actionPerformed(ActionEvent e) {
		if ((JButton) e.getSource() == configurate) {
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new ChangeConfigurationWindow(chosenPort);
			window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
		if ((JButton) e.getSource() == simulate) {
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			new SimulationWindow(chosenPort);
			window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		if ((JButton) e.getSource() == refresh) {
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			measurement.setText(communicationInterface
					.getCurentMeasurementValue(chosenPort));
			status.setText(communicationInterface.getStatus(chosenPort));
			window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
		if ((JButton) e.getSource() == stopConfiguration) {
			if (communicationInterface.closeTMU(chosenPort) == true) {
				System.exit(0);
			}
		}
	}

}
