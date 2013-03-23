package com.cz3003.message;

import java.util.ArrayList;

public class MessageLinkController {
	private ArrayList<MessageLink> linkedMessageArray;

	public MessageLinkController() {
		super();
		this.linkedMessageArray = new ArrayList<MessageLink>();
	}
	
	public boolean createNewMessageLink(CPUMessage cpuMessage, SMSMessage smsMessage){
		try{
			linkedMessageArray.add(new MessageLink(cpuMessage, smsMessage));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean stopTimeOutTimer(int incidentId){
		for(MessageLink ml: linkedMessageArray){
			if(ml.getSmsMessage().getIncidentId()==incidentId)
				ml.getSmsTimer().cancelTimer();
		}
		return true;
	}
	
}
