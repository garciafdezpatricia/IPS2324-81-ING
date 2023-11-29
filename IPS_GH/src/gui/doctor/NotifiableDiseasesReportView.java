package gui.doctor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import util.AppointmentBLDto;
import util.ConnectionFactory;
import util.FileUtil;

public class NotifiableDiseasesReportView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelPatientInfo;
	private JLabel lblPatientName;
	private JLabel lblPatientSurname;
	private JLabel lblContactInformation;
	private JTextArea txtContactInformation;
	private JLabel lblGender;
	private JPanel panelGender;
	private JRadioButton rdBtnFemale;
	private JRadioButton rdBtnMale;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblAddress;
	private JTextField txtAddress;
	private JPanel panelSouth;
	private JLabel lblOccupation;
	private JTextField txtOccupation;
	private JPanel panelPatientConsent;
	private JRadioButton rdBtnPatientConsent;
	private JPanel panelAdditionalInfo;
	private JLabel lblNotes;
	private JTextField txtNotes;
	private JPanel panelButtons;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnSend;
	private JPanel panelCenter;
	private JPanel panelClinicalInfo;
	private JLabel lblSymptomsPresent;
	private JTextField txtSymptoms;
	private JLabel lblTreatment;
	private JTextField txtTreatment;
	private JPanel panelVaccinationStatus;
	private JPanel panelDose;
	private JPanel panelDiseaseInfo;
	private JLabel lblNameOfDisease;
	private JLabel lblDateOfDiagnosis;
	private JTextField txtDateDiagnosis;
	private JLabel lblDateOfSymptoms;
	private JTextField txtDateSymptoms;
	private JTextArea txtPatientSurname;

	private AppointmentBLDto ap;
	private JTextArea txtPatientName;

	private String patientName;
	private String patientSurname;
	private JPanel panelSympthoms;
	private JLabel lblTypeOfDiagnosis;
	private JRadioButton rdBtnClinicalSuspicion;
	private JRadioButton rdBtnAnalyticalConfirmation;
	private JLabel lblRequestedLabTests;
	private JTextField txtRequestedLabTests;
	private JLabel lblNewLabel;
	private JLabel lblVaccinationStatus;
	private JRadioButton rdBtnComplete;
	private JRadioButton rdBtnIncomplete;
	private JRadioButton rdBtnNotVaccinated;
	private JRadioButton rdBtnNotProvided;
	private JLabel lblDateLastDose;
	private JLabel lblNumOfDose;
	private JTextField txtNumOfDoses;
	private JTextField txtDateOfLastDose;
	private JPanel panel1;
	private JPanel panelEmpty;
	private JComboBox<String> comboBoxDiseases;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotifiableDiseasesReportView frame = new NotifiableDiseasesReportView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NotifiableDiseasesReportView() {
		setResizable(false);
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

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(NotifiableDiseasesReportView.class.getResource("/img/descarga.jpg")));
		setTitle("Notifiable diseases report");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 823);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelPatientInfo(), BorderLayout.NORTH);
		contentPane.add(getPanelSouth(), BorderLayout.SOUTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	/**
	 * Create the frame.
	 */
	public NotifiableDiseasesReportView(AppointmentBLDto a) {
		this.ap = a;
		try {
			this.patientName = ConnectionFactory.getPatientName(BigInteger.valueOf(ap.getPatientid()));
			this.patientSurname = ConnectionFactory.getPatientSurname(BigInteger.valueOf(ap.getPatientid()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(NotifiableDiseasesReportView.class.getResource("/img/descarga.jpg")));
		setTitle("Notifiable diseases report");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 667);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelPatientInfo(), BorderLayout.NORTH);
		contentPane.add(getPanelSouth(), BorderLayout.SOUTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	private JPanel getPanelPatientInfo() {
		if (panelPatientInfo == null) {
			panelPatientInfo = new JPanel();
			panelPatientInfo.setBorder(
					new TitledBorder(null, "Patient information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelPatientInfo.setLayout(new GridLayout(0, 2, 0, 0));
			panelPatientInfo.add(getLblPatientName());
			panelPatientInfo.add(getTxtPatientName());
			panelPatientInfo.add(getLblPatientSurname());
			panelPatientInfo.add(getTxtPatientSurname());
			panelPatientInfo.add(getLblGender());
			panelPatientInfo.add(getPanelGender());
			panelPatientInfo.add(getLblAddress());
			panelPatientInfo.add(getTxtAddress());
			panelPatientInfo.add(getLblOccupation());
			panelPatientInfo.add(getTxtOccupation());
			panelPatientInfo.add(getLblContactInformation());
			panelPatientInfo.add(getTxtContactInformation());
		}
		return panelPatientInfo;
	}

	private JLabel getLblPatientName() {
		if (lblPatientName == null) {
			lblPatientName = new JLabel("Name:");
		}
		return lblPatientName;
	}

	private JLabel getLblPatientSurname() {
		if (lblPatientSurname == null) {
			lblPatientSurname = new JLabel("Surname:");
		}
		return lblPatientSurname;
	}

	private JLabel getLblContactInformation() {
		if (lblContactInformation == null) {
			lblContactInformation = new JLabel("Contact information:");
		}
		return lblContactInformation;
	}

	private JTextArea getTxtContactInformation() {
		if (txtContactInformation == null) {
			txtContactInformation = new JTextArea();
			txtContactInformation.setEditable(false);
			try {
				txtContactInformation.setText(ap.getInformation());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return txtContactInformation;
	}

	private JLabel getLblGender() {
		if (lblGender == null) {
			lblGender = new JLabel("Gender:");
		}
		return lblGender;
	}

	private JPanel getPanelGender() {
		if (panelGender == null) {
			panelGender = new JPanel();
			panelGender.setLayout(new GridLayout(0, 2, 0, 0));
			panelGender.add(getRdBtnFemale());
			panelGender.add(getRdBtnMale());
		}
		return panelGender;
	}

	private JRadioButton getRdBtnFemale() {
		if (rdBtnFemale == null) {
			rdBtnFemale = new JRadioButton("Female");
			buttonGroup.add(rdBtnFemale);
		}
		return rdBtnFemale;
	}

	private JRadioButton getRdBtnMale() {
		if (rdBtnMale == null) {
			rdBtnMale = new JRadioButton("Male");
			buttonGroup.add(rdBtnMale);
		}
		return rdBtnMale;
	}

	private JLabel getLblAddress() {
		if (lblAddress == null) {
			lblAddress = new JLabel("Address:");
		}
		return lblAddress;
	}

	private JTextField getTxtAddress() {
		if (txtAddress == null) {
			txtAddress = new JTextField();
			txtAddress.setColumns(10);
		}
		return txtAddress;
	}

	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setBorder(null);
			panelSouth.setLayout(new GridLayout(0, 1, 0, 0));
			panelSouth.add(getPanelSympthoms());
			panelSouth.add(getPanelPatientConsent());
			panelSouth.add(getPanelAdditionalInfo());
			panelSouth.add(getPanelButtons());
		}
		return panelSouth;
	}

	private JLabel getLblOccupation() {
		if (lblOccupation == null) {
			lblOccupation = new JLabel("Occupation:");
		}
		return lblOccupation;
	}

	private JTextField getTxtOccupation() {
		if (txtOccupation == null) {
			txtOccupation = new JTextField();
			txtOccupation.setColumns(10);
		}
		return txtOccupation;
	}

	private JPanel getPanelPatientConsent() {
		if (panelPatientConsent == null) {
			panelPatientConsent = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelPatientConsent.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelPatientConsent.add(getRdBtnPatientConsent());
		}
		return panelPatientConsent;
	}

	private JRadioButton getRdBtnPatientConsent() {
		if (rdBtnPatientConsent == null) {
			rdBtnPatientConsent = new JRadioButton(
					"Patient consent for reporting the disease to public health authorities");
			rdBtnPatientConsent.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return rdBtnPatientConsent;
	}

	private JPanel getPanelAdditionalInfo() {
		if (panelAdditionalInfo == null) {
			panelAdditionalInfo = new JPanel();
			panelAdditionalInfo.setBorder(new TitledBorder(null, "Additional information (optional)",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelAdditionalInfo.setLayout(new GridLayout(0, 1, 0, 0));
			panelAdditionalInfo.add(getLblNotes());
			panelAdditionalInfo.add(getTxtNotes());
		}
		return panelAdditionalInfo;
	}

	private JLabel getLblNotes() {
		if (lblNotes == null) {
			lblNotes = new JLabel("Notes:");
		}
		return lblNotes;
	}

	private JTextField getTxtNotes() {
		if (txtNotes == null) {
			txtNotes = new JTextField();
			txtNotes.setColumns(10);
		}
		return txtNotes;
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelButtons.add(getBtnCancel());
			panelButtons.add(getBtnSave());
			panelButtons.add(getBtnSend());
		}
		return panelButtons;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save report");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generatePDF();
				}
			});
		}
		return btnSave;
	}

	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton("Send report");
		}
		return btnSend;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getPanelDiseaseInfo_1());
			panelCenter.add(getPanelClinicalInfo_1());
		}
		return panelCenter;
	}

	private JPanel getPanelClinicalInfo_1() {
		if (panelClinicalInfo == null) {
			panelClinicalInfo = new JPanel();
			panelClinicalInfo.setBorder(
					new TitledBorder(null, "Clinical information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelClinicalInfo.setLayout(new GridLayout(0, 2, 0, 0));
			panelClinicalInfo.add(getPanel1());
			panelClinicalInfo.add(getPanelEmpty());
			panelClinicalInfo.add(getPanelVaccinationStatus());
			panelClinicalInfo.add(getPanelEmpty_1());
		}
		return panelClinicalInfo;
	}

	private JLabel getLblSymptomsPresent_1() {
		if (lblSymptomsPresent == null) {
			lblSymptomsPresent = new JLabel("Symptoms present:");
		}
		return lblSymptomsPresent;
	}

	private JTextField getTxtSymptoms() {
		if (txtSymptoms == null) {
			txtSymptoms = new JTextField();
			txtSymptoms.setColumns(10);
		}
		return txtSymptoms;
	}

	private JLabel getLblTreatment_1() {
		if (lblTreatment == null) {
			lblTreatment = new JLabel("Treatment given:");
		}
		return lblTreatment;
	}

	private JTextField getTxtTreatment() {
		if (txtTreatment == null) {
			txtTreatment = new JTextField();
			txtTreatment.setColumns(10);
		}
		return txtTreatment;
	}

	private JPanel getPanelVaccinationStatus() {
		if (panelVaccinationStatus == null) {
			panelVaccinationStatus = new JPanel();
			panelVaccinationStatus.setLayout(new GridLayout(0, 1, 0, 0));
			panelVaccinationStatus.add(getLblVaccinationStatus());
			panelVaccinationStatus.add(getRdBtnComplete());
			panelVaccinationStatus.add(getRdBtnIncomplete());
			panelVaccinationStatus.add(getRdBtnNotVaccinated());
			panelVaccinationStatus.add(getRdBtnNotProvided());
		}
		return panelVaccinationStatus;
	}

	private JPanel getPanelEmpty_1() {
		if (panelDose == null) {
			panelDose = new JPanel();
			panelDose.setLayout(new GridLayout(0, 2, 0, 0));
			panelDose.add(getLblNumOfDose());
			panelDose.add(getTxtNumOfDoses());
			panelDose.add(getLblDateLastDose());
			panelDose.add(getTxtDateOfLastDose());
		}
		return panelDose;
	}

	private JPanel getPanelDiseaseInfo_1() {
		if (panelDiseaseInfo == null) {
			panelDiseaseInfo = new JPanel();
			panelDiseaseInfo.setBorder(
					new TitledBorder(null, "Disease information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDiseaseInfo.setLayout(new GridLayout(0, 2, 0, 0));
			panelDiseaseInfo.add(getLblNameOfDisease_1());
			panelDiseaseInfo.add(getComboBoxDiseases());
			panelDiseaseInfo.add(getLblDateOfDiagnosis_1());
			panelDiseaseInfo.add(getTxtDateDiagnosis());
			panelDiseaseInfo.add(getLblDateOfSymptoms_1());
			panelDiseaseInfo.add(getTxtDateSymptoms());
		}
		return panelDiseaseInfo;
	}

	private JLabel getLblNameOfDisease_1() {
		if (lblNameOfDisease == null) {
			lblNameOfDisease = new JLabel("Name of the disease:");
		}
		return lblNameOfDisease;
	}

	private JLabel getLblDateOfDiagnosis_1() {
		if (lblDateOfDiagnosis == null) {
			lblDateOfDiagnosis = new JLabel("Date of diagnosis:");
		}
		return lblDateOfDiagnosis;
	}

	private JTextField getTxtDateDiagnosis() {
		if (txtDateDiagnosis == null) {
			txtDateDiagnosis = new JTextField();
			txtDateDiagnosis.setColumns(10);
		}
		return txtDateDiagnosis;
	}

	private JLabel getLblDateOfSymptoms_1() {
		if (lblDateOfSymptoms == null) {
			lblDateOfSymptoms = new JLabel("Date of onset of symptoms:");
		}
		return lblDateOfSymptoms;
	}

	private JTextField getTxtDateSymptoms() {
		if (txtDateSymptoms == null) {
			txtDateSymptoms = new JTextField();
			txtDateSymptoms.setColumns(10);
		}
		return txtDateSymptoms;
	}

	private JTextArea getTxtPatientSurname() {
		if (txtPatientSurname == null) {
			txtPatientSurname = new JTextArea();
			txtPatientSurname.setEditable(false);
			try {
				txtPatientSurname.setText(patientSurname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return txtPatientSurname;
	}

	private JTextArea getTxtPatientName() {
		if (txtPatientName == null) {
			txtPatientName = new JTextArea();
			txtPatientName.setEditable(false);
			try {
				txtPatientName.setText(patientName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return txtPatientName;
	}

	private JPanel getPanelSympthoms() {
		if (panelSympthoms == null) {
			panelSympthoms = new JPanel();
			panelSympthoms
					.setBorder(new TitledBorder(null, "Sympthoms", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelSympthoms.setLayout(new GridLayout(0, 3, 0, 0));
			panelSympthoms.add(getLblTypeOfDiagnosis());
			panelSympthoms.add(getRdBtnClinicalSuspicion());
			panelSympthoms.add(getRdBtnAnalyticalConfirmation());
			panelSympthoms.add(getLblRequestedLabTests());
			panelSympthoms.add(getTxtRequestedLabTests());
			panelSympthoms.add(getLblNewLabel());
		}
		return panelSympthoms;
	}

	private JLabel getLblTypeOfDiagnosis() {
		if (lblTypeOfDiagnosis == null) {
			lblTypeOfDiagnosis = new JLabel("Type of diagnosis:");
		}
		return lblTypeOfDiagnosis;
	}

	private JRadioButton getRdBtnClinicalSuspicion() {
		if (rdBtnClinicalSuspicion == null) {
			rdBtnClinicalSuspicion = new JRadioButton("Clinical suspicion");
		}
		return rdBtnClinicalSuspicion;
	}

	private JRadioButton getRdBtnAnalyticalConfirmation() {
		if (rdBtnAnalyticalConfirmation == null) {
			rdBtnAnalyticalConfirmation = new JRadioButton("Analytical confirmation");
		}
		return rdBtnAnalyticalConfirmation;
	}

	private JLabel getLblRequestedLabTests() {
		if (lblRequestedLabTests == null) {
			lblRequestedLabTests = new JLabel("Requested lab tests:");
		}
		return lblRequestedLabTests;
	}

	private JTextField getTxtRequestedLabTests() {
		if (txtRequestedLabTests == null) {
			txtRequestedLabTests = new JTextField();
			txtRequestedLabTests.setColumns(10);
		}
		return txtRequestedLabTests;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
		}
		return lblNewLabel;
	}

	private JLabel getLblVaccinationStatus() {
		if (lblVaccinationStatus == null) {
			lblVaccinationStatus = new JLabel("Vaccination status:");
		}
		return lblVaccinationStatus;
	}

	private JRadioButton getRdBtnComplete() {
		if (rdBtnComplete == null) {
			rdBtnComplete = new JRadioButton("Complete");
		}
		return rdBtnComplete;
	}

	private JRadioButton getRdBtnIncomplete() {
		if (rdBtnIncomplete == null) {
			rdBtnIncomplete = new JRadioButton("Incomplete");
		}
		return rdBtnIncomplete;
	}

	private JRadioButton getRdBtnNotVaccinated() {
		if (rdBtnNotVaccinated == null) {
			rdBtnNotVaccinated = new JRadioButton("Not vaccinated");
		}
		return rdBtnNotVaccinated;
	}

	private JRadioButton getRdBtnNotProvided() {
		if (rdBtnNotProvided == null) {
			rdBtnNotProvided = new JRadioButton("Not provided");
		}
		return rdBtnNotProvided;
	}

	private JLabel getLblDateLastDose() {
		if (lblDateLastDose == null) {
			lblDateLastDose = new JLabel("Date of the last dose:");
		}
		return lblDateLastDose;
	}

	private JLabel getLblNumOfDose() {
		if (lblNumOfDose == null) {
			lblNumOfDose = new JLabel("Number of doses:");
		}
		return lblNumOfDose;
	}

	private JTextField getTxtNumOfDoses() {
		if (txtNumOfDoses == null) {
			txtNumOfDoses = new JTextField();
			txtNumOfDoses.setColumns(10);
		}
		return txtNumOfDoses;
	}

	private JTextField getTxtDateOfLastDose() {
		if (txtDateOfLastDose == null) {
			txtDateOfLastDose = new JTextField();
			txtDateOfLastDose.setColumns(10);
		}
		return txtDateOfLastDose;
	}

	private JPanel getPanel1() {
		if (panel1 == null) {
			panel1 = new JPanel();
			panel1.setLayout(new GridLayout(0, 2, 0, 0));
			panel1.add(getLblSymptomsPresent_1());
			panel1.add(getTxtSymptoms());
			panel1.add(getLblTreatment_1());
			panel1.add(getTxtTreatment());
		}
		return panel1;
	}

	private JPanel getPanelEmpty() {
		if (panelEmpty == null) {
			panelEmpty = new JPanel();
		}
		return panelEmpty;
	}

	private JComboBox<String> getComboBoxDiseases() {
		if (comboBoxDiseases == null) {
			comboBoxDiseases = new JComboBox<String>();
			comboBoxDiseases.setModel(new DefaultComboBoxModel<String>(diseasesToArray()));
		}
		return comboBoxDiseases;
	}

	private String[] diseasesToArray() {
		String[] lines = new String[0];

		try {
			lines = Files.readAllLines(Path.of("files/diseases.txt")).toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	private void generatePDF() {
		String aux = "";
		String result = "NOTIFIABLE DISEASE REPORT\n\n";

		// PATIENT INFORMATION
		result += "   · Patient information\n";
		result += "Patient name:\t" + patientName + "\n";
		result += "Patient surname:\t" + patientSurname + "\n";

		result += "Gender: ";
		aux = getRdBtnFemale().isSelected() ? "Female" : "Male";
		result += aux + "\n";

		result += "Address:\t" + getTxtAddress().getText() + "\n";
		result += "Occupation:\t" + getTxtOccupation().getText() + "\n";
		result += "Contact information:\t" + ap.getInformation() + "\n\n";
		result += "\n\n";

		// DISEASE INFORMATION
		result += "   · Disease information\n";
		result += "Disease name:\t" + getComboBoxDiseases().getSelectedItem().toString() + "\n";
		result += "Diagnosis date:\t" + getTxtDateDiagnosis().getText() + "\n";
		result += "Date of onset of symptoms:\t" + getTxtDateSymptoms().getText() + "\n";
		result += "Symptoms:\t" + getTxtSymptoms().getText() + "\n";
		result += "Treatment given:\t" + getTxtTreatment().getText() + "\n";

		result += "Vaccination status:\t";
		if (getRdBtnComplete().isSelected())
			aux = "Complete";
		else if (getRdBtnIncomplete().isSelected())
			aux = "Incomplete";
		if (getRdBtnComplete().isSelected())
			aux = "Complete";
		else if (getRdBtnNotVaccinated().isSelected())
			aux = "Not vaccinated";
		else if (getRdBtnNotProvided().isSelected())
			aux = "Not provided";
		result += aux + "\n";

		result += "Number of doses:\t" + getTxtNumOfDoses() + "\n";
		result += "Date of the last dose:\t" + getTxtDateOfLastDose() + "\n";
		result += "\n\n";

		
		// SYMPTOMS
		result += "   · Symptoms information\n";
		result += "Type of diagnosis::\t";
		if (getRdBtnClinicalSuspicion().isSelected())
			aux = "Clinical suspicion";
		else if (getRdBtnAnalyticalConfirmation().isSelected())
			aux = "Analytical confirmation";
		
		result += aux + "\n";
		result += "\n\n";

		// CONSENT
		result += "   · Patient consent\n";
		result += "Consent for reporting the disease to public health authorities:\t" + "\n";
		aux = getRdBtnPatientConsent().isSelected() ? "Yes" : "No";
		result += "\n\n";

		// CONSENT
		result += "   · Notes\n";
		result += "Additional notes:\t" + getTxtNotes().getText() + "\n";

		FileUtil.saveToFile("result", result);
	}

}
