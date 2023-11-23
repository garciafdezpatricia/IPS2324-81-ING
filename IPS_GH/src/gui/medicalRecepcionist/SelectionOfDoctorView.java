package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import db.Appointment;
import db.Doctor;
import util.ConnectionFactory;
import javax.swing.ListSelectionModel;

public class SelectionOfDoctorView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelButtons;
	private JPanel panelTitle;
	private JPanel panelCenter;
	private JButton btnSave;
	private JLabel lblChooseDoctor;
	private JPanel panel_doctor;
	private JPanel panelSur;
	private JPanel panelSurDoctor;
	private JButton btnReset;
	private JPanel panel_doctor_Center;
	private JPanel panelNameAndNumber;
	private JLabel lblTypeDoctor;
	private JTextField textNameDoctor;
	private JButton btnFilterName;
	private JLabel lblSurnameDoctor;
	private JTextField textFieldSurnameDoctor;
	private JButton btnSurnameDoctor;
	private JLabel lblRegistrationNumber;
	private JTextField textRegNumber;
	private JButton btnRegNumber;
	private JScrollPane scrollPaneDoctor;
	private JList<Doctor> listDoctor;
	private JPanel panelDoctorAvailability;
	private JDateChooser dateChooser;
	private JScrollPane scrollPaneDoctorAvailability;
	private JTextArea textAreaDoctorAvailability;
	private JPanel panelButtonsDate;
	private JButton btnPrev;
	private JButton btnNext;
	private static String specialization;
	private DefaultListModel<Doctor> doctors = ConnectionFactory.getDoctorBySpecialization(specialization);
	private static Appointment app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectionOfDoctorView frame = new SelectionOfDoctorView(app, specialization);
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
	public SelectionOfDoctorView(Appointment app, String specialization) {
		this.specialization = specialization;
		this.app = app;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelTitle(), BorderLayout.NORTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
			panelButtons.add(getBtnSave());
		}
		return panelButtons;
	}

	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.setLayout(new GridLayout(0, 1, 0, 0));
			panelTitle.add(getLblChooseDoctor());
		}
		return panelTitle;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(1, 0, 0, 0));
			panelCenter.add(getPanel_doctor());
			panelCenter.add(getPanelDoctorAvailability());
		}
		return panelCenter;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// si el doctor no trabaja a esa hora ese día

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = null;
					try {
						date = sdf.parse(app.getStartdate());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
					for (int i = 0; i < listDoctor.getSelectedValuesList().size(); i++) {
						try {
							if (!ConnectionFactory.isWorking(new java.sql.Date(sqlDate.getTime()),
									outputFormat.format(sdf.parse(app.getStartdate())),
									outputFormat.format(sdf.parse(app.getEnddate())),
									listDoctor.getSelectedValuesList().get(i).getId())) {
								System.out.println(new java.sql.Date(sqlDate.getTime()));
								System.out.println(sdf.parse(app.getStartdate()));
								JOptionPane.showMessageDialog(SelectionOfDoctorView.this, "The doctor is not working.",
										"Warning", JOptionPane.INFORMATION_MESSAGE);
								System.out.println("no está trabajando");
							} else {
								System.out.println("está trabajando");
								// el doctor tiene otra cita a esa hora
//								try {
//									if (ConnectionFactory.hasAnAppointment(listDoctor.getSelectedValuesList().get(i),
//											new java.sql.Date(selectDate.getDay().getTime()) + " "
//													+ selectDate.getFrom() + ":00",
//											new java.sql.Date(selectDate.getDay().getTime()) + " " + selectDate.getTo()
//													+ ":00")) {
////										int opcion2 = JOptionPane.showConfirmDialog(EditAppointmentView.this,
////												"The doctor has another appointment at that time, do you want to edit this appointment either?",
////												"Confirmation", JOptionPane.YES_NO_OPTION);
//
//									} else {
								areYouSureJOP();
//								} catch (Exception e1) {
//									e1.printStackTrace();
//								}
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(SelectionOfDoctorView.this, "The day has passed", "Warning",
									JOptionPane.INFORMATION_MESSAGE);
						}

					}

				}

			});
		}
		return btnSave;
	}

	private void areYouSureJOP() throws Exception {
		int opcion = JOptionPane.showConfirmDialog(SelectionOfDoctorView.this,
				"Are you sure you want to choose as doctor/s: " + getListDoctor().getSelectedValuesList(),
				"Confirmation", JOptionPane.YES_NO_OPTION);

		// Verificar la respuesta del usuario
		if (opcion == JOptionPane.YES_OPTION) {
			// El usuario ha confirmado, realiza la acción
			// Puedes poner aquí el código que quieras ejecutar después de la confirmación
			System.out.println("Acción realizada.");
			if (app.getUrgency() == 1) {
				for (int j = 0; j < listDoctor.getSelectedValuesList().size(); j++) {
					sendEmail(((Doctor) listDoctor.getSelectedValuesList().get(j)).getEmail());
				}

			}
			// Si hay un solo doctor seleccionado, se updatea la que hay y listo,
			if (getListDoctor().getSelectedValuesList().size() == 1) {
				updateDoctor(app.getId(), getListDoctor().getSelectedValuesList().get(0).getId());
			}
			// Si hay más de un doctor seleccionado
			else {
				updateDoctor(app.getId(), getListDoctor().getSelectedValuesList().get(0).getId());
				// si hay mas de un doctor se cre
				// otra con los mismos datos y otro doctor
				for (int i = 1; i < getListDoctor().getSelectedValuesList().size(); i++) {
					ConnectionFactory.createAppointment(app.getPatientid(),
							getListDoctor().getSelectedValuesList().get(i).getId(), app.getStartdate(),
							app.getEnddate(), app.getUrgency(), app.getOfficeId().intValue(), app.getInformation(),
							"Booked");
					System.out.println("holaaaa");
				}
			}
		} else {
			// El usuario ha cancelado la acción
			System.out.println("Acción cancelada.");
		}

	}

	private void updateDoctor(BigInteger appId, BigInteger doctorId) throws Exception {
		ConnectionFactory.updateDoctorAppointment(appId, doctorId);
	}

	// TODO: poner más datos
	private void sendEmail(String destinatario) {
		String asunto = "Urgent appointment";
		String mensaje = "";
		try {
			mensaje = "You have a new urgent appointment with the patient "
					+ ConnectionFactory.getPatient(app.getPatientid()) + " at " + app.getStartdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	private JLabel getLblChooseDoctor() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Choose doctor");
			lblChooseDoctor.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblChooseDoctor;
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

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getPanelSurDoctor(), BorderLayout.NORTH);
		}
		return panelSur;
	}

	private JPanel getPanelSurDoctor() {
		if (panelSurDoctor == null) {
			panelSurDoctor = new JPanel();
			panelSurDoctor.setLayout(new GridLayout(0, 1, 0, 0));
			panelSurDoctor.add(getBtnReset());
		}
		return panelSurDoctor;
	}

	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset Filters");
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doctors.removeAllElements();

					doctors = ConnectionFactory.getDoctorBySpecialization(specialization);
					textNameDoctor.setText("");
					textRegNumber.setText("");
					textFieldSurnameDoctor.setText("");
//					btnFinish.setEnabled(false);
				}
			});
		}
		return btnReset;
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

	private JPanel getPanelNameAndNumber() {
		if (panelNameAndNumber == null) {
			panelNameAndNumber = new JPanel();
			panelNameAndNumber
					.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelNameAndNumber.setLayout(new GridLayout(0, 3, 0, 0));
			panelNameAndNumber.add(getLblTypeDoctor());
			panelNameAndNumber.add(getTextNameDoctor());
			panelNameAndNumber.add(getBtnFilterName());
			panelNameAndNumber.add(getLblSurnameDoctor());
			panelNameAndNumber.add(getTextFieldSurnameDoctor());
			panelNameAndNumber.add(getBtnSurnameDoctor());
			panelNameAndNumber.add(getLblRegistrationNumber());
			panelNameAndNumber.add(getTextRegNumber());
			panelNameAndNumber.add(getBtnRegNumber());
		}
		return panelNameAndNumber;
	}

	private JLabel getLblTypeDoctor() {
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
						for (int i = 0; i < doctors.getSize(); i++) {
							if ((doctors.get(i).getSurname().contains(getTextFieldSurnameDoctor().getText()))) {
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

	private JLabel getLblRegistrationNumber() {
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

	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane((Component) null);
			scrollPaneDoctor.setViewportView(getListDoctor());
		}
		return scrollPaneDoctor;
	}

	private List<Doctor> getSelectedDoctors() {

		List<Doctor> res = new ArrayList<>();
		for (int i = 0; i < getListDoctor().getSelectedValuesList().size(); i++) {
			res.add((Doctor) (getListDoctor().getSelectedValuesList().get(i)));
		}
		return res;
	}

	private JList<Doctor> getListDoctor() {
		if (listDoctor == null) {
			listDoctor = new JList<Doctor>(ConnectionFactory.getDoctorBySpecialization(specialization));
			listDoctor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
//					if (!listDoctor.getSelectedValuesList().isEmpty()) {
//						doctorChoosed = true;
//
//					} else {
//						doctorChoosed = false;
//					}

				}
			});
		}
		return listDoctor;
	}

	private JPanel getPanelDoctorAvailability() {
		if (panelDoctorAvailability == null) {
			panelDoctorAvailability = new JPanel();
			panelDoctorAvailability.setLayout(new BorderLayout(0, 0));
			panelDoctorAvailability.add(getDateChooser(), BorderLayout.NORTH);
			panelDoctorAvailability.add(getScrollPaneDoctorAvailability(), BorderLayout.CENTER);
			panelDoctorAvailability.add(getPanelButtonsDate(), BorderLayout.SOUTH);
		}
		return panelDoctorAvailability;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(new Date());
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnNext.setEnabled(true);
					getTextAreaDoctorAvailability().removeAll();
					try {
						getTextAreaDoctorAvailability()
								.setText(ConnectionFactory.getFreeHours(getListDoctor().getSelectedValuesList(),
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
			textAreaDoctorAvailability.setText((String) null);
			textAreaDoctorAvailability.setEditable(false);
		}
		return textAreaDoctorAvailability;
	}

	private JPanel getPanelButtonsDate() {
		if (panelButtonsDate == null) {
			panelButtonsDate = new JPanel();
			panelButtonsDate.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtonsDate.add(getBtnPrev());
			panelButtonsDate.add(getBtnNext());
		}
		return panelButtonsDate;
	}

	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton("Previous");
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
						getTextAreaDoctorAvailability()
								.setText(ConnectionFactory.getFreeHours(getListDoctor().getSelectedValuesList(),
										new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (getDateChooser().getDate().getDay() == new Date().getDay()
							&& getDateChooser().getDate().getMonth() == new Date().getMonth()
							&& getDateChooser().getDate().getYear() == new Date().getYear()) {
						btnPrev.setEnabled(false);
					}
				}
			});
			btnPrev.setEnabled(false);
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
						getTextAreaDoctorAvailability()
								.setText(ConnectionFactory.getFreeHours(getListDoctor().getSelectedValuesList(),
										new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
		}
		return btnNext;
	}
}
