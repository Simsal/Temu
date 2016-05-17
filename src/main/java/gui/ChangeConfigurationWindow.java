package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.fazecast.jSerialComm.SerialPort;

public class ChangeConfigurationWindow {

	JFrame window;
	JLabel actualConfiguration, futureConfiguration, unityMeasurementStart, measurementStart, unityMeasurementEnding,
			measurementEnding, failureBehavior, damping, unityActualValue, measurementStartActualValue,
			measurementEndingActualValue, failureBehaviorActualValue, dampingActualValue;
	JTextField unityFutureValue, measurementStartFutureValue, measurementEndingFutureValue, failureBehaviorFutureValue,
			dampingFutureValue;
	JButton cancel, configurate;

	public ChangeConfigurationWindow(SerialPort chosenPort) {

		window = new JFrame("Konfiguration von " + chosenPort.getDescriptivePortName());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

	}
}
