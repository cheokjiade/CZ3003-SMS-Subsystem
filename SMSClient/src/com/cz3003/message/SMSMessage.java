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
	public static final int SUCCESS = 0;
	public static final int UNABLE_TO_SEND = 1;
	public static final int UNABLE_TO_DELIVER = 2;
	public static final int UNABLE_TO_CONNECT_TO_NETWORK = 3;
	public static final int OTHERS = 4;
	private int type;
	private String message;
	
	// constructor
	public SMSMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	// getters
	public int getType() {
		return type;
	}
	public String getMessage() {
		return message;
	}
}

