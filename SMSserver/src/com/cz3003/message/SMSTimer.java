package com.cz3003.message;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
 
public class SMSTimer {
    private Timer timer;
    private Timerz timerz;
    private MessageLink messageLink;
    private CPUMessage cpuMessage;
    private SMSMessage smsMessage;
    
    public SMSTimer(int seconds) {
    	timerz = new Timerz();
        timer = new Timer();
        timer.schedule(timerz, seconds*1000);
	}
    
    public SMSTimer(int seconds,MessageLink messageLink,CPUMessage cpuMessage,SMSMessage smsMessage) {
    	this.messageLink = messageLink;
    	this.cpuMessage = cpuMessage;
    	this.smsMessage = smsMessage;
    	timerz = new Timerz();
        timer = new Timer();
        timer.schedule(timerz, seconds*1000);
	}
    
    public boolean cancelTimer(){
    	timerz.toTerminate();
    	return true;
    }

    class Timerz extends TimerTask {
        public void run() {
        	//TODO time is up, send error to CPU to notify message could not be sent in time
            System.out.format("Time's up!%n");  
        }
        
        public void toTerminate(){
        	timer.cancel(); //Terminate the timer thread
        	System.out.format("cancelled%n");
        }
    }

    public static void main(String args[]) {
        SMSTimer timer = new SMSTimer(30);
        
        System.out.format("Task scheduled.%n");
        Scanner sc = new Scanner(System.in);
        if (sc.nextInt()==1)
        timer.timerz.toTerminate();
    }
}

