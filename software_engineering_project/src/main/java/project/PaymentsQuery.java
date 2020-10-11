package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * In this class I satisfy the requirement: Report those payments greater than 100000 dollars.
 */
public class PaymentsQuery {
	
	//with con we get access to the database
	private Connect con=null;
	
	//In payments we store all the payment records that are stored in the Payments table of the database
	private List<Payment> payments=new ArrayList<Payment>();
	
	//paymentsGreaterThanThousandDollars contains all the payments that their amount is greater than thousand dollars
	private List<Payment> paymentsGreaterThanThousandDollars=new ArrayList<Payment>();
	
	/**
	 * When we call Payments Query constructor we try to get a connection to the database. Then, we store
	 * all the payment records that are stored in Payments table of the database in the payments list.
	 * We close the connection to the database and then find the payments that their amount is greater
	 * than thousand dollars and store them in the paymentsGreaterThanThousandDollars list.
	 */
	public PaymentsQuery() {
		this.con=ConnectFactory.getConnectInstance();
		this.setPayments();
		this.con.closeConnection();
		this.setPaymentsGreaterThanThousandDollars();
	}
	
	/**
	 * Store all the payments that are stored in the Payments table of the database to the payments list.
	 */
	private void setPayments() {
		try {
			ResultSet rs=this.con.useTable("Payments");
			while(rs.next()) {
				int customerNumber=rs.getInt("customerNumber");
				String checkNumber=rs.getString("checkNumber");
				String paymentDate=rs.getString("paymentDate");
				double amount=rs.getDouble("amount");
				Payment payment=new Payment(customerNumber, checkNumber, paymentDate, amount);
				this.payments.add(payment);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Find the payments that their amount is greater than thousand dollars and store them in
	 *  the paymentsGreaterThanThousandDollars list.
	 */
	private void setPaymentsGreaterThanThousandDollars(){
		Iterator <Payment> it=this.payments.iterator();
		while(it.hasNext()) {
			Payment pay=it.next();
			double amount=pay.getAmount();
			if (amount>100000) {
				this.paymentsGreaterThanThousandDollars.add(pay);
			}
		}
	}
	
	/**
	 * @return a list of Payments. These payments are the payments that are above 100000 dollars
	 */
	public List<Payment> getPaymentsGreaterThanThousandDollars(){
		return this.paymentsGreaterThanThousandDollars;
	}
	
	/**
	 * display to console the payments that are above 100000 dollars.
	 */
	public void displayPaymentsGreaterThanThousandDollars() {
		StringBuffer buffer=new StringBuffer();
		Iterator<Payment> it=this.paymentsGreaterThanThousandDollars.iterator();
		buffer.append("THE FOLLOWING PAYMENTS ARE ABOVE 100.000 DOLLARS \n");
		buffer.append(String.format("customerNumber checkNumber paymentDate amount \n"));
		while(it.hasNext()) {
			Payment payment=it.next();
			int customerNumber=payment.getCustomerNumber();
			String checkNumber= payment.getCheckNumber();
			String paymentDate=payment.getPaymentDate();
			double amount=payment.getAmount();
			buffer.append(String.format("%-15s%-12s%-12s%-7s", customerNumber, checkNumber, paymentDate, amount));
			buffer.append("\n");
		}
		System.out.println(buffer);
	}

}
