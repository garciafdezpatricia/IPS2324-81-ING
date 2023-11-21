package db;

import java.math.BigInteger;

public class Diagnosis {
	
	public String code;
	public String description;
	public String longDescription;
	public String info;
	public String date;
	public BigInteger doctor;
	
	public Diagnosis(String code, String description, String longDesc) {
		this.code = code;
		this.description = description;
		this.longDescription = longDesc;
	}

	public Diagnosis(String info, String date, BigInteger doctor) {
		this.info = info;
		this.date = date;
		this.doctor = doctor;
	}
	
	public Diagnosis() {
		
	}
	
	public String toString() {
		return this.info;
	}
	
}
