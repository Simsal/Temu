package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;

import controller.CommunicationInterface;
import controller.CommunicationMethodsImplementation;
import model.ConfigurationInformation;

public class ChangeConfigurationWindow implements ActionListener {

	JFrame window;
	JLabel actualConfiguration, futureConfiguration, unityMeasurementStart, measurementStart, unityMeasurementEnding,
			measurementEnding, failureBehavior, damping, unityMeasurementStartActualValue, measurementStartActualValue,
			unityMeasurementEndingActualValue, measurementEndingActualValue, failureBehaviorActualValue,
			dampingActualValue;
	JTextField measurementStartFutureValue, measurementEndingFutureValue, dampingFutureValue;
	JComboBox<String> unityMeasurementStartFutureValue, unityMeasurementEndingFutureValue, failureBehaviorFutureValue;
	JButton cancel, configurate;

	CommunicationInterface communicationInterface = new CommunicationMethodsImplementation();

	SerialPort connectedPort;
	ConfigurationInformation newModel = new ConfigurationInformation();
	ConfigurationInformation localModel = new ConfigurationInformation();
	
	public ChangeConfigurationWindow(SerialPort chosenPort) {

		localModel = communicationInterface.readConfiguration(chosenPort);
		
		connectedPort = chosenPort;

		window = new JFrame("Konfiguration von " + chosenPort.getDescriptivePortName());
		window.setLayout(new GridLayout(5, 4));

		JLabel empty1 = new JLabel("");
		window.add(empty1);

		actualConfiguration = new JLabel("<html><b>Aktuelle Konfiguration</b></html>");
		window.add(actualConfiguration);

		futureConfiguration = new JLabel("<html><b>Neue Konfiguration</b></html>");
		window.add(futureConfiguration);

		JLabel empty2 = new JLabel("");
		window.add(empty2);

		measurementStart = new JLabel("<html><b>Messbereichsanfang [°C]</b></html>");
		measurementStart.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(measurementStart);

		measurementStartActualValue = new JLabel(localModel.getMeasurementValueStart());
		window.add(measurementStartActualValue);

		measurementStartFutureValue = new JTextField();
		window.add(measurementStartFutureValue);

		ImageIcon image1 = new ImageIcon("src/main/resources/images/questionmark.jpg");
		JLabel infoLabel1 = new JLabel(image1, JLabel.CENTER);
		infoLabel1.setToolTipText("Konfigurierbarer Messbereichsanfang in °C (Entspricht bei Übertragung 4 mA)");
		window.add(infoLabel1);

		measurementEnding = new JLabel("<html><b>Messbereichsende [°C]</b></html>");
		measurementEnding.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(measurementEnding);

		measurementEndingActualValue = new JLabel(localModel.getMeasurementValueEnd());
		window.add(measurementEndingActualValue);

		measurementEndingFutureValue = new JTextField();
		window.add(measurementEndingFutureValue);

		ImageIcon image3 = new ImageIcon("src/main/resources/images/questionmark.jpg");
		JLabel infoLabel3 = new JLabel(image3, JLabel.CENTER);
		infoLabel3.setToolTipText("Konfigurierbarer Messbereichsende in °C (Entspricht bei Übertragung 20 mA)");
		window.add(infoLabel3);

		failureBehavior = new JLabel("<html><b>Fehlerverhalten</b></html>");
		failureBehavior.setBorder(new EmptyBorder(10, 10, 10, 10));
		window.add(failureBehavior);

		failureBehaviorActualValue = new JLabel(localModel.getFailureBehavior());
		window.add(failureBehaviorActualValue);

		failureBehaviorFutureValue = new JComboBox<String>();
		failureBehaviorFutureValue.addItem("steigend");
		failureBehaviorFutureValue.addItem("fallend");
		window.add(failureBehaviorFutureValue);

		ImageIcon image5 = new ImageIcon("src/main/resources/images/questionmark.jpg");
		JLabel infoLabel5 = new JLabel(image5, JLabel.CENTER);
		infoLabel5.setToolTipText("<html> Konfigurierbares Fehlerverhalten. Definiert Verhalten bei negativen Diagnoseergebnis. <br>"
				+ "fallend: Bei Fehler werden 3,55 mA übertragen.<br>"
				+ "steigend: Bei Fehler werden 23 mA übertragen. </html>");
		window.add(infoLabel5);

		JLabel empty3 = new JLabel("");
		window.add(empty3);

		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		window.add(cancel);

		configurate = new JButton("Konfiguration abschicken");
		configurate.addActionListener(this);
		window.add(configurate);
		
		JLabel empty4 = new JLabel("");
		window.add(empty4);

		window.getRootPane().setDefaultButton(configurate);
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if ((JButton) e.getSource() == cancel) {
			window.dispose();
		}
		if ((JButton) e.getSource() == configurate) {
			try {
				
				if (Integer.parseInt(measurementStartFutureValue.getText())>=Integer.parseInt(measurementEndingFutureValue.getText())){
					JOptionPane.showMessageDialog(new JOptionPane(), "Der Messwertstart darf nicht größer als das Messwertende sein. Bitte korrigieren.");
				}
				else {
					
					fillNewModel();
					if (communicationInterface.newConfiguration(connectedPort, newModel) == true){
						JOptionPane.showMessageDialog(new JOptionPane(), "Konfiguration erfolgreich übertragen.");
						window.dispose();
					}
					else {
						JOptionPane.showMessageDialog(new JOptionPane(), "Fehler beim übertragen. Bitte versuchen Sie es erneut.");
					}
				}
				
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Bitte verwenden sie nur Zahlen.");
			}

		}
	}

	private void fillNewModel() {

		newModel.setFailureBehavior(failureBehaviorFutureValue.getSelectedItem().toString());
		newModel.setMeasurementValueEnd(measurementEndingFutureValue.getText());
		newModel.setMeasurementValueStart(measurementStartFutureValue.getText());
	}


}
