package project;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Payment;

public class PaymentTest {
	
	
	private Payment payment=null;

	@Before
	public void setUp() throws Exception {
		this.payment=new Payment(103, "HQ336336", "2004-10-19",6066.78);
	}
	
	@Test
	public void testGetCustomerNumber() {
		assertEquals(103, this.payment.getCustomerNumber());
	}
	
	@Test
	public void testGetCheckNumber() {
		assertEquals("HQ336336", this.payment.getCheckNumber());
	}
	
	@Test
	public void testGetPaymentDate() {
		assertEquals("2004-10-19", this.payment.getPaymentDate());
	}
	
	@Test
	public void testGetAmount() {
		assertEquals(6066.78, this.payment.getAmount(), 0);
	}

	@After
	public void tearDown() throws Exception {
		this.payment=null;
	}

}
