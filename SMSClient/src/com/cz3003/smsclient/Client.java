package com.cz3003.smsclient;


import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cz3003.message.SMSMessage;

import android.util.Log;

/**
 * 
 * @author Tiffany
 *
 */
/*
 * The Client that can be run both as a console or a GUI
 */
public class Client{

	static String SENT = "SMS_SENT";
	private SMS sms;
	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	// if I use a GUI or not
	//private ClientGUI cg;
	
	// the server, the port and the username
	private String server, username;
	private int port;

	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 */


	/*
	 * Constructor call when used from a GUI
	 * in console mode the ClienGUI parameter is null
	 */
	Client(String server, int port, String username, SMS sms) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.sms = sms;
	}
	
	
	
	/*
	 * To start the dialog
	 */
	public boolean connect() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
			Log.w("sms", server);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
		Log.w("sms", msg);
			System.out.println(msg);      // println in console mode
	
	}
	
	/*
	 * To send a message to the server
	 */
	void sendMessage(SMSMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
		
			
	}
	/*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply System.out.println() it in console mode
	 */
	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					SMSMessage msg = (SMSMessage) sInput.readObject();
					Log.w("msg", msg.getMessage());
					sms.sendSMS(msg);
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);

					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}


