package project;

public class OrderDetails {
	
	
	private int orderNumber=0;
	private String productCode=null;
	private int quantityOrdered=0;
	private double priceEach=0.0;
	private int orderLineNumber=0;
	
	public OrderDetails(int orderNumber, String productCode, int quantityOrdered, double priceEach, int orderLineNumber) {
		this.orderNumber=orderNumber;
		this.productCode=productCode;
		this.quantityOrdered=quantityOrdered;
		this.priceEach=priceEach;
		this.orderLineNumber=orderLineNumber;
	}
	
	public int getOrderNumer() {
		return this.orderNumber;
	}
	
	public String getProductCode() {
		return this.productCode;
	}
	
	public int getQuantityOrdered() {
		return this.quantityOrdered;
	}
	
	public double getPriceEach() {
		return this.priceEach;
	}
	
	public int getOrderLineNumber() {
		return this.orderLineNumber;
	}
}
