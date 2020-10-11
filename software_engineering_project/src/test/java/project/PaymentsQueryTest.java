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
import project.Payment;
import project.PaymentsQuery;

public class PaymentsQueryTest {
	
	
	private PaymentsQuery paymentsQuery=null;
	
	//with con to get access to the database
	private Connect con=null;

	@Before
	public void setUp() throws Exception {
		this.paymentsQuery=new PaymentsQuery();
	}
	
	@Test
	public void testGetPaymentsGreaterThanThousandDollars(){
		
		//get the arraylist produced in code
		List<Payment> payments1=new ArrayList<Payment>();
		payments1=this.paymentsQuery.getPaymentsGreaterThanThousandDollars();
		
		//store the results when I run the MySQL query
		List<Payment> payments2=new ArrayList<Payment>();
		this.con=ConnectFactory.getConnectInstance();
		String query="SELECT * FROM Payments WHERE amount>100000";
		ResultSet resultSet=this.con.excecuteQuery(query);
		boolean isValid=true;
		try {
			while (resultSet.next()) {
				int customerNumber=resultSet.getInt("customerNumber");
				String checkNumber=resultSet.getString("checkNumber");
				String paymentDate=resultSet.getString("paymentDate");
				double amount=resultSet.getDouble("amount");
				Payment payment=new Payment(customerNumber, checkNumber, paymentDate, amount);
				payments2.add(payment);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		//if the size of the 2 array lists is not the same then the test case will fail.
		if (payments1.size()!= payments2.size()) {
			isValid=false;
		}
		//Before I compare the 2 arraylists I first need to sort them on a column as the 2 lists
		//may contain the same objects but in different order. So I sort them ascending on the
		//checkNumber.
		else {
			//sort ascending on checkNumber
			Collections.sort(payments2, new Comparator<Payment>(){

				@Override
				public int compare(Payment p1, Payment p2) {
					return (p1.getCheckNumber().compareTo(p2.getCheckNumber()));
				}
			});	
			
			//sort ascending on checkNumber
			Collections.sort(payments1, new Comparator<Payment>() {

				@Override
				public int compare(Payment p1, Payment p2) {
					return (p1.getCheckNumber().compareTo(p2.getCheckNumber()));
				}
			});
			
			//check if the the 2 array lists have the same contents
			for (int i=0;i<payments1.size();i++) {
				Payment payment1=payments1.get(i);
				Payment payment2=payments2.get(i);
				if ((payment1.getAmount() != payment2.getAmount()) || (!(payment1.getCheckNumber().equals(payment2.getCheckNumber()))) ||
					(payment1.getCustomerNumber() != payment2.getCustomerNumber()) || (!(payment1.getPaymentDate().equals(payment2.getPaymentDate())))) {
					isValid=false;
				}
			}
		}
		assertTrue(isValid);
	}

	@After
	public void tearDown() throws Exception {
		this.paymentsQuery=null;
		this.con=null;
	}

}
