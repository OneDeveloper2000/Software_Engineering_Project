package project;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Order;

public class OrderTest {
	
	
	private Order order=null;

	@Before
	public void setUp() throws Exception {
		this.order=new Order(10127, "2003-06-03", "2003-06-09", "2003-06-06", "Shipped", "Customer requested special shippment. The instructions were passed along to the warehouse", 151);
	}
	
	@Test
	public void testGetOrderNumber() {
		assertEquals(10127, this.order.getOrderNumber());
	}
	
	@Test
	public void testGetOrdertDate() {
		assertEquals("2003-06-03", this.order.getOrdertDate());
	}
	
	@Test
	public void testGetRequiredDate() {
		assertEquals("2003-06-09", this.order.getRequiredDate());
	}
	
	@Test
	public void testGetShippedDate() {
		assertEquals("2003-06-06", this.order.getShippedDate());
	}
	
	@Test
	public void testGetStatus() {
		assertEquals("Shipped", this.order.getStatus());
	}
	
	@Test
	public void testGetComments() {
		assertEquals("Customer requested special shippment. The instructions were passed along to the warehouse", this.order.getComments());
	}
	
	@Test
	public void testGetCustomerNumber() {
		assertEquals(151, this.order.getCustomerNumber());
	}

	@After
	public void tearDown() throws Exception {
		this.order=null;
	}

}
