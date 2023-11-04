package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import db.Appointment;
import db.Patient;
import gui.medicalRecepcionist.filters.FilterDoctorView;
import gui.medicalRecepcionist.filters.FilterOfficeView;
import gui.medicalRecepcionist.filters.FilterPatientView;
import gui.medicalRecepcionist.filters.FilterDayView;
import util.ConnectionFactory;
import javax.swing.JComboBox;

public class EditAndCancelView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelButtons;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnCancel;
	private JPanel panelNorth;
	private JPanel panelFilters;
	private JScrollPane scrollPaneAppointments;
	private JList<Appointment> listAppointments;
	private JPanel panelFilterByPatient;
	private JLabel lblByPatient;
	private JPanel panelDoctor;
	private JLabel lblDoctor;
	private JPanel panelUrgent;
	private JPanel panelAttended;
	private JPanel panelDay;
	private JLabel lblTimeSlot;
	private JPanel panelOffice;
	private JLabel lblOffice;
	private JPanel panelButtonsReset;
	private JButton btnFilter;
	private JButton btnResetFilters;
	private DefaultListModel<Appointment> appointments = new DefaultListModel<>();
	private DefaultListModel<Appointment> appointmentsBonitos = new DefaultListModel<>();

	private DefaultListModel<Appointment> appointmentsReset = new DefaultListModel<>();
	private JButton btnFilterPatient;

	private FilterPatientView filterPatient;
	private JButton btnDoctor;
	protected FilterDoctorView filterDoctor;
	private JButton btnUrgent;
	private JButton btnNotUrgent;
	private JButton btnCancelled;
	private JButton btnNotCancelled;
	private JButton btnOffice;
	private JButton btnDay;
	protected FilterDayView filterTime;
	private JPanel panelTime;
	private JLabel lblTime;
	private JButton btnTimeFilter;
	protected FilterOfficeView filterOffice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAndCancelView frame = new EditAndCancelView();
					frame.setLocationRelativeTo(null);

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public EditAndCancelView() throws Exception {
		appointments = ConnectionFactory.getAppointments();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
		setTitle("Edit or cancel appointment");
//		try {
//			appointments = ConnectionFactory.getAppointments();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelNorth(), BorderLayout.CENTER);

	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnCancel());
			panelButtons.add(getBtnEdit());
			panelButtons.add(getBtnRemove());
		}
		return panelButtons;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Edit");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getListAppointments().getSelectedValuesList().size() > 1) {
						JOptionPane.showMessageDialog(EditAndCancelView.this,
								"You can only edit one appointment each time.", "Warning",
								JOptionPane.INFORMATION_MESSAGE);
					}
					Appointment app = (Appointment) getListAppointments().getSelectedValue();
					if (app.getStatus().toLowerCase().equals("cancelled")) {
						JOptionPane.showMessageDialog(EditAndCancelView.this, "The appointment is cancelled", "Warning",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						EditAppointmentView mr;
						try {
							mr = new EditAppointmentView(app.getId(), app.getDoctorid(), app.getPatientid(),
									app.getOfficeId(), app.getUrgency(), app.getInformation(), app.getStartdate(),
									app.getEnddate());
							mr.setVisible(true);
							mr.setLocationRelativeTo(null);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			});
		}
		return btnEdit;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Cancel Appointment");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < getListAppointments().getSelectedValuesList().size(); i++) {
						ConnectionFactory.RemoveAppointment(
								(Appointment) (getListAppointments().getSelectedValuesList().get(i)));
						JOptionPane.showMessageDialog(EditAndCancelView.this, "The appointment has been removed.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnRemove;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setBackground(new Color(255, 0, 0));
		}
		return btnCancel;
	}

	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new GridLayout(1, 0, 0, 0));
			panelNorth.add(getPanelFilters());
			panelNorth.add(getScrollPaneAppointments());
		}
		return panelNorth;
	}

	private JPanel getPanelFilters() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters
					.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelFilters.setLayout(new GridLayout(8, 0, 0, 0));
			panelFilters.add(getPanelFilterByPatient());
			panelFilters.add(getPanelDoctor());
			panelFilters.add(getPanelUrgent());
			panelFilters.add(getPanelAttended());
			panelFilters.add(getPanelDay());
			panelFilters.add(getPanelTime());
			panelFilters.add(getPanelOffice());
			panelFilters.add(getPanelButtonsReset());
		}
		return panelFilters;
	}

	private JScrollPane getScrollPaneAppointments() {
		if (scrollPaneAppointments == null) {
			scrollPaneAppointments = new JScrollPane();
			scrollPaneAppointments.setViewportView(getListAppointments());
		}
		return scrollPaneAppointments;
	}

	public JList<Appointment> getListAppointments() {
		if (listAppointments == null) {
			listAppointments = new JList<Appointment>(appointments);
		}

		return listAppointments;
	}

	private DefaultListModel<Appointment> getFutureAppointments(DefaultListModel<Appointment> appointments2) {
		DefaultListModel<Appointment> res = new DefaultListModel<Appointment>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < appointments2.size(); i++) {
			try {
				if (dateFormat.parse(appointments2.get(i).getStartdate()).after(new Date())) {
					res.addElement(appointments2.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	private JPanel getPanelFilterByPatient() {
		if (panelFilterByPatient == null) {
			panelFilterByPatient = new JPanel();
			panelFilterByPatient.setLayout(new GridLayout(1, 0, 0, 0));
			panelFilterByPatient.add(getLblByPatient());
			panelFilterByPatient.add(getBtnFilterPatient());
		}
		return panelFilterByPatient;
	}

	private JLabel getLblByPatient() {
		if (lblByPatient == null) {
			lblByPatient = new JLabel("By patient:");
		}
		return lblByPatient;
	}

	private JPanel getPanelDoctor() {
		if (panelDoctor == null) {
			panelDoctor = new JPanel();
			panelDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelDoctor.add(getLblDoctor());
			panelDoctor.add(getBtnDoctor());
		}
		return panelDoctor;
	}

	private JLabel getLblDoctor() {
		if (lblDoctor == null) {
			lblDoctor = new JLabel("By doctor:");
		}
		return lblDoctor;
	}

	private JPanel getPanelUrgent() {
		if (panelUrgent == null) {
			panelUrgent = new JPanel();
			panelUrgent.setLayout(new GridLayout(0, 2, 0, 0));
			panelUrgent.add(getBtnUrgent());
			panelUrgent.add(getBtnNotUrgent());
		}
		return panelUrgent;
	}

	private JPanel getPanelAttended() {
		if (panelAttended == null) {
			panelAttended = new JPanel();
			panelAttended.setLayout(new GridLayout(1, 0, 0, 0));
			panelAttended.add(getBtnCancelled());
			panelAttended.add(getBtnNotCancelled());
		}
		return panelAttended;
	}

	private JPanel getPanelDay() {
		if (panelDay == null) {
			panelDay = new JPanel();
			panelDay.setLayout(new GridLayout(1, 0, 0, 0));
			panelDay.add(getLblTimeSlot());
			panelDay.add(getBtnDay());
		}
		return panelDay;
	}

	private JLabel getLblTimeSlot() {
		if (lblTimeSlot == null) {
			lblTimeSlot = new JLabel("By days:");
		}
		return lblTimeSlot;
	}

	private JPanel getPanelOffice() {
		if (panelOffice == null) {
			panelOffice = new JPanel();
			panelOffice.setLayout(new GridLayout(1, 0, 0, 0));
			panelOffice.add(getLblOffice());
			panelOffice.add(getBtnOffice());

		}
		return panelOffice;
	}

	private JLabel getLblOffice() {
		if (lblOffice == null) {
			lblOffice = new JLabel("By office:");
		}
		return lblOffice;
	}

	private JPanel getPanelButtonsReset() {
		if (panelButtonsReset == null) {
			panelButtonsReset = new JPanel();
			panelButtonsReset.setLayout(new GridLayout(1, 0, 0, 0));
//			panelButtonsReset.add(getBtnFilter());
			panelButtonsReset.add(getBtnResetFilters());
		}
		return panelButtonsReset;
	}


	private JButton getBtnResetFilters() {
		if (btnResetFilters == null) {
			btnResetFilters = new JButton("Reset filters");
			btnResetFilters.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getListAppointments().setModel(ConnectionFactory.getAppointments());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnResetFilters;
	}

	private JButton getBtnFilterPatient() {
		if (btnFilterPatient == null) {
			btnFilterPatient = new JButton("Filter");
			btnFilterPatient.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						filterPatient = new FilterPatientView(
								(DefaultListModel<Appointment>) getListAppointments().getModel());
						filterPatient.setModal(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					filterPatient.setVisible(true);
				}
			});
		}
		return btnFilterPatient;
	}

	private JButton getBtnDoctor() {
		if (btnDoctor == null) {
			btnDoctor = new JButton("Filter");
			btnDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						filterDoctor = new FilterDoctorView(
								(DefaultListModel<Appointment>) getListAppointments().getModel());
						filterDoctor.setModal(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					filterDoctor.setVisible(true);
				}
			});
		}
		return btnDoctor;
	}

	private JButton getBtnUrgent() {
		if (btnUrgent == null) {
			btnUrgent = new JButton("Urgent");
			btnUrgent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> a = new DefaultListModel<>();
					for (int i = 0; i < appointments.getSize(); i++) {
						a.addElement(appointments.get(i));
					}

					appointments.clear();
					for (int i = 0; i < a.getSize(); i++) {
						Appointment app = a.getElementAt(i);
						if (app.getUrgency() == 1) {
							appointments.addElement(a.get(i));;
						}
					}
				}
			});
		}
		return btnUrgent;
	}

	private JButton getBtnNotUrgent() {
		if (btnNotUrgent == null) {
			btnNotUrgent = new JButton("Not urgent");
			btnNotUrgent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> a = new DefaultListModel<>();
					for (int i = 0; i < appointments.getSize(); i++) {
						a.addElement(appointments.get(i));
					}

					appointments.clear();
					for (int i = 0; i < a.getSize(); i++) {
						Appointment app = a.getElementAt(i);
						if (app.getUrgency() == 0) {
							appointments.addElement(a.get(i));;
						}
					}
				}
			});
		}
		return btnNotUrgent;
	}
	private JButton getBtnCancelled() {
		if (btnCancelled == null) {
			btnCancelled = new JButton("Cancelled");
			btnCancelled.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> a = new DefaultListModel<>();
					for (int i = 0; i < appointments.getSize(); i++) {
						a.addElement(appointments.get(i));
					}

					appointments.clear();
					for (int i = 0; i < a.getSize(); i++) {
						Appointment app = a.getElementAt(i);
						if (app.getStatus().toLowerCase().equals("cancelled")) {
							appointments.addElement(a.get(i));;
						}
					}
				}
			});
		}
		return btnCancelled;
	}
	private JButton getBtnNotCancelled() {
		if (btnNotCancelled == null) {
			btnNotCancelled = new JButton("Not Cancelled");
			btnNotCancelled.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> a = new DefaultListModel<>();
					for (int i = 0; i < appointments.getSize(); i++) {
						a.addElement(appointments.get(i));
					}

					appointments.clear();
					for (int i = 0; i < a.getSize(); i++) {
						Appointment app = a.getElementAt(i);
						if (!app.getStatus().toLowerCase().equals("cancelled")) {
							appointments.addElement(a.get(i));;
						}
					}
				}
			});
		}
		return btnNotCancelled;
	}
	private JButton getBtnOffice() {
		if (btnOffice == null) {
			btnOffice = new JButton("Filter");
			btnOffice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						filterOffice = new FilterOfficeView(
								(DefaultListModel<Appointment>) getListAppointments().getModel());
						filterOffice.setModal(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					filterOffice.setVisible(true);
				}
			});
		}
		return btnOffice;
	}
	private JButton getBtnDay() {
		if (btnDay == null) {
			btnDay = new JButton("Filter");
			btnDay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						filterTime = new FilterDayView(
								(DefaultListModel<Appointment>) getListAppointments().getModel());
						filterTime.setModal(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					filterTime.setVisible(true);
				}
			});
		}
		return btnDay;
	}
	private JPanel getPanelTime() {
		if (panelTime == null) {
			panelTime = new JPanel();
			panelTime.setLayout(new GridLayout(0, 2, 0, 0));
			panelTime.add(getLblTime());
			panelTime.add(getBtnTimeFilter());
		}
		return panelTime;
	}
	private JLabel getLblTime() {
		if (lblTime == null) {
			lblTime = new JLabel("By time:");
		}
		return lblTime;
	}
	private JButton getBtnTimeFilter() {
		if (btnTimeFilter == null) {
			btnTimeFilter = new JButton("Filter");
		}
		return btnTimeFilter;
	}
}
