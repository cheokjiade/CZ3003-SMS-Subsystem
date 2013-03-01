import java.util.ArrayList;


public class LoadBalancer {
	private DeviceManager dm;
	private SMSLog smsLog;
	
	public LoadBalancer(){
		dm = new DeviceManager();
	}
	
	public boolean sendMessageOut(String msg, String no){
		sendMessageToClient(chooseBestClient(dm.getSMSClients()), msg, no);
		return true;
	}
	
	public SMSClient chooseBestClient(ArrayList<SMSClient> smsClientArrayList){
		int bestClient = smsLog.selectBestClient();
		for (SMSClient client :smsClientArrayList)
			if (client.getId()== bestClient) return client;
		smsLog.removeClient(bestClient);
		return chooseBestClient(smsClientArrayList);
		//return null;
	}
	
	
	public boolean sendMessageToClient(SMSClient client, String msg, String no){
		if (client.writeMsg(msg)){
			return true;
		}return false;
	}
	
}
