package project;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Connect;
import project.ConnectFactory;
import project.Profit;

public class ProfitTest {
	
	
	private Profit profit=null;
	
	//with con to get access to the database
	private Connect con=null;

	@Before
	public void setUp() throws Exception {
		this.profit=new Profit();
	}
	
	@Test
	public void testGetProfitOfProductLines() {
		
		//store the productline names when I run the MySQL query.
		List<String> productLines=new ArrayList<String>();
		
		//store the profits when I run the MySQL query
		List<Double> profits=new ArrayList<Double>();
		
		//get the sorted array that contains the productLine names.It was produced in code
		String[] sortedProductLines=this.profit.getSortedProductLine();
		
		//get the sorted array that contains the profits.It was produced in code
		double[] sortedProfits=this.profit.getSortedProfit();
		
		this.con=ConnectFactory.getConnectInstance();
		String query="SELECT Products.productLine, SUM(OrderDetails.quantityOrdered*(OrderDetails.priceEach-Products.buyPrice)) AS Profit " + 
				"FROM Products, OrderDetails WHERE " + 
				"(Products.productCode=OrderDetails.productCode) AND OrderDetails.orderNumber IN " + 
				"(SELECT OrderNumber FROM Orders WHERE status <> \"Cancelled\") " + 
				"GROUP BY Products.productLine " +
				"ORDER BY Profit DESC";
		ResultSet resultSet=this.con.excecuteQuery(query);
		boolean isValid=true;
		try {
			while (resultSet.next()) {
				String productLineName=resultSet.getString("productLine");
				double profit=resultSet.getDouble("Profit");
				productLines.add(productLineName);
				profits.add(profit);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//if the size of the array list and the size of the array related
		//with the productline name do not have the same size test case will fail.
		//if the size of the array list and the size of the array related
		//with the profit do not have the same size test case will fail.
		if (sortedProductLines.length!=productLines.size() || sortedProfits.length!=profits.size()) {
			isValid=false;
		}
		
		//check if the the array list and array related with the productline
		//names have the same contents
		//check if the the array list and array related with the profit
		//have the same contents
		else {
			for (int i=0;i<sortedProductLines.length;i++) {
				String productLineName1=sortedProductLines[i];
				double profit1=sortedProfits[i];
				String productLineName2=productLines.get(i);
				double profit2=profits.get(i);
				if ((!(productLineName1.equals(productLineName2))) ||
					(profit1!=profit2)) {
					isValid=false;
				}
			}
		}
		assertTrue(isValid);
	}

	@After
	public void tearDown() throws Exception {
		this.profit=null;
	}
}
