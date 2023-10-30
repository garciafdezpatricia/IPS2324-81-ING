package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
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

import db.Appointment;
import db.Doctor;
import db.Patient;
import util.ConnectionFactory;

public class EditAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_title;
	private JPanel panelGeneral;
	private JLabel lblTitle;
	private JPanel panel_doctor;
	private JPanel panel_patient;
	private JPanel panel_office;
	private JPanel panel_information;
	private JPanel panel_buttons;
	private JButton btnFinish;
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
	private boolean fromDateChoosed = false;
	private boolean toDateChoosed = false;
	private SelectDateForEdition selectDate;

	private static BigInteger doctorid, id;
	private static BigInteger patientid;
	private static BigInteger officeid;
	private static int urgency;
	private static String information;
	private static String startDate;
	private static String endDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAppointmentView frame = new EditAppointmentView(id, doctorid, patientid, officeid, urgency,
							information, startDate, endDate);
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
	private JTextArea txtContactInfo;
	private JPanel panelSouthPatient;
	private JButton btnEdit;
	private JButton btnSave;
	private String newContactInfo = "";
	private JTextField textFieldFrom;
	private JTextField textFieldTo;
	private JPanel panel_patient_center;
	private JPanel panel_doctor_Center;
	private JButton btnSelectDate;
	private JLabel lblSurname;
	private JTextField textFieldSurname;
	private JButton btnFilterSurname;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public EditAppointmentView(BigInteger id, BigInteger doctorid, BigInteger patientid, BigInteger officeid,
			int urgency, String information, String startDate, String endDate) throws Exception {

		this.doctorid = doctorid;
		this.patientid = patientid;
		this.officeid = officeid;
		this.urgency = urgency;
		this.information = information;
		this.startDate = startDate;
		this.endDate = endDate;
		setTitle("Edition of appointment");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditAppointmentView.class.getResource("/img/descarga.jpg")));
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
		setBounds(100, 100, 906, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_title(), BorderLayout.NORTH);
		contentPane.add(getPanelGeneral(), BorderLayout.CENTER);
		contentPane.add(getPanel_buttons(), BorderLayout.SOUTH);

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
			panelGeneral.setLayout(new GridLayout(2, 2, 0, 0));
			panelGeneral.add(getPanel_doctor());
			panelGeneral.add(getPanel_patient());
			panelGeneral.add(getPanel_office());
			panelGeneral.add(getPanel_information());
		}
		return panelGeneral;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Edit Appointment");
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

	private JPanel getPanel_buttons() {
		if (panel_buttons == null) {
			panel_buttons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_buttons.getLayout();
			flowLayout.setAlignment(FlowLayout.TRAILING);
			panel_buttons.add(getBtnFinish());
		}
		return panel_buttons;
	}

	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Edit Appointment");

			btnFinish.addActionListener(new ActionListener() {

				// TODO: si hay mas citas resrrvadas a esa hora para ese doctor poner un aviso
				@Override
				public void actionPerformed(ActionEvent e) {
					// si el doctor no trabaja a esa hora ese día

					for (int i = 0; i < listDoctor.getSelectedValuesList().size(); i++) {
						try {
							if (!ConnectionFactory.isWorking(new java.sql.Date(selectDate.getDay().getTime()),
									selectDate.getFrom(), selectDate.getTo(),
									listDoctor.getSelectedValuesList().get(i).getId())) {
								JOptionPane.showMessageDialog(EditAppointmentView.this, "The doctor is not working.",
										"Warning", JOptionPane.INFORMATION_MESSAGE);
								System.out.println("no está trabajando");
							} else {
								System.out.println("está trabajando");
								// el doctor tiene otra cita a esa hora
								try {
									if (ConnectionFactory.hasAnAppointment(listDoctor.getSelectedValuesList().get(i),
											new java.sql.Date(selectDate.getDay().getTime()) + " "
													+ selectDate.getFrom() + ":00",
											new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getTo()
													+ ":00")) {
										int opcion2 = JOptionPane.showConfirmDialog(EditAppointmentView.this,
												"The doctor has another appointment at that time, do you want to edit this appointment either?",
												"Confirmation", JOptionPane.YES_NO_OPTION);

										// Verificar la respuesta del usuario
										if (opcion2 == JOptionPane.YES_OPTION) {
											// El usuario ha confirmado, realiza la acción
											// Puedes poner aquí el código que quieras ejecutar después de la
											// confirmación
											areYouSureJOP();
										} else {
											// El usuario ha cancelado la acción
											System.out.println("Appointment cancelled.");
										}
									} else {
										areYouSureJOP();
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

				}

			});
		}
		return btnFinish;
	}

	private void areYouSureJOP() throws Exception {
		int opcion = JOptionPane.showConfirmDialog(EditAppointmentView.this,
				"Are you sure you want to edit the appointment " + id + " between the doctor(s) "
						+ listDoctor.getSelectedValuesList() + " and the patient " + list_patients.getSelectedValue()
						+ " on  " + selectDate.getDay().getDay() + "/" + selectDate.getDay().getMonth() + "/"
						+ selectDate.getDay().getYear() + " at " + selectDate.getFrom() + " in the office "
						+ getComboBoxOffices().getSelectedItem() + "?",
				"Confirmation", JOptionPane.YES_NO_OPTION);

		// Verificar la respuesta del usuario
		if (opcion == JOptionPane.YES_OPTION) {
			// El usuario ha confirmado, realiza la acción
			// Puedes poner aquí el código que quieras ejecutar después de la confirmación
			System.out.println("Acción realizada.");
			if (rdbtnUrgent.isSelected()) {
				for (int j = 0; j < listDoctor.getSelectedValuesList().size(); j++) {
					sendEmail(((Doctor) listDoctor.getSelectedValuesList().get(j)).getEmail());
				}
			}
			Patient p = (Patient) list_patients.getSelectedValue();
			if (rdbtnUrgent.isSelected()) {
				ConnectionFactory.updateAppointment(id, p.getId(), listDoctor.getSelectedValue().getId(),
						new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getFrom() + ":00",
						new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getTo() + ":00", 1,
						ConnectionFactory.officeIdFrom(getComboBoxOffices().getSelectedItem().toString()),
						newContactInfo);

			} else {
				ConnectionFactory.updateAppointment(id, p.getId(), listDoctor.getSelectedValue().getId(),
						new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getFrom() + ":00",
						new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getTo() + ":00", 0,
						ConnectionFactory.officeIdFrom(getComboBoxOffices().getSelectedItem().toString()),
						newContactInfo);
			}
		} else {
			// El usuario ha cancelado la acción
			System.out.println("Acción cancelada.");
		}

	}

	// TODO: poner más datos
	private void sendEmail(String destinatario) {
		String asunto = "Urgent appointment";
		String mensaje = "You have a new urgent appointment with the patient " + list_patients.getSelectedValue()
				+ " at " + new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getFrom()
				+ ":00 the day " + selectDate.getDateChooser().getDate().getDate();

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
			panel_patient_filter.add(getLblSurname());
			panel_patient_filter.add(getTextFieldSurname());
			panel_patient_filter.add(getBtnFilterSurname());
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
			for (int i = 0; i < list_patients.getModel().getSize(); i++) {
				Patient p = (Patient) list_patients.getModel().getElementAt(i);
				if (p.getId() == patientid) {
					list_patients.setSelectedIndex(i);
				}
			}
			list_patients.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Patient p = (Patient) getList_patients().getSelectedValue();
					if (p != null) {
						getTxtContactInfo().setText(p.getContactInfo());
						getBtnEdit().setEnabled(true);

						patientChoosed = true;

						checkFinishBtnEnabled();

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
			for (int i = 0; i < listDoctor.getModel().getSize(); i++) {
				if (listDoctor.getModel().getElementAt(i).getId().equals(doctorid)) {
					listDoctor.setSelectedIndex(i);
				}
			}
			listDoctor.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!listDoctor.getSelectedValuesList().isEmpty()) {
						doctorChoosed = true;
						btnSelectDate.setEnabled(true);

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
							if (getTextFieldNamePatient().getText().toLowerCase()
									.equals(patients.get(i).getFirstName().toLowerCase())) {
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
					btnFinish.setEnabled(false);

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
			panelSurDoctor.setLayout(new GridLayout(0, 3, 0, 0));
			panelSurDoctor.add(getRdbtnUrgent_1());
			panelSurDoctor.add(getBtnResetDoctor());
			panelSurDoctor.add(getBtnSelectDate());
		}
		return panelSurDoctor;
	}

	private JRadioButton getRdbtnUrgent_1() {
		if (rdbtnUrgent == null) {
			rdbtnUrgent = new JRadioButton("Urgent");
			if (urgency == 1) {
				rdbtnUrgent.setSelected(true);
			} else
				rdbtnUrgent.setSelected(false);
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
					btnFinish.setEnabled(false);
					btnSelectDate.setEnabled(false);
					btnFinish.setEnabled(false);

				}
			});

		}
		return btnReset;
	}

	private JTextArea getTxtContactInfo() {
		if (txtContactInfo == null) {
			txtContactInfo = new JTextArea();
			txtContactInfo.setEditable(false);
			txtContactInfo.setText(information);
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

					checkFinishBtnEnabled();
				}
			});
			try {
				String[] offices = ConnectionFactory.getOfficesCodes();

				comboBoxOffices.setModel(new DefaultComboBoxModel<>(offices));
				for (int i = 0; i < offices.length; i++) {
					if (offices[i].equals(ConnectionFactory.getOffice(officeid))) {
						comboBoxOffices.setSelectedIndex(i);
						;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return comboBoxOffices;
	}

	private void checkFinishBtnEnabled() {
		if (doctorChoosed && patientChoosed && officeChoosed)
			getBtnFinish().setEnabled(true);

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

	private JButton getBtnSelectDate() {
		if (btnSelectDate == null) {
			btnSelectDate = new JButton("Select date");
			btnSelectDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SelectDateForEdition mr;
					try {
						mr = new SelectDateForEdition(getSelectedDoctors(), startDate, endDate);
						mr.setVisible(true);
						mr.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
		}
		return btnSelectDate;
	}

	private JLabel getLblSurname() {
		if (lblSurname == null) {
			lblSurname = new JLabel("By Surname");
			lblSurname.setFont(new Font("Tahoma", Font.BOLD, 11));
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

	private JButton getBtnFilterSurname() {
		if (btnFilterSurname == null) {
			btnFilterSurname = new JButton("Filter");
			btnFilterSurname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Patient> filteredBySurname = new DefaultListModel<>();
					if (!getTextFieldSSNumber().getText().isBlank() && !getTextFieldSSNumber().getText().isEmpty()) {
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
}
