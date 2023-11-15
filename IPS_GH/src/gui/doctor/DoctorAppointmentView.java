package gui.doctor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import creator.CausesCreator;
import db.Diagnosis;
import db.ICDChapter;
import db.ICDSubchapter;
import db.Procedure;
import gui.medicalRecord.MedicalRecordView;
import util.AppointmentBLDto;
import util.ConnectionFactory;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;

public class DoctorAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel patientInfoPanel;
	private JPanel buttonsPanel;
	private JButton btnBack;
	private JButton btnSave;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private CausesCreator causesCreator;
	private Map<String, List<String>> causes;
	private DefaultListModel<String> finalCauses = new DefaultListModel<String>();
	private DefaultListModel<String> finalPrescription = new DefaultListModel<String>();
	private String[] vaccines = new String[] {"Covid-19 vaccine", "Tetanos vaccine", "Spanish flu vaccine", "Viruela vaccine", "Other..."};
	private List<Diagnosis> diagnosis = new ArrayList<Diagnosis>();
	private List<Procedure> procedures = new ArrayList<Procedure>();
	private List<ICDChapter> chapters;
	private List<ICDChapter> procedureChapters;
	private List<ICDSubchapter> procedureSubchapters = new ArrayList<ICDSubchapter>();
	private List<ICDSubchapter> subchapters = new ArrayList<ICDSubchapter>();
	private AppointmentBLDto appointment;
	private JTabbedPane tabbedPane;
	private JPanel appointmentOptionsPanel;
	private JLabel lblListCauses;
	private JButton btnAddCause;
	private JLabel lblCustomCause;
	private JScrollPane scrollPane_1;
	private JButton btnAddCustomCause;
	private JLabel lblSelectedCauses;
	private JButton btnRemoveCause;
	private JPanel pnHeader;
	private JLabel lblAppointmentPatient;
	private JLabel lblAppointmentDate;
	private JPanel pnBody;
	private JLabel lblCheckInTime;
	private JTextField txtCheckinTime;
	private JLabel lblCheckOutTime;
	private JTextField txtCheckoutTime;
	private JButton btnCheckInTime;
	private JButton btnCheckOutTime;
	private JLabel lblAttendance;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private JRadioButton rdbtnDontAnswer;
	private JTextArea txtAreaOtherCauses;
	private JPanel pnDiagnosis;
	private JPanel pnPrescription;
	private JTree tree;
	private JScrollPane scrollPane;
	private JLabel lblMedication;
	private JComboBox comboBoxMedication;
	private JButton btnAddMedication;
	private JLabel lblQuantity;
	private JLabel lblInterval;
	private JLabel lblDurationday;
	private JTextField txtQuantity;
	private JTextField txtInterval;
	private JTextField txtDuration;
	private JLabel lblComments;
	private JTextArea txtAreaComments;
	private JScrollPane scrollPane_2;
	private JLabel lblVaccines;
	private JComboBox comboBoxVaccines;
	private JButton btnAddVaccine;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblOther;
	private JTextArea txtAreaOther;
	private JScrollPane scrollPane_3;
	private JLabel lblPrescriptionState;
	private JList listPrescription;
	private JScrollPane scrollPane_4;
	private JButton btnAddOther;
	private JButton btnRemove;
	private JButton btnClearAll;
	private JScrollPane scrollPane_5;
	private JList listCauses;
	private JList listSelectedCauses;
	private JScrollPane scrollPane_7;
	private JList causesList;
	private JScrollPane scrollPane_6;
	private JList selectedDiagnosisList;
	private JScrollPane scrollPane_8;
	private JButton btnAddDiagnosis;
	private JButton btnRemoveDiagnosis;
	private JLabel lblFinalDiagnosis;
	private JTextArea txtAreaDiagnosisDescription;
	private JScrollPane scrollPane_9;
	private JTextField txtFilterCauses;
	private JLabel lblSearch;
	private JPanel pnMedicalRecord;
	private JButton btnMedicalRecord;
	private MedicalRecordView medicalRecord;
	private JLabel lblSearchCode;
	private JTextField txtDiagnosisCode;
	private JButton btnSearch;
	private JLabel lblNoDiagnosis;
	private JPanel pnProcedures;
	private JTree procedureTree;
	private JScrollPane scrollPane_10;
	private JList selectedProceduresList;
	private JScrollPane scrollPane_11;
	private JLabel lblFinalProcedures;
	private JButton btnAddProcedure;
	private JButton btnRemoveProcedure;
	private JTextArea txtAreaProcedureDescription;
	private JLabel lblNoProcedureResults;
	private JScrollPane scrollPane_12;
	private JTextField txtFieldSearchProcedure;
	private JButton btnSearchProcedure;
	private JLabel lblSearchProcedure;
	private JPanel pnAtenzzioneDiagnosis;
	private JButton btnAtenzzione;
	private JPanel pnButtons;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorAppointmentView frame = new DoctorAppointmentView(null);
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
	public DoctorAppointmentView(AppointmentBLDto appointment) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorAppointmentView.class.getResource("/img/descarga.jpg")));
		setTitle("Appointment");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("width: " + e.getComponent().getBounds().width);
				System.out.println("height: " + e.getComponent().getBounds().height);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO: make confirmation message
				setVisible(false);
			}
		});
		this.appointment = appointment;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 668, 626);
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
		contentPane.add(getPatientInfoPanel(), BorderLayout.NORTH);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		contentPane.add(getTabbedPane());
	}

	private JPanel getPatientInfoPanel() {
		if (patientInfoPanel == null) {
			patientInfoPanel = new JPanel();
			patientInfoPanel.setLayout(new BorderLayout(0, 0));
			patientInfoPanel.add(getPnHeader(), BorderLayout.NORTH);
			patientInfoPanel.add(getPnBody(), BorderLayout.CENTER);
		}
		return patientInfoPanel;
	}

	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setBorder(null);
			buttonsPanel.setLayout(new BorderLayout(0, 0));
			buttonsPanel.add(getPnAtenzzioneDiagnosis(), BorderLayout.WEST);
			buttonsPanel.add(getPnButtons(), BorderLayout.EAST);
		}
		return buttonsPanel;
	}

	private JButton getBtnExit() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnBack;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnYes.isSelected())
						appointment.attended = 1;
					else if (rdbtnNo.isSelected())
						appointment.attended = 0;
					else if (rdbtnDontAnswer.isSelected())
						appointment.attended = 2;
					appointment.checkIn = txtCheckinTime.getText();
					appointment.checkOut = txtCheckoutTime.getText();
					List<String> causes = getFinalCauses();
					List<String> procedures = getFinalProcedures();
					List<String> diagnosis = getFinalDiagnosis();
					List<String> prescriptions = getFinalPrescriptions();
					List<String> vaccines = getFinalVaccines();
					// save causes, procedures, diagnosis, prescriptions and vaccines into the db
					ConnectionFactory.updateAppointment(appointment);
					boolean causesSaved = true;
					boolean procSaved = true;
					boolean diagnSaved = true;
					boolean prescrSaved = true;
					boolean vaccinesSaved = true;
					if (!causes.isEmpty())
						causesSaved = ConnectionFactory.addCausesToAppointment(appointment, causes);
					if (!procedures.isEmpty())
						procSaved = ConnectionFactory.addProceduresToAppointment(appointment, procedures);
					if (!diagnosis.isEmpty())
						diagnSaved = ConnectionFactory.addDiagnosisToAppointment(appointment, diagnosis);
					if (!prescriptions.isEmpty())
						prescrSaved = ConnectionFactory.addPrescriptionsToAppointment(appointment, prescriptions);
					if (!vaccines.isEmpty())
						vaccinesSaved = ConnectionFactory.addVaccinesToAppointment(appointment, vaccines);
					
					if (causesSaved && procSaved && diagnSaved && prescrSaved && vaccinesSaved)
						JOptionPane.showMessageDialog(DoctorAppointmentView.this, "Data correctly saved", "Success", JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(DoctorAppointmentView.this, "Some data could not be saved", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnSave;
	}
	
	private List<String> getFinalVaccines() {
		List<String> vaccines = new ArrayList<String>();
		for (int i = 0; i < finalPrescription.getSize(); i++) {
			String item = finalPrescription.getElementAt(i);
		    if (item.contains("vaccine"))
		    	vaccines.add(item);
		}
		return vaccines;
	}
	
	private List<String> getFinalPrescriptions() {
		List<String> prescriptions = new ArrayList<String>();
		for (int i = 0; i < finalPrescription.getSize(); i++) {
			String item = finalPrescription.getElementAt(i);
		    if (!item.contains("vaccine"))
		    	prescriptions.add(item);
		}
		return prescriptions;
	}
	
	private List<String> getFinalDiagnosis() {
		DefaultListModel<String> model = (DefaultListModel<String>) this.selectedDiagnosisList.getModel();
		List<String> diagnosis = new ArrayList<String>();
		for (int i = 0; i < model.getSize(); i++) {
		    diagnosis.add(model.getElementAt(i));
		}
		return diagnosis;
	}
	
	private List<String> getFinalProcedures() {
		DefaultListModel<String> model = (DefaultListModel<String>) selectedProceduresList.getModel();
		List<String> procedures = new ArrayList<String>();
		for (int i = 0; i < model.getSize(); i++) {
		    procedures.add(model.getElementAt(i));
		}
		return procedures;
	}
	
	private List<String> getFinalCauses() {
		DefaultListModel<String> model = (DefaultListModel<String>) listSelectedCauses.getModel();
		List<String> causes = new ArrayList<String>();
		for (int i = 0; i < model.getSize(); i++) {
		    causes.add(model.getElementAt(i));
		}
		return causes;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Appointment causes", null, getAppointmentOptionsPanel_1(), null);
			tabbedPane.addTab("Procedures", null, getPnProcedures(), null);
			tabbedPane.addTab("Diagnosis", null, getPnDiagnosis(), null);
			tabbedPane.addTab("Prescription", null, getPnPrescription(), null);
		}
		return tabbedPane;
	}
	private JPanel getAppointmentOptionsPanel_1() {
		if (appointmentOptionsPanel == null) {
			appointmentOptionsPanel = new JPanel();
			appointmentOptionsPanel.setLayout(null);
			appointmentOptionsPanel.add(getLblListCauses_1());
			appointmentOptionsPanel.add(getBtnAddCause_1());
			appointmentOptionsPanel.add(getLblCustomCause_1());
			appointmentOptionsPanel.add(getScrollPane_1_1());
			appointmentOptionsPanel.add(getBtnAddCustomCause_1());
			appointmentOptionsPanel.add(getLblSelectedCauses_1());
			appointmentOptionsPanel.add(getBtnRemoveCause_1());
			appointmentOptionsPanel.add(getScrollPane_5());
			appointmentOptionsPanel.add(getScrollPane_7());
			appointmentOptionsPanel.add(getScrollPane_6());
			appointmentOptionsPanel.add(getTxtFilterCauses());
			appointmentOptionsPanel.add(getLblSearch());
		}
		return appointmentOptionsPanel;
	}
	private JLabel getLblListCauses_1() {
		if (lblListCauses == null) {
			lblListCauses = new JLabel("Select appointment cause(s):");
			lblListCauses.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblListCauses.setBounds(10, 15, 192, 14);
		}
		return lblListCauses;
	}
	private JButton getBtnAddCause_1() {
		if (btnAddCause == null) {
			btnAddCause = new JButton(">>");
			btnAddCause.setBounds(302, 102, 49, 23);
			btnAddCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> causesSelected = getCausesList().getSelectedValuesList();
					for (String cause : causesSelected) {
						if (!finalCauses.contains(cause)) {
							finalCauses.addElement(cause);
						}
					}
				}
			});
		}
		return btnAddCause;
	}
	private JLabel getLblCustomCause_1() {
		if (lblCustomCause == null) {
			lblCustomCause = new JLabel("Other cause(s):");
			lblCustomCause.setBounds(10, 253, 148, 14);
		}
		return lblCustomCause;
	}
	private JScrollPane getScrollPane_1_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(330, 15, 2, 2);
		}
		return scrollPane_1;
	}
	private JButton getBtnAddCustomCause_1() {
		if (btnAddCustomCause == null) {
			btnAddCustomCause = new JButton("Add");
			btnAddCustomCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalCauses.addElement(getTxtAreaOtherCauses().getText());
				}
			});
			btnAddCustomCause.setBounds(291, 329, 51, 23);
		}
		return btnAddCustomCause;
	}
	private JLabel getLblSelectedCauses_1() {
		if (lblSelectedCauses == null) {
			lblSelectedCauses = new JLabel("Final cause(s):");
			lblSelectedCauses.setBounds(380, 41, 217, 14);
		}
		return lblSelectedCauses;
	}
	private JButton getBtnRemoveCause_1() {
		if (btnRemoveCause == null) {
			btnRemoveCause = new JButton("<<");
			btnRemoveCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> selectedItems = getListSelectedCauses().getSelectedValuesList();
					for (String item : selectedItems) {
						finalCauses.removeElement(item);
					}
				}
			});
			btnRemoveCause.setBounds(302, 155, 49, 23);
		}
		return btnRemoveCause;
	}
	private JPanel getPnHeader() {
		if (pnHeader == null) {
			pnHeader = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnHeader.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnHeader.add(getLblAppointmentPatient_1());
			pnHeader.add(getLblAppointmentDate_1());
			pnHeader.add(getPnMedicalRecord());
		}
		return pnHeader;
	}
	private JLabel getLblAppointmentPatient_1() {
		if (lblAppointmentPatient == null) {
			//lblAppointmentPatient = new JLabel("PAQUITA SALAS");
			lblAppointmentPatient = new JLabel(appointment.patientName.toUpperCase() + " " + appointment.patientSurname.toUpperCase());
			lblAppointmentPatient.setFont(new Font("Tahoma", Font.PLAIN, 16));
			
		}
		return lblAppointmentPatient;
	}
	private JLabel getLblAppointmentDate_1() {
		if (lblAppointmentDate == null) {
			//lblAppointmentPatient = new JLabel("2023-11-15");
			lblAppointmentDate = new JLabel(this.appointment.startDate);
			lblAppointmentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
	
		}
		return lblAppointmentDate;
	}
	private JPanel getPnBody() {
		if (pnBody == null) {
			pnBody = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBody.getLayout();
			flowLayout.setVgap(15);
			pnBody.setPreferredSize(new Dimension(50, 100));
			flowLayout.setHgap(25);
			pnBody.add(getLblAttendance());
			pnBody.add(getRdbtnYes());
			pnBody.add(getRdbtnNo());
			pnBody.add(getRdbtnDontAnswer());
			pnBody.add(getLblCheckInTime());
			pnBody.add(getTextField_2());
			pnBody.add(getBtnCheckInTime());
			pnBody.add(getLblCheckOutTime());
			pnBody.add(getTextField_1_1());
			pnBody.add(getBtnCheckOutTime());
		}
		return pnBody;
	}
	private JLabel getLblCheckInTime() {
		if (lblCheckInTime == null) {
			lblCheckInTime = new JLabel("Check-in:");
		}
		return lblCheckInTime;
	}
	private JTextField getTextField_2() {
		if (txtCheckinTime == null) {
			txtCheckinTime = new JTextField();
			txtCheckinTime.setColumns(6);
		}
		return txtCheckinTime;
	}
	private JLabel getLblCheckOutTime() {
		if (lblCheckOutTime == null) {
			lblCheckOutTime = new JLabel("Check-out:");
		}
		return lblCheckOutTime;
	}
	private JTextField getTextField_1_1() {
		if (txtCheckoutTime == null) {
			txtCheckoutTime = new JTextField();
			txtCheckoutTime.setColumns(6);
		}
		return txtCheckoutTime;
	}
	private JButton getBtnCheckInTime() {
		if (btnCheckInTime == null) {
			btnCheckInTime = new JButton("Auto-fill in time");
			btnCheckInTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTextField_2().setText(currentTime);
				}
			});
		}
		return btnCheckInTime;
	}
	private JButton getBtnCheckOutTime() {
		if (btnCheckOutTime == null) {
			btnCheckOutTime = new JButton("Auto-fill out time");
			btnCheckOutTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTextField_1_1().setText(currentTime);
				}
			});
		}
		return btnCheckOutTime;
	}
	private JLabel getLblAttendance() {
		if (lblAttendance == null) {
			lblAttendance = new JLabel("Did the patient attend the appointment?");
		}
		return lblAttendance;
	}
	private JRadioButton getRdbtnYes() {
		if (rdbtnYes == null) {
			rdbtnYes = new JRadioButton("Attended");
			buttonGroup.add(rdbtnYes);
			rdbtnYes.setSelected(true);
		}
		return rdbtnYes;
	}
	private JRadioButton getRdbtnNo() {
		if (rdbtnNo == null) {
			rdbtnNo = new JRadioButton("Not attended");
			buttonGroup.add(rdbtnNo);
		}
		return rdbtnNo;
	}
	private JRadioButton getRdbtnDontAnswer() {
		if (rdbtnDontAnswer == null) {
			rdbtnDontAnswer = new JRadioButton("Don't answer");
			buttonGroup.add(rdbtnDontAnswer);
		}
		return rdbtnDontAnswer;
	}
	private JTextArea getTxtAreaOtherCauses() {
		if (txtAreaOtherCauses == null) {
			txtAreaOtherCauses = new JTextArea();
		}
		return txtAreaOtherCauses;
	}
	private JPanel getPnDiagnosis() {
		if (pnDiagnosis == null) {
			pnDiagnosis = new JPanel();
			pnDiagnosis.setLayout(null);
			pnDiagnosis.add(getScrollPane());
			pnDiagnosis.add(getScrollPane_8());
			pnDiagnosis.add(getBtnAddDiagnosis());
			pnDiagnosis.add(getBtnRemoveDiagnosis());
			pnDiagnosis.add(getLblFinalDiagnosis());
			pnDiagnosis.add(getScrollPane_9());
			pnDiagnosis.add(getLblSearchCode());
			pnDiagnosis.add(getTxtDiagnosisCode());
			pnDiagnosis.add(getBtnSearch());
			pnDiagnosis.add(getLblNoDiagnosis());
		}
		return pnDiagnosis;
	}
	private JPanel getPnPrescription() {
		if (pnPrescription == null) {
			pnPrescription = new JPanel();
			pnPrescription.setLayout(null);
			pnPrescription.add(getLblMedication());
			pnPrescription.add(getComboBoxMedication());
			pnPrescription.add(getBtnAddMedication());
			pnPrescription.add(getLblQuantity());
			pnPrescription.add(getLblInterval());
			pnPrescription.add(getLblDurationday());
			pnPrescription.add(getTxtQuantity());
			pnPrescription.add(getTxtInterval());
			pnPrescription.add(getTextField_2_1());
			pnPrescription.add(getLblComments());
			pnPrescription.add(getScrollPane_2());
			pnPrescription.add(getLblVaccines());
			pnPrescription.add(getComboBoxVaccines());
			pnPrescription.add(getBtnAddVaccine());
			pnPrescription.add(getSeparator());
			pnPrescription.add(getSeparator_1());
			pnPrescription.add(getLblOther());
			pnPrescription.add(getScrollPane_3());
			pnPrescription.add(getLblPrescriptionState());
			pnPrescription.add(getScrollPane_4());
			pnPrescription.add(getBtnAddOther());
			pnPrescription.add(getBtnRemove());
			pnPrescription.add(getBtnClearAll());
		}
		return pnPrescription;
	}
	private JTree getTree() {
		if (tree == null) {
			tree = new JTree();
			loadTreeData();
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
	                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	                getTxtAreaDiagnosisDescription().setText("");
	                if (selectedNode != null && selectedNode.isLeaf()) {
	                	if (selectedNode.getLevel() ==1) { // if is chapter then expand subchapters
	                		String from = "", to = "";
	                		for (ICDChapter chapter : chapters) {
	                			if (chapter.description.equals(selectedNode.getUserObject())) {
	                				from = chapter.from;
	                				to = chapter.to;
	                				getTxtAreaDiagnosisDescription().setText(chapter.cardinality + "\n"
	                						+ chapter.description.toLowerCase() + "\n"
	                						+ chapter.from + " " + chapter.to
	                						);
	                				break;
	                			}
	                		}
	                		List<ICDSubchapter> newsubchapters = ConnectionFactory.getSubchapters(from, to);
	                		subchapters.addAll(newsubchapters);
	                		for (ICDSubchapter item : newsubchapters) {
	                			DefaultMutableTreeNode subnode = new 
	                					DefaultMutableTreeNode(item.description);
	                			selectedNode.add(subnode);
	                			// Notificar al modelo del árbol que la estructura ha cambiado
		                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		                        model.nodeStructureChanged(selectedNode);
		                        // Expandir el nodo seleccionado
		                        tree.expandPath(new TreePath(selectedNode.getPath()));
	                		}
	                	}
	                	// contiene unos diagnosticos -> ENFERMEDADES NO SE QUE (B99)
	                	else if (((String)selectedNode.getUserObject()).indexOf("(") != -1){
	                		// contiene rango -> ENFERMEDADES NO SE QUE (B99-C34)
	                		if (((String)selectedNode.getUserObject()).indexOf("-") != -1) {
	                			String input = (String) selectedNode.getUserObject();
		                		int indexOfHyphen = ((String)selectedNode.getUserObject()).indexOf("-");
		                		// Encuentra la posición del primer paréntesis "("
		                        int indexOfOpeningParenthesis = input.indexOf("(");
		                        // Encuentra la posición del último paréntesis ")"
		                        int indexOfClosingParenthesis = input.lastIndexOf(")");
		                     // Extrae la parte izquierda entre "(" y "-"
	                            String from = input.substring(indexOfOpeningParenthesis + 1, indexOfHyphen);
	                            // Extrae la parte derecha entre "-" y ")"
	                            String to = input.substring(indexOfHyphen + 1, indexOfClosingParenthesis);
	                            getTxtAreaDiagnosisDescription().setText(input + "\n"
	                            		+ from + " " + to);
	                            List<Diagnosis> newdiagnosis = ConnectionFactory.getDiagnosis(from, to);
	                            diagnosis.addAll(newdiagnosis);
		                		for (Diagnosis item : newdiagnosis) {
		                			DefaultMutableTreeNode subnode = new 
		                					DefaultMutableTreeNode(item.code + "\t " + item.description);
		                			selectedNode.add(subnode);
		                			// Notificar al modelo del árbol que la estructura ha cambiado
			                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			                        model.nodeStructureChanged(selectedNode);
			                        // Expandir el nodo seleccionado
			                        tree.expandPath(new TreePath(selectedNode.getPath()));
		                		}
	                		}
	                		// no contiene rango -> ENFERMEDADES NO SE QUE (B99)
	                		else {
	                			String input = (String) selectedNode.getUserObject();
		                		// Encuentra la posición del primer paréntesis "("
		                        int indexOfOpeningParenthesis = input.indexOf("(");
		                        // Encuentra la posición del último paréntesis ")"
		                        int indexOfClosingParenthesis = input.lastIndexOf(")");
		                     // Extrae la parte entre "(" y ")"
	                            String from = input.substring(indexOfOpeningParenthesis + 1, indexOfClosingParenthesis);
	                            
	                            getTxtAreaDiagnosisDescription().setText(input + "\n"
	                            		+ from);
	                            Diagnosis newdiagnosis = ConnectionFactory.getDiagnosis(from);
	                            diagnosis.add(newdiagnosis);
	                			DefaultMutableTreeNode subnode = new 
	                					DefaultMutableTreeNode(newdiagnosis.code + "\t " + newdiagnosis.description);
	                			selectedNode.add(subnode);
	                			// Notificar al modelo del árbol que la estructura ha cambiado
		                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		                        model.nodeStructureChanged(selectedNode);
		                        // Expandir el nodo seleccionado
		                        tree.expandPath(new TreePath(selectedNode.getPath()));
	                		}
	                	}
	                	// es un diagnostico en si -> A00 Cholera
	                	else {
	                		// coger el código
	                		String code = ((String) selectedNode.getUserObject()).split("\t")[0];
	                		Diagnosis selectedDiag = ConnectionFactory.getDiagnosis(code);
	                		getTxtAreaDiagnosisDescription().setText(selectedDiag.code + "\n"
	                				+ selectedDiag.description.toLowerCase() + "\n"
	                				+ selectedDiag.longDescription.toLowerCase());
	                		List<Diagnosis> childDiagnosis = ConnectionFactory.getDiagnosis(code, code.length()+1);
	                		if (childDiagnosis.size() > 0) {
	                			diagnosis.addAll(childDiagnosis);
                				for (Diagnosis item : childDiagnosis) {
		                			DefaultMutableTreeNode subnode = new 
		                					DefaultMutableTreeNode(item.code + "\t " + item.description);
		                			selectedNode.add(subnode);
		                			// Notificar al modelo del árbol que la estructura ha cambiado
			                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			                        model.nodeStructureChanged(selectedNode);
			                        // Expandir el nodo seleccionado
			                        tree.expandPath(new TreePath(selectedNode.getPath()));
		                		}
	                		}
	                		// no tiene un descendiente con su numero de digitos + 1, si no que tiene mas cifras 
	                		else {
	                			for (int i = 0; i < 3; i++) {
	                				childDiagnosis = ConnectionFactory.getDiagnosis(code, code.length()+1+i);
	                				if (childDiagnosis.size() > 0)
	                					break;
	                			}
	                			if (childDiagnosis.size() > 0) {
	                				diagnosis.addAll(childDiagnosis);
	                				for (Diagnosis item : childDiagnosis) {
			                			DefaultMutableTreeNode subnode = new 
			                					DefaultMutableTreeNode(item.code + "\t " + item.description);
			                			selectedNode.add(subnode);
			                			// Notificar al modelo del árbol que la estructura ha cambiado
				                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				                        model.nodeStructureChanged(selectedNode);
				                        // Expandir el nodo seleccionado
				                        tree.expandPath(new TreePath(selectedNode.getPath()));
			                		}
	                			}
	                		}
	                	}
	                }
				}
			});
		}
		return tree;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 325, 206);
			scrollPane.setViewportView(getTree());
		}
		return scrollPane;
	}
	
	private void loadTreeData() {
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode("ICD10 Diagnosis"); // Nodo raíz
	    DefaultTreeModel model = new DefaultTreeModel(root);
	    tree.setModel(model);
	    
	    chapters = ConnectionFactory.getChapters();
	    
	    for (ICDChapter item : chapters) {
	    	DefaultMutableTreeNode node = new DefaultMutableTreeNode(item.description);
	    	root.add(node);
	    }
    }
	
	private void loadProcedure() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ICD10 Procedures"); // Nodo raíz
	    DefaultTreeModel model = new DefaultTreeModel(root);
	    procedureTree.setModel(model);
	    
	    procedureChapters = ConnectionFactory.getProcedureChapters();
	    
	    for (ICDChapter item : procedureChapters) {
	    	DefaultMutableTreeNode node = new DefaultMutableTreeNode(item.description);
	    	root.add(node);
	    }
	}

	private JLabel getLblMedication() {
		if (lblMedication == null) {
			lblMedication = new JLabel("Medication:");
			lblMedication.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblMedication.setBounds(23, 26, 110, 14);
		}
		return lblMedication;
	}
	private JComboBox getComboBoxMedication() {
		if (comboBoxMedication == null) {
			comboBoxMedication = new JComboBox();
			comboBoxMedication.setBounds(23, 51, 94, 22);
			var data = new String[] {"Algidol", "Ibuprofeno", "Antibiotics", "Thrombocid", "Other..."};
			comboBoxMedication.setModel(new DefaultComboBoxModel(data));
			comboBoxMedication.setSelectedIndex(0);
		}
		return comboBoxMedication;
	}
	private JButton getBtnAddMedication() {
		if (btnAddMedication == null) {
			btnAddMedication = new JButton("Add");
			btnAddMedication.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selMed = (String) getComboBoxMedication().getModel().
							getElementAt(getComboBoxMedication().getSelectedIndex());
					String quantity = getTxtQuantity().getText();
					String interval = getTxtInterval().getText();
					String duration = getTextField_2_1().getText();
					String comments = getTxtAreaComments().getText();
					String medication = selMed + ": " + quantity + " " + interval + " " + duration + 
							". Comments: " + comments;
					finalPrescription.addElement(medication);
				}
			});
			btnAddMedication.setBounds(143, 51, 62, 23);
		}
		return btnAddMedication;
	}
	private JLabel getLblQuantity() {
		if (lblQuantity == null) {
			lblQuantity = new JLabel("Quantity:");
			lblQuantity.setBounds(23, 101, 62, 14);
		}
		return lblQuantity;
	}
	private JLabel getLblInterval() {
		if (lblInterval == null) {
			lblInterval = new JLabel("Interval:");
			lblInterval.setBounds(23, 137, 80, 14);
		}
		return lblInterval;
	}
	private JLabel getLblDurationday() {
		if (lblDurationday == null) {
			lblDurationday = new JLabel("Duration:");
			lblDurationday.setBounds(23, 177, 62, 14);
		}
		return lblDurationday;
	}
	private JTextField getTxtQuantity() {
		if (txtQuantity == null) {
			txtQuantity = new JTextField();
			txtQuantity.setBounds(82, 84, 62, 28);
			txtQuantity.setColumns(10);
		}
		return txtQuantity;
	}
	private JTextField getTxtInterval() {
		if (txtInterval == null) {
			txtInterval = new JTextField();
			txtInterval.setColumns(10);
			txtInterval.setBounds(82, 123, 62, 28);
		}
		return txtInterval;
	}
	private JTextField getTextField_2_1() {
		if (txtDuration == null) {
			txtDuration = new JTextField();
			txtDuration.setColumns(10);
			txtDuration.setBounds(82, 162, 62, 28);
		}
		return txtDuration;
	}
	private JLabel getLblComments() {
		if (lblComments == null) {
			lblComments = new JLabel("Comments:");
			lblComments.setBounds(23, 232, 110, 14);
		}
		return lblComments;
	}
	private JTextArea getTxtAreaComments() {
		if (txtAreaComments == null) {
			txtAreaComments = new JTextArea();
		}
		return txtAreaComments;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(23, 258, 182, 82);
			scrollPane_2.setViewportView(getTxtAreaComments());
		}
		return scrollPane_2;
	}
	private JLabel getLblVaccines() {
		if (lblVaccines == null) {
			lblVaccines = new JLabel("Vaccines:");
			lblVaccines.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVaccines.setBounds(232, 26, 110, 14);
		}
		return lblVaccines;
	}
	private JComboBox getComboBoxVaccines() {
		if (comboBoxVaccines == null) {
			comboBoxVaccines = new JComboBox(vaccines);
			comboBoxVaccines.setBounds(231, 51, 117, 22);
		}
		return comboBoxVaccines;
	}
	private JButton getBtnAddVaccine() {
		if (btnAddVaccine == null) {
			btnAddVaccine = new JButton("Add");
			btnAddVaccine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selVacc = vaccines[getComboBoxVaccines().getSelectedIndex()];
					finalPrescription.addElement(selVacc);
				}
			});
			btnAddVaccine.setBounds(358, 51, 51, 23);
		}
		return btnAddVaccine;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(215, 26, 12, 314);
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setBounds(419, 26, 12, 314);
		}
		return separator_1;
	}
	private JLabel getLblOther() {
		if (lblOther == null) {
			lblOther = new JLabel("Other:");
			lblOther.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblOther.setBounds(226, 207, 62, 14);
		}
		return lblOther;
	}
	private JTextArea getTxtAreaOther() {
		if (txtAreaOther == null) {
			txtAreaOther = new JTextArea();
		}
		return txtAreaOther;
	}
	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(232, 230, 177, 110);
			scrollPane_3.setViewportView(getTxtAreaOther());
		}
		return scrollPane_3;
	}
	private JLabel getLblPrescriptionState() {
		if (lblPrescriptionState == null) {
			lblPrescriptionState = new JLabel("Prescription state:");
			lblPrescriptionState.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblPrescriptionState.setBounds(432, 27, 171, 14);
		}
		return lblPrescriptionState;
	}
	private JList getListPrescription() {
		if (listPrescription == null) {
			listPrescription = new JList(finalPrescription);
		}
		return listPrescription;
	}
	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setBounds(432, 53, 202, 253);
			scrollPane_4.setViewportView(getListPrescription());
		}
		return scrollPane_4;
	}
	private JButton getBtnAddOther() {
		if (btnAddOther == null) {
			btnAddOther = new JButton("Add");
			btnAddOther.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalPrescription.addElement(getTxtAreaOther().getText());
				}
			});
			btnAddOther.setBounds(358, 204, 51, 23);
		}
		return btnAddOther;
	}
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> selItems = getListPrescription().getSelectedValuesList(); 
					for (String item : selItems) {
						if (finalPrescription.contains(item))
							finalPrescription.removeElement(item);
					}
				}
			});
			btnRemove.setBounds(442, 317, 80, 23);
		}
		return btnRemove;
	}
	private JButton getBtnClearAll() {
		if (btnClearAll == null) {
			btnClearAll = new JButton("Clear all");
			btnClearAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalPrescription.clear();
				}
			});
			btnClearAll.setBounds(547, 317, 80, 23);
		}
		return btnClearAll;
	}
	private JScrollPane getScrollPane_5() {
		if (scrollPane_5 == null) {
			scrollPane_5 = new JScrollPane();
			scrollPane_5.setBounds(10, 270, 271, 82);
			scrollPane_5.setViewportView(getTxtAreaOtherCauses());
		}
		return scrollPane_5;
	}

	private JList getListSelectedCauses() {
		if (listSelectedCauses == null) {
			listSelectedCauses = new JList(finalCauses);
		}
		return listSelectedCauses;
	}
	private JScrollPane getScrollPane_7() {
		if (scrollPane_7 == null) {
			scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(381, 66, 216, 164);
			scrollPane_7.setViewportView(getListSelectedCauses());
		}
		return scrollPane_7;
	}
	private JList getCausesList() {
		DefaultListModel<String> model = new DefaultListModel();
		this.causesCreator = new CausesCreator();
		this.causes = causesCreator.getListsOfCauses();

		for (List<String> list : this.causes.values())
			for (String item : list) {
				model.addElement(item);
		}	

		if (causesList == null) {
			causesList = new JList(model);
			causesList.setBounds(10, 66, 257, 164);
		}
		return causesList;
	}
	private JScrollPane getScrollPane_6() {
		if (scrollPane_6 == null) {
			scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(10, 78, 257, 164);
			scrollPane_6.setViewportView(getCausesList());
		}
		return scrollPane_6;
	}
	private JList getSelectedDiagnosisList() {
		if (selectedDiagnosisList == null) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			selectedDiagnosisList = new JList(model);
		}
		return selectedDiagnosisList;
	}
	private JScrollPane getScrollPane_8() {
		if (scrollPane_8 == null) {
			scrollPane_8 = new JScrollPane();
			scrollPane_8.setBounds(10, 245, 617, 95);
			scrollPane_8.setViewportView(getSelectedDiagnosisList());
		}
		return scrollPane_8;
	}
	private JButton getBtnAddDiagnosis() {
		if (btnAddDiagnosis == null) {
			btnAddDiagnosis = new JButton("Add");
			btnAddDiagnosis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtAreaDiagnosisDescription().getText().equals("")) {
						String[] portions = getTxtAreaDiagnosisDescription().getText().split("\n");
						String diagnosis = "";
						for (String item : portions) {
							diagnosis += item + " ";
						}
						DefaultListModel m =(DefaultListModel) getSelectedDiagnosisList().getModel();
						m.addElement(diagnosis);
						getSelectedDiagnosisList().setModel(m);
					}
				}
			});
			btnAddDiagnosis.setBounds(538, 194, 89, 23);
		}
		return btnAddDiagnosis;
	}
	private JButton getBtnRemoveDiagnosis() {
		if (btnRemoveDiagnosis == null) {
			btnRemoveDiagnosis = new JButton("Remove");
			btnRemoveDiagnosis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSelectedDiagnosisList().getSelectedValuesList().size() > 0) {
						DefaultListModel model = (DefaultListModel) getSelectedDiagnosisList().getModel();
						List<String> sels = getSelectedDiagnosisList().getSelectedValuesList();
						for (String item : sels) {
							if (model.contains(item))
								model.removeElement(item);
						}
						getSelectedDiagnosisList().setModel(model);
					}
				}
			});
			btnRemoveDiagnosis.setBounds(439, 194, 89, 23);
		}
		return btnRemoveDiagnosis;
	}
	private JLabel getLblFinalDiagnosis() {
		if (lblFinalDiagnosis == null) {
			lblFinalDiagnosis = new JLabel("Final diagnosis:");
			lblFinalDiagnosis.setBounds(10, 228, 94, 14);
		}
		return lblFinalDiagnosis;
	}
	private JTextArea getTxtAreaDiagnosisDescription() {
		if (txtAreaDiagnosisDescription == null) {
			txtAreaDiagnosisDescription = new JTextArea();
			txtAreaDiagnosisDescription.setEditable(false);
		}
		return txtAreaDiagnosisDescription;
	}
	private JScrollPane getScrollPane_9() {
		if (scrollPane_9 == null) {
			scrollPane_9 = new JScrollPane();
			scrollPane_9.setEnabled(true);
			scrollPane_9.setBounds(345, 98, 282, 85);
			scrollPane_9.setViewportView(getTxtAreaDiagnosisDescription());
		}
		return scrollPane_9;
	}
	private JTextField getTxtFilterCauses() {
		if (txtFilterCauses == null) {
			txtFilterCauses = new JTextField();
			txtFilterCauses.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                filterElements();
	            }
	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                filterElements();
	            }
	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                // Cambios en estilos, no se utiliza en este ejemplo
	            }
	        });
			txtFilterCauses.setBounds(63, 40, 148, 31);
			txtFilterCauses.setColumns(10);
		}
		return txtFilterCauses;
	}
	
	private void filterElements() {
		DefaultListModel<String> model = new DefaultListModel();
		List<String> elements = new ArrayList<String>();
		for (List<String> list : this.causes.values())
			for (String item : list) {
				elements.add(item);
		}	
		String searchText = getTxtFilterCauses().getText().toLowerCase();

        List<String> filteredElements = elements.stream()
                .filter(element -> element.toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        for (String element : filteredElements) {
            model.addElement(element);
        }
        
        getCausesList().setModel(model);
    }
	private JLabel getLblSearch() {
		if (lblSearch == null) {
			lblSearch = new JLabel("Search:");
			lblSearch.setBounds(10, 53, 67, 14);
		}
		return lblSearch;
	}
	private JPanel getPnMedicalRecord() {
		if (pnMedicalRecord == null) {
			pnMedicalRecord = new JPanel();
			pnMedicalRecord.add(getBtnMedicalRecord());
		}
		return pnMedicalRecord;
	}
	private JButton getBtnMedicalRecord() {
		if (btnMedicalRecord == null) {
			btnMedicalRecord = new JButton("Open MedicalRecord");
			btnMedicalRecord.addActionListener(new ActionListener() {
				

				public void actionPerformed(ActionEvent e) {
					medicalRecord = new MedicalRecordView(appointment.patientid);
					medicalRecord.setVisible(true);
				}
			});
		}
		return btnMedicalRecord;
	}
	private JLabel getLblSearchCode() {
		if (lblSearchCode == null) {
			lblSearchCode = new JLabel("Search diagnosis code:");
			lblSearchCode.setBounds(345, 13, 136, 14);
		}
		return lblSearchCode;
	}
	private JTextField getTxtDiagnosisCode() {
		if (txtDiagnosisCode == null) {
			txtDiagnosisCode = new JTextField();
			txtDiagnosisCode.setBounds(345, 35, 114, 30);
			txtDiagnosisCode.setColumns(10);
		}
		return txtDiagnosisCode;
	}
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("Search");
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// get the diagnosis code from the txt field
					String code = getTxtDiagnosisCode().getText();
					// search for the diagnosis in the db
					
					Diagnosis search = ConnectionFactory.getDiagnosis(code);
					// if there is a result show it
					if (search.code != null) {
						// remove warning 
						getLblNoDiagnosis().setVisible(false);
						// show it in the description area
						getTxtAreaDiagnosisDescription().setText(search.code + "\n"
                				+ search.description.toLowerCase() + "\n"
                				+ search.longDescription.toLowerCase());
					}
					// else show label "no results for this search"
					else {
						getLblNoDiagnosis().setVisible(true);
						getTxtAreaDiagnosisDescription().setText("");
					}					
				}
			});
			btnSearch.setBounds(487, 39, 89, 23);
		}
		return btnSearch;
	}
	private JLabel getLblNoDiagnosis() {
		if (lblNoDiagnosis == null) {
			lblNoDiagnosis = new JLabel("No diagnosis found for this code!");
			lblNoDiagnosis.setForeground(Color.RED);
			lblNoDiagnosis.setBounds(345, 73, 282, 14);
			lblNoDiagnosis.setVisible(false);
		}
		return lblNoDiagnosis;
	}
	private JPanel getPnProcedures() {
		if (pnProcedures == null) {
			pnProcedures = new JPanel();
			pnProcedures.setLayout(null);
			pnProcedures.add(getScrollPane_10());
			pnProcedures.add(getScrollPane_11());
			pnProcedures.add(getLblFinalProcedures());
			pnProcedures.add(getBtnAddProcedure());
			pnProcedures.add(getBtnRemoveProcedure());
			pnProcedures.add(getScrollPane_12());
			pnProcedures.add(getLblNoProcedureResults());
			pnProcedures.add(getTxtFieldSearchProcedure());
			pnProcedures.add(getBtnSearchProcedure());
			pnProcedures.add(getLblSearchProcedure());
		}
		return pnProcedures;
	}
	private JTree getProcedureTree() {
		if (procedureTree == null) {
			procedureTree = new JTree();
			loadProcedure();
			procedureTree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
	                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) procedureTree.getLastSelectedPathComponent();
	                //getTxtAreaDiagnosisDescription().setText("");
	                if (selectedNode != null && selectedNode.isLeaf()) {
	                	if (selectedNode.getLevel() ==1) { // if is chapter then expand subchapters
	                		String from = "", to = "";
	                		for (ICDChapter chapter : procedureChapters) {
	                			if (chapter.description.equals(selectedNode.getUserObject())) {
	                				from = chapter.from;
	                				to = chapter.to;
	                				getTxtAreaProcedureDescription().setText(chapter.cardinality + "\n"
	                						+ chapter.description + "\n"
	                						+ chapter.from + " " + chapter.to
	                						);
	                				break;
	                			}
	                		}
	                		List<ICDSubchapter> newsubchapters = ConnectionFactory.getProcedureSubchapters(from, to);
	                		procedureSubchapters.addAll(newsubchapters);
	                		for (ICDSubchapter item : newsubchapters) {
	                			DefaultMutableTreeNode subnode = new 
	                					DefaultMutableTreeNode(item.description);
	                			selectedNode.add(subnode);
	                			// Notificar al modelo del árbol que la estructura ha cambiado
		                        DefaultTreeModel model = (DefaultTreeModel) procedureTree.getModel();
		                        model.nodeStructureChanged(selectedNode);
		                        // Expandir el nodo seleccionado
		                        procedureTree.expandPath(new TreePath(selectedNode.getPath()));
	                		}
	                	}
	                	// if it is subchapter expand procedures
	                	else if (selectedNode.getLevel() == 2) {
	                		String from = "", to = "";
	                		for (ICDSubchapter subchapter : procedureSubchapters) {
	                			if (subchapter.description.equals(selectedNode.getUserObject())) {
	                				from = subchapter.from;
	                				to = subchapter.to;
	                				getTxtAreaProcedureDescription().setText(subchapter.sections + "\n"
	                						+ subchapter.description + "\n"
	                						+ subchapter.from + " " + subchapter.to
	                						);
	                				break;
	                			}
	                		}
	                		List<Procedure> nprocedures = ConnectionFactory.getProcedures(from, to);
	                		procedures.addAll(nprocedures);
	                		for (Procedure item : nprocedures) {
	                			DefaultMutableTreeNode subnode = new 
	                					DefaultMutableTreeNode(item.description);
	                			selectedNode.add(subnode);
	                			// Notificar al modelo del árbol que la estructura ha cambiado
		                        DefaultTreeModel model = (DefaultTreeModel) procedureTree.getModel();
		                        model.nodeStructureChanged(selectedNode);
		                        // Expandir el nodo seleccionado
		                        procedureTree.expandPath(new TreePath(selectedNode.getPath()));
	                		}
	                	}
	                	else if (selectedNode.getLevel() == 3) {
	                		for (Procedure p : procedures) {
	                			if (p.description.equals(selectedNode.getUserObject())) {
	                				getTxtAreaProcedureDescription().setText(p.code + "\n"
	                						+ p.description);
	                				break;
	                			}
	                		}
	                	}
	                }
				}
			});
		}
		return procedureTree;
	}
	
	private JScrollPane getScrollPane_10() {
		if (scrollPane_10 == null) {
			scrollPane_10 = new JScrollPane();
			scrollPane_10.setBounds(10, 11, 325, 206);
			scrollPane_10.setViewportView(getProcedureTree());
		}
		return scrollPane_10;
	}
	
	private JList getSelectedProceduresList() {
		if (selectedProceduresList == null) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			selectedProceduresList = new JList(model);
		}
		return selectedProceduresList;
	}
	private JScrollPane getScrollPane_11() {
		if (scrollPane_11 == null) {
			scrollPane_11 = new JScrollPane();
			scrollPane_11.setBounds(10, 267, 617, 95);
			scrollPane_11.setViewportView(getSelectedProceduresList());
		}
		return scrollPane_11;
	}
	private JLabel getLblFinalProcedures() {
		if (lblFinalProcedures == null) {
			lblFinalProcedures = new JLabel("Final procedures:");
			lblFinalProcedures.setBounds(10, 242, 131, 14);
		}
		return lblFinalProcedures;
	}
	private JButton getBtnAddProcedure() {
		if (btnAddProcedure == null) {
			btnAddProcedure = new JButton("Add");
			btnAddProcedure.setBounds(524, 194, 89, 23);
			btnAddProcedure.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTxtAreaProcedureDescription().getText().equals("")) {
						String[] portions = getTxtAreaProcedureDescription().getText().split("\n");
						String procedure = "";
						for (String item : portions) {
							procedure += item + " ";
						}
						DefaultListModel m =(DefaultListModel) getSelectedProceduresList().getModel();
						m.addElement(procedure);
						getSelectedProceduresList().setModel(m);
					}
				}
			});
		}
		return btnAddProcedure;
	}
	private JButton getBtnRemoveProcedure() {
		if (btnRemoveProcedure == null) {
			btnRemoveProcedure = new JButton("Remove");
			btnRemoveProcedure.setBounds(425, 194, 89, 23);
			btnRemoveProcedure.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSelectedProceduresList().getSelectedValuesList().size() > 0) {
						DefaultListModel model = (DefaultListModel) getSelectedProceduresList().getModel();
						List<String> sels = getSelectedProceduresList().getSelectedValuesList();
						for (String item : sels) {
							if (model.contains(item))
								model.removeElement(item);
						}
						getSelectedProceduresList().setModel(model);
					}
				}
			});
		}
		return btnRemoveProcedure;
	}
	private JTextArea getTxtAreaProcedureDescription() {
		if (txtAreaProcedureDescription == null) {
			txtAreaProcedureDescription = new JTextArea();
			txtAreaProcedureDescription.setEditable(false);
		}
		return txtAreaProcedureDescription;
	}
	private JLabel getLblNoProcedureResults() {
		if (lblNoProcedureResults == null) {
			lblNoProcedureResults = new JLabel("No procedures found for this code!");
			lblNoProcedureResults.setForeground(Color.RED);
			lblNoProcedureResults.setBounds(345, 73, 234, 14);
			lblNoProcedureResults.setVisible(false);
		}
		return lblNoProcedureResults;
	}
	private JScrollPane getScrollPane_12() {
		if (scrollPane_12 == null) {
			scrollPane_12 = new JScrollPane();
			scrollPane_12.setBounds(345, 98, 282, 85);
			scrollPane_12.setViewportView(getTxtAreaProcedureDescription());
		}
		return scrollPane_12;
	}
	private JTextField getTxtFieldSearchProcedure() {
		if (txtFieldSearchProcedure == null) {
			txtFieldSearchProcedure = new JTextField();
			txtFieldSearchProcedure.setBounds(345, 35, 114, 30);
			txtFieldSearchProcedure.setColumns(10);
		}
		return txtFieldSearchProcedure;
	}
	private JButton getBtnSearchProcedure() {
		if (btnSearchProcedure == null) {
			btnSearchProcedure = new JButton("Search");
			btnSearchProcedure.setBounds(494, 39, 89, 23);
			btnSearchProcedure.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// get the procedure code from the txt field
					String code = getTxtFieldSearchProcedure().getText();
					// search for the diagnosis in the db
					
					Procedure search = ConnectionFactory.getProcedure(code);
					// if there is a result show it
					if (search.code != null) {
						// remove warning 
						getLblNoProcedureResults().setVisible(false);
						// show it in the description area
						getTxtAreaProcedureDescription().setText(search.code + "\n"
                				+ search.description);
					}
					// else show label "no results for this search"
					else {
						getLblNoProcedureResults().setVisible(true);
						getTxtAreaProcedureDescription().setText("");
					}					
				}
			});
		}
		return btnSearchProcedure;
	}
	private JLabel getLblSearchProcedure() {
		if (lblSearchProcedure == null) {
			lblSearchProcedure = new JLabel("Search procedure:");
			lblSearchProcedure.setBounds(345, 13, 131, 14);
		}
		return lblSearchProcedure;
	}
	private JPanel getPnAtenzzioneDiagnosis() {
		if (pnAtenzzioneDiagnosis == null) {
			pnAtenzzioneDiagnosis = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnAtenzzioneDiagnosis.getLayout();
			flowLayout.setAlignment(FlowLayout.LEADING);
			pnAtenzzioneDiagnosis.add(getBtnAtenzzione());
		}
		return pnAtenzzioneDiagnosis;
	}
	private JButton getBtnAtenzzione() {
		if (btnAtenzzione == null) {
			btnAtenzzione = new JButton("Report dangerous diagnosis");
			btnAtenzzione.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// nombredelaventana frame = new nombredelaventana()
					// frame.setvisible(true)
					// frame.setlocationrelativeto(null)  
				}
			});
			btnAtenzzione.setPreferredSize(new Dimension(167, 27));
			btnAtenzzione.setBorder(new CompoundBorder(null, new LineBorder(new Color(255, 255, 153))));
		}
		return btnAtenzzione;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnButtons.getLayout();
			flowLayout.setHgap(10);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtnExit());
			pnButtons.add(getBtnSave());
		}
		return pnButtons;
	}
}
