package util;

import java.util.Objects;

public class AppointmentBLDto {
	@Override
	public int hashCode() {
		return Objects.hash(patientName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppointmentBLDto other = (AppointmentBLDto) obj;
		return Objects.equals(patientName, other.patientName);
	}

	public int id;
	public int patientid;

	public int getPatientid() {
		return patientid;
	}

	public int doctorid;
	public int officeid;
	public String startDate;
	public String endDate;
	public boolean urgency;
	public int attended;
	public String checkIn;
	public String checkOut;
	public String information;

	public String getInformation() {
		return information;
	}

	public String officeCode;
	public String patientName;
	public String patientSurname;
	public String doctorSurname;
	public String doctorName;

	public String toString() {
		String aux = "Info:" + information + " - ";

		aux += "Starts at " + startDate + " and finishes at " + endDate.split(" ")[1] + " in office: " + officeCode;
		return aux;
	}

}
