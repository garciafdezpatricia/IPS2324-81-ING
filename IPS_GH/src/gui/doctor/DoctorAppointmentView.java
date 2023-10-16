package gui.doctor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import creator.CausesCreator;
import db.Appointment;
import util.AppointmentBLDto;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DoctorAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel appointmentInfoPanel;
	private JLabel lblAppointmentPatient;
	private JLabel lblAppointmentDate;
	private JPanel appointmentOptionsPanel;
	private JLabel lblCheckInTime;
	private JTextField txtCheckInTime;
	private JLabel lblCheckOutTime;
	private JTextField txtCheckOutTime;
	private JButton btnCheckInTime;
	private JButton btnCheckOutTime;
	private JPanel buttonsPanel;
	private JButton btnExit;
	private JButton btnSave;
	private JLabel lblAttendance;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JList causesList;
	private JScrollPane scrollPane;
	private JLabel lblListCauses;
	private JButton btnAddCause;
	private JLabel lblCustomCause;
	private JTextArea txtAreaCustomCause;
	private JScrollPane scrollPane_1;
	private JButton btnAddCustomCause;
	private JList finalCausesList;
	private JScrollPane scrollPane_2;
	private JLabel lblSelectedCauses;
	private JButton btnRemoveCause;

	private CausesCreator causesCreator;
	private Map<String, List<String>> causes;

	private DefaultListModel<String> finalCauses = new DefaultListModel<String>();

	
	private AppointmentBLDto appointment;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DoctorAppointmentView frame = new DoctorAppointmentView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public DoctorAppointmentView(AppointmentBLDto appointment) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO: make confirmation message
				setVisible(false);
			}
		});
		this.appointment = appointment;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getAppointmentInfoPanel(), BorderLayout.NORTH);
		contentPane.add(getAppointmentOptionsPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getAppointmentInfoPanel() {
		if (appointmentInfoPanel == null) {
			appointmentInfoPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) appointmentInfoPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setHgap(25);
			appointmentInfoPanel.add(getLblAppointmentPatient());
			appointmentInfoPanel.add(getLblAppointmentDate());
		}
		return appointmentInfoPanel;
	}

	private JLabel getLblAppointmentPatient() {
		if (lblAppointmentPatient == null) {
			lblAppointmentPatient = new JLabel("New label");
			lblAppointmentPatient.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblAppointmentPatient.setText(appointment.patientName.toUpperCase() + " " + appointment.patientSurname.toUpperCase());
		}
		return lblAppointmentPatient;
	}

	private JLabel getLblAppointmentDate() {
		if (lblAppointmentDate == null) {
			lblAppointmentDate = new JLabel("New label");
			lblAppointmentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAppointmentDate.setText(appointment.startDate);
		}
		return lblAppointmentDate;
	}

	private JPanel getAppointmentOptionsPanel() {
		if (appointmentOptionsPanel == null) {
			appointmentOptionsPanel = new JPanel();
			appointmentOptionsPanel.setLayout(null);
			appointmentOptionsPanel.add(getLblCheckInTime());
			appointmentOptionsPanel.add(getTxtCheckInTime());
			appointmentOptionsPanel.add(getLblCheckOutTime());
			appointmentOptionsPanel.add(getTxtCheckOutTime());
			appointmentOptionsPanel.add(getBtnCheckInTime());
			appointmentOptionsPanel.add(getBtnCheckOutTime());
			appointmentOptionsPanel.add(getLblAttendance());
			appointmentOptionsPanel.add(getRdbtnYes());
			appointmentOptionsPanel.add(getRdbtnNo());
			appointmentOptionsPanel.add(getScrollPane());
			appointmentOptionsPanel.add(getLblListCauses());
			appointmentOptionsPanel.add(getBtnAddCause());
			appointmentOptionsPanel.add(getLblCustomCause());
			appointmentOptionsPanel.add(getScrollPane_1());
			appointmentOptionsPanel.add(getBtnAddCustomCause());
			appointmentOptionsPanel.add(getScrollPane_2());
			appointmentOptionsPanel.add(getLblSelectedCauses());
			appointmentOptionsPanel.add(getBtnRemoveCause());
		}
		return appointmentOptionsPanel;
	}

	private JLabel getLblCheckInTime() {
		if (lblCheckInTime == null) {
			lblCheckInTime = new JLabel("Check-in:");
			lblCheckInTime.setBounds(24, 72, 59, 25);
		}
		return lblCheckInTime;
	}

	private JTextField getTxtCheckInTime() {
		if (txtCheckInTime == null) {
			txtCheckInTime = new JTextField();
			txtCheckInTime.setBounds(81, 74, 60, 20);
			txtCheckInTime.setColumns(10);
		}
		return txtCheckInTime;
	}

	private JLabel getLblCheckOutTime() {
		if (lblCheckOutTime == null) {
			lblCheckOutTime = new JLabel("Check-out:");
			lblCheckOutTime.setBounds(327, 77, 96, 14);
		}
		return lblCheckOutTime;
	}

	private JTextField getTxtCheckOutTime() {
		if (txtCheckOutTime == null) {
			txtCheckOutTime = new JTextField();
			txtCheckOutTime.setBounds(395, 74, 62, 20);
			txtCheckOutTime.setColumns(10);
		}
		return txtCheckOutTime;
	}

	private JButton getBtnCheckInTime() {
		if (btnCheckInTime == null) {
			btnCheckInTime = new JButton("Auto-fill");
			btnCheckInTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTxtCheckInTime().setText(currentTime);
				}
			});
			btnCheckInTime.setBounds(151, 73, 89, 23);
		}
		return btnCheckInTime;
	}

	private JButton getBtnCheckOutTime() {
		if (btnCheckOutTime == null) {
			btnCheckOutTime = new JButton("Auto-fill");
			btnCheckOutTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTxtCheckOutTime().setText(currentTime);
				}
			});
			btnCheckOutTime.setBounds(467, 73, 89, 23);
		}
		return btnCheckOutTime;
	}

	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonsPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			buttonsPanel.add(getBtnExit());
			buttonsPanel.add(getBtnSave());
		}
		return buttonsPanel;
	}

	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
		}
		return btnExit;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			appointment.attended = rdbtnYes.isSelected();
			//TODO: checkin y checkout + causes
		}
		return btnSave;
	}

	private JLabel getLblAttendance() {
		if (lblAttendance == null) {
			lblAttendance = new JLabel("Did the patient attend the appointment?");
			lblAttendance.setBounds(24, 11, 252, 35);
		}
		return lblAttendance;
	}

	private JRadioButton getRdbtnYes() {
		if (rdbtnYes == null) {
			rdbtnYes = new JRadioButton("Yes");
			rdbtnYes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getTxtCheckInTime().setEnabled(true);
					getTxtCheckOutTime().setEnabled(true);
					getBtnCheckOutTime().setEnabled(true);
					getBtnCheckInTime().setEnabled(true);
				}
			});
			rdbtnYes.setSelected(true);
			buttonGroup.add(rdbtnYes);
			;
			rdbtnYes.setBounds(303, 18, 59, 23);
		}
		return rdbtnYes;
	}

	private JRadioButton getRdbtnNo() {
		if (rdbtnNo == null) {
			rdbtnNo = new JRadioButton("No");
			rdbtnNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getTxtCheckInTime().setEnabled(false);
					getTxtCheckOutTime().setEnabled(false);
					getBtnCheckOutTime().setEnabled(false);
					getBtnCheckInTime().setEnabled(false);
				}
			});

			buttonGroup.add(rdbtnNo);
			rdbtnNo.setBounds(372, 18, 59, 23);
		}
		return rdbtnNo;
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
			causesList = new JList<>(model);
		}
		return causesList;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(24, 162, 216, 130);
			scrollPane.setViewportView(getCausesList());
		}
		return scrollPane;
	}

	private JLabel getLblListCauses() {
		if (lblListCauses == null) {
			lblListCauses = new JLabel("Select appointment cause(s):");
			lblListCauses.setBounds(24, 137, 184, 14);
		}
		return lblListCauses;
	}

	private JButton getBtnAddCause() {
		if (btnAddCause == null) {
			btnAddCause = new JButton(">>");
			btnAddCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> causesSelected = getCausesList().getSelectedValuesList();
					for (String cause : causesSelected) {
						if (!finalCauses.contains(cause)) {
							finalCauses.addElement(cause);
						}
					}
					getFinalCausesList().setModel(finalCauses);
				}
			});
			btnAddCause.setBounds(263, 172, 77, 23);
		}
		return btnAddCause;
	}

	private JLabel getLblCustomCause() {
		if (lblCustomCause == null) {
			lblCustomCause = new JLabel("Other cause(s):");
			lblCustomCause.setBounds(24, 310, 275, 14);
		}
		return lblCustomCause;
	}

	private JTextArea getTxtAreaCustomCause() {
		if (txtAreaCustomCause == null) {
			txtAreaCustomCause = new JTextArea();
		}
		return txtAreaCustomCause;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(24, 325, 275, 91);
			scrollPane_1.setViewportView(getTxtAreaCustomCause());
		}
		return scrollPane_1;
	}

	private JButton getBtnAddCustomCause() {
		if (btnAddCustomCause == null) {
			btnAddCustomCause = new JButton("Add");
			btnAddCustomCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String customCause = getTxtAreaCustomCause().getText();
					if (!customCause.isBlank() && !finalCauses.contains(customCause))
						finalCauses.addElement(customCause);
				}
			});
			btnAddCustomCause.setBounds(229, 427, 70, 23);

		}
		return btnAddCustomCause;
	}

	private JList getFinalCausesList() {
		if (finalCausesList == null) {
			finalCausesList = new JList();
		}
		return finalCausesList;
	}

	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(368, 165, 229, 126);
			scrollPane_2.setViewportView(getFinalCausesList());
		}
		return scrollPane_2;
	}

	private JLabel getLblSelectedCauses() {
		if (lblSelectedCauses == null) {
			lblSelectedCauses = new JLabel("Selected cause(s):");
			lblSelectedCauses.setBounds(368, 137, 161, 14);
		}
		return lblSelectedCauses;
	}

	private JButton getBtnRemoveCause() {
		if (btnRemoveCause == null) {
			btnRemoveCause = new JButton("<<");
			btnRemoveCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Get selected items from jList3
					List<String> selectedItems = getFinalCausesList().getSelectedValuesList();
					for (String item : selectedItems) {
						finalCauses.removeElement(item);
					}
				}
			});
			btnRemoveCause.setBounds(263, 221, 77, 23);
		}
		return btnRemoveCause;
	}
}
