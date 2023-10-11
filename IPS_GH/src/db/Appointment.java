package db;

public class Appointment {
	private int id;
	private int patientid;
	private int doctorid, officeId;;
	private String startdate, enddate;
	private int urgency, attended, checkedin, checkedout;

	public Appointment(int id, int patientid, int doctorid, String startdate, String enddate, int urgency, int attended,
			int checkedin, int checkedout, int officeId) {
		super();
		this.id = id;
		this.patientid = patientid;
		this.doctorid = doctorid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.urgency = urgency;
		this.attended = attended;
		this.checkedin = checkedin;
		this.checkedout = checkedout;
		this.officeId = officeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientid() {
		return patientid;
	}

	public void setPatientid(int patientid) {
		this.patientid = patientid;
	}

	public int getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public int getAttended() {
		return attended;
	}

	public void setAttended(int attended) {
		this.attended = attended;
	}

	public int getCheckedin() {
		return checkedin;
	}

	public void setCheckedin(int checkedin) {
		this.checkedin = checkedin;
	}

	public int getCheckedout() {
		return checkedout;
	}

	public void setCheckedout(int checkedout) {
		this.checkedout = checkedout;
	}

	
	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patientid=" + patientid + ", doctorid=" + doctorid + ", startdate="
				+ startdate + ", enddate=" + enddate + ", urgency=" + urgency + ", attended=" + attended
				+ ", checkedin=" + checkedin + ", checkedout=" + checkedout + "]";
	}

}
