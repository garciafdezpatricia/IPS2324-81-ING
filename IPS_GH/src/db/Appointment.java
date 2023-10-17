package db;

public class Appointment {
	private int id;
	private int patientid;
	private int doctorid, officeId;;
	private String startdate, enddate, information, checkedin, checkedout;
	private int urgency, attended;

	public Appointment(int id, int patientid, int doctorid, String startdate, String enddate, int urgency, int attended,
			String checkedin, String checkedout, int officeId, String information) {
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
		this.information = information;
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

	public String getCheckedin() {
		return checkedin;
	}

	public void setCheckedin(String checkedin) {
		this.checkedin = checkedin;
	}

	public String getCheckedout() {
		return checkedout;
	}

	public void setCheckedout(String checkedout) {
		this.checkedout = checkedout;
	}

	
	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patientid=" + patientid + ", doctorid=" + doctorid + ", startdate="
				+ startdate + ", enddate=" + enddate + ", urgency=" + urgency + ", attended=" + attended
				+ ", checkedin=" + checkedin + ", checkedout=" + checkedout + "]";
	}

}
