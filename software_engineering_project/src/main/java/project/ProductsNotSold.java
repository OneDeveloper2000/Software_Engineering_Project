package project;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/** 
 *  In ProductsNotSold class I satisfy the requirement "Report the products that have not been sold."  
 */
public class ProductsNotSold {

	//with con we get access to the database
	private Connect con=null;
	
	//In orders we store all the order records that are stored in the Orders table of the database
	private List<Order> orders = null;
	
	//In orderDetails we store all the orderDetails records that are stored in the OrderDetails table of the database
	private List<OrderDetails> orderDetails=null;
	
	//In products we store all the product records that are stored in the Product table of the database
	private List<Product> products=null;
	
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
	
	/**
	 * productsNotSold list contains the products that have not been sold.
	 * All the productCodes are checked against productCodeOfOrderDetailsNotCancelled
	 * list and if productCodes not found, these products are saved in
	 * productsNotSold list.
	 */
	private List<Product> productsNotSold=null;
	
	
	/**
	 * When we call the ProductsNotSold constructor, the arraylists to store objects are initialised.
	 * Then we try to get a connection to the database. We then store all the orderDetails, orders, 
	 * products records that are stored in OrderDetails, Orders, Products tables of the database
	 * in the corresponding lists. We then close the connection to the database and call the method
	 * to find and store the products that have not been sold.
	 */
	public ProductsNotSold() {
		this.orders=new ArrayList <Order>();
		this.orderDetails=new ArrayList <OrderDetails>();
		this.products=new ArrayList <Product>();
		this.productsNotSold=new ArrayList<Product>();
		this.orderNumberOfOrdersNotCancelled=new ArrayList<Integer>();
		this.productCodeOfOrderDetailsNotCancelled=new ArrayList<String>();
		this.con=ConnectFactory.getConnectInstance();
		this.setOrderDetails();
		this.setOrders();
		this.setProducts();
		this.con.closeConnection();
		this.setProductsNotSold();
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
	 * Store all the orderdetails that are stored in the OrderDetails table of the database to the orderDetails list.
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
	 * set productsNotSold list
	 */
	private void setProductsNotSold(){
		this.setOrderNumberOfOrdersNotCancelled();
		this.setProductCodeOfOrderDetailsNotCancelled();
		
		//Iterate through the product list and get the product code for each object.
		for (Product product:this.products) {
			String productCode=product.getProductCode();
			
			//if productCodeOfOrderDetailsNotCancelled does not contain the product code of that product,
			//then it means that we found an object that has never been sold, so we add it to the
			//productsNotSold list.
			if(!(productCodeOfOrderDetailsNotCancelled.contains(productCode))) {
				this.productsNotSold.add(product);
			}
		}
	}
	
	/**
	 * @return a list of products. These are the products that have not been sold.
	 * 
	 */
	public List<Product> getProductsNotSold(){
		return this.productsNotSold;
	}
	
	/**
	 * Display to console the products that have not been sold.
	 */
	public void displayProductsNotSold() {
		StringBuffer buffer=new StringBuffer();
		Iterator<Product> it=this.productsNotSold.iterator();
		buffer.append("THE FOLLOWING PRODUCTS ARE THE PRODUCTS THAT ITS PRODUCT CODE HAS NOT BEEN SOLD NOT EVEN ONCE \n");
		buffer.append(String.format("%-12s%-45s%-17s%-13s%-28s%-16s%-9s%-9s%s", "productCode", "productName", "productLine", "productScale", "productVendor", "quantityInStock", "buyPrice", "mSRP", "productDescription"));
		buffer.append("\n");
		while(it.hasNext()) {
			Product product=it.next();
			String productCode=product.getProductCode();
			String productName=product.getProductName();
			String productLine=product.getProductLine();
			String productScale=product.getProductScale();
			String productVendor=product.getProductVendor();
			String productDescription=product.getProductDescription();
			int quantityInStock=product.getQuantityInStock();
			double buyPrice=product.getBuyPrice();
			double mSRP=product.getMSRP();
			buffer.append(String.format("%-12s%-45s%-17s%-13s%-28s%-16s%-9s%-9s%s", productCode, productName, productLine, productScale, productVendor, quantityInStock, buyPrice, mSRP, productDescription));
			buffer.append("\n");
		}
		System.out.println(buffer);
	}
}
