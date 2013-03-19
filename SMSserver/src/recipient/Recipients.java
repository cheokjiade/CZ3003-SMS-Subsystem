package recipient;

import java.util.ArrayList;

public class Recipients {
	private ArrayList<Recipient> recipientList;

	public Recipients() {
		super();
		this.recipientList = new ArrayList<Recipient>();
	}
	public String selectNumberBasedOnIncidentType(String incidentType){
		for (Recipient recipient : recipientList)
			if(recipient.disasterType.equalsIgnoreCase(incidentType)) return recipient.phoneNumber;
		return null;
	}
}
