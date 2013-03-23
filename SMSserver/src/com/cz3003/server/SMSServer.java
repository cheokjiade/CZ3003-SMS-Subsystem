/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz3003.server;

import java.util.Date;
import java.net.MalformedURLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.util.ArrayList;

import com.cz3003.interfaces.ISMS;
import com.cz3003.interfaces.SMSInterface;
import com.cz3003.message.CPUMessage;
import com.cz3003.recipient.AgencyNumbers;





public class SMSServer extends UnicastRemoteObject implements SMSInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7399499986439982894L;
	private LoadBalancer loadBalancer;
    public SMSServer(LoadBalancer loadBalancer) throws RemoteException {
        System.out.println("Initializing Server");
        //java.rmi.Naming.rebind("SMS", this);
        this.loadBalancer = loadBalancer;
        
    }

//    public static void main(String[] args) {
//        try {
//            SMSServer sms = new SMSServer();
//            java.rmi.Naming.rebind("SMS", sms);
//            System.out.println("Server Ready");
//        } catch (RemoteException RE) {
//            System.out.println("Remote Server Error:" + RE.getMessage());
//            System.exit(0);
//        } catch (MalformedURLException ME) {
//            System.out.println("Invalid URL!!");
//        }
//    }

    public void sendOutSMS(String incidentName, String location, String type, double longtitude, double latitude, Date timeStamp, String description, int severity, String callno) {
        System.out.println("Message RECEIVED!!" + incidentName + location + type + longtitude + latitude + timeStamp + description + severity + callno);
        loadBalancer.createMessageToSMS(new CPUMessage(timeStamp, incidentName, location, type, longtitude, latitude, description, severity, callno, 0, null));
    }

    public void sendAgencyNumbers(String host) throws java.rmi.NotBoundException, java.net.MalformedURLException, java.rmi.RemoteException{
        ISMS isms = (ISMS) Naming.lookup(host);
        ArrayList<AgencyNumbers> numberList = new ArrayList<AgencyNumbers>();
        numberList = isms.sendAgencyNumbers();
    }

    public void sendErrorReport(String host, Date timestamp, String incidentName, String location, String type, double longitude, double latitude, String description, int severity, String callno, int errorCode, String errorDescription) throws java.rmi.NotBoundException, java.net.MalformedURLException, java.rmi.RemoteException {
        ISMS isms = (ISMS) Naming.lookup(host);
        isms.sendErrorReport(timestamp, incidentName, location, type, longitude, latitude, description, severity, callno, errorCode, errorDescription);
    }

	public LoadBalancer getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
}
