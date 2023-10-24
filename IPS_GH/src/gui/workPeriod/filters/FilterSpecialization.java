package gui.workPeriod.filters;

import javax.swing.DefaultListModel;

import db.Doctor;

public class FilterSpecialization implements Filter {

	private DefaultListModel<Doctor> doctors;
	private String value;
	private DefaultListModel<Doctor> filtered;
	
	public FilterSpecialization(DefaultListModel<Doctor> doctors, String value) {
		this.filtered = new DefaultListModel<Doctor>();
		this.doctors = doctors;
		this.value = value;
	}
	@Override
	public DefaultListModel<Doctor> filter() {
		for (int i = 0; i < doctors.getSize(); i++) {
			if (value.equals(doctors.get(i).getSpecialization())) {
				filtered.addElement(doctors.get(i));
			}
		}
		return filtered;
	}

}
