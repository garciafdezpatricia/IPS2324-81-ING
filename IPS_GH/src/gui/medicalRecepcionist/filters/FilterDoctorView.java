package gui.medicalRecepcionist.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.Appointment;
import db.Doctor;
import db.Patient;
import gui.medicalRecepcionist.EditAndCancelView;
import gui.medicalRecepcionist.MedicalRecepcionistView;
import util.ConnectionFactory;

public class FilterDoctorView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelButtons;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel panelCenter;
	private JPanel panelCenterSur;
	private JButton btnReset;
	private JPanel panelFilters;
	private JLabel lblName;
	private JTextField textFieldName;
	private JButton btnName;
	private JLabel lblSurname;
	private JTextField textFieldSurname;
	private JButton btnSurname;
	private JScrollPane scrollPaneDoctor;
	private JList listDoctor;

	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();
	private DefaultListModel<Doctor> doctorsReset = new DefaultListModel<>();

	private JLabel lblSocialSecurityNumber;
	private JButton btnReg;
	private JTextField textFieldReg;

	private EditAndCancelView prev = new EditAndCancelView();
	private static DefaultListModel<Appointment> appointments;
	private DefaultListModel<Appointment> a2 = new DefaultListModel<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FilterDoctorView dialog = new FilterDoctorView(appointments);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public FilterDoctorView(DefaultListModel<Appointment> appointments) throws Exception {
		this.appointments = appointments;
		a2 = this.appointments;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
		setTitle("Filter by doctor/s");

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		doctors = ConnectionFactory.getDoctors();
		doctorsReset = ConnectionFactory.getDoctors();

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelButtons(), BorderLayout.SOUTH);
		getContentPane().add(getPanelCenter(), BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtons.add(getBtnCancel());
			panelButtons.add(getBtnSave());
		}
		return panelButtons;
	}

	private void doSave(DefaultListModel<Appointment> a) {
		DefaultListModel<Appointment> appointments2 = new DefaultListModel<>();
//
		Patient p = (Patient) getListDoctor().getSelectedValue();
//		prev.setPatient(p);
//		prev.updateFilters(this);
		if (getListDoctor().getSelectedValuesList().size() == 1) {
			for (int i = 0; i < a.getSize(); i++) {
				if (a.get(i).getPatientid() == (p.getId())) {

					appointments2.addElement(a.get(i));
				}
			}
		}
//		prev.updateModelPatients(appointments);
//		dispose();
//		validate();
//		prev.validate();
		System.out.println(appointments2);
		appointments = appointments2;
		System.out.println("FURRULÓ");
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (getListDoctor().getSelectedValuesList().size() == 1) {

						Doctor p = (Doctor) getListDoctor().getSelectedValue();
						appointments.clear();

						try {
							DefaultListModel<Appointment> a = ConnectionFactory.getAppointmentsByDoctorId(p.getId());
							for (int i = 0; i < a.size(); i++) {
								appointments.addElement(a.get(i));
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						for (int i = 0; i < getListDoctor().getSelectedValuesList().size(); i++) {
							Doctor d = (Doctor) getListDoctor().getSelectedValuesList().get(i);
							DefaultListModel<Appointment> a = ConnectionFactory.getAppointmentsByDoctorId(d.getId());

							for (int j = 0; j < a.size(); j++) {
								appointments.addElement(a.get(j));
							}
						}
					}
					setVisible(false);

				}
			});
			btnSave.setEnabled(false);
			btnSave.setBackground(new Color(128, 255, 0));
		}
		return btnSave;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setBackground(new Color(255, 0, 0));
		}
		return btnCancel;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getPanelCenterSur());
			panelCenter.add(getScrollPaneDoctor());
		}
		return panelCenter;
	}

	private JPanel getPanelCenterSur() {
		if (panelCenterSur == null) {
			panelCenterSur = new JPanel();
			panelCenterSur.setLayout(new BorderLayout(0, 0));
			panelCenterSur.add(getBtnReset(), BorderLayout.SOUTH);
			panelCenterSur.add(getPanelFilters(), BorderLayout.CENTER);
		}
		return panelCenterSur;
	}

	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset Filters");
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doctors.removeAllElements();
					for (int i = 0; i < doctorsReset.size(); i++) {
						doctors.addElement(doctorsReset.get(i));
					}
					textFieldName.setText("");
					textFieldReg.setText("");
					textFieldSurname.setText("");
					btnSave.setEnabled(false);
				}
			});

		}
		return btnReset;
	}

	private JPanel getPanelFilters() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters
					.setBorder(new TitledBorder(null, "Filter by", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelFilters.setLayout(new GridLayout(0, 3, 0, 0));
			panelFilters.add(getLblName());
			panelFilters.add(getTextFieldName());
			panelFilters.add(getBtnName());
			panelFilters.add(getLblSurname());
			panelFilters.add(getTextFieldSurname());
			panelFilters.add(getBtnSurname());
			panelFilters.add(getLblSocialSecurityNumber());
			panelFilters.add(getTextFieldReg());
			panelFilters.add(getBtnReg());
		}
		return panelFilters;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name");
		}
		return lblName;
	}

	private JTextField getTextFieldName() {
		if (textFieldName == null) {
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
		}
		return textFieldName;
	}

	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton("Filter");
			DefaultListModel<Doctor> filteredByName = new DefaultListModel<>();
			btnName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldName().getText().isBlank() && !getTextFieldName().getText().isEmpty()) {
						for (int i = 0; i < doctors.getSize(); i++) {
							if (getTextFieldName().getText().toLowerCase()
									.equals(doctors.get(i).getName().toLowerCase())) {
								filteredByName.addElement(doctors.get(i));
							}
						}
					}

					List<Doctor> selected = listDoctor.getSelectedValuesList();

					doctors.removeAllElements();
					listDoctor.getSelectedValue();
					for (int i = 0; i < filteredByName.size(); i++) {
						if (!doctors.contains(filteredByName.get(i))) {
							doctors.addElement(filteredByName.get(i));
						}
					}
					for (int i = 0; i < selected.size(); i++) {
						doctors.addElement(selected.get(i));
					}
				}
			});
		}
		return btnName;

	}

	private JLabel getLblSurname() {
		if (lblSurname == null) {
			lblSurname = new JLabel("Surname");
		}
		return lblSurname;
	}

	private JTextField getTextFieldSurname() {
		if (textFieldSurname == null) {
			textFieldSurname = new JTextField();
			textFieldSurname.setColumns(10);
		}
		return textFieldSurname;
	}

	private JButton getBtnSurname() {
		if (btnSurname == null) {
			btnSurname = new JButton("Filter");
			btnSurname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Doctor> filteredBySurname = new DefaultListModel<>();
					if (!getTextFieldSurname().getText().isBlank() && !getTextFieldSurname().getText().isEmpty()) {
						for (int i = 0; i < doctors.getSize(); i++) {
							if ((doctors.get(i).getSurname().contains(getTextFieldSurname().getText()))) {
								filteredBySurname.addElement(doctors.get(i));
							}
						}
					}

					doctors.removeAllElements();
					for (int i = 0; i < filteredBySurname.size(); i++) {
						if (!doctors.contains(filteredBySurname.get(i))) {
							doctors.addElement(filteredBySurname.get(i));
						}
					}
				}
			});
		}
		return btnSurname;
	}

	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane();
			scrollPaneDoctor.setViewportView(getListDoctor());
		}
		return scrollPaneDoctor;
	}

	private JList getListDoctor() {
		if (listDoctor == null) {
			listDoctor = new JList<>(doctors); // Asegúrate de especificar el tipo de elemento en la JList
			listDoctor.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					btnSave.setEnabled(true);
				}
			});
		}
		return listDoctor;
	}

	private JLabel getLblSocialSecurityNumber() {
		if (lblSocialSecurityNumber == null) {
			lblSocialSecurityNumber = new JLabel("Registration number:");
		}
		return lblSocialSecurityNumber;
	}

	private JButton getBtnReg() {
		if (btnReg == null) {
			btnReg = new JButton("Filter");
			DefaultListModel<Doctor> filteredBySSNumber = new DefaultListModel<>();
			btnReg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldReg().getText().isBlank() && !getTextFieldReg().getText().isEmpty()) {
						for (int i = 0; i < doctors.getSize(); i++) {
							if (getTextFieldReg().getText().equals(doctors.get(i).getNumColegiado())) {
								filteredBySSNumber.addElement(doctors.get(i));
							}
						}
					}

					doctors.removeAllElements();
					for (int i = 0; i < filteredBySSNumber.size(); i++) {
						if (!doctors.contains(filteredBySSNumber.get(i))) {
							doctors.addElement(filteredBySSNumber.get(i));
						}
					}
				}
			});
		}
		return btnReg;
	}

	private JTextField getTextFieldReg() {
		if (textFieldReg == null) {
			textFieldReg = new JTextField();
			textFieldReg.setColumns(10);
		}
		return textFieldReg;
	}
}
