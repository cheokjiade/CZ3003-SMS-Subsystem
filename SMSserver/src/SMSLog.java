import interfaces.ClientMessageReceived;

import java.util.ArrayList;

import logs.SMSClientLog;


public class SMSLog implements ClientMessageReceived{
	private ArrayList<SMSClientLog> smsClientArrayList;// = new ArrayList<>();
	
	public SMSLog() {
		smsClientArrayList = new ArrayList<SMSClientLog>();
	}
	
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
	public void onMessageReceived(int uniqueId, String msg, int errorCode) {
		SMSClientLog clientLog = selectClientsLog(uniqueId);
		switch (errorCode) {
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
		for (SMSClientLog log : smsClientArrayList) 
			if (log.getUniqueId() == uniqueId) return log;
		return null;
		
	}
}
