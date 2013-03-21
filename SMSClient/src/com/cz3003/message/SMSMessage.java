package com.cz3003.message;

import java.io.*;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class SMSMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	public static final int SUCCESS = 0;//SMS was delivered
	public static final int UNABLE_TO_SEND = 1; //unable to send to telco
	public static final int UNABLE_TO_DELIVER = 2; //send to telco but cannot deliver to recipient
	public static final int UNABLE_TO_CONNECT_TO_NETWORK = 3; // device cannot connect to network
	public static final int OTHERS = 4; // others
	public static final int MESSAGETOSMS = 5; // server sends a message to device
	private int type; //for the types above
	private String message; // the message
	private int incidentId; // the incident id to map to
	private String recipient; // phone number for the message
	private int others;
	
	public SMSMessage(int type, String message, int incidentId,
			String recipient) {
		super();
		this.type = type;
		this.message = message;
		this.incidentId = incidentId;
		this.recipient = recipient;
	}

	// constructor
	public SMSMessage(int type, int incidentId, String message) {
		this.type = type;
		this.message = message;
		this.incidentId = incidentId;
	}
	
	// getters
	public int getType() {
		return type;
	}
	public String getMessage() {
		return message;
	}

	public int getIncidentId() {
		return incidentId;
	}

	public String getRecipient() {
		return recipient;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}
}

