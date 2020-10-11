package project;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.OrderDetails;

public class OrderDetailsTest {
	
	
	private OrderDetails orderDetails=null;

	@Before
	public void setUp() throws Exception {
		this.orderDetails=new OrderDetails(10100, "S18_1749", 30, 136.0, 3);
	}
	
	@Test
	public void testGetOrderNumer() {
		assertEquals(10100, this.orderDetails.getOrderNumer());
	}
	
	@Test
	public void testGetProductCode() {
		assertEquals("S18_1749", this.orderDetails.getProductCode());
	}
	
	@Test
	public void testGetQuantityOrdered() {
		assertEquals(30, this.orderDetails.getQuantityOrdered());
	}
	
	@Test
	public void testGetPriceEach() {
		assertEquals(136.0, this.orderDetails.getPriceEach(), 0);
	}
	
	@Test
	public void testGetOrderLineNumber() {
		assertEquals(3, this.orderDetails.getOrderLineNumber());
		
	}

	@After
	public void tearDown() throws Exception {
		this.orderDetails=null;
	}


}
