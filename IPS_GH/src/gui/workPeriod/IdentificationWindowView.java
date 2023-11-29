package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.Doctor;
import gui.workPeriod.filters.Filter;
import gui.workPeriod.filters.FilterMedicalLicenseID;
import gui.workPeriod.filters.FilterName;
import gui.workPeriod.filters.FilterPersonalID;
import gui.workPeriod.filters.FilterSpecialization;
import gui.workPeriod.filters.FilterSurname;
import util.ConnectionFactory;

public class IdentificationWindowView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();
	private DefaultListModel<Doctor> doctorsForReset = new DefaultListModel<>();
	private DefaultListModel<Doctor> selectedDoctors = new DefaultListModel<>();

	private Doctor selectedDoctor;
	private JPanel panelTitle;
	private JLabel lblChooseDoctor;
	private JPanel panelFilters;
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnFilterName;
	private JLabel lblSurname;
	private JTextField txtSurname;
	private JButton btnFilterSurname;
	private JLabel lblPersonalId;
	private JTextField txtPersonalID;
	private JButton btnFilterPersonalID;
	private JPanel panelButton;
	private JButton btnDone;
	private JButton btnCancel;
	private JPanel panelList;
	private JScrollPane scrollPaneAllDoctors;
	private JPanel pnButtons;
	private JButton btnPass;
	private JButton btnBack;
	private JLabel lblMedicalLicense;
	private JTextField txtMedicalLicense;
	private JButton btnFilterMedicalLicense;
	private JLabel lblListAllDoctors;
	private JTextField txtSpecialization;
	private JButton btnFilterSpecialization;
	private JList<Doctor> listDoctors;
	private JLabel lblSpecialization;
	private JScrollPane scrollPaneSelectedDoctors;
	private JList<Doctor> listSelectedDoctors;
	private JLabel lblListSelectedDoctors;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnResetFilters;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IdentificationWindowView dialog = new IdentificationWindowView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IdentificationWindowView() {
		setResizable(false);
		selectedDoctor = null;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(IdentificationWindowView.class.getResource("/img/descarga.jpg")));
		setTitle("Doctor selection");
		try {
			doctors = ConnectionFactory.getDoctors();
			doctorsForReset = ConnectionFactory.getDoctors();
		} catch (Exception e) {
			e.printStackTrace();
		}

		selectedDoctors = new DefaultListModel<Doctor>();

		setBounds(100, 100, 841, 534);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPanelTitle(), BorderLayout.NORTH);
		contentPanel.add(getPanelFilters(), BorderLayout.WEST);
		contentPanel.add(getPanelButton(), BorderLayout.SOUTH);
		contentPanel.add(getPanelList(), BorderLayout.CENTER);
	}

	public boolean isDoctorSelected() {
		if (getListSelectedDoctors().getModel().getSize() > 0)
			return true;
		return false;

	}

	public List<Doctor> getSelectedDoctor() {
		List<Doctor> aux = new ArrayList<Doctor>();
		if (isDoctorSelected())
			for (int i = 0; i < getListSelectedDoctors().getModel().getSize(); i++) {
				aux.add(getListSelectedDoctors().getModel().getElementAt(i));
			}
		return null;
	}

	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.add(getLblChooseDoctor_1());
		}
		return panelTitle;
	}

	private JLabel getLblChooseDoctor_1() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Select the doctor to assign a work period");
			lblChooseDoctor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblChooseDoctor;
	}

	private JPanel getPanelFilters() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters.setLayout(new GridLayout(0, 3, 0, 0));
			panelFilters.add(getLblTypeDoctor_1());
			panelFilters.add(getTxtName());
			panelFilters.add(getBtnFilterName());
			panelFilters.add(getLblSurnameDoctor());
			panelFilters.add(getTxtSurname());
			panelFilters.add(getBtnFilterSurname());
			panelFilters.add(getLblPersonalID());
			panelFilters.add(getTxtPersonalID());
			panelFilters.add(getBtnFilterPersonalID());
			panelFilters.add(getLblMedicalLicense());
			panelFilters.add(getTxtMedicalLicense());
			panelFilters.add(getBtnFilterMedicalLicense());
			panelFilters.add(getLblSpecialization());
			panelFilters.add(getTxtSpecialization());
			panelFilters.add(getBtnFilterSpecialization());
			panelFilters.add(getLblNewLabel());
			panelFilters.add(getLblNewLabel_1());
			panelFilters.add(getBtnResetFilters());
		}
		return panelFilters;
	}

	private JLabel getLblTypeDoctor_1() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblName;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setColumns(10);
		}
		return txtName;
	}

	private JButton getBtnFilterName() {
		if (btnFilterName == null) {
			btnFilterName = new JButton("Filter");
			btnFilterName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtName().getText().isEmpty()) {

						Filter f = new FilterName(doctors, getTxtName().getText());
						DefaultListModel<Doctor> filtered = f.filter();

						doctors.clear();

						for (int i = 0; i < filtered.size(); i++) {
							if (!doctors.contains(filtered.get(i))) {
								doctors.addElement(filtered.get(i));
							}
						}
						System.out.println("Filtered list: " + filtered.toString());
					}
				}
			});
		}
		return btnFilterName;
	}

	private JLabel getLblSurnameDoctor() {
		if (lblSurname == null) {
			lblSurname = new JLabel("Surname:");
			lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSurname;
	}

	private JTextField getTxtSurname() {
		if (txtSurname == null) {
			txtSurname = new JTextField();
			txtSurname.setColumns(10);
		}
		return txtSurname;
	}

	private JButton getBtnFilterSurname() {
		if (btnFilterSurname == null) {
			btnFilterSurname = new JButton("Filter");
			btnFilterSurname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtSurname().getText().isEmpty()) {

						Filter f = new FilterSurname(doctors, getTxtName().getText());
						DefaultListModel<Doctor> filtered = f.filter();

						doctors.clear();

						for (int i = 0; i < filtered.size(); i++) {
							if (!doctors.contains(filtered.get(i))) {
								doctors.addElement(filtered.get(i));
							}
						}
						System.out.println("Filtered list: " + filtered.toString());
					}
				}
			});
		}
		return btnFilterSurname;
	}

	private JLabel getLblPersonalID() {
		if (lblPersonalId == null) {
			lblPersonalId = new JLabel("Personal ID:");
			lblPersonalId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblPersonalId;
	}

	private JTextField getTxtPersonalID() {
		if (txtPersonalID == null) {
			txtPersonalID = new JTextField();
			txtPersonalID.setColumns(10);
		}
		return txtPersonalID;
	}

	private JButton getBtnFilterPersonalID() {
		if (btnFilterPersonalID == null) {
			btnFilterPersonalID = new JButton("Filter");
			btnFilterPersonalID.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtPersonalID().getText().isEmpty()) {

						Filter f = new FilterPersonalID(doctors, getTxtName().getText());
						DefaultListModel<Doctor> filtered = f.filter();

						doctors.clear();

						for (int i = 0; i < filtered.size(); i++) {
							if (!doctors.contains(filtered.get(i))) {
								doctors.addElement(filtered.get(i));
							}
						}
						System.out.println("Filtered list: " + filtered.toString());
					}
				}
			});
		}
		return btnFilterPersonalID;
	}

	private JPanel getPanelButton() {
		if (panelButton == null) {
			panelButton = new JPanel();
			panelButton.setLayout(new GridLayout(0, 2, 0, 0));
			panelButton.add(getBtnCancel());
			panelButton.add(getBtnDone());
		}
		return panelButton;
	}

	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton("Done");
			btnDone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getListSelectedDoctors().getModel().getSize() == 0)
						JOptionPane.showMessageDialog(IdentificationWindowView.this,
								"At least, one doctor should be selected.", "Warning",
								JOptionPane.INFORMATION_MESSAGE);
					else {
						
					}
				}
			});
			btnDone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnDone;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnCancel;
	}

	private JPanel getPanelList() {
		if (panelList == null) {
			panelList = new JPanel();
			panelList.setLayout(new GridLayout(0, 3, 0, 0));
			panelList.add(getScrollPaneAllDoctors());
			panelList.add(getPnButtons());
			panelList.add(getScrollPaneSelectedDoctors());
		}
		return panelList;
	}

	private JScrollPane getScrollPaneAllDoctors() {
		if (scrollPaneAllDoctors == null) {
			scrollPaneAllDoctors = new JScrollPane();
			scrollPaneAllDoctors.setViewportView(getListDoctors());
			scrollPaneAllDoctors.setColumnHeaderView(getLblListAllDoctors());
		}
		return scrollPaneAllDoctors;
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.setLayout(new GridLayout(0, 1, 0, 0));
			pnButtons.add(getBtnPass());
			pnButtons.add(getBtnBack());
		}
		return pnButtons;
	}

	private JButton getBtnPass() {
		if (btnPass == null) {
			btnPass = new JButton("->");
			btnPass.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Doctor> selected = getListDoctors().getSelectedValuesList();
					for (Doctor d : selected) {
						if (!selectedDoctors.contains(d))
							selectedDoctors.addElement(d);
					}
				}
			});
		}
		return btnPass;
	}

	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("<-");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Doctor> selected = getListDoctors().getSelectedValuesList();
					for (Doctor d : selected) {
						selectedDoctors.removeElement(d);
					}
				}
			});
		}
		return btnBack;
	}

	private JLabel getLblMedicalLicense() {
		if (lblMedicalLicense == null) {
			lblMedicalLicense = new JLabel("Medical license:");
			lblMedicalLicense.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblMedicalLicense;
	}

	private JTextField getTxtMedicalLicense() {
		if (txtMedicalLicense == null) {
			txtMedicalLicense = new JTextField();
			txtMedicalLicense.setColumns(10);
		}
		return txtMedicalLicense;
	}

	private JButton getBtnFilterMedicalLicense() {
		if (btnFilterMedicalLicense == null) {
			btnFilterMedicalLicense = new JButton("Filter");
			btnFilterMedicalLicense.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtMedicalLicense().getText().isEmpty()) {

						Filter f = new FilterMedicalLicenseID(doctors, getTxtName().getText());
						DefaultListModel<Doctor> filtered = f.filter();

						doctors.clear();

						for (int i = 0; i < filtered.size(); i++) {
							if (!doctors.contains(filtered.get(i))) {
								doctors.addElement(filtered.get(i));
							}
						}
						System.out.println("Filtered list: " + filtered.toString());
					}
				}
			});
		}
		return btnFilterMedicalLicense;
	}

	private JLabel getLblListAllDoctors() {
		if (lblListAllDoctors == null) {
			lblListAllDoctors = new JLabel("All doctors:");
			lblListAllDoctors.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblListAllDoctors;
	}

	private JTextField getTxtSpecialization() {
		if (txtSpecialization == null) {
			txtSpecialization = new JTextField();
			txtSpecialization.setColumns(10);
		}
		return txtSpecialization;
	}

	private JButton getBtnFilterSpecialization() {
		if (btnFilterSpecialization == null) {
			btnFilterSpecialization = new JButton("Filter");
			btnFilterSpecialization.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtSpecialization().getText().isEmpty()) {

						Filter f = new FilterSpecialization(doctors, getTxtName().getText());
						DefaultListModel<Doctor> filtered = f.filter();

						doctors.clear();

						for (int i = 0; i < filtered.size(); i++) {
							if (!doctors.contains(filtered.get(i))) {
								doctors.addElement(filtered.get(i));
							}
						}
						System.out.println("Filtered list: " + filtered.toString());
					}
				}
			});
		}
		return btnFilterSpecialization;
	}

	private JList<Doctor> getListDoctors() {
		if (listDoctors == null) {
			listDoctors = new JList<Doctor>(doctors);
		}
		return listDoctors;
	}

	private JLabel getLblSpecialization() {
		if (lblSpecialization == null) {
			lblSpecialization = new JLabel("Specialization:");
			lblSpecialization.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSpecialization;
	}

	private JScrollPane getScrollPaneSelectedDoctors() {
		if (scrollPaneSelectedDoctors == null) {
			scrollPaneSelectedDoctors = new JScrollPane();

			scrollPaneSelectedDoctors.setViewportView(getListSelectedDoctors());
			scrollPaneSelectedDoctors.setColumnHeaderView(getLblListSelectedDoctors());
		}
		return scrollPaneSelectedDoctors;
	}

	private JList<Doctor> getListSelectedDoctors() {
		if (listSelectedDoctors == null) {
			listSelectedDoctors = new JList<Doctor>(selectedDoctors);
		}
		return listSelectedDoctors;
	}

	private JLabel getLblListSelectedDoctors() {
		if (lblListSelectedDoctors == null) {
			lblListSelectedDoctors = new JLabel("Selected doctors:");
			lblListSelectedDoctors.setFont(new Font("Tahoma", Font.PLAIN, 12));

		}
		return lblListSelectedDoctors;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
		}
		return lblNewLabel;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("");
		}
		return lblNewLabel_1;
	}

	private JButton getBtnResetFilters() {
		if (btnResetFilters == null) {
			btnResetFilters = new JButton("Reset filters");
			btnResetFilters.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < doctorsForReset.size(); i++) {
						if (!doctors.contains(doctorsForReset.get(i))) {
							doctors.addElement(doctorsForReset.get(i));
						}
					}
				}
			});
		}
		return btnResetFilters;
	}
}
