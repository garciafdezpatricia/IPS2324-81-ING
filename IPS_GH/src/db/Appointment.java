package db;

import java.math.BigInteger;

import util.ConnectionFactory;

public class Appointment {
	private BigInteger id;
	private BigInteger patientid;
	private BigInteger doctorid, officeId;;
	private String startdate, enddate, information, checkedin, checkedout, status;
	private int urgency, attended;
	private String comments;



	public Appointment(BigInteger id, BigInteger patientid, BigInteger doctorid, String startdate, String enddate,
			int urgency, int attended, String checkedin, String checkedout, BigInteger officeId, String information,
			String status, String comments) {
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
		this.status = status;
		this.comments = comments;
	}
	
	public Appointment(BigInteger id, BigInteger patientid, BigInteger doctorid, String startdate, String enddate,
			int urgency, int attended, String checkedin, String checkedout, BigInteger officeId, String information,
			String status) {
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
		this.status = status;
		this.comments = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getPatientid() {
		return patientid;
	}

	public void setPatientid(BigInteger patientid) {
		this.patientid = patientid;
	}

	public BigInteger getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(BigInteger doctorid) {
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

	public BigInteger getOfficeId() {
		return officeId;
	}

	public void setOfficeId(BigInteger officeId) {
		this.officeId = officeId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	

	@Override
	public String toString() {
		String p = "";
		String doctor = "";
		try {
			p = ConnectionFactory.getPatient(patientid);
			doctor = ConnectionFactory.getDoctor(doctorid);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (urgency == 1) {
			if (getStatus() != null) {
				if (getStatus().toLowerCase().equals("cancelled")) {
					try {
						return " (cancelled) [URGENT] \n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: "
								+ startdate + "; \n\tTo: " + enddate;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				} else {
					try {
						return "[URGENT] \n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: " + startdate
								+ "; \n\tTo: " + enddate;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}
			} else
				return "[URGENT] \n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: " + startdate + "; \n\tTo: "
						+ enddate;

		}
		if (urgency == 0) {
			if (getStatus() != null) {
				if (getStatus().toLowerCase().equals("cancelled")) {
					try {
						return " (cancelled) \n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: " + startdate
								+ "; \n\tTo: " + enddate;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				} else {
					try {
						return "\n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: " + startdate + "; \n\tTo: "
								+ enddate;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}
			} else
				return "\n\tPatient: " + p + "; \n\tDc.: " + doctor + "; \n\tFrom: " + startdate + "; \n\tTo: "
						+ enddate;
		}

		return "";

	}

}
