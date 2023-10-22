package db;

import java.math.BigInteger;
import java.sql.Date;

public class WorkPeriod {

	private BigInteger id;
	private Date startDate;
	private Date endDate;
	private BigInteger id_doctor;
	
	
	public WorkPeriod(BigInteger id, Date startDate, Date endDate, BigInteger id_doctor) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id_doctor = id_doctor;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
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
	public BigInteger getId_doctor() {
		return id_doctor;
	}
	public void setId_doctor(BigInteger id_doctor) {
		this.id_doctor = id_doctor;
	}
	@Override
	public String toString() {
		return "WorkPeriod [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", id_doctor=" + id_doctor
				+ "]";
	}
	

}
