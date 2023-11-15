package gui.schedule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import gui.doctor.DoctorAppointmentView;
import gui.medicalRecepcionist.MenuMedicalRecepcionist;
import util.AppointmentBLDto;
import util.ConnectionFactory;

public class Schedule extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel lblDescription;
	private JCalendar calendar;
	private JLabel lblDate;

	private List<AppointmentBLDto> appointments = null;
	private List<AppointmentBLDto> filteredApmnts = null;
	private static int doctorId = 1;

	private JPanel panel;
	private JButton btnBack;
	private JButton btnFilter;
	private JPanel pnAppointments;

	// ***** REFERENCES TO WINDOWS ******
	DoctorAppointmentView doctorView;

	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JLabel lblInfo;
	private JLabel lblOffice;

	private JButton btnOpen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Schedule frame = new Schedule();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void filterAppointments() {
		var filtered = new ArrayList<AppointmentBLDto>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(getCalendar().getDate());
		for (AppointmentBLDto apmnt : appointments) {
			var apDate = apmnt.startDate.split(" ");
			if (apDate[0].equals(format)) {
				filtered.add(apmnt);
			}
		}
		this.filteredApmnts = filtered;
		getPnAppointments().removeAll();
		for (AppointmentBLDto p : filteredApmnts) {
			System.out.println(p);
			getPnAppointments().add(getPnAppointment(p));
		}

		getPnAppointments().invalidate();
		getPnAppointments().validate();
		getPnAppointments().repaint();
	}

	

	/**
	 * Create the frame.
	 */
	public Schedule() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Schedule");
		appointments = ConnectionFactory.getAppointmentsByDoctorId(doctorId);
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
		filterAppointments();
		setBounds(100, 100, 877, 467);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getLeftPanel());
		getContentPane().add(getRightPanel());

	}

	private JPanel getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new JPanel();
			leftPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			leftPanel.setLayout(new BorderLayout(0, 0));
			leftPanel.add(getCalendar());
			leftPanel.add(getLblDate(), BorderLayout.NORTH);
			leftPanel.add(getPanel(), BorderLayout.SOUTH);
		}
		return leftPanel;
	}

	private JPanel getRightPanel() {
		if (rightPanel == null) {
			rightPanel = new JPanel();
			rightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			rightPanel.setLayout(new BorderLayout(0, 0));
			rightPanel.add(getLblDescription(), BorderLayout.NORTH);
			rightPanel.add(getPnAppointments(), BorderLayout.CENTER);

		}
		return rightPanel;
	}

	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel("Selected Days Appointments");
		}
		return lblDescription;
	}

	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBorder(new EmptyBorder(25, 25, 25, 25));
			calendar.setTodayButtonVisible(true);
		}
		return calendar;
	}

	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel("Calendar View");
		}
		return lblDate;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnBack());
			panel.add(getBtnFilter());
		}
		return panel;
	}

	private JButton getBtnBack() {
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

	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter Appointments");
			btnFilter.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					filterAppointments();

				}
			});
		}
		return btnFilter;
	}

	private JPanel getPnAppointments() {
		if (pnAppointments == null) {
			pnAppointments = new JPanel();
			pnAppointments.setBackground(Color.WHITE);
			pnAppointments.setLayout(new GridLayout(0, 1, 0, 0));
	

			pnAppointments.removeAll();
			for (AppointmentBLDto p : filteredApmnts) {
				System.out.println(p);
				pnAppointments.add(getPnAppointment(p));
			}
			pnAppointments.invalidate();
			pnAppointments.validate();
			pnAppointments.repaint();

		}
		return pnAppointments;
	}

	private JPanel getPnAppointment(AppointmentBLDto apmnt) {

		var pnAppointment = new JPanel();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		var apDate = LocalDate.parse(apmnt.startDate.toString(), formatter);
		if (apmnt.urgency)
			pnAppointment.setBackground(new Color(235, 107, 70));
		else
			pnAppointment.setBackground(new Color(159, 234, 124));
		if (apDate.compareTo(LocalDate.now()) < 0) {
			pnAppointment.setBackground(new Color(235, 226, 107));
		}
		pnAppointment.setLayout(new BorderLayout(0, 0));
		pnAppointment.add(new JLabel((apmnt.patientName+" "+apmnt.patientSurname).toUpperCase()),
				BorderLayout.NORTH);
		pnAppointment.add(getInfoPanel(apmnt), BorderLayout.CENTER);
		pnAppointment.add(getBtnOpen(apmnt), BorderLayout.SOUTH);

		return pnAppointment;
	}

	private Component getInfoPanel(AppointmentBLDto a) {
		var panel_2 = new JPanel();
		if (a.urgency)
			panel_2.setBackground(new Color(235, 107, 70));
		else
			panel_2.setBackground(new Color(159, 234, 124));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		var apDate = LocalDate.parse(a.startDate.toString(), formatter);
		if (apDate.compareTo(LocalDate.now()) < 0) {
			panel_2.setBackground(new Color(235, 226, 107));
		}
		panel_2.setLayout(new GridLayout(4, 2, 0, 0));
		panel_2.add(getLblStartDate());
		panel_2.add(new JLabel(a.startDate));
		panel_2.add(getLblEndDate());
		panel_2.add(new JLabel(a.endDate));
		panel_2.add(getLblInfo());
		panel_2.add(new JLabel(a.information));
		panel_2.add(getLblOffice());
		panel_2.add(new JLabel(a.officeCode));
		return panel_2;
	}

	

	private JLabel getLblStartDate() {
		
			lblStartDate = new JLabel("Start Date:");
		
		return lblStartDate;
	}

	private JLabel getLblEndDate() {
		
			lblEndDate = new JLabel("End Date:");
		
		return lblEndDate;
	}

	private JLabel getLblInfo() {
		
			lblInfo = new JLabel("Information:");
		
		return lblInfo;
	}

	private JLabel getLblOffice() {
		
			lblOffice = new JLabel("Office:");
		
		return lblOffice;
	}




	private JButton getBtnOpen(AppointmentBLDto p) {
		
			btnOpen = new JButton("Open");
			btnOpen.setVerticalAlignment(SwingConstants.BOTTOM);
			btnOpen.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doctorView = new DoctorAppointmentView(p);
					doctorView.setVisible(true);
					doctorView.setLocationRelativeTo(null);
				}
			});
		
		return btnOpen;
	}
}
