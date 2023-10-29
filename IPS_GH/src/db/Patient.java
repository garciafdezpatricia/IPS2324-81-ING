package db;

import java.math.BigInteger;

public class Patient {
	private BigInteger id;
	private String firstName;
	private String surName;
	private String dni;
	private String contactInfo;
	private int ssnumber;

	public Patient(BigInteger id, String firstName, String surName, String dni, String contactInfo, int ssnumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
		this.dni = dni;
		this.contactInfo = contactInfo;
		this.setSsnumber(ssnumber);
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Override
	public String toString() {
		return firstName + " " + surName;
	}

	public int getSsnumber() {
		return ssnumber;
	}

	public void setSsnumber(int ssnumber) {
		this.ssnumber = ssnumber;
	}

}
