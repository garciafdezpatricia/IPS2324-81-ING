package db;

import java.math.BigInteger;

public class WorkDay {
	private BigInteger id;
	private String weekday;
	private String startHour;
	private String endHour;
	private BigInteger workperiodid;
	
	
	public WorkDay(BigInteger id, String weekday, String startHour, String endHour, BigInteger workperiodid) {
		super();
		this.id = id;
		this.weekday = weekday;
		this.startHour = startHour;
		this.endHour = endHour;
		this.workperiodid = workperiodid;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public BigInteger getWorkperiodid() {
		return workperiodid;
	}
	public void setWorkperiodid(BigInteger workperiodid) {
		this.workperiodid = workperiodid;
	}
	@Override
	public String toString() {
		return "WorkDay [id=" + id + ", weekday=" + weekday + ", startHour=" + startHour + ", endHour=" + endHour
				+ ", workperiodid=" + workperiodid + "]";
	}
	
	
}
