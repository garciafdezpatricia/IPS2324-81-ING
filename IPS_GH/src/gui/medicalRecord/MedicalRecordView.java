package gui.medicalRecord;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
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

import db.Appointment;
import db.Diagnosis;
import db.MedicalRecord;
import gui.doctor.DoctorAppointmentView;
import gui.medicalRecepcionist.MenuMedicalRecepcionist;
import util.AppointmentBLDto;
import util.ConnectionFactory;
import util.MedicalRecordBLDto;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.CardLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;

public class MedicalRecordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnButtons;
	private JButton btnBack;
	private List<Appointment> appointments;
	
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
	private JLabel lblDiagnosisHistory;
	private JButton btnAddReport;
	private JButton btnCloseProcedure;
	private JButton btnOpenDiagnosis;
	private JLabel lblDetailHistory;
	private JTextArea txtAreaDetailsHistory;
	private JScrollPane scrollPane_2;
	private JPanel pnListAppointment;
	private JList listapptmnt;
	private JScrollPane scrollPane_3;
	private JLabel lblApptmnt;
	private JTabbedPane tabbedPane_1;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel_3;
	private JLabel lblPutDoctorAppointment;
	private JLabel lblNewLabel_4;
	private JLabel lblPutDateAppointment;
	private JLabel lblNewLabel_5;
	private JLabel lblPutUrgencyAppointment;
	private JLabel lblNewLabel_6;
	private JLabel lblPutAttendedAppointment;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblPutCheckin;
	private JLabel lblPutCheckout;
	private JLabel lblNewLabel_9;
	private JList listPutDiagnosis;
	private JScrollPane scrollPane_4;
	private JLabel lblNewLabel_10;
	private JList listCausesOfAppointment;
	private JScrollPane scrollPane_5;
	private JLabel lblNewLabel_11;
	private JList proceduresOfAppointmentList;
	private JScrollPane scrollPane_6;
	private String name;
	private BigInteger patientID;
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
		this.patientID = BigInteger.valueOf(patientId);
		name = ConnectionFactory.getPatientInformation(patientId);
		appointments = convertToList(ConnectionFactory.getAppointmentsByPatientID(BigInteger.valueOf(patientId)));
	
//		causes=MedicalRecord.getCauses(patientId);
//		prescription=MedicalRecord.getPrescription(patientId);
//		vaccines=MedicalRecord.getVaccines(patientId);
//		diagnosis=MedicalRecord.getDiagnosis(patientId);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Medical record");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 668, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnButtons(), BorderLayout.SOUTH);
		contentPane.add(getPnPatient(), BorderLayout.NORTH);
		contentPane.add(getTabbedPane_1(), BorderLayout.CENTER);
	}
	
	public List<Appointment> convertToList(DefaultListModel<Appointment> defaultListModel) {
        List<Appointment> appointmentList = new ArrayList<Appointment>();

        // Recorre los elementos en el DefaultListModel y los agrega a la lista
        for (int i = 0; i < defaultListModel.size(); i++) {
            appointmentList.add(defaultListModel.get(i));
        }

        return appointmentList;
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
			lblName = new JLabel("MEDICAL RECORD OF " + name.toUpperCase());
		}
		return lblName;
	}
	
	private JTabbedPane getTabbedPane_1() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
	                if (tabbedPane.getSelectedIndex() == 1) {
	                    // Carga las citas desde la base de datos
	                    List<Diagnosis> allDiagnosis = ConnectionFactory.getDiagnosisOfPatient(patientID);
	                    DefaultListModel<Diagnosis> model = new DefaultListModel<Diagnosis>();
	                    if (allDiagnosis.size()>0) {
	                    	// construct each diagnosis
	                    	for (Diagnosis item : allDiagnosis) {
	                    		model.addElement(item);
	                    	}
	                    }
	                    list.setModel(model);
	                }
				}
			});
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
			pnAppointmentList.add(getLblDiagnosisHistory(), BorderLayout.NORTH);
		}
		return pnAppointmentList;
	}
	private JList getList() {
		if (list == null) {
			list = new JList();
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Diagnosis selected = (Diagnosis) list.getSelectedValue();
					lblPutDoctor.setText(ConnectionFactory.getDoctor(selected.doctor));
					lblPutDate.setText(selected.date);
				}
			});
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
			pnContent.add(getBtnAddReport());
			pnContent.add(getBtnCloseProcedure());
			pnContent.add(getBtnOpenDiagnosis());
			pnContent.add(getLblDetailHistory());
			pnContent.add(getScrollPane_2());
		}
		return pnContent;
	}
	private JLabel getLblInitialDate() {
		if (lblInitialDate == null) {
			lblInitialDate = new JLabel("Initial date:");
			lblInitialDate.setBounds(10, 11, 62, 14);
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
			scrollPane_1.setBounds(10, 65, 359, 121);
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
			lblNewLabel_2.setBounds(203, 11, 46, 14);
		}
		return lblNewLabel_2;
	}
	private JLabel getLblPutDoctor() {
		if (lblPutDoctor == null) {
			lblPutDoctor = new JLabel("");
			lblPutDoctor.setBounds(250, 11, 119, 14);
		}
		return lblPutDoctor;
	}
	private JLabel getLblPutDate() {
		if (lblPutDate == null) {
			lblPutDate = new JLabel("");
			lblPutDate.setBounds(74, 11, 119, 14);
		}
		return lblPutDate;
	}
	private JPanel getPnHistory() {
		if (pnHistory == null) {
			pnHistory = new JPanel();
			pnHistory.setLayout(new BorderLayout(0, 0));
			pnHistory.add(getPnListAppointment(), BorderLayout.WEST);
			pnHistory.add(getTabbedPane_1_1(), BorderLayout.CENTER);
		}
		return pnHistory;
	}
	private JLabel getLblDiagnosisHistory() {
		if (lblDiagnosisHistory == null) {
			lblDiagnosisHistory = new JLabel("Diagnosis:");
		}
		return lblDiagnosisHistory;
	}
	private JButton getBtnAddReport() {
		if (btnAddReport == null) {
			btnAddReport = new JButton("Add report");
			btnAddReport.setBounds(28, 292, 89, 23);
		}
		return btnAddReport;
	}
	private JButton getBtnCloseProcedure() {
		if (btnCloseProcedure == null) {
			btnCloseProcedure = new JButton("Close");
			btnCloseProcedure.setBounds(256, 292, 95, 23);
		}
		return btnCloseProcedure;
	}
	private JButton getBtnOpenDiagnosis() {
		if (btnOpenDiagnosis == null) {
			btnOpenDiagnosis = new JButton("Open");
			btnOpenDiagnosis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnOpenDiagnosis.setBounds(145, 292, 83, 23);
		}
		return btnOpenDiagnosis;
	}
	private JLabel getLblDetailHistory() {
		if (lblDetailHistory == null) {
			lblDetailHistory = new JLabel("Details:");
			lblDetailHistory.setBounds(10, 212, 46, 14);
		}
		return lblDetailHistory;
	}
	private JTextArea getTxtAreaDetailsHistory() {
		if (txtAreaDetailsHistory == null) {
			txtAreaDetailsHistory = new JTextArea();
		}
		return txtAreaDetailsHistory;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(10, 227, 359, 54);
			scrollPane_2.setViewportView(getTxtAreaDetailsHistory());
		}
		return scrollPane_2;
	}
	private JPanel getPnListAppointment() {
		if (pnListAppointment == null) {
			pnListAppointment = new JPanel();
			pnListAppointment.setLayout(new BorderLayout(0, 0));
			pnListAppointment.add(getScrollPane_3());
			pnListAppointment.add(getLblApptmnt(), BorderLayout.NORTH);
		}
		return pnListAppointment;
	}
	private JList getListapptmnt() {
		if (listapptmnt == null) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			for (Appointment item : appointments) {
				model.addElement(item.getStartdate());
			}
			listapptmnt = new JList(model);
			listapptmnt.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (listapptmnt.getSelectedValuesList().size() > 0) {
						tabbedPane_1.setVisible(true);
						// get the appointment
						Appointment apnmt = null;
						for (Appointment item : appointments) {
							if (item.getStartdate() == listapptmnt.getSelectedValue())
								apnmt = item;
						}
						// fill the values
						lblPutDoctorAppointment.setText(ConnectionFactory.getDoctor(apnmt.getDoctorid()));
						lblPutDateAppointment.setText(apnmt.getStartdate());
						lblPutUrgencyAppointment.setText(apnmt.getUrgency() == 1 ? "Urgent" : "Not urgent");
						lblPutAttendedAppointment.setText(apnmt.getAttended() == 1 ? "Attended" :
							apnmt.getAttended() == 2 ? "Not registered" : "Not attended");
						lblPutCheckin.setText(apnmt.getCheckedin());
						lblPutCheckout.setText(apnmt.getCheckedout());
						// fill the diagnosis
						DefaultListModel<String> diagn = new DefaultListModel<String>();
						String allDiagnosis = "";
						List<String> result = ConnectionFactory.getDiagnosisFromAppointment(apnmt.getId()); 
						if (result.size() > 0) {
							allDiagnosis = result.get(0);
							// if there are more than one diagnosis
							if (allDiagnosis.contains("||")) {
								String[] diagnosis = allDiagnosis.split("||");
								for (String item : diagnosis) {
									diagn.addElement(item.strip());
								}
							}
							// if there is only one diagnosis
							else
								diagn.addElement(allDiagnosis.strip());
						}
						listPutDiagnosis.setModel(diagn);
					}
					else
						tabbedPane_1.setVisible(false);
				}
			});
		}
		return listapptmnt;
	}
	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setViewportView(getListapptmnt());
		}
		return scrollPane_3;
	}
	private JLabel getLblApptmnt() {
		if (lblApptmnt == null) {
			lblApptmnt = new JLabel("Appointment:");
		}
		return lblApptmnt;
	}
	private JTabbedPane getTabbedPane_1_1() {
		if (tabbedPane_1 == null) {
			tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane_1.addTab("Information", null, getPanel(), null);
			tabbedPane_1.addTab("Causes", null, getPanel_1(), null);
			tabbedPane_1.addTab("Procedures", null, getPanel_3(), null);
			tabbedPane_1.addTab("Prescription", null, getPanel_2(), null);
			tabbedPane_1.setVisible(false);
		}
		return tabbedPane_1;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLblNewLabel_3());
			panel.add(getLblPutDoctorAppointment());
			panel.add(getLblNewLabel_4());
			panel.add(getLblPutDateAppointment());
			panel.add(getLblNewLabel_5());
			panel.add(getLblPutUrgencyAppointment());
			panel.add(getLblNewLabel_6());
			panel.add(getLblPutAttendedAppointment());
			panel.add(getLblNewLabel_7());
			panel.add(getLblNewLabel_8());
			panel.add(getLblPutCheckin());
			panel.add(getLblPutCheckout());
			panel.add(getLblNewLabel_9());
			panel.add(getScrollPane_4());
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getLblNewLabel_10());
			panel_1.add(getScrollPane_5());
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
		}
		return panel_2;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(null);
			panel_3.add(getLblNewLabel_11());
			panel_3.add(getScrollPane_6());
		}
		return panel_3;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Doctor:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_3.setBounds(10, 11, 59, 14);
		}
		return lblNewLabel_3;
	}
	private JLabel getLblPutDoctorAppointment() {
		if (lblPutDoctorAppointment == null) {
			lblPutDoctorAppointment = new JLabel("");
			lblPutDoctorAppointment.setBounds(86, 11, 201, 14);
		}
		return lblPutDoctorAppointment;
	}
	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Date:");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_4.setBounds(10, 36, 84, 14);
		}
		return lblNewLabel_4;
	}
	private JLabel getLblPutDateAppointment() {
		if (lblPutDateAppointment == null) {
			lblPutDateAppointment = new JLabel("");
			lblPutDateAppointment.setBounds(86, 36, 201, 14);
		}
		return lblPutDateAppointment;
	}
	private JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("Urgency:");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_5.setBounds(10, 61, 84, 14);
		}
		return lblNewLabel_5;
	}
	private JLabel getLblPutUrgencyAppointment() {
		if (lblPutUrgencyAppointment == null) {
			lblPutUrgencyAppointment = new JLabel("");
			lblPutUrgencyAppointment.setBounds(86, 61, 201, 14);
		}
		return lblPutUrgencyAppointment;
	}
	private JLabel getLblNewLabel_6() {
		if (lblNewLabel_6 == null) {
			lblNewLabel_6 = new JLabel("Attended:");
			lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_6.setBounds(10, 137, 84, 14);
		}
		return lblNewLabel_6;
	}
	private JLabel getLblPutAttendedAppointment() {
		if (lblPutAttendedAppointment == null) {
			lblPutAttendedAppointment = new JLabel("");
			lblPutAttendedAppointment.setBounds(86, 137, 185, 14);
		}
		return lblPutAttendedAppointment;
	}
	private JLabel getLblNewLabel_7() {
		if (lblNewLabel_7 == null) {
			lblNewLabel_7 = new JLabel("Check-in:");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_7.setBounds(10, 88, 84, 14);
		}
		return lblNewLabel_7;
	}
	private JLabel getLblNewLabel_8() {
		if (lblNewLabel_8 == null) {
			lblNewLabel_8 = new JLabel("Check-out:");
			lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_8.setBounds(10, 112, 84, 14);
		}
		return lblNewLabel_8;
	}
	private JLabel getLblPutCheckin() {
		if (lblPutCheckin == null) {
			lblPutCheckin = new JLabel("");
			lblPutCheckin.setBounds(86, 86, 201, 14);
		}
		return lblPutCheckin;
	}
	private JLabel getLblPutCheckout() {
		if (lblPutCheckout == null) {
			lblPutCheckout = new JLabel("");
			lblPutCheckout.setBounds(86, 112, 201, 14);
		}
		return lblPutCheckout;
	}
	private JLabel getLblNewLabel_9() {
		if (lblNewLabel_9 == null) {
			lblNewLabel_9 = new JLabel("Diagnosis:");
			lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_9.setBounds(10, 162, 84, 14);
		}
		return lblNewLabel_9;
	}
	private JList getListPutDiagnosis() {
		if (listPutDiagnosis == null) {
			listPutDiagnosis = new JList();
		}
		return listPutDiagnosis;
	}
	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setBounds(10, 187, 341, 89);
			scrollPane_4.setViewportView(getListPutDiagnosis());
		}
		return scrollPane_4;
	}
	private JLabel getLblNewLabel_10() {
		if (lblNewLabel_10 == null) {
			lblNewLabel_10 = new JLabel("Causes of the appointment:");
			lblNewLabel_10.setBounds(10, 11, 235, 14);
		}
		return lblNewLabel_10;
	}
	private JList getListCausesOfAppointment() {
		if (listCausesOfAppointment == null) {
			listCausesOfAppointment = new JList();
		}
		return listCausesOfAppointment;
	}
	private JScrollPane getScrollPane_5() {
		if (scrollPane_5 == null) {
			scrollPane_5 = new JScrollPane();
			scrollPane_5.setBounds(10, 25, 354, 262);
			scrollPane_5.setViewportView(getListCausesOfAppointment());
		}
		return scrollPane_5;
	}
	private JLabel getLblNewLabel_11() {
		if (lblNewLabel_11 == null) {
			lblNewLabel_11 = new JLabel("Procedures of the appointment:");
			lblNewLabel_11.setBounds(10, 11, 203, 14);
		}
		return lblNewLabel_11;
	}
	private JList getProceduresOfAppointmentList() {
		if (proceduresOfAppointmentList == null) {
			proceduresOfAppointmentList = new JList();
		}
		return proceduresOfAppointmentList;
	}
	private JScrollPane getScrollPane_6() {
		if (scrollPane_6 == null) {
			scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(10, 26, 354, 261);
			scrollPane_6.setViewportView(getProceduresOfAppointmentList());
		}
		return scrollPane_6;
	}
}
