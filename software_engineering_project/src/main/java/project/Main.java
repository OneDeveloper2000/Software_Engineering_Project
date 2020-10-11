package project;


public class Main {

	public static void main(String[] args) {
		
		
		//requirement1
	    PaymentsQuery payments=new PaymentsQuery();
		payments.displayPaymentsGreaterThanThousandDollars();

		System.out.println();
	
		//requirement2
		ProductsNotSold products=new ProductsNotSold();
		products.displayProductsNotSold();

		System.out.println();
		
		//requirement3
		Profit profit=new Profit();
		profit.displayProfitOfProductLines();
	}
}
