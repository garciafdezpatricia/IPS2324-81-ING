package util;

public class AppointmentBLDto {
	public int id;
	public int patientid;
	public int doctorid;
	public int officeid;
	public String startDate;
	public String endDate;
	public boolean urgency;
	public boolean attended;
	public boolean checkIn;
	public boolean checkOut;
	public String information;
	public String officeCode;
	
	public String toString() {
		String aux = "Info:"+ information+" - ";
		
		aux+= "Starts at "+startDate +" and finishes at "+endDate.split(" ")[1]+" in office: "+officeCode;
		return aux;
	}

}
