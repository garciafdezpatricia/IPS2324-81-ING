package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class WorkPeriodView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_north;
	private JLabel lblIntroduceWork;
	private JPanel panel_south;
	private JButton btnCancel;
	private JButton btnNext;
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
			panel_south.add(getBtnCancel());
			panel_south.add(getBtnNext());
		}
		return panel_south;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
		}
		return btnCancel;
	}
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Finish");
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		return btnNext;
	}
	private JPanel getPanel_center() {
		if (panel_center == null) {
			panel_center = new JPanel();
			panel_center.setLayout(new BorderLayout(0, 0));
			panel_center.add(getPanel_center_1(), BorderLayout.CENTER);
			panel_center.add(getPanel_center_north(), BorderLayout.NORTH);
		}
		return panel_center;
	}
	private JPanel getPanel_center_1() {
		if (panel_center_center == null) {
			panel_center_center = new JPanel();
			panel_center_center.setBorder(new TitledBorder(null, "In time and out time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_center_center.setLayout(new GridLayout(0, 3, 0, 0));
			panel_center_center.add(getLblEmpty());
			panel_center_center.add(getLblClockInTime());
			panel_center_center.add(getLblClockOutTime());
			panel_center_center.add(getLblMonday_1());
			panel_center_center.add(getTxtMondaySt());
			panel_center_center.add(getTextField_1_2());
			panel_center_center.add(getLblTuesday_1());
			panel_center_center.add(getTxtTuesdaySt());
			panel_center_center.add(getTxtTuesdayEnd());
			panel_center_center.add(getLblWednesday_1());
			panel_center_center.add(getTxtWednesdaySt());
			panel_center_center.add(getTxtWednesdayEnd());
			panel_center_center.add(getLblThursday_1());
			panel_center_center.add(getTxtThursdaySt());
			panel_center_center.add(getTxtThursdayEnd());
			panel_center_center.add(getLblFriday_1());
			panel_center_center.add(getTxtFridaySt());
			panel_center_center.add(getTxtFridayEnd());
			panel_center_center.add(getLblSaturday_1());
			panel_center_center.add(getTxtSaturdaySt());
			panel_center_center.add(getTxtSaturdayEnd());
			panel_center_center.add(getLblSunday_1());
			panel_center_center.add(getTxtSundaySt());
			panel_center_center.add(getTxtSundayEnd());
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
	private JTextField getTextField_1_2() {
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
			panel_center_north.setBorder(new TitledBorder(null, "Period days", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
			lblFirstDay = new JLabel("First day of work period (dd/mm/yy)");
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
			lblLastDay = new JLabel("Last day of work period (dd/mm/yy");
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
}