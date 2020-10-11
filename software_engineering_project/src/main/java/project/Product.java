package project;

public class Product {
	
	
	private String productCode=null;
	private String productName=null;
	private String productLine=null;
	private String productScale=null;
	private String productVendor=null;
	private String productDescription=null;
	private int quantityInStock=0;
	private double buyPrice=0.0;
	private double mSRP=0.0;
	
	public Product(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, int quantityInStock, double buyPrice, double mSRP) {
		this.productCode=productCode;
		this.productName=productName;
		this.productLine=productLine;
		this.productScale=productScale;
		this.productVendor=productVendor;
		this.productDescription=productDescription;
		this.quantityInStock=quantityInStock;
		this.buyPrice=buyPrice;
		this.mSRP=mSRP;
	}
	
	public String getProductCode() {
		return this.productCode;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public String getProductLine() {
		return this.productLine;
	}
	
	public String getProductScale() {
		return this.productScale;
	}
	
	public String getProductVendor() {
		return this.productVendor;
	}
	
	public String getProductDescription(){
		return this.productDescription;
	}
	
	public int getQuantityInStock() {
		return this.quantityInStock;
	}
	
	public double getBuyPrice() {
		return this.buyPrice;
	}
	
	public double getMSRP() {
		return this.mSRP;
	}
}
