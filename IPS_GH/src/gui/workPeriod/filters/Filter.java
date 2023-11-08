package gui.workPeriod.filters;

import javax.swing.DefaultListModel;

import db.Doctor;

public interface Filter {
	
	/**
	 *  filters a list of objects based on a certain criteria that depends on the type of filter
	 */
	DefaultListModel<Doctor> filter();
	
	
	

}
