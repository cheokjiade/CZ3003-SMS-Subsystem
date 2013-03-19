package com.cz3003.logs;

import java.util.ArrayList;

public class SMSClientLog {
	private int score;
	private int uniqueId;
	private ArrayList<SMSLogEntry> smsLogEntryArrayList;
	public SMSClientLog (int uniqueId) {
		this.uniqueId = uniqueId;
		smsLogEntryArrayList = new ArrayList<SMSLogEntry>();
		score = 0;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int id) {
		this.uniqueId = id;
	}
	public ArrayList<SMSLogEntry> getSmsLogEntryArrayList() {
		return smsLogEntryArrayList;
	}
	public void setSmsLogEntryArrayList(ArrayList<SMSLogEntry> smsLogEntryArrayList) {
		this.smsLogEntryArrayList = smsLogEntryArrayList;
	}
	
}
