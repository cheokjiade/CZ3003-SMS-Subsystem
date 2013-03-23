package test.cz3003.server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.cz3003.logs.SMSClientLog;
import com.cz3003.message.SMSMessage;
import com.cz3003.server.SMSLog;

public class SMSLogTest {

	@Test
	public void testSMSLog() {
		SMSLog smsLog = new SMSLog();
		assertEquals(new ArrayList<SMSClientLog>(), smsLog.getSmsClientArrayList());
		//fail("Not yet implemented");
	}

	@Test
	public void testSelectBestClient() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		smsLog.addClient(2);
		smsLog.editClientScore(1, new SMSMessage(SMSMessage.DELIVERED, 0, ""));
		assertEquals(1, smsLog.selectBestClient());
		//fail("Not yet implemented");
	}

	@Test
	public void testRemoveClient() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		smsLog.addClient(2);
		assertTrue(smsLog.removeClient(1));
		assertEquals(null, smsLog.selectClientsLog(1));
		assertNotNull(smsLog.selectClientsLog(2));
		//fail("Not yet implemented");
	}

	@Test
	public void testAddClient() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		assertNotNull(smsLog);
		//fail("Not yet implemented");
	}

	//if score is updated, message is received
	@Test
	public void testOnMessageReceived() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		smsLog.selectClientsLog(1);
		smsLog.editClientScore(1, new SMSMessage(SMSMessage.DELIVERED, 0, ""));
		assertFalse(smsLog.selectClientsLog(1).getScore()==0);
		//fail("Not yet implemented");
	}

	@Test
	public void testEditClientScore() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		smsLog.selectClientsLog(1);
		smsLog.editClientScore(1, new SMSMessage(SMSMessage.DELIVERED, 0, ""));
		assertFalse(smsLog.selectClientsLog(1).getScore()==0);
		//fail("Not yet implemented");
	}

	@Test
	public void testSelectClientsLog() {
		SMSLog smsLog = new SMSLog();
		smsLog.addClient(1);
		assertEquals(1, smsLog.selectClientsLog(1).getUniqueId());
		//fail("Not yet implemented");
	}

}
