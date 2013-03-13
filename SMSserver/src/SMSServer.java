import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * The server that can be run both as a console application or a GUI
 */
public class SMSServer extends Thread{
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	//private ArrayList<ClientThread> al;
	// if I am in a GUI
	//private ServerGUI sg = null;
	// to display time
	private SimpleDateFormat sdf;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;
	private LoadBalancer lb;
	

	/*
	 *  server constructor that receive the port to listen to for connection as parameter
	 *  in console
	 */
	
	public SMSServer(int port) {
		// GUI or not
		//this.sg = sg;
		// the port
		this.port = port;
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		//al = new ArrayList<ClientThread>();
		lb = new LoadBalancer();
		
	}
	
//	public void start() {
//		keepGoing = true;
//		/* create socket server and wait for connection requests */
//		try 
//		{
//			// the socket used by the server
//			ServerSocket serverSocket = new ServerSocket(port);
//
//			// infinite loop to wait for connections
//			while(keepGoing) 
//			{
//				// format message saying we are waiting
//				display("Server waiting for Clients on port " + port + ".");
//				
//				Socket socket = serverSocket.accept();  	// accept connection
//				// if I was asked to stop
//				if(!keepGoing)
//					break;
//				ClientThread t = new ClientThread(socket);  // make a thread of it
//				al.add(t);									// save it in the ArrayList
//				t.start();
//			}
//			// I was asked to stop
//			try {
//				serverSocket.close();
//				for(int i = 0; i < al.size(); ++i) {
//					ClientThread tc = al.get(i);
//					try {
//					tc.sInput.close();
//					tc.sOutput.close();
//					tc.socket.close();
//					}
//					catch(IOException ioE) {
//						// not much I can do
//					}
//				}
//			}
//			catch(Exception e) {
//				display("Exception closing the server and clients: " + e);
//			}
//		}
//		// something went bad
//		catch (IOException e) {
//            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
//			display(msg);
//		}
//	}		
    /*
     * For the GUI to stop the server
     */
	/*protected void stop() {
		keepGoing = false;
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}*/
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
			System.out.println(time);
	}
	/*
	 *  to broadcast a message to all Clients
	 */
//	public synchronized void broadcast(String message) {
//		// add HH:mm:ss and \n to the message
//		String time = sdf.format(new Date());
//		String messageLf = message + "\n";
//		// display message on console or GUI
//			System.out.print(messageLf);
//		
//		// we loop in reverse order in case we would have to remove a Client
//		// because it has disconnected
//		for(int i = al.size(); --i >= 0;) {
//			ClientThread ct = al.get(i);
//			// try to write to the Client if it fails remove it from the list
//			if(!ct.writeMsg(messageLf)) {
//				al.remove(i);
//				display("Disconnected Client " + ct.username + " removed from list.");
//			}else break;
//		}
//	}

//	synchronized void remove(int id) {
//		// scan the array list until we found the Id
//		for(int i = 0; i < al.size(); ++i) {
//			ClientThread ct = al.get(i);
//			// found it
//			if(ct.id == id) {
//				al.remove(i);
//				return;
//			}
//		}
//	}
	
	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 1500 is used
	 */ 
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified 
		int portNumber = 1500;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Usage is: > java Server [portNumber]");
				return;
				
		}
		// create a server object and start it
		SMSServer server = new SMSServer(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	
}


