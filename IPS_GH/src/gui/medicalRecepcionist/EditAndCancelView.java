package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import db.Appointment;
import db.Patient;
import util.ConnectionFactory;

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
	private JList listAppointments;
	private JPanel panelFilterByPatient;
	private JLabel lblByPatient;
	private JTextField textFieldPatient;
	private JPanel panelDoctor;
	private JLabel lblDoctor;
	private JTextField textFieldDoctor;
	private JPanel panelUrgent;
	private JCheckBox chckbxUrgent;
	private JCheckBox chckbxNotUrgent;
	private JPanel panelAttended;
	private JCheckBox chckbxCancelled;
	private JCheckBox chckbxNotCancelled;
	private JPanel panelTimeSlot;
	private JLabel lblTimeSlot;
	private JPanel panelOffice;
	private JLabel lblOffice;
	private JTextField textFieldOffice;
	private JPanel panelButtonsReset;
	private JButton btnFilter;
	private JButton btnResetFilters;
	private DefaultListModel<Appointment> appointments = new DefaultListModel<>();
	private DefaultListModel<Appointment> appointmentsBonitos = new DefaultListModel<>();

	private DefaultListModel<Appointment> appointmentsReset = new DefaultListModel<>();
	private JTextField textFieldDay;

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
	 */
	public EditAndCancelView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
			panelFilters.setLayout(new GridLayout(7, 0, 0, 0));
			panelFilters.add(getPanelFilterByPatient());
			panelFilters.add(getPanelDoctor());
			panelFilters.add(getPanelUrgent());
			panelFilters.add(getPanelAttended());
			panelFilters.add(getPanelTimeSlot());
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

	private JList getListAppointments() {
		if (listAppointments == null) {
			try {
				listAppointments = new JList(ConnectionFactory.getAppointments());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listAppointments;
	}

	private JPanel getPanelFilterByPatient() {
		if (panelFilterByPatient == null) {
			panelFilterByPatient = new JPanel();
			panelFilterByPatient.setLayout(new GridLayout(1, 0, 0, 0));
			panelFilterByPatient.add(getLblByPatient());
			panelFilterByPatient.add(getTextFieldPatient());
		}
		return panelFilterByPatient;
	}

	private JLabel getLblByPatient() {
		if (lblByPatient == null) {
			lblByPatient = new JLabel("By patient:");
		}
		return lblByPatient;
	}

	private JTextField getTextFieldPatient() {
		if (textFieldPatient == null) {
			textFieldPatient = new JTextField();
			textFieldPatient.setColumns(10);
		}
		return textFieldPatient;
	}

	private JPanel getPanelDoctor() {
		if (panelDoctor == null) {
			panelDoctor = new JPanel();
			panelDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelDoctor.add(getLblDoctor());
			panelDoctor.add(getTextFieldDoctor());
		}
		return panelDoctor;
	}

	private JLabel getLblDoctor() {
		if (lblDoctor == null) {
			lblDoctor = new JLabel("By doctor:");
		}
		return lblDoctor;
	}

	private JTextField getTextFieldDoctor() {
		if (textFieldDoctor == null) {
			textFieldDoctor = new JTextField();
			textFieldDoctor.setColumns(10);
		}
		return textFieldDoctor;
	}

	private JPanel getPanelUrgent() {
		if (panelUrgent == null) {
			panelUrgent = new JPanel();
			panelUrgent.setLayout(new GridLayout(0, 2, 0, 0));
			panelUrgent.add(getChckbxUrgent());
			panelUrgent.add(getChckbxNotUrgent());
		}
		return panelUrgent;
	}

	private JCheckBox getChckbxUrgent() {
		if (chckbxUrgent == null) {
			chckbxUrgent = new JCheckBox("Urgent");
		}
		return chckbxUrgent;
	}

	private JCheckBox getChckbxNotUrgent() {
		if (chckbxNotUrgent == null) {
			chckbxNotUrgent = new JCheckBox("Not urgent");
		}
		return chckbxNotUrgent;
	}

	private JPanel getPanelAttended() {
		if (panelAttended == null) {
			panelAttended = new JPanel();
			panelAttended.setLayout(new GridLayout(1, 0, 0, 0));
			panelAttended.add(getChckbxCancelled());
			panelAttended.add(getChckbxNotCancelled());
		}
		return panelAttended;
	}

	private JCheckBox getChckbxCancelled() {
		if (chckbxCancelled == null) {
			chckbxCancelled = new JCheckBox("Cancelled");
		}
		return chckbxCancelled;
	}

	private JCheckBox getChckbxNotCancelled() {
		if (chckbxNotCancelled == null) {
			chckbxNotCancelled = new JCheckBox("Not cancelled");
		}
		return chckbxNotCancelled;
	}

	private JPanel getPanelTimeSlot() {
		if (panelTimeSlot == null) {
			panelTimeSlot = new JPanel();
			panelTimeSlot.setLayout(new GridLayout(1, 0, 0, 0));
			panelTimeSlot.add(getLblTimeSlot());
			panelTimeSlot.add(getTextFieldDay());
		}
		return panelTimeSlot;
	}

	private JLabel getLblTimeSlot() {
		if (lblTimeSlot == null) {
			lblTimeSlot = new JLabel("By time:");
		}
		return lblTimeSlot;
	}

	private JPanel getPanelOffice() {
		if (panelOffice == null) {
			panelOffice = new JPanel();
			panelOffice.setLayout(new GridLayout(1, 0, 0, 0));
			panelOffice.add(getLblOffice());
			panelOffice.add(getTextFieldOffice());

		}
		return panelOffice;
	}

	private JLabel getLblOffice() {
		if (lblOffice == null) {
			lblOffice = new JLabel("By office:");
		}
		return lblOffice;
	}

	private JTextField getTextFieldOffice() {
		if (textFieldOffice == null) {
			textFieldOffice = new JTextField();
			textFieldOffice.setColumns(10);
		}
		return textFieldOffice;
	}

	private JPanel getPanelButtonsReset() {
		if (panelButtonsReset == null) {
			panelButtonsReset = new JPanel();
			panelButtonsReset.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtonsReset.add(getBtnFilter());
			panelButtonsReset.add(getBtnResetFilters());
		}
		return panelButtonsReset;
	}

	private JButton getBtnFilter() {

		if (btnFilter == null) {
			btnFilter = new JButton("Filter");

			btnFilter.addActionListener(new ActionListener() {
				DefaultListModel<Appointment> aux = appointments;

				public void actionPerformed(ActionEvent e) {
					// canceladas y no canceladas y no urgentes y urgentes
					if ((!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && !chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected())
							|| (chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected()
									&& chckbxCancelled.isSelected() && chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}

					}
					// solo urgente
					else if (chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && !chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// solo no urgente
					else if (!chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected() && !chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// urgentes y no urgentes
					else if ((chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected() && !chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected())
							|| (!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
									&& !chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// solo canceladas
					else if (!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// solo no canceladas
					else if (!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
							&& !chckbxCancelled.isSelected() && chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
					// canceladas y no canceladas
					else if ((!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
							&& chckbxCancelled.isSelected() && chckbxNotCancelled.isSelected())
							|| (!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
									&& !chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if ((getTextFieldDoctor().getText().equals("")
									|| doctor.toLowerCase().equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
					// urgentes y canceladas
					else if (chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// urgentes y no canceladas
					else if (chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// no urgentes y canceladas
					else if (!chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// no urgentes y no canceladas
					else if (!chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& !chckbxNotCancelled.isSelected()) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled") && app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// urgentes y no urgentes y canceladas
					else if ((!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
							&& chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())
							|| (chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected()
									&& chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// urgentes y no urgentes y no canceladas
					else if ((!chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
							&& chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())
							|| (chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected()
									&& chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (!app.getStatus().toLowerCase().equals("cancelled")
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// canceladas y no canceladas y urgentes
					else if ((chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& chckbxNotCancelled.isSelected())
							|| (chckbxUrgent.isSelected() && !chckbxNotUrgent.isSelected()
									&& !chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getUrgency() == 1
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					// canceladas y no canceladas y no urgentes
					else if ((!chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected() && chckbxCancelled.isSelected()
							&& chckbxNotCancelled.isSelected())
							|| (!chckbxUrgent.isSelected() && chckbxNotUrgent.isSelected()
									&& !chckbxCancelled.isSelected() && !chckbxNotCancelled.isSelected())) {
						for (int i = 0; i < getListAppointments().getModel().getSize(); i++) {
							Appointment app = (Appointment) (getListAppointments().getModel().getElementAt(i));

							String patient = "";
							try {
								patient = ConnectionFactory.getPatient(app.getPatientid());
							} catch (Exception e1) {
							}

							String doctor = "";
							try {
								doctor = ConnectionFactory.getDoctor(app.getDoctorid());
							} catch (Exception e1) {
							}

							String office = "";
							try {
								office = ConnectionFactory.getOffice(app.getOfficeId());
							} catch (Exception e1) {
							}
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");

							// si no se filtra por dia
							if (app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								aux.addElement(app);
							}
							// si se filtra por dia
							if (app.getUrgency() == 0
									&& (getTextFieldDoctor().getText().equals("") || doctor.toLowerCase()
											.equals(getTextFieldDoctor().getText().toLowerCase()))
									&& (getTextFieldPatient().getText().equals("") || patient.toLowerCase()
											.equals(getTextFieldPatient().getText().toLowerCase()))
									&& (!getTextFieldDay().getText().equals(""))
									&& (getTextFieldOffice().getText().equals("") || office.toLowerCase()
											.equals(getTextFieldOffice().getText().toLowerCase()))) {
								try {
									if (sdf3.parse(app.getStartdate())
											.equals(sdf3.parse(getTextFieldDay().getText()))) {
										aux.addElement(app);

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}

					getListAppointments().setModel(aux);

				}

			});
		}
		return btnFilter;
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

	private JTextField getTextFieldDay() {
		if (textFieldDay == null) {
			textFieldDay = new JTextField();
			textFieldDay.setColumns(10);
		}
		return textFieldDay;
	}
}
