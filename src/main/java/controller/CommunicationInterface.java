package controller;

import com.fazecast.jSerialComm.SerialPort;

import model.ConfigurationInformation;

public interface CommunicationInterface {
	
	ConfigurationInformation readConfiguration(SerialPort connectedPort);
	
	boolean newConfiguration(SerialPort connectedPort, ConfigurationInformation newConfiguration);
	
	boolean newSimulation (SerialPort connectedPort, String simulatedTemperature);
	
	String getCurentMeasurementValue (SerialPort connectedPort);
	
	String getStatus(SerialPort connectedPort);
	
	boolean stopSimulation(SerialPort connectedPort);
	
	boolean closeTMU ( SerialPort connectedPort);
	
	void resetAVR ( SerialPort connectedPort);

}
