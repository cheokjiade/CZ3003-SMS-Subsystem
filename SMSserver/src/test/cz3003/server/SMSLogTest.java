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
		//assertt
		fail("Not yet implemented");
	}

	@Test
	public void testAddClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnMessageReceived() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditClientScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectClientsLog() {
		fail("Not yet implemented");
	}

}
