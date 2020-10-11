package project;

public class Payment {
	
	
	private int customerNumber=0;
	private String checkNumber=null;
	private String paymentDate=null;
	private double amount=0.0;
	
	public Payment(int customerNumber,String checkNumber, String paymentDate, double amount) {
		this.customerNumber=customerNumber;
		this.checkNumber=checkNumber;
		this.paymentDate=paymentDate;
		this.amount=amount;
	}
	
	public int getCustomerNumber() {
		return this.customerNumber;
	}
	
	public String getCheckNumber() {
		return this.checkNumber;
	}
	
	public String getPaymentDate() {
		return this.paymentDate;
	}
	
	public double getAmount() {
		return this.amount;
	}
}
