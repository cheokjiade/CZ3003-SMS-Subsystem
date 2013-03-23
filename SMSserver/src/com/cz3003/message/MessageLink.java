package com.cz3003.message;

public class MessageLink {
	private CPUMessage cpuMessage;
	private SMSMessage smsMessage;
	private SMSTimer smsTimer;
	
	public MessageLink(CPUMessage cpuMessage, SMSMessage smsMessage) {
		super();
		this.cpuMessage = cpuMessage;
		this.smsMessage = smsMessage;
		smsTimer = new SMSTimer(15, this, cpuMessage, smsMessage);
	}
	public MessageLink(CPUMessage cpuMessage, SMSMessage smsMessage,
			SMSTimer smsTimer) {
		super();
		this.cpuMessage = cpuMessage;
		this.smsMessage = smsMessage;
		this.smsTimer = smsTimer;
	}
	public CPUMessage getCpuMessage() {
		return cpuMessage;
	}
	public void setCpuMessage(CPUMessage cpuMessage) {
		this.cpuMessage = cpuMessage;
	}
	public SMSMessage getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(SMSMessage smsMessage) {
		this.smsMessage = smsMessage;
	}
	public SMSTimer getSmsTimer() {
		return smsTimer;
	}
	public void setSmsTimer(SMSTimer smsTimer) {
		this.smsTimer = smsTimer;
	}
	
	

}
