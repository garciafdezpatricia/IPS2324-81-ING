package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import db.Appointment;
import util.ConnectionFactory;

public class InBoxView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panelPendingOfAssigning;
	private JPanel panelTitle;
	private JLabel lblTitle;
	private JPanel panelAppointments;
	private JPanel panelButtons;
	private JButton btnAssignDoctor;
	private JList listAppointments;
	private int selectedIndex;
	private JPanel panelPendingOfBooking;
	private JPanel panelAppointmentsRequested;
	private JList listAppointmentsRequested;
	private JPanel panelButtonsRequested;
	private JButton btnSeeRequest;
	private JPanel panelTitleRequested;
	private JLabel lblRequestedAppointments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InBoxView frame = new InBoxView();
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
	public InBoxView() {

		setTitle("Inbox");
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getTabbedPane());
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					selectedIndex = tabbedPane.getSelectedIndex();
//					if (selectedIndex == 0) {
//						getListSpecialization().clearSelection();
//
//					}
//					if (selectedIndex == 1) {
//						getListDoctor().clearSelection();
//					}
				}
			});
			tabbedPane.addTab("Pending of assigning doctor", null, getPanelPendingOfAssigning(), null);
			tabbedPane.addTab("Requested", null, getPanelPendingOfBooking(), null);
		}
		return tabbedPane;
	}

	private JPanel getPanelPendingOfAssigning() {
		if (panelPendingOfAssigning == null) {
			panelPendingOfAssigning = new JPanel();
			panelPendingOfAssigning.setLayout(new BorderLayout(0, 0));
			panelPendingOfAssigning.add(getPanelTitle(), BorderLayout.NORTH);
			panelPendingOfAssigning.add(getPanelAppointments(), BorderLayout.CENTER);
			panelPendingOfAssigning.add(getPanelButtons(), BorderLayout.SOUTH);
		}
		return panelPendingOfAssigning;
	}

	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.add(getLblTitle());
		}
		return panelTitle;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Pending appointments");
		}
		return lblTitle;
	}

	private JPanel getPanelAppointments() {
		if (panelAppointments == null) {
			panelAppointments = new JPanel();
			panelAppointments.setLayout(new BorderLayout(0, 0));
			panelAppointments.add(getListAppointments(), BorderLayout.CENTER);
		}
		return panelAppointments;
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnAssignDoctor());
		}
		return panelButtons;
	}

	private JButton getBtnAssignDoctor() {
		if (btnAssignDoctor == null) {
			btnAssignDoctor = new JButton("Assign doctor");
			btnAssignDoctor.setEnabled(false);
			btnAssignDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SelectionOfDoctorView editWp;
					try {
						Appointment app = (Appointment) getListAppointments().getSelectedValue();
						editWp = new SelectionOfDoctorView(app, app.getComments().trim());
						editWp.setVisible(true);
						editWp.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnAssignDoctor;
	}

	private JList getListAppointments() {
		if (listAppointments == null) {
			listAppointments = new JList(ConnectionFactory.getAppointmentsPendingOfAssigning());
			listAppointments.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					btnAssignDoctor.setEnabled(true);
				}
			});
		}
		return listAppointments;
	}
	private JPanel getPanelPendingOfBooking() {
		if (panelPendingOfBooking == null) {
			panelPendingOfBooking = new JPanel();
			panelPendingOfBooking.setLayout(new BorderLayout(0, 0));
			panelPendingOfBooking.add(getPanelAppointmentsRequested());
			panelPendingOfBooking.add(getPanelButtonsRequested(), BorderLayout.SOUTH);
			panelPendingOfBooking.add(getPanelTitleRequested(), BorderLayout.NORTH);
		}
		return panelPendingOfBooking;
	}
	private JPanel getPanelAppointmentsRequested() {
		if (panelAppointmentsRequested == null) {
			panelAppointmentsRequested = new JPanel();
			panelAppointmentsRequested.setLayout(new BorderLayout(0, 0));
			panelAppointmentsRequested.add(getListAppointmentsRequested(), BorderLayout.NORTH);
		}
		return panelAppointmentsRequested;
	}
	private JList getListAppointmentsRequested() {
		if (listAppointmentsRequested == null) {
			listAppointmentsRequested = new JList(ConnectionFactory.getAppointmentsRequested());
		}
		return listAppointmentsRequested;
	}
	private JPanel getPanelButtonsRequested() {
		if (panelButtonsRequested == null) {
			panelButtonsRequested = new JPanel();
			panelButtonsRequested.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtonsRequested.add(getBtnSeeRequest());
		}
		return panelButtonsRequested;
	}
	private JButton getBtnSeeRequest() {
		if (btnSeeRequest == null) {
			btnSeeRequest = new JButton("See request");
			btnSeeRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Appointment a = (Appointment) getListAppointmentsRequested().getSelectedValue();
					try {
						MedicalRecepcionistView mrv = new MedicalRecepcionistView(a);
						mrv.setVisible(true);
						mrv.setLocationRelativeTo(null);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
		}
		return btnSeeRequest;
	}
	private JPanel getPanelTitleRequested() {
		if (panelTitleRequested == null) {
			panelTitleRequested = new JPanel();
			panelTitleRequested.add(getLblRequestedAppointments());
		}
		return panelTitleRequested;
	}
	private JLabel getLblRequestedAppointments() {
		if (lblRequestedAppointments == null) {
			lblRequestedAppointments = new JLabel("Requested appointments");
		}
		return lblRequestedAppointments;
	}
}
