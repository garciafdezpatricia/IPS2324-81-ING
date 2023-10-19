package gui.workPeriod;

import java.awt.BorderLayout;
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
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import db.WorkDay;
import db.WorkPeriod;
import util.ConnectionFactory;

public class WorkPeriodView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_north;
	private JLabel lblIntroduceWork;
	private JPanel panel_south;
	private JButton btnFinish;
	private JPanel panel_center;
	private JPanel panel_center_center;
	private JLabel lblMonday;
	private JTextField txtMondaySt;
	private JTextField txtMondayEnd;
	private JLabel lblTuesday;
	private JTextField txtTuesdaySt;
	private JTextField txtTuesdayEnd;
	private JLabel lblWednesday;
	private JTextField txtWednesdaySt;
	private JTextField txtWednesdayEnd;
	private JLabel lblThursday;
	private JTextField txtThursdaySt;
	private JTextField txtThursdayEnd;
	private JLabel lblFriday;
	private JTextField txtFridaySt;
	private JTextField txtFridayEnd;
	private JLabel lblSaturday;
	private JTextField txtSaturdaySt;
	private JTextField txtSaturdayEnd;
	private JLabel lblSunday;
	private JTextField txtSundaySt;
	private JTextField txtSundayEnd;
	private JPanel panel_center_north;
	private JLabel lblFirstDay;
	private JTextField txtFirstDay;
	private JLabel lblLastDay;
	private JTextField txtLastDay;
	private JLabel lblClockOutTime;
	private JLabel lblClockInTime;
	private JLabel lblEmpty;
	private IdentificationWindow iw;
	private JButton btnDoctor;

	private WorkPeriod wp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkPeriodView frame = new WorkPeriodView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WorkPeriodView() {
		iw = new IdentificationWindow();

		setIconImage(Toolkit.getDefaultToolkit().getImage(WorkPeriodView.class.getResource("/img/descarga.jpg")));
		setTitle("Work period");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 702, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_north(), BorderLayout.NORTH);
		contentPane.add(getPanel_south(), BorderLayout.SOUTH);
		contentPane.add(getPanel_center(), BorderLayout.CENTER);
	}

	private JPanel getPanel_north() {
		if (panel_north == null) {
			panel_north = new JPanel();
			panel_north.add(getLblIntroduceWork());
		}
		return panel_north;
	}

	private JLabel getLblIntroduceWork() {
		if (lblIntroduceWork == null) {
			lblIntroduceWork = new JLabel("Introduce the work period time and the hours of work");
			lblIntroduceWork.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblIntroduceWork;
	}

	private JPanel getPanel_south() {
		if (panel_south == null) {
			panel_south = new JPanel();
			panel_south.setLayout(new GridLayout(1, 2, 0, 0));
			panel_south.add(getBtnDoctor());
			panel_south.add(getBtnFinish());
		}
		return panel_south;
	}

	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish");
			btnFinish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!checkFinish())
						JOptionPane.showMessageDialog(null, "All fields must be filled out before finishing.");
					else if (!iw.isDoctorSelected())
						JOptionPane.showMessageDialog(null, "A doctor must be selected before finishing.");
					else {
						try {
							createWorkPeriod();
							List<WorkDay> wds = createWorkDays();

							// save to database
							saveToDB(wds);

							// close the window
							dispose();
						} catch (Exception pe) {
							pe.printStackTrace();
						}
					}
				}
			});
		}
		return btnFinish;
	}

	private void saveToDB(List<WorkDay> wds) {
		try {
			System.out.println(wp.getStartDate());
			System.out.println(wp.getEndDate());
			
			System.out.println(wp.toString());

			ConnectionFactory.createWorkPeriod(wp.getId(), wp.getStartDate(), wp.getEndDate(), wp.getId_doctor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (WorkDay wd : wds) {
			ConnectionFactory.createWorkDay(wd.getId(), wd.getWeekday(), wd.getStartHour(), wd.getEndHour(),
					wd.getWorkperiodid(),  wp.getId_doctor());
		}
	}

	/**
	 * public WorkDay(int id, String weekday, String startHour, String endHour, int
	 * workperiodid)
	 */
	private List<WorkDay> createWorkDays() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		List<WorkDay> aux = new ArrayList<WorkDay>();

		BigInteger id = generateID();
		BigInteger workperiodid = wp.getId();

		// monday
		String startHour = getTxtMondaySt().getText();
		String endHour = getTxtMondayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Monday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Monday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay monday = new WorkDay(id, "Monday", startHour, endHour, workperiodid);

				aux.add(monday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// tuesday
		startHour = getTxtTuesdaySt().getText();
		endHour = getTxtTuesdayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Tuesday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Tuesday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay tuesday = new WorkDay(id, "Tuesday", startHour, endHour, workperiodid);

				aux.add(tuesday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// wednesday
		startHour = getTxtWednesdaySt().getText();
		endHour = getTxtWednesdayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Wednesday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Wednesday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay wednesday = new WorkDay(id, "Wednesday", startHour, endHour, workperiodid);

				aux.add(wednesday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// thursday
		startHour = getTxtThursdaySt().getText();
		endHour = getTxtThursdayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Thursday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Thursday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay thursday = new WorkDay(id, "Thursday", startHour, endHour, workperiodid);

				aux.add(thursday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// friday
		startHour = getTxtFridaySt().getText();
		endHour = getTxtFridayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Friday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Friday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay friday = new WorkDay(id, "Friday", startHour, endHour, workperiodid);

				aux.add(friday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// saturday
		startHour = getTxtSaturdaySt().getText();
		endHour = getTxtSaturdayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Saturday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Saturday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay saturday = new WorkDay(id, "Saturday", startHour, endHour, workperiodid);

				aux.add(saturday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sunday
		startHour = getTxtSundaySt().getText();
		endHour = getTxtSundayEnd().getText();
		try {
			if (startHour.trim().isEmpty() || endHour.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sunday: clock-out time and clock-in time must be defined.");
			}
			else if (sdf.parse(startHour + ":00").after(sdf.parse(endHour + ":00"))) {
				JOptionPane.showMessageDialog(null, "Sunday: clock-out time must be later than clock-in time.");
			} else {
				WorkDay sunday = new WorkDay(id, "Sunday", startHour, endHour, workperiodid);

				aux.add(sunday);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return aux;
	}

	/**
	 * public WorkPeriod(int id, Date startDate, Date endDate, int id_doctor)
	 */
	private BigInteger createWorkPeriod() {
		// ID
		BigInteger id = generateID();

		// startDate
		String startDate = getTxtFirstDay().getText();
		String endDate = getTxtLastDay().getText();

		System.out.println(startDate);
		System.out.println(getTxtFirstDay().getText());

		System.out.println(endDate);
		System.out.println(getTxtLastDay().getText());

		//esta bien

		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date start = null;
		java.util.Date end = null;
		java.sql.Date startSQL = null;
		java.sql.Date endSQL = null;

		try {
			start = format.parse(startDate);
			end = format.parse(endDate);
			startSQL = new java.sql.Date(start.getTime());
			endSQL = new java.sql.Date(end.getTime());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "The format of the date is not correct.");
		}

		// id_doctor
		BigInteger id_doctor = iw.getSelectedDoctor().getId();

		if (startSQL != null && endSQL != null) {
			wp = new WorkPeriod(id, startSQL, endSQL, id_doctor);
		}

		return id;
	}

	public static BigInteger generateID() {
		Random random = new Random();
		int number = 0;
		int c = random.nextInt(29);
		StringBuilder sb = new StringBuilder("1"); // Starting at 1

		for (int i = 1; i < c; i++) {
			number = random.nextInt(9) + 1;
			sb.append(number);
		}

		return new BigInteger(sb.toString());
	}

	private JPanel getPanel_center() {
		if (panel_center == null) {
			panel_center = new JPanel();
			panel_center.setLayout(new BorderLayout(0, 0));
			panel_center.add(getPanel_center_center(), BorderLayout.CENTER);
			panel_center.add(getPanel_center_north(), BorderLayout.NORTH);
		}
		return panel_center;
	}

	private JPanel getPanel_center_center() {
		if (panel_center_center == null) {
			panel_center_center = new JPanel();
			panel_center_center.setBorder(
					new TitledBorder(null, "In time and out time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_center_center.setLayout(new GridLayout(0, 3, 0, 0));

			// 24 components
			panel_center_center.add(getLblEmpty()); // 0
			panel_center_center.add(getLblClockInTime()); // 1
			panel_center_center.add(getLblClockOutTime()); // 2
			panel_center_center.add(getLblMonday_1()); // 3
			panel_center_center.add(getTxtMondaySt()); // 4
			panel_center_center.add(getTxtMondayEnd()); // 5
			panel_center_center.add(getLblTuesday_1()); // 6
			panel_center_center.add(getTxtTuesdaySt()); // 7
			panel_center_center.add(getTxtTuesdayEnd()); // 8
			panel_center_center.add(getLblWednesday_1()); // 9
			panel_center_center.add(getTxtWednesdaySt()); // 10
			panel_center_center.add(getTxtWednesdayEnd()); // 11
			panel_center_center.add(getLblThursday_1()); // 12
			panel_center_center.add(getTxtThursdaySt()); // 13
			panel_center_center.add(getTxtThursdayEnd()); // 14
			panel_center_center.add(getLblFriday_1()); // 15
			panel_center_center.add(getTxtFridaySt()); // 16
			panel_center_center.add(getTxtFridayEnd()); // 17
			panel_center_center.add(getLblSaturday_1()); // 18
			panel_center_center.add(getTxtSaturdaySt()); // 19
			panel_center_center.add(getTxtSaturdayEnd()); // 20
			panel_center_center.add(getLblSunday_1()); // 21
			panel_center_center.add(getTxtSundaySt()); // 22
			panel_center_center.add(getTxtSundayEnd()); // 23
		}
		return panel_center_center;
	}

	private JLabel getLblMonday_1() {
		if (lblMonday == null) {
			lblMonday = new JLabel("Monday");
			lblMonday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblMonday;
	}

	private JTextField getTxtMondaySt() {
		if (txtMondaySt == null) {
			txtMondaySt = new JTextField();
			txtMondaySt.setColumns(10);
		}
		return txtMondaySt;
	}

	private JTextField getTxtMondayEnd() {
		if (txtMondayEnd == null) {
			txtMondayEnd = new JTextField();
			txtMondayEnd.setColumns(10);
		}
		return txtMondayEnd;
	}

	private JLabel getLblTuesday_1() {
		if (lblTuesday == null) {
			lblTuesday = new JLabel("Tuesday");
			lblTuesday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblTuesday;
	}

	private JTextField getTxtTuesdaySt() {
		if (txtTuesdaySt == null) {
			txtTuesdaySt = new JTextField();
			txtTuesdaySt.setColumns(10);
		}
		return txtTuesdaySt;
	}

	private JTextField getTxtTuesdayEnd() {
		if (txtTuesdayEnd == null) {
			txtTuesdayEnd = new JTextField();
			txtTuesdayEnd.setColumns(10);
		}
		return txtTuesdayEnd;
	}

	private JLabel getLblWednesday_1() {
		if (lblWednesday == null) {
			lblWednesday = new JLabel("Wednesday");
			lblWednesday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblWednesday;
	}

	private JTextField getTxtWednesdaySt() {
		if (txtWednesdaySt == null) {
			txtWednesdaySt = new JTextField();
			txtWednesdaySt.setColumns(10);
		}
		return txtWednesdaySt;
	}

	private JTextField getTxtWednesdayEnd() {
		if (txtWednesdayEnd == null) {
			txtWednesdayEnd = new JTextField();
			txtWednesdayEnd.setColumns(10);
		}
		return txtWednesdayEnd;
	}

	private JLabel getLblThursday_1() {
		if (lblThursday == null) {
			lblThursday = new JLabel("Thursday");
			lblThursday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblThursday;
	}

	private JTextField getTxtThursdaySt() {
		if (txtThursdaySt == null) {
			txtThursdaySt = new JTextField();
			txtThursdaySt.setColumns(10);
		}
		return txtThursdaySt;
	}

	private JTextField getTxtThursdayEnd() {
		if (txtThursdayEnd == null) {
			txtThursdayEnd = new JTextField();
			txtThursdayEnd.setColumns(10);
		}
		return txtThursdayEnd;
	}

	private JLabel getLblFriday_1() {
		if (lblFriday == null) {
			lblFriday = new JLabel("Friday");
			lblFriday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblFriday;
	}

	private JTextField getTxtFridaySt() {
		if (txtFridaySt == null) {
			txtFridaySt = new JTextField();
			txtFridaySt.setColumns(10);
		}
		return txtFridaySt;
	}

	private JTextField getTxtFridayEnd() {
		if (txtFridayEnd == null) {
			txtFridayEnd = new JTextField();
			txtFridayEnd.setColumns(10);
		}
		return txtFridayEnd;
	}

	private JLabel getLblSaturday_1() {
		if (lblSaturday == null) {
			lblSaturday = new JLabel("Saturday");
			lblSaturday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblSaturday;
	}

	private JTextField getTxtSaturdaySt() {
		if (txtSaturdaySt == null) {
			txtSaturdaySt = new JTextField();
			txtSaturdaySt.setColumns(10);
		}
		return txtSaturdaySt;
	}

	private JTextField getTxtSaturdayEnd() {
		if (txtSaturdayEnd == null) {
			txtSaturdayEnd = new JTextField();
			txtSaturdayEnd.setColumns(10);
		}
		return txtSaturdayEnd;
	}

	private JLabel getLblSunday_1() {
		if (lblSunday == null) {
			lblSunday = new JLabel("Sunday");
			lblSunday.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblSunday;
	}

	private JTextField getTxtSundaySt() {
		if (txtSundaySt == null) {
			txtSundaySt = new JTextField();
			txtSundaySt.setColumns(10);
		}
		return txtSundaySt;
	}

	private JTextField getTxtSundayEnd() {
		if (txtSundayEnd == null) {
			txtSundayEnd = new JTextField();
			txtSundayEnd.setColumns(10);
		}
		return txtSundayEnd;
	}

	private JPanel getPanel_center_north() {
		if (panel_center_north == null) {
			panel_center_north = new JPanel();
			panel_center_north.setBorder(
					new TitledBorder(null, "Period days", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_center_north.setLayout(new GridLayout(0, 2, 0, 0));
			panel_center_north.add(getLblFirstDay());
			panel_center_north.add(getTxtFirstDay());
			panel_center_north.add(getLblLastDay());
			panel_center_north.add(getTxtLastDay());
		}
		return panel_center_north;
	}

	private JLabel getLblFirstDay() {
		if (lblFirstDay == null) {
			lblFirstDay = new JLabel("First day of work period (dd/mm/yyyy)");
			lblFirstDay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblFirstDay;
	}

	private JTextField getTxtFirstDay() {
		if (txtFirstDay == null) {
			txtFirstDay = new JTextField();
			txtFirstDay.setColumns(10);
		}
		return txtFirstDay;
	}

	private JLabel getLblLastDay() {
		if (lblLastDay == null) {
			lblLastDay = new JLabel("Last day of work period (dd/mm/yyyy)");
			lblLastDay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return lblLastDay;
	}

	private JTextField getTxtLastDay() {
		if (txtLastDay == null) {
			txtLastDay = new JTextField();
			txtLastDay.setColumns(10);
		}
		return txtLastDay;
	}

	private JLabel getLblClockOutTime() {
		if (lblClockOutTime == null) {
			lblClockOutTime = new JLabel("Clock-out time (hh:mm)");
			lblClockOutTime.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblClockOutTime;
	}

	private JLabel getLblClockInTime() {
		if (lblClockInTime == null) {
			lblClockInTime = new JLabel("Clock-in time (hh:mm)");
			lblClockInTime.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblClockInTime;
	}

	private JLabel getLblEmpty() {
		if (lblEmpty == null) {
			lblEmpty = new JLabel("");
		}
		return lblEmpty;
	}

	private JButton getBtnDoctor() {
		if (btnDoctor == null) {
			btnDoctor = new JButton("Choose doctor");
			btnDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iw = new IdentificationWindow();
					iw.setVisible(true);
					iw.setLocationRelativeTo(null);
				}
			});
		}
		return btnDoctor;
	}

	private boolean checkFinish() {
		if (getTxtMondaySt().getText().trim().isEmpty() && getTxtMondayEnd().getText().trim().isEmpty()
				|| getTxtTuesdaySt().getText().trim().isEmpty() || getTxtTuesdayEnd().getText().trim().isEmpty()
				|| getTxtWednesdaySt().getText().trim().isEmpty() || getTxtWednesdayEnd().getText().trim().isEmpty()
				|| getTxtThursdaySt().getText().trim().isEmpty() || getTxtThursdayEnd().getText().trim().isEmpty()
				|| getTxtFridaySt().getText().trim().isEmpty() || getTxtFridayEnd().getText().trim().isEmpty()
				|| getTxtSaturdaySt().getText().trim().isEmpty() || getTxtSaturdayEnd().getText().trim().isEmpty()
				|| getTxtSundaySt().getText().trim().isEmpty() || getTxtSundayEnd().getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}
}