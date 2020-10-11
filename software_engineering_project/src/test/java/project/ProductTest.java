package project;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Product;

public class ProductTest {
	
	
	private Product product=null;

	@Before
	public void setUp() throws Exception {
		this.product=new Product("S10_1678", "1969 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", 
				"This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts areparticularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7);
	}
	
	@Test
	public void getProductCode() {
		assertEquals("S10_1678", this.product.getProductCode());
	}
	
	@Test
	public void getProductName() {
		assertEquals("1969 Harley Davidson Ultimate Chopper", this.product.getProductName());
	}
	
	@Test
	public void getProductLine() {
		assertEquals("Motorcycles", this.product.getProductLine());
	}
	
	@Test
	public void getProductScale() {
		assertEquals("1:10", this.product.getProductScale());
	}
	
	@Test
	public void getProductVendor() {
		assertEquals("Min Lin Diecast", this.product.getProductVendor());
	}
	
	@Test
	public void getProductDescription(){
		assertEquals("This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts areparticularly delicate due to their precise scale and require special care and attention.",
				this.product.getProductDescription());
	}
	
	@Test
	public void getQuantityInStock() {
		assertEquals( 7933, this.product.getQuantityInStock());
	}
	
	@Test
	public void getBuyPrice() {
		assertEquals(48.81, this.product.getBuyPrice(), 0);
	}
	
	@Test
	public void getMSRP() {
		assertEquals(95.7, this.product.getMSRP(), 0);
	}

	@After
	public void tearDown() throws Exception {
		this.product=null;
	}

}
