package logs;

import java.util.Date;

public class SMSLogEntry {
	public static final int SMSSENT = 0, SMSDELIVERED = 1, MESSAGESENT = 2, SMSTIMEDOUT = 3, SMSFAILED = 4;
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
