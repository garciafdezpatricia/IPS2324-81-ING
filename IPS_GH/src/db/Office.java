package db;

public class Office {

	private int id;
	private String officeCode;
	
	public Office(int id, String officeCode) {
		super();
		this.id = id;
		this.officeCode = officeCode;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOfficeCode() {
		return this.officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	@Override
	public String toString() {
		return "Office: " + officeCode;
	}
}
