import interfaces.ClientMessageReceived;

import java.util.ArrayList;

import logs.SMSClientLog;


public class SMSLog implements ClientMessageReceived{
	private ArrayList<SMSClientLog> smsClientArrayList;// = new ArrayList<>();
	
	public SMSLog() {
		smsClientArrayList = new ArrayList<SMSClientLog>();
	}
	
	public int selectBestClient(){
		return smsClientArrayList.get(0).getId();
	}
	
	public boolean removeClient(int id){
		for (SMSClientLog clientLog: smsClientArrayList)
			if(clientLog.getId()==id){
				smsClientArrayList.remove(clientLog);
				return true;
			}
		return false;
	}
	
	public void addClient(int id){
		smsClientArrayList.add(new SMSClientLog(id));
	}

	@Override
	public void onMessageReceived(String msg, int errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	//public boolean
	
}
