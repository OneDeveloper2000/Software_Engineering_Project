package project;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Connect;
import project.ConnectFactory;
import project.Product;
import project.ProductsNotSold;

public class ProductsNotSoldTest {
	
	
	private ProductsNotSold productsNotSold=null;
	
	//with con to get access to the database
	private Connect con=null;

	@Before
	public void setUp() throws Exception {
		this.productsNotSold=new ProductsNotSold();
	}
	
	@Test
	public void testGetProductsNotSold(){
		
		//get the arraylist produced in code
		List<Product> products1=new ArrayList<Product>();
		products1=this.productsNotSold.getProductsNotSold();

		//store the results when I run the MySQL query
		List<Product> products2=new ArrayList<Product>();
		this.con=ConnectFactory.getConnectInstance();
		String query="SELECT * FROM Products WHERE productCode NOT IN " + 
				"(SELECT DISTINCT productCode FROM OrderDetails WHERE orderNumber IN " + 
				"(SELECT orderNumber FROM Orders  WHERE status <> \"Cancelled\"))";
		ResultSet resultSet=this.con.excecuteQuery(query);
		boolean isValid=true;
		try {
			while (resultSet.next()) {
				String productCode=resultSet.getString("productCode");
				String productName=resultSet.getString("productName");
				String productLine=resultSet.getString("productLine");
				String productScale=resultSet.getString("productScale");
				String productVendor=resultSet.getString("productVendor");
				String productDescription=resultSet.getString("productDescription");
				int quantityInStock=resultSet.getInt("quantityInStock");
				double buyPrice=resultSet.getDouble("buyPrice");
				double mSRP=resultSet.getDouble("MSRP");
				Product product=new Product(productCode, productName, productLine, productScale, productVendor,
						productDescription, quantityInStock, buyPrice, mSRP);
				products2.add(product);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//if the size of the 2 array lists is not the same then the test case will fail.
		if(products1.size() != products2.size()) {
			isValid=false;
		}
		
		//Before I compare the 2 arraylists I first need to sort them on a column as the 2 lists
		//may contain the same objects but in different order. So I sort them ascending on the
		//productCode.
		else {
			//sort ascending on productCode
			Collections.sort(products2, new Comparator<Product>(){
				@Override
				public int compare(Product p1, Product p2) {
					return (p1.getProductCode().compareTo(p2.getProductCode()));
				}
			});	
			//sort ascending on productCode
			Collections.sort(products1, new Comparator<Product>(){
				@Override
				public int compare(Product p1, Product p2) {
					return (p1.getProductCode().compareTo(p2.getProductCode()));
				}
			});	
			
			//check if the the 2 array lists have the same contents
			for (int i=0;i<products1.size();i++) {
				Product product1=products1.get(i);
				Product product2=products2.get(i);
				if ((!(product1.getProductCode().equals(product2.getProductCode()))) ||
					(!(product1.getProductName().equals(product2.getProductName()))) || 
					(!(product1.getProductLine().equals(product2.getProductLine()))) ||
					(!(product1.getProductScale().equals(product2.getProductScale()))) ||
					(!(product1.getProductVendor().equals(product2.getProductVendor()))) ||
					(!(product1.getProductDescription().equals(product2.getProductDescription()))) ||
					(product1.getQuantityInStock() != product2.getQuantityInStock()) ||
					(product1.getBuyPrice() != product2.getBuyPrice()) ||
					(product1.getMSRP() != product2.getMSRP())){
					isValid=false;
				}
			}	
		}
		assertTrue(isValid);
	}

	@After
	public void tearDown() throws Exception {
		this.productsNotSold=null;
	}

}
