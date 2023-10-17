package db;

import java.sql.Date;

public class WorkPeriod {

	private int id;
	private Date startDate;
	private Date endDate;
	private int id_doctor;
	public WorkPeriod(int id, Date startDate, Date endDate, int id_doctor) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id_doctor = id_doctor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getId_doctor() {
		return id_doctor;
	}
	public void setId_doctor(int id_doctor) {
		this.id_doctor = id_doctor;
	}
	@Override
	public String toString() {
		return "WorkPeriod [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", id_doctor=" + id_doctor
				+ "]";
	}
	

}
