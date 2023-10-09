package db;

public class Doctor {
	private int id;
	private String numColegiado;
	private String name;
	private String surname;
	private String email;

	public Doctor(int id, String numColegiado, String name, String surname, String email) {
		super();
		this.id = id;
		this.numColegiado = numColegiado;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "Dr. " + getName() + " " + getSurname();
	}
	
}
