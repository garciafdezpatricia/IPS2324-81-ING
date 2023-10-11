package db;

import java.sql.Date;

public class WorkDay {
	private int id;
	private String weekday;
	private String startHour;
	private String endHour;
	private int workperiodid;
	public WorkDay(int id, String weekday, String startHour, String endHour, int workperiodid) {
		super();
		this.id = id;
		this.weekday = weekday;
		this.startHour = startHour;
		this.endHour = endHour;
		this.workperiodid = workperiodid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getWorkperiodid() {
		return workperiodid;
	}
	public void setWorkperiodid(int workperiodid) {
		this.workperiodid = workperiodid;
	}
	@Override
	public String toString() {
		return "WorkDay [id=" + id + ", weekday=" + weekday + ", startHour=" + startHour + ", endHour=" + endHour
				+ ", workperiodid=" + workperiodid + "]";
	}
	
	
}
