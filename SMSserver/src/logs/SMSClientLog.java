package logs;

import java.util.ArrayList;

public class SMSClientLog {
	private int score;
	private int id;
	private ArrayList<SMSLogEntry> smsLogEntryArrayList;
	public SMSClientLog (int id) {
		this.id = id;
		smsLogEntryArrayList = new ArrayList<SMSLogEntry>();
		score = 0;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<SMSLogEntry> getSmsLogEntryArrayList() {
		return smsLogEntryArrayList;
	}
	public void setSmsLogEntryArrayList(ArrayList<SMSLogEntry> smsLogEntryArrayList) {
		this.smsLogEntryArrayList = smsLogEntryArrayList;
	}
	
}
