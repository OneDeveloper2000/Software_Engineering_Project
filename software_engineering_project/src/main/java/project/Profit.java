package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In Profit class I satisfy the requirement: "Compute the profit generated by each product line, sorted by profit descending.".
 */
public class Profit {

	//with con we get access to the database.
	private Connect con=null;
	
	//In orders we store all the order records that are stored in the Orders table of the database.
	private List<Order> orders =null;
	
	//In orderDetails we store all the orderDetails records that are stored in the OrderDetails table of the database.
	private List<OrderDetails> orderDetails=null;
	
	//In products we store all the product records that are stored in the Product table of the database.
	private List<Product> products=null;
	
	/**
	 * profitOfProductLines map contains the profit generated by each product line. The keys of the map
	 * have the product line names and the values have the corresponding profit. Note that this map is not
	 * sorted. 
	 * For each productCode, we find the corresponding orderdetails records of the
	 * orders not cancelled. We then accumulate the profit generated by multiplying the quantity ordered
	 * with the difference of priceEach and buyPrice. That product profit is then added to the productLine
	 * profit.
	 */
	private Map<String, Double> profitOfProductLines=null;
	
	/**
	 * orderNumberOfOrdersNotCancelled list contains the order numbers of the orders that are not 
	 * considered as cancelled. By taking a look at the status column in the Orders table of the database
	 * I can see that the status of an order can be one of these states:"In Process", "Shipped", "Cancelled",
	 * "Disputed", "Resolved" or "On Hold". So I will save in the orderNumberOfOrdersNotCancelled list
	 * the orderNumber(Orders.orderNumber) of the orders that their status(Orders.status) is not equalled
	 * to "Cancelled".
	 */
	private List<Integer> orderNumberOfOrdersNotCancelled=null;
	
	/**
	 * We filter the product codes of the orders( that were not cancelled),
	 * from the order details table, and save them in
	 * productCodeOfOrderDetailsNotCancelled list.
	 */
	private List<String> productCodeOfOrderDetailsNotCancelled=null;
	
	//sortedProductLine contains the product line names sorted descending according to their corresponding profits.
	String[] sortedProductLine=null;
	
	//sortedProfit contains the profits of each product line sorted by descending.
	double[] sortedProfit=null;
	
	/**
	 * When we call the ProductsNotSold constructor, the arraylists to store objects are initialised.
	 * Then we try to get a connection to the database. We then store all the orderDetails, orders, 
	 * products records that are stored in OrderDetails, Orders, Products tables of the database
	 * in the corresponding lists. We then close the connection to the database and call the method
	 * to find and store the the profit generated by each product line. We call a sorting algorithm,
	 * in order to sort them descending.
	 */
	public Profit() {
		this.orders=new ArrayList <Order>();
		this.orderDetails=new ArrayList <OrderDetails>();
		this.products=new ArrayList <Product>();
		this.profitOfProductLines=new HashMap<String, Double>();
		this.orderNumberOfOrdersNotCancelled=new ArrayList<Integer>();
		this.productCodeOfOrderDetailsNotCancelled=new ArrayList<String>();
		this.con=ConnectFactory.getConnectInstance();
		this.setOrderDetails();
		this.setOrders();
		this.setProducts();
		this.con.closeConnection();
		this.setProfitOfProductLines();
		this.setSortedProfitOfProductLines();
	}
	
	/**
	 * Store all the orders that are stored in the Orders table of the database to the orders list.
	 */
	private void setOrders() {
		try {
			ResultSet rs=this.con.useTable("Orders");
			while(rs.next()) {
				int orderNumber=rs.getInt("orderNumber");
				String orderDate=rs.getString("orderDate");
				String requiredDate=rs.getString("requiredDate");
				String shippedDate=rs.getString("shippedDate");
				String status=rs.getString("status");
				String comments=rs.getString("comments");
				int customerNumber=rs.getInt("customerNumber");
				Order order=new Order(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
				this.orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Store all the order details that are stored in the OrderDetails table of the database to the orderDetails list.
	 */
	private void setOrderDetails() {
		try {
			ResultSet rs=this.con.useTable("OrderDetails");
			while(rs.next()) {
				int orderNumber=rs.getInt("orderNumber");
				String productCode=rs.getString("productCode");
				int quantityOrdered=rs.getInt("quantityOrdered");
				double priceEach=rs.getDouble("priceEach");
				int orderLineNumber=rs.getInt("orderLineNumber");
				OrderDetails OrderD=new OrderDetails(orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber);
				this.orderDetails.add(OrderD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Store all the products that are stored in the Products table of the database to the products list.
	 */
	private void setProducts() {
		try {
			ResultSet rs=this.con.useTable("Products");
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				String productName=rs.getString("productName");
				String productLine=rs.getString("productLine");
				String productScale=rs.getString("productScale");
				String productVendor=rs.getString("productVendor");
				String productDescription=rs.getString("productDescription");
				int quantityInStock=rs.getInt("quantityInStock");
				double buyPrice=rs.getDouble("buyPrice");
				double mSRP=rs.getDouble("MSRP");
				Product product=new Product(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice,mSRP);
				this.products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * set orderNumberOfOrdersNotCancelled list
	 */
	private void setOrderNumberOfOrdersNotCancelled() {
		//Iterate through the orders list
		for (Order order:this.orders) {
			String status=order.getStatus();
			//Check if the status of that order is not equalled to "Cancelled".
			//If that is true, get the order number of that order and add it
			//to the orderNumberOfOrdersNotCancelled list.
			if (!(status.equals("Cancelled"))) {
				int orderNumber=order.getOrderNumber();
				this.orderNumberOfOrdersNotCancelled.add(orderNumber);
			}
		}
	}
	
	/**
	 * set productCodeOfOrderDetailsNotCancelled list
	 */
	private void setProductCodeOfOrderDetailsNotCancelled() {
		//Iterate through the orderDetails list
		for (OrderDetails orderD:this.orderDetails) {
			int orderNumber=orderD.getOrderNumer();
			String productCode=orderD.getProductCode();
			
			//For each OrderDetails object check if the following two things hold:
			//1)If the orderNumberOfOrdersNotCancelled list contains the order number of that OrderDetails object.That
			//means-> if the order with that order number is not considered as "Cancelled".
			//2)If  the productCodeOfOrderDetailsNotCancelled list does not contains the product of that OrderDetails object.
			//That means that if productCodeOfOrderDetailsNotCancelled contains the product code of that OrderDetails object,
			//we will not add the product code again to the list as it is already in there.
			if (orderNumberOfOrdersNotCancelled.contains(orderNumber) && (!productCodeOfOrderDetailsNotCancelled.contains(productCode))){
				this.productCodeOfOrderDetailsNotCancelled.add(productCode);
			}
		}
	}
	
	/**
	 * calculate and set the profit of productLines list
	 */
	private void setProfitOfProductLines(){
		this.setOrderNumberOfOrdersNotCancelled();
		this.setProductCodeOfOrderDetailsNotCancelled();
		//Iterate through products list
		for (Product product:this.products) {
			//the profit for each product
			double profit=0.0;
			String productCode=product.getProductCode();
			String productLine=product.getProductLine();
			
			//if productCodeOfOrderDetailsNotCancelled contains the product code of that product,
			//then it means that we found an object that it had been sold at least once
			if(productCodeOfOrderDetailsNotCancelled.contains(productCode)) {
				double buyPrice=product.getBuyPrice();
				//if the above statement holds, we iterate through the orderDetails list
				for(OrderDetails orderD:this.orderDetails) {
					String productcode1=orderD.getProductCode();
					int orderNumber=orderD.getOrderNumer();
					
					//1)If the product code of the Product object in the products list is the same as the
					//product code of the the OrderDetails object in the orderDetails list AND
					//2)If the orderNumberOfOrdersNotCancelled list contains the order number of that OrderDetails
					//object.That means-> if the order with that order number is not considered as "Cancelled".
					if (productcode1.equals(productCode) && orderNumberOfOrdersNotCancelled.contains(orderNumber)) {
						int quantityOrdered=orderD.getQuantityOrdered();
						double priceEach=orderD.getPriceEach();
						profit=profit+(quantityOrdered*(priceEach-buyPrice));
					}
				}
				
				//if the map contains the product line we add to the existing profit
				//of the product line the new profit.
				if (this.profitOfProductLines.containsKey(productLine)) {
					double profitSoFar=this.profitOfProductLines.get(productLine);
					profitSoFar=Math.round((profit+ profitSoFar)*100.0)/100.0;
					this.profitOfProductLines.put(productLine, profitSoFar);
				}

				//if the map does not contain the product line
				else {
					profit=Math.round(profit*100.0)/100.0;
					this.profitOfProductLines.put(productLine, profit);
				}
			}
		}
	}
	
	/**
	 * I used Bubble Sort method in order to sort the profit generated by each product line.
	 */
	private void setSortedProfitOfProductLines() {
		//To do that I created two arrays, one for productlines and one for profits.
		this.sortedProductLine=new String[this.profitOfProductLines.size()];
		this.sortedProfit=new double[this.profitOfProductLines.size()];
		int z=0;
		
		//Iterate through the map and store the keys(productlines) to the string array
		//and the values(profits) to the double array.
		for(String productLine:this.profitOfProductLines.keySet()) {
			this.sortedProductLine[z]=productLine;
			this.sortedProfit[z]=this.profitOfProductLines.get(productLine);
			z=z+1;
		}
		
		//descending bubble sort
		for(int i=0;i<this.sortedProductLine.length-1;i++) {
			for (int j=0;j<this.sortedProductLine.length-i-1;j++) {
				double profit1=this.sortedProfit[j];
				double profit2=this.sortedProfit[j+1];
				if(profit1<profit2) {
					this.sortedProfit[j+1]=profit1;
					this.sortedProfit[j]=profit2;
					String productLineTemp=this.sortedProductLine[j+1];
					this.sortedProductLine[j+1]=this.sortedProductLine[j];
					this.sortedProductLine[j]=productLineTemp;
				}
			}
		}
	}
	
	/**
	 * Display to console the profit generated by each product line sorted by descending.
	 */
	public void displayProfitOfProductLines() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("THE PROFIT OF EACH PRODUCT LINE \n");
		buffer.append(String.format("%-17s%s", "productLine", "Profit(in dollars)"));
		buffer.append("\n");
		for(int i=0;i<this.sortedProductLine.length;i++) {
			String productLineName=this.sortedProductLine[i];
			double profit=this.sortedProfit[i];
			buffer.append(String.format("%-17s%s", productLineName, profit));
			buffer.append("\n");
		}
		System.out.println(buffer);
	}
	
	/**
	 * @return the product line names sorted descending according to their corresponding profits.
	 */
	public String[] getSortedProductLine(){
		return this.sortedProductLine;
	}
	
	/**
	 * @return the profits of each product line sorted by descending.
	 */
	public double[] getSortedProfit(){
		return this.sortedProfit;
	}
}
