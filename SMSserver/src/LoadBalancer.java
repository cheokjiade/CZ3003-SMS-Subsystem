import java.util.ArrayList;



public class LoadBalancer {
	private DeviceManager dm;
	private SMSLog smsLog;
	
	public LoadBalancer(){
		dm = new DeviceManager(5832);
		smsLog = dm.getSmsLog();
		//dm.start();
		new Thread(new Runnable(){
		    public void run()
		    {
		    	//DeviceUuidFactory uuid = new DeviceUuidFactory(getApplicationContext());
		    	dm.start();
		    }
		}).start();
	}
	
	public boolean sendMessageOut(String msg, String no){
		if(sendMessageToClient(chooseBestClient(dm.getSMSClients()), msg, no)==false){
			//TODO no connected clientadd to pending queue or something
		}
			
		return true;
	}
	
	public SMSClient chooseBestClient(ArrayList<SMSClient> smsClientArrayList){
		//when no connected client method fails
		if(smsClientArrayList.size()==0) return null;
		int bestClient = smsLog.selectBestClient();
		for (SMSClient client :smsClientArrayList)
			if (client.getId()== bestClient) return client;
		smsLog.removeClient(bestClient);
		//either return a best client or fail
		return chooseBestClient(smsClientArrayList);
		//return null;
	}
	
	
	public boolean sendMessageToClient(SMSClient client, String msg, String no){
		//when no connected client we cant send a message
		if (client == null) return false;
		//message was sent to client
		if (client.writeMsg(msg)) return true;
		//call function
		return sendMessageToClient(client, msg, no);
	}
	
}
