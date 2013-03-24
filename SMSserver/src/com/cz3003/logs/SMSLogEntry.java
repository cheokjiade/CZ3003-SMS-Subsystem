package com.cz3003.logs;

import java.util.Date;

public class SMSLogEntry {
	public static final int MESSAGESENT = 8; // message sent to client
	public static final int SMSTIMEDOUT = 9; //client did not respond in time
	public static final int SENT = 7;//SMS was sent
	public static final int DELIVERED = 6;//SMS was delivered
	public static final int UNABLE_TO_SEND = 1; //unable to send to telco
	public static final int UNABLE_TO_DELIVER = 2; //send to telco but cannot deliver to recipient
	public static final int UNABLE_TO_CONNECT_TO_NETWORK = 3; // device cannot connect to network
	public static final int OTHERS = 4; // others
	private String details;
	private Date dateCreated;
	private int code;
	private int messageId;
	public SMSLogEntry(String details, int code) {
		super();
		this.details = details;
		this.code = code;
		dateCreated = new Date();
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	
	

}
