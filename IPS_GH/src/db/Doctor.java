package db;

import java.math.BigInteger;

public class Doctor {
	private BigInteger id;
	private String numColegiado;
	private String name;
	private String surname;
	private String email;
	private String personal_id;
	private String specialization;

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Doctor(BigInteger id, String numColegiado, String name, String surname, String email, String personal_id,
			String specialization) {
		super();
		this.id = id;
		this.numColegiado = numColegiado;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.personal_id = personal_id;
		this.specialization = specialization;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonal_id() {
		return personal_id;
	}

	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}

	@Override
	public String toString() {
		return "Dr. " + getName() + " " + getSurname();
	}

}
