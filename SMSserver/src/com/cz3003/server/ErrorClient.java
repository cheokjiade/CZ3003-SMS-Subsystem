package com.cz3003.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import com.cz3003.interfaces.ISMS;
import com.cz3003.message.CPUMessage;
import com.cz3003.message.SMSMessage;
/**
 * 
 * @author Sri Hartati
 *
 */ 
public class ErrorClient { 
    // "obj" is the reference of the remote object
    ISMS obj = null; 
 
    public void sendError(CPUMessage cpuMessage, SMSMessage smsMessage) { 
        try { 
            obj = (ISMS)Naming.lookup("//localhost/RmiServer");
            obj.sendErrorReport(cpuMessage.getTimeStamp(), cpuMessage.getIncidentName(), cpuMessage.getLocation(), cpuMessage.getType(), cpuMessage.getLongitude(), cpuMessage.getLatitude(), cpuMessage.getDescription(), cpuMessage.getSeverity(), cpuMessage.getCallno(), 2, smsMessage.getMessage()); 
        } catch (Exception e) { 
            System.err.println("RmiClient exception: " + e); 
            e.printStackTrace(); 
 
            //return e.getMessage();
        } 
    } 
 
    public static void main(String args[]) {
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
 
        ErrorClient cli = new ErrorClient();
 
        //System.out.println(cli.getMessage());
    }
}