package com.cz3003.smsclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3003.message.SMSMessage;
import com.cz3003.utils.DeviceUuidFactory;

public class SMS extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	private static final String SENT = "SMS_SENT";
	private static final String DELIVERED = "SMS_DELIVERED";
	static int uniqueSMSId = 1;
    Client client;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    ArrayList<SMSMessage> messageList = new ArrayList<SMSMessage>();
    //public PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	final SMS sms = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		registerReceivers();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		
		new Thread(new Runnable(){
		    public void run()
		    {
		    	DeviceUuidFactory uuid = new DeviceUuidFactory(getApplicationContext());
		    	client = new Client("172.22.178.49", 5832, uuid.getDeviceUuid().toString(),sms);
				client.connect();
		    }
		}).start();
		Toast.makeText(getBaseContext(), "Test", Toast.LENGTH_SHORT).show();
	}
	
	public void registerReceivers(){
		//---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
            	Log.w("sms","SMS sent" + sdf.format(new Date()));
            	SMSMessage message=null;
            	for(SMSMessage tempMessage:messageList){
            		if(tempMessage.getIncidentId()==arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)){
            			message= tempMessage;
            			break;
            		}return;
            	}
                switch (getResultCode())
                {
                    case Activity.RESULT_OK: {
                    	if(message!=null&&message.getSent()==1){
                    		client.sendMessage(new SMSMessage(SMSMessage.SUCCESS,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " sent at " + sdf.format(new Date())));
                    		//messageList.remove(message);
                    	}else if(message!=null){
                    		message.setSent(message.getSent()-1);
                    	}
                    	
                        Toast.makeText(getApplicationContext(), "SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " sent" + sdf.format(new Date()), 
                                Toast.LENGTH_SHORT).show();
                        //Log.w("sms","SMS sent" + sdf.format(new Date()));
                    }
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    {
                    	client.sendMessage(new SMSMessage(SMSMessage.UNABLE_TO_SEND,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " unable to send sms for unknown reason. " + sdf.format(new Date())));
                        Toast.makeText(sms, "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    {
                    	client.sendMessage(new SMSMessage(SMSMessage.UNABLE_TO_CONNECT_TO_NETWORK,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " unable to connect to network due to lack of service. " + sdf.format(new Date())));
                        Toast.makeText(sms, "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    {
                    	client.sendMessage(new SMSMessage(SMSMessage.UNABLE_TO_CONNECT_TO_NETWORK,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " unable to connect to network due to lack to radio. " + sdf.format(new Date())));
                        Toast.makeText(sms, "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                    {
                    	client.sendMessage(new SMSMessage(SMSMessage.UNABLE_TO_CONNECT_TO_NETWORK,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " unable to connect to network due to radio turned off. " + sdf.format(new Date())));
                        Toast.makeText(sms, "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
            	Log.w("sms","SMS delivered" + sdf.format(new Date()));
            	SMSMessage message=null;
            	for(SMSMessage tempMessage:messageList){
            		if(tempMessage.getIncidentId()==arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)){
            			message= tempMessage;
            			break;
            		}return;
            	}
            	
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                    {
                    	if(message!=null&&message.getDelivered()==1){
                    		client.sendMessage(new SMSMessage(SMSMessage.SUCCESS,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS id " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + " delivered at "+ sdf.format(new Date())));
                    		messageList.remove(message);
                    	}else if(message!=null){
                    		message.setDelivered(message.getDelivered()-1);
                    	}
                    	
                    	Toast.makeText(sms, "SMS delivered"+ sdf.format(new Date()), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                        
                    case Activity.RESULT_CANCELED:
                    	client.sendMessage(new SMSMessage(SMSMessage.UNABLE_TO_DELIVER,arg1.getIntExtra("com.cz3003.smsclient.smsid", 0),"SMS id " + Integer.toString(arg1.getIntExtra("com.cz3003.smsclient.smsid", 0)) + "  was unable to be delivered due to rejection by recipient's telco. "+ sdf.format(new Date())));
                        Toast.makeText(sms, "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));    
	}
	
	public void sendSMS(SMSMessage smsMessage){  
		messageList.add(smsMessage);
		SmsManager sms = SmsManager.getDefault();
        Intent sentIntent = new Intent(SENT).putExtra("com.cz3003.smsclient.smsid", smsMessage.getIncidentId());
        Intent deliveredIntent = new Intent(DELIVERED).putExtra("com.cz3003.smsclient.smsid", smsMessage.getIncidentId());
        
        //sentIntent.putExtra("com.cz3003.smsclient.smsid", smsMessage.getIncidentId());
        //deliveredIntent.putExtra("com.cz3003.smsclient.smsid", smsMessage.getIncidentId());
        
        
        if(smsMessage.getMessage().length()>160){
        	
        	ArrayList<String> parts = sms.divideMessage(smsMessage.getMessage());
        	ArrayList<PendingIntent> sentList = new ArrayList<PendingIntent>();
        	ArrayList<PendingIntent> receivedList = new ArrayList<PendingIntent>();
        	
        	for(int i =0;i<(int)Math.ceil(smsMessage.getMessage().length()/160.0);i++){
        		sentList.add(PendingIntent.getBroadcast(this, (uniqueSMSId%50)+i,
        		sentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        		receivedList.add(PendingIntent.getBroadcast(this, (uniqueSMSId%50)+i,
                		deliveredIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        	}
        	smsMessage.setSent((int)Math.ceil(smsMessage.getMessage().length()/160.0));
        	smsMessage.setDelivered((int)Math.ceil(smsMessage.getMessage().length()/160.0));
        	sms.sendMultipartTextMessage(smsMessage.getRecipient(), null, parts, sentList, receivedList);
        	
        }else{
        	PendingIntent sentPI = PendingIntent.getBroadcast(this, uniqueSMSId%50,
            		sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, uniqueSMSId%50,
            		deliveredIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        	smsMessage.setSent(1);
        	smsMessage.setDelivered(1);
        	sms.sendTextMessage(smsMessage.getRecipient(), null, smsMessage.getMessage(), sentPI, deliveredPI);
        	
        }
        
        
        uniqueSMSId++;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sms, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd_HH:mm:ss");
			//String currentDateandTime = sdf.format(new Date());
			
			//SmsManager smsManager = SmsManager.getDefault();
			//darling
			//String smsString = createMsg(sdf);
			//smsManager.sendTextMessage("97368902", null, smsString, null, null);
			//lim guan
			//smsManager.sendTextMessage("92230282", null, "Jia De loves June and...\nThe time this SMS was sent is: " + sdf.format(new Date()) + "\nPlease tell me how long it took you to receive. Or apps me.", null, null);
			//sri
			//smsManager.sendTextMessage("81127957", null, "Jia De loves June and...\nThe time this SMS was sent is: " + sdf.format(new Date()) + "\nPlease tell me how long it took you to receive. Or apps me.", null, null);
			//wei leng
			//smsManager.sendTextMessage("94593932", null, "Jia De loves June and...\nThe time this SMS was sent is: " + sdf.format(new Date()) + "\nPlease tell me how long it took you to receive. Or apps me.", null, null);
			//tiffany
			//smsManager.sendTextMessage("92266801", null, "Jia De loves June and...\nThe time this SMS was sent is: " + sdf.format(new Date()) + "\nPlease tell me how long it took you to receive. Or apps me.", null, null);
			//textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			//textView.setText(smsString);
			return textView;
		}
		
		public String createMsg(SimpleDateFormat sdf){
			return randomASCII()+"The time this SMS was sent is: " + sdf.format(new Date());
		}
		public String randomASCII(){
			Random rand = new Random();
			String[] asciiArray = {
					"\n(\\__/)\n( =-.-)\nC(\")(\")\n",
					"\n,¡i|¹i¡¡i¹|i¡,\n`'¹li¡|¡|¡il¹´ m u a h\n",
					"\n╔══╗╔╗ ♥\n╚╗╔╝║║♫═╦╦╦╔╗\n╔╝╚╗♫╚╣║║║║╔╣\n╚══╝╚═╩═╩═╩═╝\nஜ۩۞۩ஜ YOU ஜ۩۞۩ஜ\n",
					"\n╔╗╔═╦╦╦═╗\n║╚╣║║║║╩╣\n╚═╩═╩═╩═╩\n",
					"\nن٥ﻻ ﻉ√٥ﺎ ٱ\n",
					"\n╔═══╗♫\n║███║\n║ (o) Now Playing: Love\n╚═══╝\n"
					};
			return asciiArray[rand.nextInt(asciiArray.length)];
		}
	}

	}
