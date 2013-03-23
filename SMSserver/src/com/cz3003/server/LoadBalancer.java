package com.cz3003.server;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.naming.ldap.Control;


import com.cz3003.logs.SMSClientLog;
import com.cz3003.logs.SMSLogEntry;
import com.cz3003.message.CPUMessage;
import com.cz3003.message.MessageLinkController;
import com.cz3003.message.SMSMessage;
import com.cz3003.recipient.AgencyNumbers;
import com.cz3003.recipient.Recipient;
import com.cz3003.recipient.Recipients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * @author Jia De
 */
public class LoadBalancer {
	private DeviceManager dm;
	private SMSLog smsLog;
	private Recipients recipients;
	private MessageLinkController messageController;
	private SMSServer sms;
	private ErrorClient errorClient;
	public LoadBalancer(){
		messageController = new MessageLinkController();
		dm = new DeviceManager(5832);
		smsLog = dm.getSmsLog();
		recipients = new Recipients();
		//dm.start();
		new Thread(new Runnable(){
		    public void run()
		    {
		    	//DeviceUuidFactory uuid = new DeviceUuidFactory(getApplicationContext());
		    	dm.start();
		    }
		}).start();
	}
	/**
	 * 
	 * @param smsMessage
	 * @param cpuMessage
	 * @return
	 * 
	 * The method that will be called by the RMI server when CPU passes us an incident.
	 */
	public boolean sendMessageOut(SMSMessage smsMessage, CPUMessage cpuMessage){
		SMSClient bestClient = chooseBestClient(dm.getSMSClients());
		if(sendMessageToClient(bestClient, smsMessage)==false){
			//TODO no connected client. add to pending queue or something
			//TODO send error message to CPU
			System.out.println("error");
			
		}
		else {
			SMSClientLog clientLog = smsLog.selectClientsLog(bestClient.getUniqueId());
			clientLog.getSmsLogEntryArrayList().add(new SMSLogEntry("Message sent to device", SMSLogEntry.MESSAGESENT));
			clientLog.setScore(clientLog.getScore()-50);
		}
			
		return true;
	}
	/**
	 * 
	 * @param smsClientArrayList
	 * @return
	 * Chooses the client with the highest score.
	 */
	public synchronized SMSClient chooseBestClient(ArrayList<SMSClient> smsClientArrayList){
		//when no connected client method fails
		if(smsClientArrayList.size()==0) return null;
		int bestClient = smsLog.selectBestClient();
		for (SMSClient client :smsClientArrayList)
			if (client.getUniqueId()== bestClient) return client;
		smsLog.removeClient(bestClient);
		//either return a best client or fail
		return chooseBestClient(smsClientArrayList);
		//return null;
	}
	
	//TODO change msg and no to smsmessage object
	public boolean sendMessageToClient(SMSClient client, SMSMessage smsMessage){
		//when no connected client we cant send a message
		if (client == null) return false;
		//message was sent to client
		if (client.writeMsg(smsMessage)) return true;
		//call function
		return sendMessageToClient(client, smsMessage);
	}
	/**
	 * 
	 * @param incidentId
	 * @param location
	 * @param type
	 * @param description
	 * @param callerNumber
	 * @return
	 * A method for creating an SMSMessage via individual fields. Used for testing purposes.
	 */
	public boolean createMessageToSMS(int incidentId, String location, String type, String description, String callerNumber){
		String messageContents = type + " @ " + location + "\nDescription: " + description + "\nReported by " + callerNumber + " @ " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "\nRef No.: " + incidentId;
		SMSMessage smsMessage = new SMSMessage(SMSMessage.MESSAGETOSMS, messageContents, incidentId, callerNumber);
		return true;
	}
	
	/**
	 * 
	 * @param cpuMessage
	 * @return
	 * 
	 * Creates the SMSMessage object from the CPUMessage object created by our RMIServer.
	 */
	public boolean createMessageToSMS(CPUMessage cpuMessage){
		sendMessageOut(new SMSMessage(SMSMessage.MESSAGETOSMS, "there is a fire", 5, this.recipients.selectNumberBasedOnIncidentType(cpuMessage.getType())), cpuMessage);
		return true;
	}
	
	/**
	 * 
	 * @param jsonString
	 * @return
	 * Updates the list of recipients. Values are currently hard coded.
	 */
	public boolean updateRecipientList(String jsonString){
		ArrayList<AgencyNumbers> recipientList = new ArrayList<AgencyNumbers>();
		recipientList.add(new AgencyNumbers("SCDF", "97368902", "FIRE"));
//		Type recipientListType = new TypeToken<ArrayList<Recipient>>() {}.getType();
//		Gson gson = new Gson();
//		ArrayList<Recipient> recipientList = gson.fromJson(jsonString, recipientListType);
//		for(Recipient r: recipientList)
//			System.out.println(r.disasterType + " " + r.phoneNumber);
		recipients.setRecipientList(recipientList);
		return true;
	}
	/**
	 * 
	 * @param smsMessage
	 * @return
	 * Called when sending an error report to CPU.
	 */
	public boolean sendErrorReport(SMSMessage smsMessage){
		
		return true;
	}
	/**
	 * 
	 * @param args
	 * Main method of program.
	 */
	public static void main(String[]args){
//		if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new RMISecurityManager());
//        }
		LoadBalancer server = new LoadBalancer();
		try {
			SMSServer sms = new SMSServer(server);
			sms.setLoadBalancer(server);
            //java.rmi.Naming.rebind("SMS", sms);
			//Remote stub = (Remote) UnicastRemoteObject.exportObject(sms, 1076);
			Registry reg = LocateRegistry.createRegistry(1099);
			System.out.println("Server is ready");
			reg.rebind("SMS", sms);
            System.out.println("Server Ready");
        } catch (RemoteException RE) {
            System.out.println("Remote Server Error:" + RE.getMessage());
            System.exit(0);
        }
		server.updateRecipientList("");
		int someid=1;
		//server.updateRecipientList("[{\"disasterType\":\"TIFFANY\",\"phoneNumber\":\"97374214\"},{\"disasterType\":\"SRI\",\"phoneNumber\":\"81127957\"},{\"disasterType\":\"JUNE\",\"phoneNumber\":\"97368902\"}]");
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter type fire : ");
		String recipient = scan.nextLine();
		while(true){
			System.out.print("Enter message : ");
			server.sendMessageOut(new SMSMessage(SMSMessage.MESSAGETOSMS, scan.nextLine(), ++someid, server.recipients.selectNumberBasedOnIncidentType(recipient)));
		}
		
	}
	/**
	 * 
	 * @param smsMessage
	 * Sends an SMSMessage to a client. And modifies the score.
	 */
	private void sendMessageOut(SMSMessage smsMessage) {
		SMSClient bestClient = chooseBestClient(dm.getSMSClients());
		if(sendMessageToClient(bestClient, smsMessage)==false){
			//TODO no connected client. add to pending queue or something
			//TODO send error message to CPU
			System.out.println("error");
			
		}
		else {
			SMSClientLog clientLog = smsLog.selectClientsLog(bestClient.getUniqueId());
			clientLog.getSmsLogEntryArrayList().add(new SMSLogEntry("Message sent to device", SMSLogEntry.MESSAGESENT));
			clientLog.setScore(clientLog.getScore()-50);
		}
			
		//return true;
		
	}
	
}
