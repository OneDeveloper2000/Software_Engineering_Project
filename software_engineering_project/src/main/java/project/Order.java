package project;

public class Order {
	
	
	private int orderNumber=0;
	private String orderDate=null;
	private String requiredDate=null;
	private String shippedDate=null;
	private String status=null;
	private String comments=null;
	private int customerNumber=0;
	
	public Order(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments,int customerNumber) {
		this.orderNumber=orderNumber;
		this.orderDate=orderDate;
		this.requiredDate=requiredDate;
		this.shippedDate=shippedDate;
		this.status=status;
		this.comments=comments;
		this.customerNumber=customerNumber;
	}
	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	public String getOrdertDate() {
		return this.orderDate;
	}
	
	public String getRequiredDate() {
		return this.requiredDate;
	}
	
	public String getShippedDate() {
		return this.shippedDate;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getComments() {
		return this.comments;
	}
	
	public int getCustomerNumber() {
		return this.customerNumber;
	}
}
