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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import db.Doctor;
import db.WorkDay;
import db.WorkPeriod;
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
	private JPanel panel_north;
	private JLabel lblEditTheWorkperiod;
	private JPanel panel_center_south;
	private JLabel lblNewFirstDay;
	private JTextField txtNewFirstDay;
	private JLabel lblNewLastDay;
	private JTextField txtNewLastDay;
	private JPanel panel_south;
	private JButton btnSaveChange;
	private JButton btnAddJustification;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnOtherWPs;
	private JLabel lblNewLabel_3;
	private JPanel panel_center_north_unique_center;
	private JLabel lblSelectFIlter;
	private JComboBox<String> comboBoxFilter;
	private JLabel lblNewLabel_2;
	private JLabel lblIntroduceID;
	private JTextField txtValue;
	private JButton btnSearch;
	private JComboBox<String> comboBoxDoctors;
	private JLabel lblChooseDoctor;
	private JButton btnSelect;

	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();
	
	private BigInteger selectedDocID;
	private JLabel lblFirstDay;
	private JLabel lblShowFirstDay;
	private JLabel lblSecondDay;
	private JLabel lblShowLastDay;
	
	private JustificationView jv;
	private WorkPeriod wp;
	private BigInteger wpID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditWorkPeriodView frame = new EditWorkPeriodView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
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
		jv = new JustificationView();
		try {
			doctors = ConnectionFactory.getDoctors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditWorkPeriodView.class.getResource("/img/descarga.jpg")));
		setTitle("Editing workperiod...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 534);
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
			panel_center_center.add(getTxtThursdayIn());
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

	private JTextField getTxtThursdayIn() {
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
			panel_center_north_unique.setLayout(new BorderLayout(0, 0));
			panel_center_north_unique.add(getPanel_center_north_unique_center(), BorderLayout.NORTH);
		}
		return panel_center_north_unique;
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
			panel_center_south.setLayout(new GridLayout(0, 2, 0, 0));
			panel_center_south.add(getLblFirstDay());
			panel_center_south.add(getLblShowFirstDay());
			panel_center_south.add(getLblSecondDay());
			panel_center_south.add(getLblShowLastDay());
			panel_center_south.add(getLblNewFirstDay_1());
			panel_center_south.add(getTxtNewFirstDay());
			panel_center_south.add(getLblNewLastDay_1());
			panel_center_south.add(getTxtNewLastDay());
		}
		return panel_center_south;
	}

	private JLabel getLblNewFirstDay_1() {
		if (lblNewFirstDay == null) {
			lblNewFirstDay = new JLabel("Introduce the new first day:");
			lblNewFirstDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblNewFirstDay;
	}

	private JTextField getTxtNewFirstDay() {
		if (txtNewFirstDay == null) {
			txtNewFirstDay = new JTextField();
			txtNewFirstDay.setColumns(10);
		}
		return txtNewFirstDay;
	}

	private JLabel getLblNewLastDay_1() {
		if (lblNewLastDay == null) {
			lblNewLastDay = new JLabel("Introduce the new last day:");
			lblNewLastDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblNewLastDay;
	}

	private JTextField getTxtNewLastDay() {
		if (txtNewLastDay == null) {
			txtNewLastDay = new JTextField();
			txtNewLastDay.setColumns(10);
		}
		return txtNewLastDay;
	}

	private JPanel getPanel_south() {
		if (panel_south == null) {
			panel_south = new JPanel();
			panel_south.setLayout(new GridLayout(2, 0, 0, 0));
			panel_south.add(getLblNewLabel_3());
			panel_south.add(getLblNewLabel());
			panel_south.add(getLblNewLabel_1());
			panel_south.add(getBtnOtherWPs());
			panel_south.add(getBtnAddJustification());
			panel_south.add(getBtnSaveChange());
		}
		return panel_south;
	}

	private JButton getBtnSaveChange() {
		if (btnSaveChange == null) {
			btnSaveChange = new JButton("Save change");
			btnSaveChange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createWorkPeriod();
					List<WorkDay> wds = createWorkDays();

					// save to database
					saveToDB(wds);
					
					ConnectionFactory.updateWorkPeriod(wpID, wp.getStartDate(), wp.getEndDate(), wp.getId_doctor());

				}
			});
			btnSaveChange.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnSaveChange;
	}

	private JButton getBtnAddJustification() {
		if (btnAddJustification == null) {
			btnAddJustification = new JButton("Add justification");
			btnAddJustification.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jv = new JustificationView();
					jv.setVisible(true);
					jv.setLocationRelativeTo(null);
				}
			});
			btnAddJustification.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
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

	private String[] getFilters() {
		String[] f = { "Filtering by name", "Filtering by surname", "Filtering by personal ID",
				"Filtering by medical license ID", "Filtering by specialization" };

		return f;
	}

	private JButton getBtnOtherWPs() {
		if (btnOtherWPs == null) {
			btnOtherWPs = new JButton("Show other workperiod");
			btnOtherWPs.setEnabled(false);
			btnOtherWPs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnOtherWPs;
	}

	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("");
		}
		return lblNewLabel_3;
	}

	private JPanel getPanel_center_north_unique_center() {
		if (panel_center_north_unique_center == null) {
			panel_center_north_unique_center = new JPanel();
			panel_center_north_unique_center.setLayout(new GridLayout(0, 3, 0, 0));
			panel_center_north_unique_center.add(getLblSelectFIlter_1());
			panel_center_north_unique_center.add(getComboBoxFilter());
			panel_center_north_unique_center.add(getLblNewLabel_2_1());
			panel_center_north_unique_center.add(getLblIntroduceID());
			panel_center_north_unique_center.add(getTxtValue());
			panel_center_north_unique_center.add(getBtnSearch());
			panel_center_north_unique_center.add(getLblChooseDoctor());
			panel_center_north_unique_center.add(getComboBoxDoctors());
			panel_center_north_unique_center.add(getBtnSelect());
		}
		return panel_center_north_unique_center;
	}

	private JLabel getLblSelectFIlter_1() {
		if (lblSelectFIlter == null) {
			lblSelectFIlter = new JLabel("Select the type of filtering:");
			lblSelectFIlter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSelectFIlter;
	}

	private JComboBox<String> getComboBoxFilter() {
		if (comboBoxFilter == null) {
			comboBoxFilter = new JComboBox<String>();
			comboBoxFilter.setModel(new DefaultComboBoxModel<>(getFilters()));
		}
		return comboBoxFilter;
	}

	private JLabel getLblNewLabel_2_1() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("");
		}
		return lblNewLabel_2;
	}

	private JLabel getLblIntroduceID() {
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

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("Search");
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						selectedDocID = applyFilter((String) getComboBoxFilter().getSelectedItem(), getTxtValue().getText());
						// TODO
						System.out.println("id=" + selectedDocID);
						showSchedule(selectedDocID);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});
			btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnSearch;
	}

	private JComboBox<String> getComboBoxDoctors() {
		if (comboBoxDoctors == null) {
			comboBoxDoctors = new JComboBox<String>();
			comboBoxDoctors.setModel(new DefaultComboBoxModel<>(doctorsToArray()));
			
			
		}
		return comboBoxDoctors;
	}
	
	private String[] doctorsToArray() {
		String[] aux = new String[doctors.size()];
		for (int i = 0; i < doctors.size(); i++) {
			aux[i] = doctors.get(i).getName() + " " + doctors.get(i).getSurname();
		}
		return aux;
	}

	private JLabel getLblChooseDoctor() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Choose the doctor:");
			lblChooseDoctor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblChooseDoctor;
	}

	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton("Select");
			btnSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						System.out.println(String.valueOf(getComboBoxFilter().getSelectedItem()));
						selectedDocID = ConnectionFactory.doctorFromNameAndSurname(String.valueOf(getComboBoxDoctors().getSelectedItem()));
						
						// TODO
						System.out.println("id=" + selectedDocID);
						showSchedule(selectedDocID);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// TODO llamar al metodo de connection factory
					

				}
			});
			btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return btnSelect;
	}
	
	private void showSchedule(BigInteger selectedDocID) {
		List<WorkPeriod> workperiods = ConnectionFactory.getWorkPeriodByDoctorId(selectedDocID);

		if (workperiods.size() > 0) {
			if (workperiods.size() > 1) {
				getBtnOtherWPs().setEnabled(true);
				// TODO: no se sabe si se puede tener más de un workperiod a la vez
			}
			else {
				wpID = workperiods.get(0).getId();
				List<WorkDay> workdays = ConnectionFactory.getWorkDayByWPId(wpID);

				setTimetable(workdays);
				
				getLblShowFirstDay().setText(String.valueOf(workperiods.get(0).getStartDate()));
				getLblShowLastDay().setText(String.valueOf(workperiods.get(0).getEndDate()));
				
			}
		}
	}

	private void setTimetable(List<WorkDay> wds) {
		for (int i = 0; i < wds.size(); i++) {
			System.out.println(wds.get(i).toString());
			if (wds.get(i).getWeekday().toLowerCase().equals("monday")) {
				getTxtMondayIn().setText(wds.get(i).getStartHour());
				getTxtMondayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("tuesday")) {
				getTxtTuesdayIn().setText(wds.get(i).getStartHour());
				getTxtTuesdayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("wednesday")) {
				getTxtWednesdayIn().setText(wds.get(i).getStartHour());
				getTxtWednesdayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("thursday")) {
				getTxtThursdayIn().setText(wds.get(i).getStartHour());
				getTxtThursdayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("friday")) {
				getTxtFridayIn().setText(wds.get(i).getStartHour());
				getTxtFridayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("saturday")) {
				getTxtSaturdayIn().setText(wds.get(i).getStartHour());
				getTxtSaturdayOut().setText(wds.get(i).getEndHour());
			} else if (wds.get(i).getWeekday().toLowerCase().equals("sunday")) {
				getTxtSundayIn().setText(wds.get(i).getStartHour());
				getTxtSundayOut().setText(wds.get(i).getEndHour());
			}
		}
	}
	private JLabel getLblFirstDay() {
		if (lblFirstDay == null) {
			lblFirstDay = new JLabel("First day of the workperiod:");
			lblFirstDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblFirstDay;
	}
	private JLabel getLblShowFirstDay() {
		if (lblShowFirstDay == null) {
			lblShowFirstDay = new JLabel("");
			lblShowFirstDay.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		return lblShowFirstDay;
	}
	private JLabel getLblSecondDay() {
		if (lblSecondDay == null) {
			lblSecondDay = new JLabel("Last day of the workperiod:");
			lblSecondDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblSecondDay;
	}
	private JLabel getLblShowLastDay() {
		if (lblShowLastDay == null) {
			lblShowLastDay = new JLabel("");
			lblShowLastDay.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		return lblShowLastDay;
	}
	
	private WorkPeriod createWorkPeriod() {
		BigInteger id = generateID();
		
		// startDate
		String startDate = getTxtNewFirstDay().getText();
		String endDate = getTxtNewLastDay().getText();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date start = null;
		java.util.Date end = null;
		java.sql.Date startSQL = null;
		java.sql.Date endSQL = null;

		try {
			start = format.parse(startDate + " 00:00:00");
			System.out.println("  start: " + start);
			end = format.parse(endDate + " 00:00:00");
			System.out.println("  end: " + end);

			startSQL = new java.sql.Date(start.getTime());
			System.out.println("  startsql: " + startSQL);

			endSQL = new java.sql.Date(end.getTime());
			System.out.println("  endsql: " + endSQL);

		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "The format of the date is not correct.");
		}
		
		if (startSQL != null && endSQL != null) {
			wp = new WorkPeriod(id, startSQL, endSQL, selectedDocID);
		}
		return wp;
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
	
	
	private void saveToDB(List<WorkDay> wds) {
		try {
			System.out.println("HOLA " + wp.getStartDate());
			System.out.println("HOLA " +wp.getEndDate());
			
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
		String startHour = getTxtMondayIn().getText();
		String endHour = getTxtMondayOut().getText();
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
		startHour = getTxtTuesdayIn().getText();
		endHour = getTxtTuesdayOut().getText();
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
		startHour = getTxtWednesdayIn().getText();
		endHour = getTxtWednesdayOut().getText();
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
		startHour = getTxtThursdayIn().getText();
		endHour = getTxtThursdayOut().getText();
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
		startHour = getTxtFridayIn().getText();
		endHour = getTxtFridayOut().getText();
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
		startHour = getTxtSaturdayIn().getText();
		endHour = getTxtSaturdayOut().getText();
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
		startHour = getTxtSundayIn().getText();
		endHour = getTxtSundayOut().getText();
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
}
