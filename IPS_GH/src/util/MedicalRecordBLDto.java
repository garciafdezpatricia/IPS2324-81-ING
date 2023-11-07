package util;

public class MedicalRecordBLDto {

	public int id;
	public int doctorId;
	public int patientId;
	public String date;
	public String description;
	@Override
	public String toString() {
		return "Date: " + date + "\t - description: " + description;
	}
	
}
