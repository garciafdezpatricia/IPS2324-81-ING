package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import db.Doctor;
import db.Patient;
import util.ConnectionFactory;

public class MedicalRecepcionistView extends JFrame {

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
	private JList listDoctor;
	private JLabel lblSSNumber;
	private JTextField textFieldSSNumber;
	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();
	private DefaultListModel<Doctor> doctorsReset = new DefaultListModel<>();
	private DefaultListModel<Patient> patients = new DefaultListModel<>();
	private DefaultListModel<Patient> patientsReset = new DefaultListModel<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicalRecepcionistView frame = new MedicalRecepcionistView();
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
	private JPanel panelDate;
	private JLabel lblDay;
	private JTextField textFieldDay;
	private JLabel lblFrom;
	private JTextField textFieldFrom;
	private JLabel lblTo;
	private JTextField textFieldTo;
	private JButton btnFilterName;
	private JButton btnRegNumber;
	private JPanel panelSurDoctor;
	private JRadioButton rdbtnUrgent;
	private JButton btnReset;
	private JButton btnFilterPatientName;
	private JButton btnFilterSS;
	private JPanel panelPatientsReset;
	private JButton btnResetPatients;

	/**
	 * Create the frame.
	 */
	public MedicalRecepcionistView() {
		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			Statement statement_patient = connection.createStatement();

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM DOCTOR";
			ResultSet resultSet = statement.executeQuery(sql);

			String sql_patients = "SELECT * FROM PATIENT";
			ResultSet resultSet_patients = statement_patient.executeQuery(sql_patients);

			// Procesar los resultados
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String numcolegiado = resultSet.getString("numcolegiado");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				// Procesa otros campos según la estructura de tu tabla
				doctors.addElement(new Doctor(id, numcolegiado, name, surname, email));
				doctorsReset.addElement(new Doctor(id, numcolegiado, name, surname, email));

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();

			// Procesar los resultados
			while (resultSet_patients.next()) {
				int id = resultSet_patients.getInt("id");
				String contactinfo = resultSet_patients.getString("contactinfo");
				String name = resultSet_patients.getString("firstname");
				String surname = resultSet_patients.getString("surname");
				String dni = resultSet_patients.getString("dni");
				int ssnumber = resultSet_patients.getInt("ssnumber");


				patients.addElement(new Patient(id, name, surname, dni, contactinfo, ssnumber));
				patientsReset.addElement(new Patient(id, name, surname, dni, contactinfo, ssnumber));

			}

			// Cerrar la conexión
			resultSet_patients.close();
			statement_patient.close();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 906, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_title(), BorderLayout.NORTH);
		contentPane.add(getPanelGeneral(), BorderLayout.CENTER);
		contentPane.add(getPanel_buttons(), BorderLayout.SOUTH);

		// doctors
		panel_doctor.add(scrollPaneDoctor, BorderLayout.CENTER);

		// patient
		panel_patient.add(scrollPane_patients, BorderLayout.CENTER);

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
			lblTitle = new JLabel("Reservation of appointments");
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
			panel_doctor.add(getPanel_5(), BorderLayout.NORTH);
			panel_doctor.add(getScrollPaneDoctor(), BorderLayout.CENTER);
			panel_doctor.add(getPanelSurDoctor(), BorderLayout.SOUTH);
		}
		return panel_doctor;
	}

	private JPanel getPanel_patient() {
		if (panel_patient == null) {
			panel_patient = new JPanel();
			panel_patient.setBorder(
					new TitledBorder(null, "Patient Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_patient.setLayout(new BorderLayout(0, 0));
			panel_patient.add(getPanel_patient_filter(), BorderLayout.NORTH);
			panel_patient.add(getScrollPane_patients(), BorderLayout.CENTER);
			panel_patient.add(getPanelPatientsReset(), BorderLayout.SOUTH);
		}
		return panel_patient;
	}

	private JPanel getPanel_office() {
		if (panel_office == null) {
			panel_office = new JPanel();
		}
		return panel_office;
	}

	private JPanel getPanel_information() {
		if (panel_information == null) {
			panel_information = new JPanel();
			panel_information.setBorder(
					new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_information.setLayout(new BorderLayout(0, 0));
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
			btnFinish = new JButton("Finish");
			btnFinish.addActionListener(new ActionListener() {

				// TODO: si hay mas citas resrrvadas a esa hora para ese doctor poner un aviso
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = JOptionPane.showConfirmDialog(MedicalRecepcionistView.this,
							"Are you sure you want to reserve the appointment between the doctor(s) "
									+ listDoctor.getSelectedValuesList() + " and the patient "
									+ list_patients.getSelectedValue() + " on "
											+ getTextFieldDay().getText() + " "
													+ "at " + getTextFieldFrom().getText() + "in the office xxxx?",
							"Confirmation", JOptionPane.YES_NO_OPTION);

					// Verificar la respuesta del usuario
					if (opcion == JOptionPane.YES_OPTION) {
						// El usuario ha confirmado, realiza la acción
						// Puedes poner aquí el código que quieras ejecutar después de la confirmación
						System.out.println("Acción realizada.");
						if (rdbtnUrgent.isSelected()) {
							for (int i = 0; i < listDoctor.getSelectedValuesList().size(); i++) {
								sendEmail(((Doctor) listDoctor.getSelectedValuesList().get(i)).getEmail());
							}
						}
					} else {
						// El usuario ha cancelado la acción
						System.out.println("Acción cancelada.");
					}

				}
			});
		}
		return btnFinish;
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

	private JPanel getPanel_5() {
		if (panel_doctor_filter == null) {
			panel_doctor_filter = new JPanel();
			panel_doctor_filter.setLayout(new BorderLayout(0, 0));
			panel_doctor_filter.add(getPanelNameAndNumber());
			panel_doctor_filter.add(getPanelDate(), BorderLayout.NORTH);
		}
		return panel_doctor_filter;
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
		}
		return list_patients;
	}

	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane(listDoctor);
			scrollPaneDoctor.setViewportView(getListDoctor());
		}
		return scrollPaneDoctor;
	}

	private JList getListDoctor() {
		if (listDoctor == null) {
			listDoctor = new JList<>(doctors);
		}
		return listDoctor;
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

	private JPanel getPanelDate() {
		if (panelDate == null) {
			panelDate = new JPanel();
			panelDate.setBorder(
					new TitledBorder(null, "Day and hour", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDate.setLayout(new GridLayout(0, 6, 0, 0));
			panelDate.add(getLblDay());
			panelDate.add(getTextFieldDay());
			panelDate.add(getLblFrom());
			panelDate.add(getTextFieldFrom());
			panelDate.add(getLblTo());
			panelDate.add(getTextFieldTo());
		}
		return panelDate;
	}

	private JLabel getLblDay() {
		if (lblDay == null) {
			lblDay = new JLabel("Day:");
			lblDay.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblDay;
	}

	private JTextField getTextFieldDay() {
		if (textFieldDay == null) {
			textFieldDay = new JTextField();
			textFieldDay.setText("mm/dd/yy");
			textFieldDay.setColumns(10);
		}
		return textFieldDay;
	}

	private JLabel getLblFrom() {
		if (lblFrom == null) {
			lblFrom = new JLabel("From:");
			lblFrom.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblFrom;
	}

	private JTextField getTextFieldFrom() {
		if (textFieldFrom == null) {
			textFieldFrom = new JTextField();
			textFieldFrom.setText("hh:mm");
			textFieldFrom.setColumns(10);
		}
		return textFieldFrom;
	}

	private JLabel getLblTo() {
		if (lblTo == null) {
			lblTo = new JLabel("To:");
			lblTo.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblTo;
	}

	private JTextField getTextFieldTo() {
		if (textFieldTo == null) {
			textFieldTo = new JTextField();
			textFieldTo.setText("hh:mm");
			textFieldTo.setColumns(10);
		}
		return textFieldTo;
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

	private JPanel getPanelSurDoctor() {
		if (panelSurDoctor == null) {
			panelSurDoctor = new JPanel();
			panelSurDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelSurDoctor.add(getRdbtnUrgent());
			panelSurDoctor.add(getBtnReset());
		}
		return panelSurDoctor;
	}

	private JRadioButton getRdbtnUrgent() {
		if (rdbtnUrgent == null) {
			rdbtnUrgent = new JRadioButton("Urgent");
		}
		return rdbtnUrgent;
	}

	private JButton getBtnReset() {
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
				}
			});
		}
		return btnReset;
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
					if (!getTextFieldSSNumber().getText().isBlank()
							&& !getTextFieldSSNumber().getText().isEmpty()) {
						for (int i = 0; i < patients.getSize(); i++) {
							if (Integer.valueOf(getTextFieldSSNumber().getText())==
									patients.get(i).getSsnumber()) {
								filteredBySSNumber.addElement(patients.get(i));
							}
						}
					}

//					List<Patient> selected = list_patients.getSelectedValuesList();
//
					patients.removeAllElements();
//					list_patients.getSelectedValue();
					for (int i = 0; i < filteredBySSNumber.size(); i++) {
						if (!patients.contains(filteredBySSNumber.get(i))) {
							patients.addElement(filteredBySSNumber.get(i));
						}
					}
//					for (int i = 0; i < selected.size(); i++) {
//						patients.addElement(selected.get(i));
//					}
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
				}
			});
		}
		return btnResetPatients;
	}
}
