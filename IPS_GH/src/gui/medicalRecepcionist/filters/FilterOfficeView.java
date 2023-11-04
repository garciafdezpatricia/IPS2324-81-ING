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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.Appointment;
import db.Doctor;
import db.Office;
import db.Patient;
import gui.medicalRecepcionist.EditAndCancelView;
import gui.medicalRecepcionist.MedicalRecepcionistView;
import util.ConnectionFactory;

public class FilterOfficeView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelButtons;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel panelCenter;
	private JScrollPane scrollPaneDoctor;
	private JList listOffices;

	private DefaultListModel<Office> offices = new DefaultListModel<>();
	private DefaultListModel<Office> officesreset = new DefaultListModel<>();

	private EditAndCancelView prev = new EditAndCancelView();
	private static DefaultListModel<Appointment> appointments;
	private DefaultListModel<Appointment> a2 = new DefaultListModel<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FilterOfficeView dialog = new FilterOfficeView(appointments);
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
	public FilterOfficeView(DefaultListModel<Appointment> appointments) throws Exception {
		this.appointments = appointments;
		a2 = this.appointments;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
		setTitle("Filter by office/s");
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
		offices = (DefaultListModel<Office>) ConnectionFactory.getOfficesDefaultList();
		officesreset = (DefaultListModel<Office>) ConnectionFactory.getOfficesDefaultList();

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
		Patient p = (Patient) getListOffices().getSelectedValue();
//		prev.setPatient(p);
//		prev.updateFilters(this);
		if (getListOffices().getSelectedValuesList().size() == 1) {
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
					DefaultListModel<Appointment> appointments2 = new DefaultListModel<>();

					Office p = (Office) getListOffices().getSelectedValue();
					appointments.clear();

					try {
						DefaultListModel<Appointment> a = ConnectionFactory.getAppointmentsByOffice(p.getId());
						for (int i = 0; i < a.size(); i++) {
							appointments.addElement(a.get(i));
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					dispose();
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
			panelCenter.setLayout(new BorderLayout(0, 0));
			panelCenter.add(getScrollPaneDoctor());
		}
		return panelCenter;
	}

	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane();
			scrollPaneDoctor.setViewportView(getListOffices());
		}
		return scrollPaneDoctor;
	}

	private JList getListOffices() {
		if (listOffices == null) {
			listOffices = new JList<>(offices); // Asegúrate de especificar el tipo de elemento en la JList
			listOffices.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					btnSave.setEnabled(true);
				}
			});
			listOffices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listOffices;
	}
}
