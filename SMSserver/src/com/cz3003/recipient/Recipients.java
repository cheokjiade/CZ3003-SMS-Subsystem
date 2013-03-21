package com.cz3003.recipient;

import java.util.ArrayList;
/**
 * 
 * @author Wei Leng
 *
 */
public class Recipients {
	private ArrayList<AgencyNumbers> recipientList;

	public Recipients() {
		super();
		this.recipientList = new ArrayList<AgencyNumbers>();
	}
	public String selectNumberBasedOnIncidentType(String incidentType){
		for (AgencyNumbers recipient : recipientList)
			if(recipient.getType().equalsIgnoreCase(incidentType)) return recipient.getNumber();
		return null;
	}
	public ArrayList<AgencyNumbers> getRecipientList() {
		return recipientList;
	}
	public void setRecipientList(ArrayList<AgencyNumbers> recipientList) {
		this.recipientList = recipientList;
	}
	
}
