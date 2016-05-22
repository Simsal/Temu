package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.fazecast.jSerialComm.SerialPort;

public class ChangeConfigurationWindow implements ActionListener {

	JFrame window;
	JLabel actualConfiguration, futureConfiguration, unityMeasurementStart, measurementStart, unityMeasurementEnding,
			measurementEnding, failureBehavior, damping, unityMeasurementStartActualValue, measurementStartActualValue,
			unityMeasurementEndingActualValue, measurementEndingActualValue, failureBehaviorActualValue,
			dampingActualValue;
	JTextField measurementStartFutureValue, measurementEndingFutureValue, dampingFutureValue;
	JComboBox<String> unityMeasurementStartFutureValue, unityMeasurementEndingFutureValue, failureBehaviorFutureValue;
	JButton cancel, configurate;
	int getConfiguration = 00000001;
	int sendConfiguration = 00000010;
	

	 public ChangeConfigurationWindow(SerialPort chosenPort) {

		 window = new JFrame("Konfiguration von " +
		 chosenPort.getDescriptivePortName());
		window.setLayout(new GridLayout(9, 3));

		JLabel empty1 = new JLabel("");
		window.add(empty1);

		actualConfiguration = new JLabel("Aktuelle Konfiguration");
		window.add(actualConfiguration);

		futureConfiguration = new JLabel("Neue Konfiguration");
		window.add(futureConfiguration);

		measurementStart = new JLabel("Messbereichsanfang");
		window.add(measurementStart);

		measurementStartActualValue = new JLabel("");
		window.add(measurementStartActualValue);

		measurementStartFutureValue = new JTextField();
		window.add(measurementStartFutureValue);

		unityMeasurementStart = new JLabel("Einheit");
		window.add(unityMeasurementStart);

		unityMeasurementStartActualValue = new JLabel("");
		window.add(unityMeasurementStartActualValue);

		unityMeasurementStartFutureValue = new JComboBox<String>();
		unityMeasurementStartFutureValue.addItem("°C");
		unityMeasurementStartFutureValue.addItem("K");
		unityMeasurementStartFutureValue.addItem("F");
		window.add(unityMeasurementStartFutureValue);

		measurementEnding = new JLabel("Messbereichsende");
		window.add(measurementEnding);

		measurementEndingActualValue = new JLabel("");
		window.add(measurementEndingActualValue);

		measurementEndingFutureValue = new JTextField();
		window.add(measurementEndingFutureValue);

		unityMeasurementEnding = new JLabel("Einheit");
		window.add(unityMeasurementEnding);

		unityMeasurementEndingActualValue = new JLabel("");
		window.add(unityMeasurementEndingActualValue);

		unityMeasurementEndingFutureValue = new JComboBox<String>();
		unityMeasurementEndingFutureValue.addItem("°C");
		unityMeasurementEndingFutureValue.addItem("K");
		unityMeasurementEndingFutureValue.addItem("F");
		window.add(unityMeasurementEndingFutureValue);

		failureBehavior = new JLabel("Fehlerverhalten");
		window.add(failureBehavior);

		failureBehaviorActualValue = new JLabel("");
		window.add(failureBehaviorActualValue);

		failureBehaviorFutureValue = new JComboBox<String>();
		failureBehaviorFutureValue.addItem("steigend");
		failureBehaviorFutureValue.addItem("fallend");
		window.add(failureBehaviorFutureValue);

		damping = new JLabel("Dämpfung");
		window.add(damping);

		dampingActualValue = new JLabel("");
		window.add(dampingActualValue);

		dampingFutureValue = new JTextField();
		window.add(dampingFutureValue);

		JLabel empty2 = new JLabel("");
		window.add(empty2);

		cancel = new JButton("Abbrechen");
		cancel.addActionListener(this);
		window.add(cancel);

		configurate = new JButton("Konfiguration abschicken");
		configurate.addActionListener(this);
		window.add(configurate);
		
		getConfiguration();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if((JButton) e.getSource() == cancel){
			window.dispose();
		}
		if((JButton) e.getSource() == configurate){
			sendNewConfiguration();
		}
	}

	private void sendNewConfiguration() {
		// TODO Auto-generated method stub
		
	}
	
	private void getConfiguration() {
		// TODO Auto-generated method stub
		
	}

}
