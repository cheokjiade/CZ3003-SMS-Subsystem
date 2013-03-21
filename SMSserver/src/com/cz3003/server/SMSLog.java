package com.cz3003.server;

import java.util.ArrayList;

import com.cz3003.interfaces.ClientMessageReceived;
import com.cz3003.logs.SMSClientLog;
import com.cz3003.message.SMSMessage;


/**
 * @author June Quak
 */
public class SMSLog implements ClientMessageReceived{
	private ArrayList<SMSClientLog> smsClientArrayList;// = new ArrayList<>();

	/**
	 * create a new log. done on server start up.
	 */
	public SMSLog() {
		smsClientArrayList = new ArrayList<SMSClientLog>();
	}
	/**
	 * 
	 * @return int an integer representing the unique id of the best client.
	 */
	public synchronized int selectBestClient(){
		//TODO choose a best client and return it.
		int maxScore=0, bestClient=0;
		for (int i = 0; i < smsClientArrayList.size();i++){
			if (i==0) {
				maxScore = smsClientArrayList.get(0).getScore();
				bestClient = smsClientArrayList.get(0).getUniqueId();
			} else {
				if (smsClientArrayList.get(i).getScore()> maxScore)
					bestClient = smsClientArrayList.get(i).getUniqueId();
			}
		}
		return bestClient;
	}
	/**
	 * 
	 * @param id
	 * @return boolean true if had a log of the client, false if no log.
	 */
	public boolean removeClient(int id){
		for (SMSClientLog clientLog: smsClientArrayList)
			if(clientLog.getUniqueId()==id){
				smsClientArrayList.remove(clientLog);
				return true;
			}
		return false;
	}

	public void addClient(int id){
		smsClientArrayList.add(new SMSClientLog(id));
	}

	@Override
	public void onMessageReceived(int uniqueId, SMSMessage smsMessage) {
		editClientScore(uniqueId, smsMessage);		
	}

	public synchronized void editClientScore(int uniqueId, SMSMessage smsMessage){
		SMSClientLog clientLog = selectClientsLog(uniqueId);
		switch (smsMessage.getType()) {
		case 0:
		{
			clientLog.setScore(clientLog.getScore()+50);
		}
		break;

		default:
			break;
		}
	}

	//public boolean
	public SMSClientLog selectClientsLog(int uniqueId){
		try{
			for (SMSClientLog log : smsClientArrayList) 
				if (log.getUniqueId() == uniqueId) return log;
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}
}
