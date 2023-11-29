package db;

import java.math.BigInteger;

public class Diagnosis {
	
	private final static int NOT_TRACKED = 0;
	private final static int OPENED = 1;
	private final static int CLOSED = 2;
	
	public int id;
	public String code;
	public String description;
	public String longDescription;
	public String info;
	public String date;
	public BigInteger doctor;
	public int status;
	
	public Diagnosis(String code, String description, String longDesc) {
		this.code = code;
		this.description = description;
		this.longDescription = longDesc;
	}

	public Diagnosis(String info, String date, BigInteger doctor, int status) {
		this.info = info;
		this.date = date;
		this.doctor = doctor;
		this.status = status;
	}
	
	public Diagnosis() {
		
	}
	
	public String toString() {
		String status = "";
		if (this.status == this.NOT_TRACKED)
			status = "Not tracked diagnosis";
		else if (this.status == this.OPENED)
			status = "Opened diagnosis";
		else if (this.status == this.CLOSED)
			status = "Closed diagnosis";
		return this.info + " -> " + status;
	}
	
}
