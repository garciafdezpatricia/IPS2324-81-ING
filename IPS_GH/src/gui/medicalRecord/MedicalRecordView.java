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
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

public class MedicalRecordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnButtons;
	private JButton btnBack;
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
	private JTabbedPane tabbedPane;
	private JPanel pnDiagnosis;
	private JPanel pnAppointmentList;
	private JList list;
	private JScrollPane scrollPane;
	private JPanel pnContent;
	private JLabel lblInitialDate;
	private JList list_1;
	private JScrollPane scrollPane_1;
	private JLabel lblHistory;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblPutDoctor;
	private JLabel lblPutDate;
	private JPanel pnHistory;

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
		setBounds(100, 100, 668, 414);
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
		contentPane.add(getPnButtons(), BorderLayout.SOUTH);
		contentPane.add(getPnPatient(), BorderLayout.NORTH);
		contentPane.add(getTabbedPane_1(), BorderLayout.CENTER);
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.setLayout(new BorderLayout(0, 0));
			pnButtons.add(getBtnBack(), BorderLayout.WEST);
		}
		return pnButtons;
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
	
	private JTabbedPane getTabbedPane_1() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Medical History", null, getPnHistory(), null);
			tabbedPane.addTab("Diagnosis", null, getPnDiagnosis(), null);
		}
		return tabbedPane;
	}
	private JPanel getPnDiagnosis() {
		if (pnDiagnosis == null) {
			pnDiagnosis = new JPanel();
			pnDiagnosis.setLayout(new BorderLayout(0, 0));
			pnDiagnosis.add(getPnAppointmentList(), BorderLayout.WEST);
			pnDiagnosis.add(getPnContent(), BorderLayout.CENTER);
		}
		return pnDiagnosis;
	}
	private JPanel getPnAppointmentList() {
		if (pnAppointmentList == null) {
			pnAppointmentList = new JPanel();
			pnAppointmentList.setLayout(new BorderLayout(0, 0));
			pnAppointmentList.add(getScrollPane());
		}
		return pnAppointmentList;
	}
	private JList getList() {
		if (list == null) {
			list = new JList();
		}
		return list;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new JPanel();
			pnContent.setLayout(null);
			pnContent.add(getLblInitialDate());
			pnContent.add(getScrollPane_1());
			pnContent.add(getLblHistory());
			pnContent.add(getLblNewLabel_2());
			pnContent.add(getLblPutDoctor());
			pnContent.add(getLblPutDate());
		}
		return pnContent;
	}
	private JLabel getLblInitialDate() {
		if (lblInitialDate == null) {
			lblInitialDate = new JLabel("Initial date:");
			lblInitialDate.setBounds(10, 11, 74, 14);
		}
		return lblInitialDate;
	}
	private JList getList_1() {
		if (list_1 == null) {
			list_1 = new JList();
		}
		return list_1;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(10, 65, 217, 179);
			scrollPane_1.setViewportView(getList_1());
		}
		return scrollPane_1;
	}
	private JLabel getLblHistory() {
		if (lblHistory == null) {
			lblHistory = new JLabel("History:");
			lblHistory.setBounds(10, 44, 74, 14);
		}
		return lblHistory;
	}
	private JLabel getLblDoctor() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setBounds(237, 67, 46, 14);
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Doctor:");
			lblNewLabel_2.setBounds(237, 67, 46, 14);
		}
		return lblNewLabel_2;
	}
	private JLabel getLblPutDoctor() {
		if (lblPutDoctor == null) {
			lblPutDoctor = new JLabel("");
			lblPutDoctor.setBounds(237, 84, 132, 14);
		}
		return lblPutDoctor;
	}
	private JLabel getLblPutDate() {
		if (lblPutDate == null) {
			lblPutDate = new JLabel("");
			lblPutDate.setBounds(94, 11, 133, 14);
		}
		return lblPutDate;
	}
	private JPanel getPnHistory() {
		if (pnHistory == null) {
			pnHistory = new JPanel();
		}
		return pnHistory;
	}
}
