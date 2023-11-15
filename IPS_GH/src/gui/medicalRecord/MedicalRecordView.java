package gui.medicalRecord;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import db.MedicalRecord;
import gui.doctor.DoctorAppointmentView;
import gui.medicalRecepcionist.MenuMedicalRecepcionist;
import util.AppointmentBLDto;
import util.ConnectionFactory;
import util.MedicalRecordBLDto;

public class MedicalRecordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JButton btnBack;
	private JTabbedPane tabbedPane;
	private JPanel pnAppointments;
	private JPanel pnPrescription;
	private JPanel pnDiagnosis;
	private JPanel pnVaccines;
	private List<AppointmentBLDto> appointments;
	private List<MedicalRecordBLDto> causes;
	private List<MedicalRecordBLDto> diagnosis;
	private List<MedicalRecordBLDto> vaccines;
	private List<MedicalRecordBLDto> prescription;
	private JPanel pnPatient;
	private JLabel lblName;
	private JPanel pnAppointmentInfo;
	private JLabel lblDate;
	private JLabel lblUrgency;
	private JLabel lblChekedIn;
	private JButton btnOpen;
	private JLabel lblDoctor_1;
	private DoctorAppointmentView doctorView;
	private JList lPrescriptions;
	private JList lDiagnosis;
	private JPanel pnCauses;
	private JList lCauses;
	private JList lVaccines;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicalRecordView frame = new MedicalRecordView(1);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MedicalRecordView(int patientId) {
		appointments = ConnectionFactory.getAppointmentsByPatientIDList(patientId);
	
		causes=MedicalRecord.getCauses(patientId);
		prescription=MedicalRecord.getPrescription(patientId);
		vaccines=MedicalRecord.getVaccines(patientId);
		diagnosis=MedicalRecord.getDiagnosis(patientId);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Medical record");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		UIManager.getLookAndFeelDefaults().put("nimbusBase", new Color(51, 153, 255)); // Cambiar el color bases
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
//		contentPane.add(getPnSearch(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		contentPane.add(getTabbedPane(), BorderLayout.CENTER);
		contentPane.add(getPnPatient(), BorderLayout.NORTH);
	}

//	private JPanel getPnSearch() {
//		if (pnSearch == null) {
//			pnSearch = new JPanel();
//			pnSearch.add(getLblNewLabel());
//			pnSearch.add(getTxtfSearch());
//			pnSearch.add(getBtnSearch());
//		}
//		return pnSearch;
//	}
//	private JTextField getTxtfSearch() {
//		if (txtfSearch == null) {
//			txtfSearch = new JTextField();
//			
//			txtfSearch.setColumns(20);
//		}
//		return txtfSearch;
//	}
//	private JButton getBtnSearch() {
//		if (btnSearch == null) {
//			btnSearch = new JButton("Search");
//			btnSearch.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					
//				}
//			});
//		}
//		return btnSearch;
//	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getBtnBack(), BorderLayout.WEST);
		}
		return panel;
	}

	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btnBack.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return btnBack;
	}

//	private JLabel getLblNewLabel() {
//		if (lblNewLabel == null) {
//			lblNewLabel = new JLabel("Select Patient ID");
//		}
//		return lblNewLabel;
//	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Appointments", null, getPnAppointments(), null);
			pnAppointments.setLayout(new GridLayout(0, 1, 0, 0));
			tabbedPane.addTab("Prescription", null, getPnPrescription(), null);
			tabbedPane.addTab("Causes", null, getPnCauses(), null);
			tabbedPane.addTab("Diagnosis", null, getPnDiagnosis(), null);
			tabbedPane.addTab("Vaccines", null, getPnVaccines(), null);
		}
		return tabbedPane;
	}

	private JPanel getPnAppointments() {

			pnAppointments = new JPanel();
			for(AppointmentBLDto appointment: appointments)
				pnAppointments.add(getPnAppointmentInfo(appointment));
			pnAppointments.invalidate();
			pnAppointments.validate();
			pnAppointments.repaint();
	
		return pnAppointments;
	}

	private JPanel getPnPrescription() {
		if (pnPrescription == null) {
			pnPrescription = new JPanel();
			pnPrescription.setLayout(new BorderLayout(0, 0));
			pnPrescription.add(getLPrescriptions());
		}
		return pnPrescription;
	}

	private JPanel getPnDiagnosis() {
		if (pnDiagnosis == null) {
			pnDiagnosis = new JPanel();
			pnDiagnosis.setLayout(new BorderLayout(0, 0));
			pnDiagnosis.add(getLDiagnosis());
		}
		return pnDiagnosis;
	}

	private JPanel getPnVaccines() {
		if (pnVaccines == null) {
			pnVaccines = new JPanel();
			pnVaccines.setLayout(new BorderLayout(0, 0));
			pnVaccines.add(getLVaccines());
		}
		return pnVaccines;
	}

	

	private JPanel getPnPatient() {
		if (pnPatient == null) {
			pnPatient = new JPanel();
			pnPatient.add(getLblName());
		}
		return pnPatient;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			if(appointments.size()!=0)
			lblName = new JLabel("Medical Record of "
					+ (appointments.get(0).patientName + " " + appointments.get(0).patientSurname).toUpperCase());
			else
				lblName = new JLabel("ERROR NO APPOINTMENTS");
		}
		return lblName;
	}
	private JPanel getPnAppointmentInfo(AppointmentBLDto ap) {
		
			pnAppointmentInfo = new JPanel();
			pnAppointmentInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			pnAppointmentInfo.add(getLblDoctor());
			pnAppointmentInfo.add(new JLabel(ap.doctorName+" "+ap.doctorSurname));
			pnAppointmentInfo.add(getLblDate());
			pnAppointmentInfo.add(new JLabel(ap.startDate));
			pnAppointmentInfo.add(getLblUrgency());
			if(ap.urgency)
				pnAppointmentInfo.add(new JLabel("yes"));
			else
				pnAppointmentInfo.add(new JLabel("no"));
			pnAppointmentInfo.add(getLblChekedIn());
			if(ap.checkIn!=null&&ap.checkIn.equals("1"))
				pnAppointmentInfo.add(new JLabel("yes"));
			else
				pnAppointmentInfo.add(new JLabel("no"));
			pnAppointmentInfo.add(getBtnOpen(ap));
		
		return pnAppointmentInfo;
	}
	private JLabel getLblDate() {
		
			lblDate = new JLabel("Date:");
		
		return lblDate;
	}
	private JLabel getLblUrgency() {
		
			lblUrgency = new JLabel("Urgency:");
		
		return lblUrgency;
	}
	private JLabel getLblChekedIn() {
		
			lblChekedIn = new JLabel("Checked in:");
		
		return lblChekedIn;
	}
	private JButton getBtnOpen(AppointmentBLDto ap) {
		
			btnOpen = new JButton("Open");
			btnOpen.addActionListener(new ActionListener() {
				

				public void actionPerformed(ActionEvent e) {
					doctorView = new DoctorAppointmentView(ap);
					doctorView.setVisible(true);
				}
			});
		
		return btnOpen;
	}
	private JLabel getLblDoctor() {
		
			lblDoctor_1 = new JLabel("Doctor:");
		
		return lblDoctor_1;
	}
	private JList getLPrescriptions() {
		if (lPrescriptions == null) {
			lPrescriptions = new JList();
			var model = new DefaultListModel<MedicalRecordBLDto>();
			model.addAll(prescription);
			lPrescriptions.setModel(model);
		}
		return lPrescriptions;
	}
	private JList getLDiagnosis() {
		if (lDiagnosis == null) {
			lDiagnosis = new JList();
			var model = new DefaultListModel<MedicalRecordBLDto>();
			model.addAll(diagnosis);
			lDiagnosis.setModel(model);
		}
		return lDiagnosis;
	}
	private JPanel getPnCauses() {
		if (pnCauses == null) {
			pnCauses = new JPanel();
			pnCauses.setLayout(new BorderLayout(0, 0));
			pnCauses.add(getLCauses(), BorderLayout.NORTH);
		}
		return pnCauses;
	}
	private JList getLCauses() {
		if (lCauses == null) {
			lCauses = new JList();
			var model = new DefaultListModel<MedicalRecordBLDto>();
			model.addAll(causes);
			lCauses.setModel(model);
		}
		return lCauses;
	}
	private JList getLVaccines() {
		if (lVaccines == null) {
			lVaccines = new JList();
			var model = new DefaultListModel<MedicalRecordBLDto>();
			model.addAll(vaccines);
			lVaccines.setModel(model);
		}
		return lVaccines;
	}
}
