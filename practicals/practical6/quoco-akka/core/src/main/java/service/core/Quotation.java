package service.core;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation implements java.io.Serializable {
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
		
	}

	// default constructor
	public Quotation(){};
	private String company;
	private String reference;
	private double price;

	public String getCompany(){
		return this.company;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getReference(){
		return this.reference;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price){
		this.price = price;
	}
}
