package controller;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.ConfigurationInformation;

import com.fazecast.jSerialComm.SerialPort;

public class CommunicationMethodsImplementation implements
		CommunicationInterface {

	String readConfigurationCommand = "1";
	String getCurentMeasurementValueCommand = "2";
	String getStatusCommand = "3";
	String newConfigurationCommand = "4";
	String newSimulationCommand = "5";
	String stopSimulationCommand = "6";
	String stopTMUCommand = "7";

	public ConfigurationInformation readConfiguration(SerialPort connectedPort) {

		Scanner data = new Scanner(connectedPort.getInputStream());
		String response = null;

		ConfigurationInformation currentConfiguration = new ConfigurationInformation();
		int counter = 0;

		currentConfiguration.setMeasurementValueEndUnity("°C");
		currentConfiguration.setMeasurementValueStartUnity("°C");

		if (connectedPort.isOpen()) {
			if (connectedPort.writeBytes(
					readConfigurationCommand.getBytes(StandardCharsets.UTF_8),
					8) != -1) {
				System.out.println("Bytes geschrieben: "
						+ new String(readConfigurationCommand.getBytes(),
								StandardCharsets.UTF_8));
			} else {
				System.out.println("Fehler beim Bytes schreiben.");
			}

			try {
				while (connectedPort.bytesAvailable() == 0) {
					System.out.println("Verfügbare Bytes:"
							+ connectedPort.bytesAvailable());

					Thread.sleep(500);
				}
				System.out.println("nach Pause verfügbare Bytes: "
						+ connectedPort.bytesAvailable());
				response = data.nextLine();
				System.out.println("Antwort: "
						+ new String(response.getBytes(),
								StandardCharsets.UTF_8));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}

		if (response.equals("1")) {
			System.out.println("Antwort in readConfiguration ist true");

			while (data.hasNextLine()) {
				try {

					if (counter == 0) {
						String help = data.nextLine();
						System.out.println("help: " + help);
						currentConfiguration.setMeasurementValueStart(help);
						System.out.println("Messwertbeginn: "
								+ currentConfiguration
										.getMeasurementValueStart());
					}
					if (counter == 1) {
						currentConfiguration.setMeasurementValueEnd(data
								.nextLine());
					}
					if (counter == 2) {
						String message = data.nextLine();
						if (message.equals("1")) {
							currentConfiguration.setFailureBehavior("fallend");
						}
						if (message.equals("2")) {
							currentConfiguration.setFailureBehavior("steigend");
						}
					}
					counter++;
					TimeUnit.SECONDS.sleep(1);

				} catch (Exception e) {
				}
			}

		}

		else {
			// Fehler
			System.out.println(" response in readConfiguration false");
		}
		data.close();

		return currentConfiguration;

	}

	public String getCurentMeasurementValue(SerialPort connectedPort) {
		Scanner data = new Scanner(connectedPort.getInputStream());
		String response = null;
		String message = null;

		if (connectedPort.isOpen()) {

			if (connectedPort.writeBytes(getCurentMeasurementValueCommand
					.getBytes(StandardCharsets.UTF_8), 8) != -1) {
				System.out.println("Bytes geschrieben: "
						+ new String(getCurentMeasurementValueCommand
								.getBytes(), StandardCharsets.UTF_8));
			} else {
				System.out.println("Fehler beim Bytes schreiben.");
			}

			try {
				while (connectedPort.bytesAvailable() == 0) {
					System.out.println("Verfügbare Bytes: "
							+ connectedPort.bytesAvailable());

					Thread.sleep(500);
				}
				response = data.nextLine();
				System.out.println("Antwort: " + response);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (response.equals("2")) {
				System.out.println(response.equals("2"));

				while (data.hasNextLine()) {
					try {

						message = data.nextLine();
						System.out.println(Integer.parseInt(message));

					} catch (Exception e) {
					}
				}

				data.close();
				return message;
			}

		}
		data.close();
		return "Temperatur auslesen fehlgeschlagen";

	}

	public String getStatus(SerialPort connectedPort) {

		Scanner data = new Scanner(connectedPort.getInputStream());
		String response = null;
		String message = null;

		if (connectedPort.isOpen()) {

			if (connectedPort.writeBytes(
					getStatusCommand.getBytes(StandardCharsets.UTF_8), 8) != -1) {
				System.out.println("Bytes geschrieben: "
						+ new String(getStatusCommand.getBytes(),
								StandardCharsets.UTF_8));
			} else {
				System.out.println("Fehler beim Bytes schreiben.");
			}
			try {
				while (connectedPort.bytesAvailable() == 0) {
					System.out.println("Verfügbare Bytes:"
							+ connectedPort.bytesAvailable());
					Thread.sleep(500);
				}
				response = data.nextLine();
				System.out.println("Antwort: " + response);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (response.equals("3")) {

				while (data.hasNextLine()) {
					try {

						message = data.nextLine();
						System.out.println(Integer.parseInt(message));

					} catch (Exception e) {
					}
				}
				data.close();

				if (Objects.equals(Integer.parseInt(message), 1)) {
					return "<html><p style='color:#04B431'>OK</p></html>";
				}
				if (Objects.equals(Integer.parseInt(message), 2)) {

					return "<html><p style='color:#F22C2C'>Kabelbruch Sensor</p></html>";
				}
				if (Objects.equals(Integer.parseInt(message), 3)) {

					return "<html><p style='color:#F22C2C'>Kurzschluss Sensor</p></html>";
				}
				if (Objects.equals(Integer.parseInt(message), 4)) {

					return "<html><p style='color:#F22C2C'>Messbereichsüber-/unterschreitung</p></html>";
				}
			}

		}

		return "<html><p style='color:#F22C2C'>Status auslesen fehlgeschlagen</p></html>";

	}

	public boolean newConfiguration(SerialPort connectedPort,
			ConfigurationInformation newConfiguration) {
		System.out.println(newConfiguration.toString());
		Scanner data = new Scanner(connectedPort.getInputStream());
		String response = null;

		String newFailureBehaviour = "";
		if (newConfiguration.getFailureBehavior().equals("fallend")) {
			newFailureBehaviour = "1";
		}
		if (newConfiguration.getFailureBehavior().equals("steigend")) {
			newFailureBehaviour = "2";
		}

		if (connectedPort.isOpen()) {
			connectedPort.writeBytes(newConfigurationCommand.getBytes(), 8);

			try {
				while (connectedPort.bytesAvailable() == 0) {
					Thread.sleep(500);
				}
				response = data.nextLine();
				System.out.println("Antwort: " + response);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (response.equals("4")) {

				if (connectedPort.writeBytes(
						newConfiguration.getMeasurementValueStart().getBytes(
								StandardCharsets.UTF_8), 8) != -1) {
					connectedPort.writeBytes("e".getBytes(), 8);
					System.out.println("Bytes geschrieben: "
							+ new String(newConfiguration
									.getMeasurementValueStart().getBytes(),
									StandardCharsets.UTF_8));
					System.out.println("Bytes geschrieben: "
							+ new String("e".getBytes()));
				} else {
					System.out.println("Fehler beim Bytes schreiben.");
				}

				try {
					while (connectedPort.bytesAvailable() == 0) {

						Thread.sleep(500);
					}
					System.out.println("nach Pause verfügbare Bytes: "
							+ connectedPort.bytesAvailable());
					response = "";
					response = data.nextLine();
					System.out.println("Antwort: " + response);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			if (response.equals("4")) {

				if (connectedPort.writeBytes(
						newConfiguration.getMeasurementValueEnd().getBytes(
								StandardCharsets.UTF_8), 8) != -1) {
					connectedPort.writeBytes("e".getBytes(), 8);
					System.out.println("Bytes geschrieben: "
							+ new String(newConfiguration
									.getMeasurementValueEnd().getBytes(),
									StandardCharsets.UTF_8));
					System.out.println("Bytes geschrieben: "
							+ new String("e".getBytes()));
				} else {
					System.out.println("Fehler beim Bytes schreiben.");
				}

				try {
					while (connectedPort.bytesAvailable() == 0) {

						Thread.sleep(500);
					}
					System.out.println("nach Pause verfügbare Bytes: "
							+ connectedPort.bytesAvailable());
					response = "";
					response = data.nextLine();
					System.out.println("Antwort: " + response);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			if (response.equals("4")) {

				if (connectedPort
						.writeBytes(newFailureBehaviour
								.getBytes(StandardCharsets.UTF_8), 8) != -1) {
					connectedPort.writeBytes("e".getBytes(), 8);
					System.out.println("Bytes geschrieben: "
							+ new String(newFailureBehaviour.getBytes(),
									StandardCharsets.UTF_8));
					System.out.println("Bytes geschrieben: "
							+ new String("e".getBytes()));
				} else {
					System.out.println("Fehler beim Bytes schreiben.");
				}

				try {
					while (connectedPort.bytesAvailable() == 0) {

						Thread.sleep(500);
					}
					System.out.println("nach Pause verfügbare Bytes: "
							+ connectedPort.bytesAvailable());
					response = "";
					response = data.nextLine();
					System.out.println("Antwort: " + response);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			if (response.equals("4")) {
				return true;
			}

		}
		data.close();
		return false;
	}

	public boolean newSimulation(SerialPort connectedPort,
			String simulatedTemperature) {
		// System.out.println(simulatedTemperature);
		// int newTemperature = Integer.valueOf(simulatedTemperature);
		// System.out.println(newTemperature);
		// String temperatureToSendIn16Bit =
		// addLeadingZerosAndPositiveOrNegativeSign(newTemperature);
		String response = null;
		Scanner data = new Scanner(connectedPort.getInputStream());

		if (connectedPort.isOpen()) {

			connectedPort.writeBytes(newSimulationCommand.getBytes(), 8);

			try {
				while (connectedPort.bytesAvailable() == 0) {
					Thread.sleep(500);
				}
				response = data.nextLine();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// String[] temperatureToSendIn16BitParts = {
			// temperatureToSendIn16Bit.substring(0, 8),
			// temperatureToSendIn16Bit.substring(8) };
			// connectedPort.writeBytes(temperatureToSendIn16BitParts[0].getBytes(),
			// 8);
			if (response.equals("5")) {
				connectedPort.writeBytes(simulatedTemperature.getBytes(), 8);
				connectedPort.writeBytes("e".getBytes(), 8);
				System.out.println("Bytes geschrieben: "
						+ new String(simulatedTemperature.getBytes(),
								StandardCharsets.UTF_8));

			}
			try {
				while (connectedPort.bytesAvailable() == 0) {
					Thread.sleep(500);
				}
				response = null;
				response = data.nextLine();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (response.equals("5")) {
				return true;
			}
		}
		return false;
	}

	public boolean stopSimulation(SerialPort connectedPort) {

		String response = null;
		Scanner data = new Scanner(connectedPort.getInputStream());

		if (connectedPort.isOpen()) {

			if (connectedPort.writeBytes(
					stopSimulationCommand.getBytes(StandardCharsets.UTF_8), 8) != -1) {
				System.out.println("Bytes geschrieben: "
						+ new String(stopSimulationCommand.getBytes(),
								StandardCharsets.UTF_8));
			} else {
				System.out.println("Fehler beim Bytes schreiben.");
			}

			try {
				while (connectedPort.bytesAvailable() == 0) {
					Thread.sleep(500);
				}
				response = data.nextLine();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		if (response.equals("6")) {
			return true;
		}
		return false;
	}

	public boolean closeTMU(SerialPort connectedPort) {

		String response = null;
		Scanner data = new Scanner(connectedPort.getInputStream());

		if (connectedPort.isOpen()) {

			if (connectedPort.writeBytes(
					stopTMUCommand.getBytes(StandardCharsets.UTF_8), 8) != -1) {
				System.out.println("Bytes geschrieben: "
						+ new String(stopTMUCommand.getBytes(),
								StandardCharsets.UTF_8));
			} else {
				System.out.println("Fehler beim Bytes schreiben.");
			}

			try {
				while (connectedPort.bytesAvailable() == 0) {
					Thread.sleep(500);
				}
				response = null;
				response = data.nextLine();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		if (response.equals("7")) {
			connectedPort.closePort();
			return true;
		}
		return false;
	}
}
