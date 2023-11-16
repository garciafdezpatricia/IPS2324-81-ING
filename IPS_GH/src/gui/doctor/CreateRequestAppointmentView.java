package gui.doctor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import db.Doctor;
import db.Office;
import db.Patient;
import util.ConnectionFactory;

public class CreateRequestAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_title;
	private JPanel panelGeneral;
	private JLabel lblTitle;
	private JPanel panel_doctor;
	private JPanel panel_patient;
	private JPanel panel_office;
	private JPanel panel_information;
	private JPanel panelSouth;
	private JPanel panel_doctor_filter;
	private JPanel panel_patient_filter;
	private JLabel lblIPatientNam;
	private JTextField textFieldNamePatient;
	private JScrollPane scrollPane_patients;
	private JList list_patients;
	private JScrollPane scrollPaneDoctor;
	private JLabel lblSSNumber;
	private JTextField textFieldSSNumber;
	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();
	private DefaultListModel<Doctor> doctorsReset = new DefaultListModel<>();
	private DefaultListModel<Patient> patients = new DefaultListModel<>();
	private DefaultListModel<Patient> patientsReset = new DefaultListModel<>();

	private JPanel panel_office_north;
	private JLabel lblChooseOffice;
	private JComboBox<String> comboBoxOffices;

	private boolean doctorChoosed = false;
	private boolean patientChoosed = false;
	private boolean officeChoosed = false;

	private boolean dateChoosed = false;

	private SelectDateRequestApp selectDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateRequestAppointmentView frame = new CreateRequestAppointmentView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null); // centrar pantalla

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static final String url = "jdbc:oracle:thin:@";
	public static final String usuario = "Admin";
	public static final String contraseña = "LyQmZ7HwG4edJ2";
	private JPanel panelNameAndNumber;
	private JLabel lblTypeDoctor;
	private JTextField textNameDoctor;
	private JLabel lblRegistrationNumber;
	private JTextField textRegNumber;
	private JButton btnFilterName;
	private JButton btnRegNumber;
	private JButton btnFilterPatientName;
	private JButton btnFilterSS;
	private JPanel panelPatientsReset;
	private JButton btnResetPatients;
	private JTextField textField;
	private JList<Doctor> listDoctor;
	private JPanel panelSur;
	private JPanel panelSurDoctor;
	private JRadioButton rdbtnUrgent;
	private JButton btnReset;
	private JDateChooser dateChooser;
	private JTextArea txtContactInfo;
	private JPanel panelSouthPatient;
	private JButton btnEdit;
	private JButton btnSave;
	private String newContactInfo = "";
	private JTextField textFieldFrom;
	private JTextField textFieldTo;

	private JPanel panel_patient_center;
	private JPanel panel_doctor_Center;
	private JLabel lblsurname;
	private JTextField textFieldSurname;
	private JButton btnFilterSurname;
	private JLabel lblSurnameDoctor;
	private JTextField textFieldSurnameDoctor;
	private JButton btnSurnameDoctor;
	private JLabel lblDNI;
	private JTextField textFieldDni;
	private JButton btnDNI;
	private JScrollPane scrollPane;
	private JPanel panelDoctorAvailability;
	private JPanel panelOfficeAvailability;
	private JScrollPane scrollPaneDoctorAvailability;
	private JTextArea textAreaDoctorAvailability;
	private JPanel panelOfficeButtons;
	private JPanel panelPrevAndNext;
	private JButton btnPrev;
	private JButton btnNext;
	private JPanel panelHours;
	private JLabel lblFrom;
	private JTextField textFieldFromH;
	private JLabel lblTo;
	private JTextField textFieldToH;
	private JScrollPane scrollPaneOfficeAvailability;
	private JTextArea textAreaOfficeAvailability;
	private JPanel panelComments;
	private JTextField txtComments;
	private JPanel panelSouthButtons;
	private JButton btnSendRequest;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public CreateRequestAppointmentView() throws Exception {

		setTitle("Create request of appointment");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(CreateRequestAppointmentView.class.getResource("/img/descarga.jpg")));
		doctors = ConnectionFactory.getDoctors();
		doctorsReset = ConnectionFactory.getDoctors();

		patients = ConnectionFactory.getPatients();
		patientsReset = ConnectionFactory.getPatients();

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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1271, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_title(), BorderLayout.NORTH);
		contentPane.add(getPanelGeneral(), BorderLayout.CENTER);
		contentPane.add(getPanelSouth(), BorderLayout.SOUTH);

		// doctors
		panel_doctor_Center.add(scrollPaneDoctor, BorderLayout.CENTER);

		// patient
		panel_patient_center.add(scrollPane_patients, BorderLayout.CENTER);

	}

	private List<Doctor> getSelectedDoctors() {

		List<Doctor> res = new ArrayList<>();
		for (int i = 0; i < getListDoctor().getSelectedValuesList().size(); i++) {
			res.add((Doctor) (getListDoctor().getSelectedValuesList().get(i)));
		}
		return res;
	}

	private JPanel getPanel_title() {
		if (panel_title == null) {
			panel_title = new JPanel();
			panel_title.add(getLblTitle());
		}
		return panel_title;
	}

	private JPanel getPanelGeneral() {
		if (panelGeneral == null) {
			panelGeneral = new JPanel();
			panelGeneral.setLayout(new GridLayout(0, 3, 0, 0));
			panelGeneral.add(getPanel_doctor());
			panelGeneral.add(getPanel_patient());
			panelGeneral.add(getPanelDoctorAvailability());
			panelGeneral.add(getPanel_office());
			panelGeneral.add(getPanel_information());
			panelGeneral.add(getPanelOfficeAvailability());
		}
		return panelGeneral;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Request of appointment");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblTitle;
	}

	private JPanel getPanel_doctor() {
		if (panel_doctor == null) {
			panel_doctor = new JPanel();
			panel_doctor
					.setBorder(new TitledBorder(null, "Doctor ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_doctor.setLayout(new BorderLayout(0, 0));
			panel_doctor.add(getPanelSur(), BorderLayout.SOUTH);
			panel_doctor.add(getPanel_doctor_Center(), BorderLayout.CENTER);
		}
		return panel_doctor;
	}

	private JPanel getPanel_patient() {
		if (panel_patient == null) {
			panel_patient = new JPanel();
			panel_patient.setBorder(
					new TitledBorder(null, "Patient Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_patient.setLayout(new BorderLayout(0, 0));
			panel_patient.add(getPanelPatientsReset(), BorderLayout.SOUTH);
			panel_patient.add(getPanel_patient_center(), BorderLayout.CENTER);
		}
		return panel_patient;
	}

	private JPanel getPanel_office() {
		if (panel_office == null) {
			panel_office = new JPanel();
			panel_office.setLayout(new BorderLayout(0, 0));
			panel_office
					.setBorder(new TitledBorder(null, "Office ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_office.add(getPanel_office_north(), BorderLayout.NORTH);
			panel_office.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel_office;
	}

	private JPanel getPanel_information() {
		if (panel_information == null) {
			panel_information = new JPanel();
			panel_information.setBorder(
					new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_information.setLayout(new BorderLayout(0, 0));
			panel_information.add(getTxtContactInfo(), BorderLayout.CENTER);
			panel_information.add(getPanelSouthPatient(), BorderLayout.SOUTH);
		}
		return panel_information;
	}

	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout(0, 0));
			panelSouth.add(getPanelComments(), BorderLayout.CENTER);
			panelSouth.add(getPanelSouthButtons(), BorderLayout.SOUTH);
		}
		return panelSouth;
	}

	private void areYouSureJOP() throws Exception {
		int opcion = JOptionPane.showConfirmDialog(CreateRequestAppointmentView.this,
				"Are you sure you want to send the request?", "Confirmation", JOptionPane.YES_NO_OPTION);

		// Verificar la respuesta del usuario
		if (opcion == JOptionPane.YES_OPTION) {
			// El usuario ha confirmado, realiza la acción
			// Puedes poner aquí el código que quieras ejecutar después de la confirmación
			System.out.println("Request sent.");
			if (rdbtnUrgent.isSelected()) {
				for (int j = 0; j < listDoctor.getSelectedValuesList().size(); j++) {
					sendEmail(((Doctor) listDoctor.getSelectedValuesList().get(j)).getEmail());
				}
			}
			Patient p = (Patient) list_patients.getSelectedValue();
			if (rdbtnUrgent.isSelected()) {
				ConnectionFactory.createAppointment(p.getId(), listDoctor.getSelectedValue().getId(),
						new java.sql.Date(getDateChooser().getDate().getTime()) + " " + getTextFieldFromH().getText()
								+ ":00",
						new java.sql.Date(getDateChooser().getDate().getTime()) + " " + getTextFieldToH().getText()
								+ ":00",
						1, ConnectionFactory.officeIdFrom(getComboBoxOffices().getSelectedItem().toString()),
						newContactInfo);

			} else {
				ConnectionFactory.createAppointment(p.getId(), listDoctor.getSelectedValue().getId(),
						new java.sql.Date(getDateChooser().getDate().getTime()) + " " + getTextFieldFromH().getText()
								+ ":00",
						new java.sql.Date(getDateChooser().getDate().getTime()) + " " + getTextFieldToH().getText()
								+ ":00",
						0, ConnectionFactory.officeIdFrom(getComboBoxOffices().getSelectedItem().toString()),
						newContactInfo);
			}
//			getTextAreaDoctorAvailability().removeAll();
//			try {
//				getTextAreaDoctorAvailability().setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
//						new java.sql.Date(dateChooser.getDate().getTime())));
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//			getTextAreaOfficeAvailability().removeAll();
			
			showFreeHours(dateChooser.getDate());
		} else {
			// El usuario ha cancelado la acción
			System.out.println("Acción cancelada.");
		}

	}

	// TODO: poner más datos
	private void sendEmail(String destinatario) {
		String asunto = "Urgent appointment";
		String mensaje = "You have a new urgent appointment with the patient " + list_patients.getSelectedValue();

		// Configurar propiedades para la conexión SMTP
		Properties propiedades = new Properties();
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port", "587");
		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "true");

		// Crear una sesión de correo electrónico
		Session sesion = Session.getInstance(propiedades, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ips232481@gmail.com", "tojp oyjw kamn xxmy");
			}
		});

		try {
			// Crear un mensaje de correo electrónico
			MimeMessage mensajeCorreo = new MimeMessage(sesion);
			mensajeCorreo.setFrom(new InternetAddress("tu-correo@gmail.com"));
			mensajeCorreo.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mensajeCorreo.setSubject(asunto);
			mensajeCorreo.setText(mensaje);

			// Enviar el correo electrónico
			Transport.send(mensajeCorreo);
			System.out.println("Correo enviado con éxito.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private JPanel getPanel_patient_filter() {
		if (panel_patient_filter == null) {
			panel_patient_filter = new JPanel();
			panel_patient_filter
					.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_patient_filter.setLayout(new GridLayout(0, 3, 0, 0));
			panel_patient_filter.add(getLblIPatientNam());
			panel_patient_filter.add(getTextFieldNamePatient());
			panel_patient_filter.add(getBtnFilterPatientName());
			panel_patient_filter.add(getLblSSNumber());
			panel_patient_filter.add(getTextFieldSSNumber());
			panel_patient_filter.add(getBtnFilterSS());
			panel_patient_filter.add(getLblsurname());
			panel_patient_filter.add(getTextFieldSurname());
			panel_patient_filter.add(getBtnFilterSurname());
			panel_patient_filter.add(getLblDNI());
			panel_patient_filter.add(getTextFieldDni());
			panel_patient_filter.add(getBtnDNI());
		}
		return panel_patient_filter;
	}

	private JLabel getLblIPatientNam() {
		if (lblIPatientNam == null) {
			lblIPatientNam = new JLabel("By name:");
			lblIPatientNam.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblIPatientNam;
	}

	private JTextField getTextFieldNamePatient() {
		if (textFieldNamePatient == null) {
			textFieldNamePatient = new JTextField();
			textFieldNamePatient.setColumns(10);
		}
		return textFieldNamePatient;
	}

	private JScrollPane getScrollPane_patients() {
		if (scrollPane_patients == null) {

			scrollPane_patients = new JScrollPane(list_patients);
			scrollPane_patients.setViewportView(getList_patients());
		}
		return scrollPane_patients;
	}

	private JList getList_patients() {
		if (list_patients == null) {
			list_patients = new JList<>(patients); // Asegúrate de especificar el tipo de elemento en la JList
			list_patients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_patients.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {

					Patient p = (Patient) getList_patients().getSelectedValue();
					if (p != null) {
						getTxtContactInfo().setText(p.getContactInfo());
						getBtnEdit().setEnabled(true);

						patientChoosed = true;

						checkSendRequestBtnEnabled();

						newContactInfo = p.getContactInfo();
					} else {
						getTxtContactInfo().setText("");

						patientChoosed = false;
					}
				}

			});
		}
		return list_patients;
	}

	public JList getListDoctor() {
		if (listDoctor == null) {
			listDoctor = new JList<>(doctors);
			listDoctor.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					getTextAreaDoctorAvailability().removeAll();
					try {
						getTextAreaDoctorAvailability().setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (!listDoctor.getSelectedValuesList().isEmpty()) {
						doctorChoosed = true;

					} else {
						doctorChoosed = false;
					}

				}
			});
		}
		return listDoctor;
	}

	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane(listDoctor);
			scrollPaneDoctor.setViewportView(getListDoctor());
		}
		return scrollPaneDoctor;
	}

	private JLabel getLblSSNumber() {
		if (lblSSNumber == null) {
			lblSSNumber = new JLabel("By social security number:");
			lblSSNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblSSNumber;
	}

	private JTextField getTextFieldSSNumber() {
		if (textFieldSSNumber == null) {
			textFieldSSNumber = new JTextField();
			textFieldSSNumber.setColumns(10);
		}
		return textFieldSSNumber;
	}

	private JPanel getPanelNameAndNumber() {
		if (panelNameAndNumber == null) {
			panelNameAndNumber = new JPanel();
			panelNameAndNumber
					.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelNameAndNumber.setLayout(new GridLayout(0, 3, 0, 0));
			panelNameAndNumber.add(getLblTypeDoctor_1());
			panelNameAndNumber.add(getTextNameDoctor());
			panelNameAndNumber.add(getBtnFilterName());
			panelNameAndNumber.add(getLblSurnameDoctor());
			panelNameAndNumber.add(getTextFieldSurnameDoctor());
			panelNameAndNumber.add(getBtnSurnameDoctor());
			panelNameAndNumber.add(getLblRegistrationNumber_1());
			panelNameAndNumber.add(getTextRegNumber());
			panelNameAndNumber.add(getBtnRegNumber());
		}
		return panelNameAndNumber;
	}

	private JLabel getLblTypeDoctor_1() {
		if (lblTypeDoctor == null) {
			lblTypeDoctor = new JLabel("By name:");
			lblTypeDoctor.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblTypeDoctor;
	}

	private JTextField getTextNameDoctor() {
		if (textNameDoctor == null) {
			textNameDoctor = new JTextField();
			textNameDoctor.setColumns(10);
		}
		return textNameDoctor;
	}

	private JLabel getLblRegistrationNumber_1() {
		if (lblRegistrationNumber == null) {
			lblRegistrationNumber = new JLabel("By doctor's registration number:");
			lblRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblRegistrationNumber;
	}

	private JTextField getTextRegNumber() {
		if (textRegNumber == null) {
			textRegNumber = new JTextField();
			textRegNumber.setColumns(10);

		}
		return textRegNumber;
	}

	private JButton getBtnFilterName() {
		if (btnFilterName == null) {
			btnFilterName = new JButton("Filter");
			DefaultListModel<Doctor> filteredByName = new DefaultListModel<>();
			btnFilterName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextNameDoctor().getText().isBlank() && !getTextNameDoctor().getText().isEmpty()) {
						for (int i = 0; i < doctors.getSize(); i++) {
							if (getTextNameDoctor().getText().toLowerCase()
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
		return btnFilterName;
	}

	private JButton getBtnRegNumber() {
		if (btnRegNumber == null) {
			btnRegNumber = new JButton("Filter");

			DefaultListModel<Doctor> filteredByRegNumber = new DefaultListModel<>();
			btnRegNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextRegNumber().getText().isBlank() && !getTextRegNumber().getText().isEmpty()) {
						for (int i = 0; i < doctors.getSize(); i++) {
							if (getTextRegNumber().getText().equals(doctors.get(i).getNumColegiado())) {
								filteredByRegNumber.addElement(doctors.get(i));
							}
						}
					}

					List<Doctor> selected = listDoctor.getSelectedValuesList();

					doctors.removeAllElements();
					listDoctor.getSelectedValue();
					for (int i = 0; i < filteredByRegNumber.size(); i++) {
						if (!doctors.contains(filteredByRegNumber.get(i))) {
							doctors.addElement(filteredByRegNumber.get(i));
						}
					}
					for (int i = 0; i < selected.size(); i++) {
						doctors.addElement(selected.get(i));
					}
				}
			});
		}
		return btnRegNumber;
	}

	private JButton getBtnFilterPatientName() {
		if (btnFilterPatientName == null) {
			btnFilterPatientName = new JButton("Filter");
			DefaultListModel<Patient> filteredByName = new DefaultListModel<>();
			btnFilterPatientName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldNamePatient().getText().isBlank()
							&& !getTextFieldNamePatient().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (patients.get(i).getFirstName().toLowerCase()
									.contains(getTextFieldNamePatient().getText().toLowerCase())) {
								filteredByName.addElement(patients.get(i));
							}
						}
					}

//					List<Patient> selected = list_patients.getSelectedValuesList();
//
					patients.removeAllElements();
//					list_patients.getSelectedValue();
					for (int i = 0; i < filteredByName.size(); i++) {
						if (!patients.contains(filteredByName.get(i))) {
							patients.addElement(filteredByName.get(i));
						}
					}
//					for (int i = 0; i < selected.size(); i++) {
//						patients.addElement(selected.get(i));
//					}
				}
			});
		}
		return btnFilterPatientName;
	}

	private JButton getBtnFilterSS() {
		if (btnFilterSS == null) {
			btnFilterSS = new JButton("Filter");
			DefaultListModel<Patient> filteredBySSNumber = new DefaultListModel<>();
			btnFilterSS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!getTextFieldSSNumber().getText().isBlank() && !getTextFieldSSNumber().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (Integer.valueOf(getTextFieldSSNumber().getText()) == patients.get(i).getSsnumber()) {
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
		return btnFilterSS;
	}

	private JPanel getPanelPatientsReset() {
		if (panelPatientsReset == null) {
			panelPatientsReset = new JPanel();
			panelPatientsReset.setLayout(new GridLayout(0, 1, 0, 0));
			panelPatientsReset.add(getBtnResetPatients());
		}
		return panelPatientsReset;
	}

	private JButton getBtnResetPatients() {
		if (btnResetPatients == null) {
			btnResetPatients = new JButton("Reset filters");
			btnResetPatients.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					patients.removeAllElements();
					for (int i = 0; i < patientsReset.size(); i++) {
						patients.addElement(patientsReset.get(i));
					}
					textFieldNamePatient.setText("");
					textFieldSSNumber.setText("");
					textFieldSurname.setText("");
					btnSendRequest.setEnabled(false);

				}
			});
		}
		return btnResetPatients;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}

	private JList<Doctor> getListDoctor_1() {
		if (listDoctor == null) {
			listDoctor = new JList<Doctor>((ListModel) null);
		}
		return listDoctor;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getPanelSurDoctor_1());
		}
		return panelSur;
	}

	private JPanel getPanelSurDoctor_1() {
		if (panelSurDoctor == null) {
			panelSurDoctor = new JPanel();
			panelSurDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelSurDoctor.add(getRdbtnUrgent_1());
			panelSurDoctor.add(getBtnResetDoctor());
		}
		return panelSurDoctor;
	}

	private JRadioButton getRdbtnUrgent_1() {
		if (rdbtnUrgent == null) {
			rdbtnUrgent = new JRadioButton("Urgent");
		}
		return rdbtnUrgent;
	}

	private JButton getBtnResetDoctor() {
		if (btnReset == null) {
			btnReset = new JButton("Reset Filters");
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doctors.removeAllElements();
					for (int i = 0; i < doctorsReset.size(); i++) {
						doctors.addElement(doctorsReset.get(i));
					}
					textNameDoctor.setText("");
					textRegNumber.setText("");
					textFieldFrom.setEnabled(false);
					textFieldTo.setEnabled(false);
					btnSendRequest.setEnabled(false);
					btnSendRequest.setEnabled(false);

				}
			});

		}
		return btnReset;
	}

	private JDateChooser getDateChooser_1() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textFieldFrom.setEnabled(true);
					textFieldFrom.setEditable(true);
					textFieldTo.setEnabled(true);
					textFieldTo.setEditable(true);
				}
			});
			dateChooser.getCalendarButton().setEnabled(false);
			dateChooser.setMinSelectableDate(new Date(System.currentTimeMillis()));

		}
		return dateChooser;
	}

	private JTextArea getTxtContactInfo() {
		if (txtContactInfo == null) {
			txtContactInfo = new JTextArea();
			txtContactInfo.setEditable(false);
		}
		return txtContactInfo;
	}

	private JPanel getPanelSouthPatient() {
		if (panelSouthPatient == null) {
			panelSouthPatient = new JPanel();
			panelSouthPatient.setLayout(new GridLayout(0, 2, 0, 0));
			panelSouthPatient.add(getBtnEdit());
			panelSouthPatient.add(getBtnSave());
		}
		return panelSouthPatient;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Edit");
			btnEdit.setEnabled(false);
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnEdit.setEnabled(false); // by default, the edit button is disabled until a patient is selected
					getBtnSave().setEnabled(true); // when the edit button is pressed, is assumed that the contact info
													// of the patient is modified so the save button is enabled
					getTxtContactInfo().setEditable(true);
				}
			});

		}
		return btnEdit;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String newCInfo = getTxtContactInfo().getText();
					Patient p = (Patient) getList_patients().getSelectedValue();

					newContactInfo = newCInfo;

					p.setContactInfo(newCInfo);
					btnSave.setEnabled(false);
				}

			});
			btnSave.setEnabled(false);

		}
		return btnSave;
	}

	private JPanel getPanel_office_north() {
		if (panel_office_north == null) {
			panel_office_north = new JPanel();
			panel_office_north.setLayout(new GridLayout(1, 0, 0, 0));
			panel_office_north.add(getLblChooseOffice());
			panel_office_north.add(getComboBoxOffices());
		}
		return panel_office_north;
	}

	private JLabel getLblChooseOffice() {
		if (lblChooseOffice == null) {
			lblChooseOffice = new JLabel("Choose an office to book:");
		}
		return lblChooseOffice;
	}

	private JComboBox<String> getComboBoxOffices() {
		if (comboBoxOffices == null) {
			comboBoxOffices = new JComboBox<String>();
			comboBoxOffices.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					officeChoosed = true;
//					getBtnSeeFreeHours().setEnabled(true);

					checkSendRequestBtnEnabled();
					showFreeHours(getDateChooser().getDate());

				}
			});
			try {
				comboBoxOffices.setModel(new DefaultComboBoxModel<>(ConnectionFactory.getOfficesCodes()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return comboBoxOffices;
	}

	private void checkSendRequestBtnEnabled() {
		if (doctorChoosed && patientChoosed)
			getBtnSendRequest().setEnabled(true);

	}

	private JTextField getTextFieldFrom() {
		if (textFieldFrom == null) {
			textFieldFrom = new JTextField();

			textFieldFrom.setEnabled(false);
			textFieldFrom.setEditable(false);
			textFieldFrom.setColumns(10);
			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");

//			textFieldFrom.addFocusListener(new FocusAdapter() {
//				@Override
//				public void focusLost(FocusEvent e) {
//					try {
//						if (sdf3.parse(textFieldFrom.getText() + ":00").after(sdf3.parse(textFieldTo.getText()+ ":00"))) {
//							JOptionPane.showMessageDialog(MedicalRecepcionistView.this,
//									"The end hour of the appointment must be later than the start one.", "Warning",
//									JOptionPane.INFORMATION_MESSAGE);
//							btnFinish.setEnabled(false);
//
//						}
//					} catch (HeadlessException | ParseException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			});
		}
		return textFieldFrom;
	}

	private JTextField getTextFieldTo() {
		if (textFieldTo == null) {
			textFieldTo = new JTextField();
			textFieldTo.setEnabled(false);
			textFieldTo.setEditable(false);
			textFieldTo.setColumns(10);
			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
			textFieldTo.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						if (sdf3.parse(textFieldFrom.getText() + ":00")
								.after(sdf3.parse(textFieldTo.getText() + ":00"))) {
							JOptionPane.showMessageDialog(CreateRequestAppointmentView.this,
									"The end hour of the appointment must be later than the start one.", "Warning",
									JOptionPane.INFORMATION_MESSAGE);
							btnSendRequest.setEnabled(false);
						}
					} catch (HeadlessException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
		}
		return textFieldTo;
	}

	private JPanel getPanel_patient_center() {
		if (panel_patient_center == null) {
			panel_patient_center = new JPanel();
			panel_patient_center.setLayout(new GridLayout(0, 2, 0, 0));
			panel_patient_center.add(getPanel_patient_filter());
			panel_patient_center.add(getScrollPane_patients());
		}
		return panel_patient_center;
	}

	private JPanel getPanel_doctor_Center() {
		if (panel_doctor_Center == null) {
			panel_doctor_Center = new JPanel();
			panel_doctor_Center.setLayout(new GridLayout(0, 2, 0, 0));
			panel_doctor_Center.add(getPanelNameAndNumber());
			panel_doctor_Center.add(getScrollPaneDoctor());

		}
		return panel_doctor_Center;
	}

	private JLabel getLblsurname() {
		if (lblsurname == null) {
			lblsurname = new JLabel("By surname");
			lblsurname.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblsurname;
	}

	private JTextField getTextFieldSurname() {
		if (textFieldSurname == null) {
			textFieldSurname = new JTextField();
			textFieldSurname.setColumns(10);
		}
		return textFieldSurname;
	}

	private JButton getBtnFilterSurname() {
		if (btnFilterSurname == null) {
			btnFilterSurname = new JButton("Filter");
			btnFilterSurname.addActionListener(new ActionListener() {
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
		return btnFilterSurname;
	}

	private JLabel getLblSurnameDoctor() {
		if (lblSurnameDoctor == null) {
			lblSurnameDoctor = new JLabel("By surname:");
			lblSurnameDoctor.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblSurnameDoctor;
	}

	private JTextField getTextFieldSurnameDoctor() {
		if (textFieldSurnameDoctor == null) {
			textFieldSurnameDoctor = new JTextField();
			textFieldSurnameDoctor.setColumns(10);
		}
		return textFieldSurnameDoctor;
	}

	private JButton getBtnSurnameDoctor() {
		if (btnSurnameDoctor == null) {
			btnSurnameDoctor = new JButton("Filter");
			btnSurnameDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Doctor> filteredBySurname = new DefaultListModel<>();
					if (!getTextFieldSurnameDoctor().getText().isBlank()
							&& !getTextFieldSurnameDoctor().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
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
		return btnSurnameDoctor;
	}

	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("By DNI:");
			lblDNI.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblDNI;
	}

	private JTextField getTextFieldDni() {
		if (textFieldDni == null) {
			textFieldDni = new JTextField();
			textFieldDni.setColumns(10);
		}
		return textFieldDni;
	}

	private JButton getBtnDNI() {
		if (btnDNI == null) {
			btnDNI = new JButton("Filter");
			btnDNI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Patient> filteredByDNI = new DefaultListModel<>();
					if (!getTextFieldDni().getText().isBlank() && !getTextFieldDni().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (patients.get(i).getDni().contains(getTextFieldDni().getText())) {
								filteredByDNI.addElement(patients.get(i));
							}
						}
					}

					patients.removeAllElements();
					for (int i = 0; i < filteredByDNI.size(); i++) {
						if (!patients.contains(filteredByDNI.get(i))) {
							patients.addElement(filteredByDNI.get(i));
						}
					}
				}
			});
		}
		return btnDNI;
	}

	private void showFreeHours(Date d) {
		int officeId = ConnectionFactory.getOfficeIDFromCode(getComboBoxOffices().getSelectedItem().toString());

		Date tdy = d;
		java.sql.Date sqlDate = new java.sql.Date(tdy.getTime());

		String date = sqlDate.toString();

		String text = "";
		try {
			text = ConnectionFactory.getFreeHours(officeId, date, date);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		getTextAreaOfficeAvailability().setText(text);

	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
		}
		return scrollPane;
	}

	private JPanel getPanelDoctorAvailability() {
		if (panelDoctorAvailability == null) {
			panelDoctorAvailability = new JPanel();
			panelDoctorAvailability.setLayout(new BorderLayout(0, 0));
			panelDoctorAvailability.add(getDateChooser(), BorderLayout.NORTH);
			panelDoctorAvailability.add(getScrollPaneDoctorAvailability(), BorderLayout.CENTER);
		}
		return panelDoctorAvailability;
	}

	private JPanel getPanelOfficeAvailability() {
		if (panelOfficeAvailability == null) {
			panelOfficeAvailability = new JPanel();
			panelOfficeAvailability.setLayout(new BorderLayout(0, 0));
			panelOfficeAvailability.add(getPanelOfficeButtons(), BorderLayout.SOUTH);
			panelOfficeAvailability.add(getScrollPane_1_1(), BorderLayout.CENTER);
		}
		return panelOfficeAvailability;
	}

	public JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(new Date());
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					btnNext.setEnabled(true);
					getTextAreaDoctorAvailability().removeAll();
					try {
						getTextAreaDoctorAvailability().setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			dateChooser.setMinSelectableDate(new Date());

		}
		return dateChooser;
	}

	private JScrollPane getScrollPaneDoctorAvailability() {
		if (scrollPaneDoctorAvailability == null) {
			scrollPaneDoctorAvailability = new JScrollPane();
			scrollPaneDoctorAvailability.setViewportView(getTextAreaDoctorAvailability());
		}
		return scrollPaneDoctorAvailability;
	}

	private JTextArea getTextAreaDoctorAvailability() {
		if (textAreaDoctorAvailability == null) {
			textAreaDoctorAvailability = new JTextArea();
			textAreaDoctorAvailability.setEditable(false);
			try {
				textAreaDoctorAvailability.setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
						new java.sql.Date(dateChooser.getDate().getTime())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return textAreaDoctorAvailability;
	}

	private JPanel getPanelOfficeButtons() {
		if (panelOfficeButtons == null) {
			panelOfficeButtons = new JPanel();
			panelOfficeButtons.setLayout(new GridLayout(2, 0, 0, 0));
			panelOfficeButtons.add(getPanelPrevAndNext());
			panelOfficeButtons.add(getPanelHours());
		}
		return panelOfficeButtons;
	}

	private JPanel getPanelPrevAndNext() {
		if (panelPrevAndNext == null) {
			panelPrevAndNext = new JPanel();
			panelPrevAndNext.setLayout(new GridLayout(1, 0, 0, 0));
			panelPrevAndNext.add(getBtnPrev());
			panelPrevAndNext.add(getBtnNext());
		}
		return panelPrevAndNext;
	}

	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton("Previous");
			btnPrev.setEnabled(false);
			btnPrev.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Obtén la fecha actual seleccionada en el JDateChooser
					Date currentDate = dateChooser.getDate();

					// Crea un objeto Calendar y configúralo con la fecha actual
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currentDate);

					// Agrega un día al Calendar
					calendar.add(Calendar.DAY_OF_MONTH, -1);

					// Obtén la nueva fecha después de agregar un día
					Date newDate = calendar.getTime();

					// Establece la nueva fecha en el JDateChooser
					dateChooser.setDate(newDate);

					getTextAreaDoctorAvailability().removeAll();
					try {
						getTextAreaDoctorAvailability().setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					getTextAreaOfficeAvailability().removeAll();
					showFreeHours(newDate);
					SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println(getDateChooser().getDate());
					System.out.println(new Date());
					if (getDateChooser().getDate().getDay() == new Date().getDay()
							&& getDateChooser().getDate().getMonth() == new Date().getMonth()
							&& getDateChooser().getDate().getYear() == new Date().getYear()) {
						btnPrev.setEnabled(false);
					}
				}
			});
		}
		return btnPrev;
	}

	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Obtén la fecha actual seleccionada en el JDateChooser
					Date currentDate = dateChooser.getDate();

					// Crea un objeto Calendar y configúralo con la fecha actual
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currentDate);

					// Agrega un día al Calendar
					calendar.add(Calendar.DAY_OF_MONTH, 1);

					// Obtén la nueva fecha después de agregar un día
					Date newDate = calendar.getTime();

					// Establece la nueva fecha en el JDateChooser
					dateChooser.setDate(newDate);
					btnPrev.setEnabled(true);

					getTextAreaDoctorAvailability().removeAll();
					try {
						getTextAreaDoctorAvailability().setText(ConnectionFactory.getFreeHours(getSelectedDoctors(),
								new java.sql.Date(newDate.getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					getTextAreaOfficeAvailability().removeAll();
					;
					showFreeHours(newDate);
				}
			});
		}
		return btnNext;
	}

	private JPanel getPanelHours() {
		if (panelHours == null) {
			panelHours = new JPanel();
			panelHours.setLayout(new GridLayout(0, 4, 0, 0));
			panelHours.add(getLblFrom());
			panelHours.add(getTextFieldFromH());
			panelHours.add(getLblTo());
			panelHours.add(getTextFieldToH());
		}
		return panelHours;
	}

	private JLabel getLblFrom() {
		if (lblFrom == null) {
			lblFrom = new JLabel("From");
		}
		return lblFrom;
	}

	private JTextField getTextFieldFromH() {
		if (textFieldFromH == null) {
			textFieldFromH = new JTextField();
			textFieldFromH.setColumns(10);
		}
		return textFieldFromH;
	}

	private JLabel getLblTo() {
		if (lblTo == null) {
			lblTo = new JLabel("To");
		}
		return lblTo;
	}

	private JTextField getTextFieldToH() {
		if (textFieldToH == null) {
			textFieldToH = new JTextField();
			textFieldToH.setColumns(10);
		}
		return textFieldToH;
	}

	private JScrollPane getScrollPane_1_1() {
		if (scrollPaneOfficeAvailability == null) {
			scrollPaneOfficeAvailability = new JScrollPane();
			scrollPaneOfficeAvailability.setViewportView(getTextAreaOfficeAvailability());
		}
		return scrollPaneOfficeAvailability;
	}

	private JTextArea getTextAreaOfficeAvailability() {
		if (textAreaOfficeAvailability == null) {
			textAreaOfficeAvailability = new JTextArea();
			textAreaOfficeAvailability.setEditable(false);
			showFreeHours(getDateChooser().getDate());

		}
		return textAreaOfficeAvailability;
	}

	private JPanel getPanelComments() {
		if (panelComments == null) {
			panelComments = new JPanel();
			panelComments
					.setBorder(new TitledBorder(null, "Comments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelComments.setLayout(new GridLayout(0, 1, 0, 0));
			panelComments.add(getTxtComments());
		}
		return panelComments;
	}

	private JTextField getTxtComments() {
		if (txtComments == null) {
			txtComments = new JTextField();
			txtComments.setColumns(10);
		}
		return txtComments;
	}

	private JPanel getPanelSouthButtons() {
		if (panelSouthButtons == null) {
			panelSouthButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelSouthButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelSouthButtons.add(getBtnSendRequest());
		}
		return panelSouthButtons;
	}

	private JButton getBtnSendRequest() {
		if (btnSendRequest == null) {
			btnSendRequest = new JButton("Send request");
			btnSendRequest.setEnabled(false);
			btnSendRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						areYouSureJOP();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});			
		}
		return btnSendRequest;
	}
}
