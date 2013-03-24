package com.cz3003.message;

import java.util.ArrayList;

import com.cz3003.server.LoadBalancer;

public class MessageLinkController {
	private ArrayList<MessageLink> linkedMessageArray;
	private LoadBalancer loadBalancer;

	public MessageLinkController(LoadBalancer loadBalancer) {
		super();
		this.linkedMessageArray = new ArrayList<MessageLink>();
		this.loadBalancer = loadBalancer;
	}
	
	public boolean createNewMessageLink(CPUMessage cpuMessage, SMSMessage smsMessage){
		try{
			linkedMessageArray.add(new MessageLink(cpuMessage, smsMessage, this));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public void removeMessageLink(MessageLink ml){
		linkedMessageArray.remove(ml);
	}
	
	public void sendErrorMessageToLoadBalancer(MessageLink messageLink){
		loadBalancer.sendErrorReport(messageLink);
	}
	
	public boolean stopTimeOutTimer(int incidentId){
		System.out.println("stopping timer");
		for(MessageLink ml: linkedMessageArray){
			if(ml.getSmsMessage().getIncidentId()==incidentId){
				ml.getSmsTimer().cancelTimer();
				System.out.println("timer cancelled");
			}
		}
		return true;
	}

	public LoadBalancer getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	
}
