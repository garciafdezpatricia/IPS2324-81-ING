package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.Doctor;
import gui.workPeriod.filters.Filter;
import gui.workPeriod.filters.FilterMedicalLicenseID;
import gui.workPeriod.filters.FilterName;
import gui.workPeriod.filters.FilterPersonalID;
import gui.workPeriod.filters.FilterSpecialization;
import gui.workPeriod.filters.FilterSurname;
import util.ConnectionFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class IdentificationWindowView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblChooseDoctor;
	private JList<Doctor> listDoctors;

	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();

	private Doctor selectedDoctor;
	private JButton btnDone;
	private JButton btnCancel;
	private JTextField txtValue;
	private JComboBox<String> comboBoxFilter;
	private JLabel lblSelectTheFilter;
	private JLabel lblIntroduceValue;
	private JButton btnReset;
	private JButton btnApply;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IdentificationWindowView dialog = new IdentificationWindowView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IdentificationWindowView() {
		selectedDoctor = null;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(IdentificationWindowView.class.getResource("/img/descarga.jpg")));
		setTitle("Doctor selection");
		try {
			doctors = ConnectionFactory.getDoctors();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setBounds(100, 100, 765, 467);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblChooseDoctor());
		contentPanel.add(getListDoctors());
		contentPanel.add(getBtnDone());
		contentPanel.add(getBtnCancel());
		contentPanel.add(getComboBoxFilter());
		contentPanel.add(getLblSelectTheFilter());
		contentPanel.add(getbLblIntroduceValue());
		contentPanel.add(getTxtValue());
		contentPanel.add(getBtnReset());
		contentPanel.add(getBtnApply());
	}

	private JLabel getLblChooseDoctor() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Select the doctor to assign a work period");
			lblChooseDoctor.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblChooseDoctor.setBounds(211, 20, 302, 26);
		}
		return lblChooseDoctor;
	}

	private JList<Doctor> getListDoctors() {
		if (listDoctors == null) {
			listDoctors = new JList<Doctor>(doctors);
			listDoctors.setBounds(351, 59, 371, 276);

			listDoctors.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Doctor d = (Doctor) getListDoctors().getSelectedValue();

					if (d != null) {
						selectedDoctor = d;
					}
				}

			});
		}
		return listDoctors;
	}

	public boolean isDoctorSelected() {
		if (selectedDoctor == null)
			return false;
		return true;

	}

	public Doctor getSelectedDoctor() {
		if (isDoctorSelected())
			return selectedDoctor;
		return null;
	}

	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton("Done");
			btnDone.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnDone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selectedDoctor != null)
						dispose();
				}
			});
			btnDone.setBounds(633, 374, 89, 23);
		}
		return btnDone;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBounds(514, 374, 89, 23);
		}
		return btnCancel;
	}

	private JComboBox<String> getComboBoxFilter() {
		if (comboBoxFilter == null) {
			comboBoxFilter = new JComboBox<String>();
			comboBoxFilter.setBounds(25, 96, 296, 26);

			comboBoxFilter.setModel(new DefaultComboBoxModel<>(getFilters()));

		}
		return comboBoxFilter;
	}

	private String[] getFilters() {
		String[] f = { "Filtering by name", "Filtering by surname", "Filtering by personal ID",
				"Filtering by medical license ID", "Filtering by specialization" };

		return f;
	}

	private JLabel getLblSelectTheFilter() {
		if (lblSelectTheFilter == null) {
			lblSelectTheFilter = new JLabel("Select the type of filtering:");
			lblSelectTheFilter.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblSelectTheFilter.setBounds(25, 60, 179, 26);

		}

		return lblSelectTheFilter;
	}

	private JLabel getbLblIntroduceValue() {
		if (lblIntroduceValue == null) {
			lblIntroduceValue = new JLabel("Introduce the value used to filter:");
			lblIntroduceValue.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblIntroduceValue.setBounds(25, 140, 302, 26);
		}
		return lblIntroduceValue;
	}

	private JTextField getTxtValue() {
		if (txtValue == null) {
			txtValue = new JTextField();
			txtValue.setBounds(25, 176, 296, 31);
			contentPanel.add(txtValue);
			txtValue.setColumns(10);
		}
		return txtValue;
	}

	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset");
			btnReset.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnReset.setBounds(140, 228, 89, 23);
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getListDoctors().setModel(doctors);
				}
			});
		}
		return btnReset;
	}

	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply");
			btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Filter f = applyFilter((String) getComboBoxFilter().getSelectedItem(), getTxtValue().getText());
					getListDoctors().setModel(f.filter());
				}
			});
			btnApply.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnApply.setBounds(25, 228, 89, 23);
		}
		return btnApply;
	}

	/**
	 * String[] f = { "Filtering by name", "Filtering by surname", "Filtering by
	 * personal ID", "Filtering by medical license ID", "Filtering by
	 * specialization" };
	 * 
	 * @param filter
	 * @param value
	 * @return
	 */

	private Filter applyFilter(String filter, String value) {
		if (filter.equals("Filtering by name"))
			return new FilterName(doctors, value);
		else if (filter.equals("Filtering by surname"))
			return new FilterSurname(doctors, value);
		else if (filter.equals("Filtering by personal ID"))
			return new FilterPersonalID(doctors, value);
		else if (filter.equals("Filtering by personal ID"))
			return new FilterPersonalID(doctors, value);
		else if (filter.equals("Filtering by medical license ID"))
			return new FilterMedicalLicenseID(doctors, value);
		else if (filter.equals("Filtering by specialization"))
			return new FilterSpecialization(doctors, value);

		return null;
	}
}
