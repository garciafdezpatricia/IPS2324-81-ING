package gui.medicalRecepcionist.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.Appointment;
import db.Patient;
import gui.medicalRecepcionist.EditAndCancelView;
import gui.medicalRecepcionist.MedicalRecepcionistView;
import util.ConnectionFactory;

public class FilterPatientView extends JDialog {

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
	private JScrollPane scrollPanePatients;
	private JList listPatients;

	private DefaultListModel<Patient> patients = new DefaultListModel<>();
	private DefaultListModel<Patient> patientsReset = new DefaultListModel<>();

	private JLabel lblSocialSecurityNumber;
	private JButton btnSS;
	private JTextField textFieldSS;

	private EditAndCancelView prev = new EditAndCancelView();
	private static DefaultListModel<Appointment> appointments;
	private DefaultListModel<Appointment> a2 = new DefaultListModel<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FilterPatientView dialog = new FilterPatientView(appointments);
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
	public FilterPatientView(DefaultListModel<Appointment> appointments) throws Exception {
		this.appointments = appointments;
		a2 = this.appointments;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
		setTitle("Filter by patient/s");
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
		patients = ConnectionFactory.getPatients();
		patientsReset = ConnectionFactory.getPatients();

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


	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> appointments2 = new DefaultListModel<>();

					Patient p = (Patient) getListPatients().getSelectedValue();
					appointments.clear();

					try {
						DefaultListModel<Appointment> a = ConnectionFactory.getAppointmentsByPatientID(p.getId());
						for (int i = 0; i < a.size(); i++) {
							appointments.addElement(a.get(i));

						}
					} catch (Exception e1) {
						e1.printStackTrace();
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
			panelCenter.add(getScrollPanePatients());
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
					patients.removeAllElements();
					for (int i = 0; i < patientsReset.size(); i++) {
						patients.addElement(patientsReset.get(i));
					}
					textFieldName.setText("");
					textFieldSS.setText("");
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
			panelFilters.add(getTextFieldSS());
			panelFilters.add(getBtnSS());
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
			DefaultListModel<Patient> filteredByName = new DefaultListModel<>();
			btnName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldName().getText().isBlank() && !getTextFieldName().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (patients.get(i).getFirstName().toLowerCase()
									.contains(getTextFieldName().getText().toLowerCase())) {
								filteredByName.addElement(patients.get(i));
							}
						}
					}

					patients.removeAllElements();
					for (int i = 0; i < filteredByName.size(); i++) {
						if (!patients.contains(filteredByName.get(i))) {
							patients.addElement(filteredByName.get(i));
						}
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
					DefaultListModel<Patient> filteredBySurname = new DefaultListModel<>();
					if (!getTextFieldSurname().getText().isBlank() && !getTextFieldSurname().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if ((patients.get(i).getSurName().contains(getTextFieldSurname().getText()))) {
								filteredBySurname.addElement(patients.get(i));
							}
						}
					}

					patients.removeAllElements();
					for (int i = 0; i < filteredBySurname.size(); i++) {
						if (!patients.contains(filteredBySurname.get(i))) {
							patients.addElement(filteredBySurname.get(i));
						}
					}
				}
			});
		}
		return btnSurname;
	}

	private JScrollPane getScrollPanePatients() {
		if (scrollPanePatients == null) {
			scrollPanePatients = new JScrollPane();
			scrollPanePatients.setViewportView(getListPatients());
		}
		return scrollPanePatients;
	}

	private JList getListPatients() {
		if (listPatients == null) {
			listPatients = new JList<>(patients); // Asegúrate de especificar el tipo de elemento en la JList
			listPatients.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					btnSave.setEnabled(true);
				}
			});
			listPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listPatients;
	}

	private JLabel getLblSocialSecurityNumber() {
		if (lblSocialSecurityNumber == null) {
			lblSocialSecurityNumber = new JLabel("Social security number");
		}
		return lblSocialSecurityNumber;
	}

	private JButton getBtnSS() {
		if (btnSS == null) {
			btnSS = new JButton("Filter");
			DefaultListModel<Patient> filteredBySSNumber = new DefaultListModel<>();
			btnSS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldSS().getText().isBlank() && !getTextFieldSS().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (Integer.valueOf(getTextFieldSS().getText()) == patients.get(i).getSsnumber()) {
								filteredBySSNumber.addElement(patients.get(i));
							}
						}
					}

					patients.removeAllElements();
					for (int i = 0; i < filteredBySSNumber.size(); i++) {
						if (!patients.contains(filteredBySSNumber.get(i))) {
							patients.addElement(filteredBySSNumber.get(i));
						}
					}
				}
			});
		}
		return btnSS;
	}

	private JTextField getTextFieldSS() {
		if (textFieldSS == null) {
			textFieldSS = new JTextField();
			textFieldSS.setColumns(10);
		}
		return textFieldSS;
	}
}
