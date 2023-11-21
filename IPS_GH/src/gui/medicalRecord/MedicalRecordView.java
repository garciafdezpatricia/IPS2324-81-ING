package gui.medicalRecord;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import db.DiagnosisReport;
import db.MedicalRecord;
import gui.doctor.DoctorAppointmentView;
import gui.medicalRecepcionist.MenuMedicalRecepcionist;
import oracle.sql.DATE;
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
import javax.swing.ListSelectionModel;
import java.awt.Dimension;

public class MedicalRecordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnButtons;
	private JButton btnBack;
	private List<Appointment> appointments;
	private Diagnosis selected;
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
	private JList listHistorial;
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
	private JTextArea txtAreaReport;
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
	private JButton btnNewButton;
	private JList putPrescriptionList;
	private JScrollPane scrollPane_7;
	private JLabel lblNewLabel_12;
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
	
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Medical record");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 813, 456);
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
	                    cargarDiagnosticosPaciente();
	                }
				}
			});
			tabbedPane.addTab("Medical History", null, getPnHistory(), null);
			tabbedPane.addTab("Diagnosis", null, getPnDiagnosis(), null);
		}
		return tabbedPane;
	}
	
	private void cargarDiagnosticosPaciente() {
		int selIndex = list.getSelectedIndex();
		// Carga los diagnosticos desde la base de datos
        List<Diagnosis> allDiagnosis = ConnectionFactory.getDiagnosisOfPatient(patientID);
        DefaultListModel<Diagnosis> model = new DefaultListModel<Diagnosis>();
        if (allDiagnosis.size()>0) {
        	// construct each diagnosis
        	for (Diagnosis item : allDiagnosis) {
        		model.addElement(item);
        	}
        }
        list.setModel(model);
        list.setSelectedIndex(selIndex);
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
			pnAppointmentList.setMaximumSize(new Dimension(100, 100));
			pnAppointmentList.setLayout(new BorderLayout(0, 0));
			pnAppointmentList.add(getScrollPane());
			pnAppointmentList.add(getLblDiagnosisHistory(), BorderLayout.NORTH);
		}
		return pnAppointmentList;
	}
	private JList getList() {
		if (list == null) {
			list = new JList();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setMaximumSize(new Dimension(100, 100));
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (list.getSelectedValue() != null) {
						selected = (Diagnosis) list.getSelectedValue();
						lblPutDoctor.setText(ConnectionFactory.getDoctor(selected.doctor));
						lblPutDate.setText(selected.date);
						// rellenar historial
						cargarReports(selected);
					}
				}
			});
		}
		return list;
	}
	
	private void cargarReports(Diagnosis diagnosis) {
		DefaultListModel<DiagnosisReport> historial = new DefaultListModel<DiagnosisReport>();
		List<DiagnosisReport> reports = ConnectionFactory.getReportFromDiagnosis(diagnosis.id);
		for (DiagnosisReport item : reports)
			historial.addElement(item);
		listHistorial.setModel(historial);
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
			pnContent.add(getBtnNewButton());
		}
		return pnContent;
	}
	private JLabel getLblInitialDate() {
		if (lblInitialDate == null) {
			lblInitialDate = new JLabel("Initial date:");
			lblInitialDate.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblInitialDate.setBounds(10, 11, 74, 14);
		}
		return lblInitialDate;
	}
	private JList getListHistorial() {
		if (listHistorial == null) {
			listHistorial = new JList();
		}
		return listHistorial;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(10, 65, 504, 121);
			scrollPane_1.setViewportView(getListHistorial());
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
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
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
			lblPutDate.setBounds(83, 11, 119, 14);
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
			btnAddReport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String report = txtAreaReport.getText();
					// TODO: poner el id del doctor que este logged in
					BigInteger doctorId = new BigInteger("4");
					Diagnosis selected = (Diagnosis) list.getSelectedValue();
					String currentDate = LocalDate.now().toString(); 
					ConnectionFactory.addReportToDiagnosis(selected.id, doctorId, currentDate, report);
					cargarReports(selected);
				}
			});
			btnAddReport.setBounds(10, 292, 89, 23);
		}
		return btnAddReport;
	}
	private JButton getBtnCloseProcedure() {
		if (btnCloseProcedure == null) {
			btnCloseProcedure = new JButton("Close");
			btnCloseProcedure.setBounds(349, 292, 66, 23);
			btnCloseProcedure.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected.status != 2) {
						selected.status = 2;
						boolean result = ConnectionFactory.updateDiagnosis(selected);
						if (result) {
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis closed", "Success", JOptionPane.INFORMATION_MESSAGE);
							cargarDiagnosticosPaciente();
						}
						else
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis could not be closed", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(MedicalRecordView.this, "This diagnosis is already closed!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnCloseProcedure;
	}
	private JButton getBtnOpenDiagnosis() {
		if (btnOpenDiagnosis == null) {
			btnOpenDiagnosis = new JButton("Open");
			btnOpenDiagnosis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected.status != 1) {
						selected.status = 1;
						boolean result = ConnectionFactory.updateDiagnosis(selected);
						if (result) {
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis opened", "Success", JOptionPane.INFORMATION_MESSAGE);
							cargarDiagnosticosPaciente();
						}
						else
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis could not be opened", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(MedicalRecordView.this, "This diagnosis is already opened!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnOpenDiagnosis.setBounds(273, 292, 66, 23);
		}
		return btnOpenDiagnosis;
	}
	private JLabel getLblDetailHistory() {
		if (lblDetailHistory == null) {
			lblDetailHistory = new JLabel("Report:");
			lblDetailHistory.setBounds(10, 212, 46, 14);
		}
		return lblDetailHistory;
	}
	private JTextArea getTxtAreaReport() {
		if (txtAreaReport == null) {
			txtAreaReport = new JTextArea();
		}
		return txtAreaReport;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(10, 227, 504, 54);
			scrollPane_2.setViewportView(getTxtAreaReport());
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
			listapptmnt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
						// fill the information
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
							for (String diagnosis : result) {
								diagn.addElement(diagnosis);
							}
						}
						listPutDiagnosis.setModel(diagn);
						// fill the causes
						DefaultListModel<String> causesModel = new DefaultListModel<String>();
						List<String> causes = ConnectionFactory.getCausesFromAppointment(apnmt.getId());
						for (String item : causes)
							causesModel.addElement(item);
						listCausesOfAppointment.setModel(causesModel);
						// fill the procedures
						DefaultListModel<String> procModel = new DefaultListModel<String>();
						List<String> proc = ConnectionFactory.getProceduresFromAppointment(apnmt.getId());
						for (String item : proc)
							procModel.addElement(item);
						proceduresOfAppointmentList.setModel(procModel);
						// fill the prescriptions
						DefaultListModel<String> prescriptionsModel = new DefaultListModel<String>();
						List<String> prescriptions = ConnectionFactory.getPrescriptionsFromAppointment(apnmt.getId());
						prescriptions.addAll(ConnectionFactory.getVaccinesFromAppointment(apnmt.getId()));
						for (String item : prescriptions)
							prescriptionsModel.addElement(item);
						putPrescriptionList.setModel(prescriptionsModel);
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
			panel_2.setLayout(null);
			panel_2.add(getScrollPane_7());
			panel_2.add(getLblNewLabel_12());
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
			lblNewLabel_9.setBounds(10, 172, 84, 14);
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
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Don't track");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected.status != 0) {
						selected.status = 0;
						boolean result = ConnectionFactory.updateDiagnosis(selected);
						if (result) {
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis untracked", "Success", JOptionPane.INFORMATION_MESSAGE);
							cargarDiagnosticosPaciente();
						}
						else
							JOptionPane.showMessageDialog(MedicalRecordView.this, "Diagnosis could not be untracked", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(MedicalRecordView.this, "This diagnosis is already untracked!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnNewButton.setBounds(425, 292, 89, 23);
		}
		return btnNewButton;
	}
	private JList getPutPrescriptionList() {
		if (putPrescriptionList == null) {
			putPrescriptionList = new JList();
		}
		return putPrescriptionList;
	}
	private JScrollPane getScrollPane_7() {
		if (scrollPane_7 == null) {
			scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(10, 24, 389, 269);
			scrollPane_7.setViewportView(getPutPrescriptionList());
		}
		return scrollPane_7;
	}
	private JLabel getLblNewLabel_12() {
		if (lblNewLabel_12 == null) {
			lblNewLabel_12 = new JLabel("Prescriptions of the appointment:");
			lblNewLabel_12.setBounds(10, 11, 174, 14);
		}
		return lblNewLabel_12;
	}
}
