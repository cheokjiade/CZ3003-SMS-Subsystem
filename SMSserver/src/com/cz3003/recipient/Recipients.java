package com.cz3003.recipient;

import java.util.ArrayList;
/**
 * 
 * @author June Quak
 *
 */
public class Recipients {
	private ArrayList<AgencyNumbers> recipientList;
	/**
	 * @return ArrayList
	 */
	public Recipients() {
		super();
		this.recipientList = new ArrayList<AgencyNumbers>();
	}
	/**
	 * Returns correct recipient's number
	 * @param incidentType
	 * @return String number of agency with incident type
	 */
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
