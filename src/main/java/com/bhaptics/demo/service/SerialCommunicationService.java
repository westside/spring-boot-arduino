package com.bhaptics.demo.service;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.bhaptics.demo.component.SerialCommunication;

@Service
public class SerialCommunicationService {

	private ConcurrentHashMap<String, SerialCommunication> serialCommunicationMap = new ConcurrentHashMap<String, SerialCommunication>(); 
	
	public synchronized void stop(String portName) {
		SerialCommunication serialCommunication = serialCommunicationMap.remove(portName);
		if (serialCommunication != null) {
			serialCommunication.close();
		}
	}
	
	public synchronized void write(String portName, String value) {
		SerialCommunication serialCommunication = serialCommunicationMap.get(portName);
		if (serialCommunication == null) {
 			serialCommunication = new SerialCommunication(portName);
 			serialCommunication.connect();
			serialCommunicationMap.put(portName, serialCommunication);
		}
		
		serialCommunication.write(value);
		
	}
	
	public synchronized void readLine(String portName) {
		throw new UnsupportedOperationException();
	}
	
	public Collection<String> getPorts() {
		List<String> ports = new ArrayList<String>();
		
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			ports.add(currPortId.getName());
		}
		
		return ports;
	}
}
