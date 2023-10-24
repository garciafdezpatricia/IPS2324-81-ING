package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.workPeriod.filters.FilterMedicalLicenseID;
import gui.workPeriod.filters.FilterPersonalID;
import gui.workPeriod.filters.FilterSpecialization;
import gui.workPeriod.filters.FilterSurname;
import util.ConnectionFactory;

public class EditWorkPeriodView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_center;
	private JPanel panel_center_center;
	private JTextField textField;
	private JTextField txtMonday;
	private JTextField txtTuesday;
	private JTextField txtWednesday;
	private JTextField txtThursday;
	private JTextField txtFriday;
	private JTextField txtSaturday;
	private JTextField txtSunday;
	private JTextField txtClockinTime;
	private JTextField txtMondayIn;
	private JTextField txtTuesdayIn;
	private JTextField txtWednesdayIn;
	private JTextField txtThursdayIn;
	private JTextField txtFridayIn;
	private JTextField txtSaturdayIn;
	private JTextField txtSundayIn;
	private JTextField txtClockoutTime;
	private JTextField txtMondayOut;
	private JTextField txtTuesdayOut;
	private JTextField txtWednesdayOut;
	private JTextField txtThursdayOut;
	private JTextField txtFridayOut;
	private JTextField txtSaturdayOut;
	private JTextField txtSundayOut;
	private JPanel panel_center_north;
	private JPanel panel_center_north_unique;
	private JLabel lblIntroduceID;
	private JTextField txtValue;
	private JPanel panel_north;
	private JLabel lblEditTheWorkperiod;
	private JPanel panel_center_south;
	private JLabel lblNewFirstDay;
	private JTextField textField_25;
	private JLabel lblNewLastDay;
	private JTextField textField_26;
	private JPanel panel_south;
	private JButton btnSaveChange;
	private JButton btnAddJustification;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnSearch;
	private JLabel lblSelectFIlter;
	private JComboBox<String> comboBoxFilter;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditWorkPeriodView frame = new EditWorkPeriodView();
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
	public EditWorkPeriodView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditWorkPeriodView.class.getResource("/img/descarga.jpg")));
		setTitle("Editing workperiod...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_center(), BorderLayout.CENTER);
		contentPane.add(getPanel_north(), BorderLayout.NORTH);
		contentPane.add(getPanel_south(), BorderLayout.SOUTH);
	}

	private JPanel getPanel_center() {
		if (panel_center == null) {
			panel_center = new JPanel();
			panel_center.setLayout(new BorderLayout(0, 0));
			panel_center.add(getPanel_center_center());
			panel_center.add(getPanel_center_north(), BorderLayout.NORTH);
			panel_center.add(getPanel_south_1(), BorderLayout.SOUTH);
		}
		return panel_center;
	}

	private JPanel getPanel_center_center() {
		if (panel_center_center == null) {
			panel_center_center = new JPanel();
			panel_center_center
					.setBorder(new TitledBorder(null, "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_center_center.setLayout(new GridLayout(3, 8, 0, 0));

			panel_center_center.add(getTxt1());
			panel_center_center.add(getTxtMonday());
			panel_center_center.add(getTxtTuesday());
			panel_center_center.add(getTxtWednesday());
			panel_center_center.add(getTxtThursday());
			panel_center_center.add(getTxtFriday());
			panel_center_center.add(getTxtSaturday());
			panel_center_center.add(getTxtSunday());
			panel_center_center.add(getTxtClockIn());

			panel_center_center.add(getTxtMondayIn());
			panel_center_center.add(getTxtTuesdayIn());
			panel_center_center.add(getTxtWednesdayIn());
			panel_center_center.add(gettxtTuesdayIn());
			panel_center_center.add(getTxtFridayIn());
			panel_center_center.add(getTxtSaturdayIn());
			panel_center_center.add(getTxtSundayIn());
			panel_center_center.add(getTxtClockOut());

			panel_center_center.add(getTxtMondayOut());
			panel_center_center.add(getTxtTuesdayOut());
			panel_center_center.add(getTxtWednesdayOut());
			panel_center_center.add(getTxtThursdayOut());
			panel_center_center.add(getTxtFridayOut());
			panel_center_center.add(getTxtSaturdayOut());
			panel_center_center.add(getTxtSundayOut());
		}
		return panel_center_center;
	}

	private JTextField getTxt1() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEnabled(false);
			textField.setEditable(false);
			textField.setColumns(10);
		}
		return textField;
	}

	private JTextField getTxtMonday() {
		if (txtMonday == null) {
			txtMonday = new JTextField();
			txtMonday.setDisabledTextColor(Color.BLACK);
			txtMonday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtMonday.setHorizontalAlignment(SwingConstants.CENTER);
			txtMonday.setText("Monday");
			txtMonday.setEnabled(false);
			txtMonday.setEditable(false);
			txtMonday.setColumns(10);
		}
		return txtMonday;
	}

	private JTextField getTxtTuesday() {
		if (txtTuesday == null) {
			txtTuesday = new JTextField();
			txtTuesday.setDisabledTextColor(Color.BLACK);
			txtTuesday.setHorizontalAlignment(SwingConstants.CENTER);
			txtTuesday.setText("Tuesday");
			txtTuesday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtTuesday.setEnabled(false);
			txtTuesday.setEditable(false);
			txtTuesday.setColumns(10);
		}
		return txtTuesday;
	}

	private JTextField getTxtWednesday() {
		if (txtWednesday == null) {
			txtWednesday = new JTextField();
			txtWednesday.setDisabledTextColor(Color.BLACK);
			txtWednesday.setHorizontalAlignment(SwingConstants.CENTER);
			txtWednesday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtWednesday.setText("Wednesday");
			txtWednesday.setEnabled(false);
			txtWednesday.setEditable(false);
			txtWednesday.setColumns(10);
		}
		return txtWednesday;
	}

	private JTextField getTxtThursday() {
		if (txtThursday == null) {
			txtThursday = new JTextField();
			txtThursday.setDisabledTextColor(Color.BLACK);
			txtThursday.setHorizontalAlignment(SwingConstants.CENTER);
			txtThursday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtThursday.setText("Thursday");
			txtThursday.setEnabled(false);
			txtThursday.setEditable(false);
			txtThursday.setColumns(10);
		}
		return txtThursday;
	}

	private JTextField getTxtFriday() {
		if (txtFriday == null) {
			txtFriday = new JTextField();
			txtFriday.setDisabledTextColor(Color.BLACK);
			txtFriday.setHorizontalAlignment(SwingConstants.CENTER);
			txtFriday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtFriday.setText("Friday");
			txtFriday.setEnabled(false);
			txtFriday.setEditable(false);
			txtFriday.setColumns(10);
		}
		return txtFriday;
	}

	private JTextField getTxtSaturday() {
		if (txtSaturday == null) {
			txtSaturday = new JTextField();
			txtSaturday.setDisabledTextColor(Color.BLACK);
			txtSaturday.setHorizontalAlignment(SwingConstants.CENTER);
			txtSaturday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtSaturday.setText("Saturday");
			txtSaturday.setEnabled(false);
			txtSaturday.setEditable(false);
			txtSaturday.setColumns(10);
		}
		return txtSaturday;
	}

	private JTextField getTxtSunday() {
		if (txtSunday == null) {
			txtSunday = new JTextField();
			txtSunday.setDisabledTextColor(Color.BLACK);
			txtSunday.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtSunday.setHorizontalAlignment(SwingConstants.CENTER);
			txtSunday.setText("Sunday");
			txtSunday.setEnabled(false);
			txtSunday.setEditable(false);
			txtSunday.setColumns(10);
		}
		return txtSunday;
	}

	private JTextField getTxtClockIn() {
		if (txtClockinTime == null) {
			txtClockinTime = new JTextField();
			txtClockinTime.setDisabledTextColor(Color.BLACK);
			txtClockinTime.setText("Clock-in time");
			txtClockinTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtClockinTime.setEnabled(false);
			txtClockinTime.setEditable(false);
			txtClockinTime.setColumns(10);
		}
		return txtClockinTime;
	}

	private JTextField getTxtMondayIn() {
		if (txtMondayIn == null) {
			txtMondayIn = new JTextField();
			txtMondayIn.setColumns(10);
		}
		return txtMondayIn;
	}

	private JTextField getTxtTuesdayIn() {
		if (txtTuesdayIn == null) {
			txtTuesdayIn = new JTextField();
			txtTuesdayIn.setColumns(10);
		}
		return txtTuesdayIn;
	}

	private JTextField getTxtWednesdayIn() {
		if (txtWednesdayIn == null) {
			txtWednesdayIn = new JTextField();
			txtWednesdayIn.setColumns(10);
		}
		return txtWednesdayIn;
	}

	private JTextField gettxtTuesdayIn() {
		if (txtThursdayIn == null) {
			txtThursdayIn = new JTextField();
			txtThursdayIn.setColumns(10);
		}
		return txtThursdayIn;
	}

	private JTextField getTxtFridayIn() {
		if (txtFridayIn == null) {
			txtFridayIn = new JTextField();
			txtFridayIn.setColumns(10);
		}
		return txtFridayIn;
	}

	private JTextField getTxtSaturdayIn() {
		if (txtSaturdayIn == null) {
			txtSaturdayIn = new JTextField();
			txtSaturdayIn.setColumns(10);
		}
		return txtSaturdayIn;
	}

	private JTextField getTxtSundayIn() {
		if (txtSundayIn == null) {
			txtSundayIn = new JTextField();
			txtSundayIn.setColumns(10);
		}
		return txtSundayIn;
	}

	private JTextField getTxtClockOut() {
		if (txtClockoutTime == null) {
			txtClockoutTime = new JTextField();
			txtClockoutTime.setDisabledTextColor(Color.BLACK);
			txtClockoutTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtClockoutTime.setText("Clock-out time");
			txtClockoutTime.setEnabled(false);
			txtClockoutTime.setEditable(false);
			txtClockoutTime.setColumns(10);
		}
		return txtClockoutTime;
	}

	private JTextField getTxtMondayOut() {
		if (txtMondayOut == null) {
			txtMondayOut = new JTextField();
			txtMondayOut.setColumns(10);
		}
		return txtMondayOut;
	}

	private JTextField getTxtTuesdayOut() {
		if (txtTuesdayOut == null) {
			txtTuesdayOut = new JTextField();
			txtTuesdayOut.setColumns(10);
		}
		return txtTuesdayOut;
	}

	private JTextField getTxtWednesdayOut() {
		if (txtWednesdayOut == null) {
			txtWednesdayOut = new JTextField();
			txtWednesdayOut.setColumns(10);
		}
		return txtWednesdayOut;
	}

	private JTextField getTxtThursdayOut() {
		if (txtThursdayOut == null) {
			txtThursdayOut = new JTextField();
			txtThursdayOut.setColumns(10);
		}
		return txtThursdayOut;
	}

	private JTextField getTxtFridayOut() {
		if (txtFridayOut == null) {
			txtFridayOut = new JTextField();
			txtFridayOut.setColumns(10);
		}
		return txtFridayOut;
	}

	private JTextField getTxtSaturdayOut() {
		if (txtSaturdayOut == null) {
			txtSaturdayOut = new JTextField();
			txtSaturdayOut.setColumns(10);
		}
		return txtSaturdayOut;
	}

	private JTextField getTxtSundayOut() {
		if (txtSundayOut == null) {
			txtSundayOut = new JTextField();
			txtSundayOut.setColumns(10);
		}
		return txtSundayOut;
	}

	private JPanel getPanel_center_north() {
		if (panel_center_north == null) {
			panel_center_north = new JPanel();
			panel_center_north.setLayout(new GridLayout(0, 1, 0, 0));
			panel_center_north.add(getPanel_north_1());
		}
		return panel_center_north;
	}

	private JPanel getPanel_north_1() {
		if (panel_center_north_unique == null) {
			panel_center_north_unique = new JPanel();
			panel_center_north_unique.setBorder(new TitledBorder(null, "Doctor identification", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			panel_center_north_unique.setLayout(new GridLayout(0, 3, 0, 0));
			panel_center_north_unique.add(getLblSelectFIlter());
			panel_center_north_unique.add(getComboBoxFilter());
			panel_center_north_unique.add(getLblNewLabel_2());
			panel_center_north_unique.add(getLblIntroduceID_1());
			panel_center_north_unique.add(getTxtValue());
			panel_center_north_unique.add(getBtnSearch());
		}
		return panel_center_north_unique;
	}

	private JLabel getLblIntroduceID_1() {
		if (lblIntroduceID == null) {
			lblIntroduceID = new JLabel("Introduce the value used to filter:");
			lblIntroduceID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblIntroduceID;
	}

	private JTextField getTxtValue() {
		if (txtValue == null) {
			txtValue = new JTextField();
			txtValue.setColumns(10);
		}
		return txtValue;
	}

	private JPanel getPanel_north() {
		if (panel_north == null) {
			panel_north = new JPanel();
			panel_north.add(getLblEditTheWorkperiod());
		}
		return panel_north;
	}

	private JLabel getLblEditTheWorkperiod() {
		if (lblEditTheWorkperiod == null) {
			lblEditTheWorkperiod = new JLabel("Edit the workperiod of a doctor");
			lblEditTheWorkperiod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblEditTheWorkperiod;
	}

	private JPanel getPanel_south_1() {
		if (panel_center_south == null) {
			panel_center_south = new JPanel();
			panel_center_south.setBorder(
					new TitledBorder(null, "Date modifications", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_center_south.setLayout(new GridLayout(2, 0, 0, 0));
			panel_center_south.add(getLblNewFirstDay_1());
			panel_center_south.add(getTextField_25());
			panel_center_south.add(getLblNewLastDay_1());
			panel_center_south.add(getTextField_26());
		}
		return panel_center_south;
	}

	private JLabel getLblNewFirstDay_1() {
		if (lblNewFirstDay == null) {
			lblNewFirstDay = new JLabel("Introduce the new first day of the workperiod:");
			lblNewFirstDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblNewFirstDay;
	}

	private JTextField getTextField_25() {
		if (textField_25 == null) {
			textField_25 = new JTextField();
			textField_25.setColumns(10);
		}
		return textField_25;
	}

	private JLabel getLblNewLastDay_1() {
		if (lblNewLastDay == null) {
			lblNewLastDay = new JLabel("Introduce the last first day of the workperiod:");
			lblNewLastDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblNewLastDay;
	}

	private JTextField getTextField_26() {
		if (textField_26 == null) {
			textField_26 = new JTextField();
			textField_26.setColumns(10);
		}
		return textField_26;
	}

	private JPanel getPanel_south() {
		if (panel_south == null) {
			panel_south = new JPanel();
			panel_south.setLayout(new GridLayout(2, 0, 0, 0));
			panel_south.add(getLblNewLabel());
			panel_south.add(getLblNewLabel_1());
			panel_south.add(getBtnAddJustification());
			panel_south.add(getBtnSaveChange());
		}
		return panel_south;
	}

	private JButton getBtnSaveChange() {
		if (btnSaveChange == null) {
			btnSaveChange = new JButton("Save change");
		}
		return btnSaveChange;
	}

	private JButton getBtnAddJustification() {
		if (btnAddJustification == null) {
			btnAddJustification = new JButton("Add justification");
		}
		return btnAddJustification;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
		}
		return lblNewLabel;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("");
		}
		return lblNewLabel_1;
	}

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("Search");
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						BigInteger selectedDocID = applyFilter((String) getComboBoxFilter().getSelectedItem(), getTxtValue().getText());
						showSchedule(selectedDocID);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			});
		}
		return btnSearch;
	}

	private void showSchedule(BigInteger selectedDocID) {
		
	}
	
	/**
	 * String[] f = { "Filtering by name", "Filtering by surname", "Filtering by
	 * personal ID", "Filtering by medical license ID", "Filtering by
	 * specialization" };
	 * 
	 * @param filter
	 * @param value
	 * @return
	 * @throws Exception 
	 */

	private BigInteger applyFilter(String filter, String value) throws Exception {
		if (filter.equals("Filtering by name"))
			return ConnectionFactory.doctorFromName(value);
		else if (filter.equals("Filtering by surname"))
			return ConnectionFactory.doctorFromSurname(value);
		else if (filter.equals("Filtering by personal ID"))
			return ConnectionFactory.doctorFromPersonalID(value);
		else if (filter.equals("Filtering by personal ID"))
			return ConnectionFactory.doctorFromMedicalLicenseID(value);

		return new BigInteger("1234567890123456789012345678901234567890");
	}

	private JLabel getLblSelectFIlter() {
		if (lblSelectFIlter == null) {
			lblSelectFIlter = new JLabel("Select the type of filtering:");
			lblSelectFIlter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSelectFIlter;
	}

	private JComboBox<String> getComboBoxFilter() {
		if (comboBoxFilter == null) {
			comboBoxFilter = new JComboBox<String>();
		}
		return comboBoxFilter;
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("");
		}
		return lblNewLabel_2;
	}

}
