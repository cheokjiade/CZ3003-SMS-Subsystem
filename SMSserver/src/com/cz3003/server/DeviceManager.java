package com.cz3003.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class DeviceManager extends Thread{
	
	private ArrayList<SMSClient> smsClientArrayList;
	private static int uniqueId;
	private SimpleDateFormat sdf;
	private SMSLog smsLog;
	private boolean keepGoing;// boolean to tell
	private int port;//port the server runs on
	
	public DeviceManager (int port){
		
		sdf = new SimpleDateFormat("HH:mm:ss");
		smsLog = new SMSLog();
		this.port = port;
		smsClientArrayList= new ArrayList<SMSClient>();
		
	}
	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{
				// format message saying we are waiting
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				SMSClient t = new SMSClient(socket, ++uniqueId, smsLog, this);  // make a thread of it
				smsClientArrayList.add(t);	
				smsLog.addClient(uniqueId);// save it in the ArrayList
				t.start();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < smsClientArrayList.size(); ++i) {
					SMSClient tc = smsClientArrayList.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}
	
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < smsClientArrayList.size(); ++i) {
			SMSClient ct = smsClientArrayList.get(i);
			// found it
			if(ct.uniqueId == id) {
				smsClientArrayList.remove(i);
				return;
			}
		}
	}
	
	
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
			System.out.println(time);
	}
	
	public ArrayList<SMSClient> getSMSClients(){
		return smsClientArrayList;
	}
	public static int getUniqueId() {
		return uniqueId;
	}
	public static void setUniqueId(int uniqueId) {
		DeviceManager.uniqueId = uniqueId;
	}
	public SMSLog getSmsLog() {
		return smsLog;
	}
	public void setSmsLog(SMSLog smsLog) {
		this.smsLog = smsLog;
	}
	public boolean isKeepGoing() {
		return keepGoing;
	}
	public void setKeepGoing(boolean keepGoing) {
		this.keepGoing = keepGoing;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	

	
}
