package db;

import java.math.BigInteger;

import util.ConnectionFactory;

public class DiagnosisReport {
	
	public String date;
	public String report;
	public int diagnosisID;
	public BigInteger doctorID;
	
	public DiagnosisReport(String report, String date, BigInteger doctor) {
		this.date = date;
		this.report = report;
		this.doctorID = doctor;
	}
	
	private String getDoctorName() {
		return ConnectionFactory.getDoctor(doctorID);
	}
	
	public String toString() {
		return "By: " + getDoctorName() + " on: " + date + ". '" + report + "'.";
	}

}
