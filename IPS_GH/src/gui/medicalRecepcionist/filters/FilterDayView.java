package gui.medicalRecepcionist.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
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
import db.Patient;
import gui.medicalRecepcionist.EditAndCancelView;
import gui.medicalRecepcionist.MedicalRecepcionistView;
import util.ConnectionFactory;
import javax.swing.SwingConstants;
import java.awt.Font;

public class FilterDayView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelButtons;
	private JButton btnCancel;
	private JPanel panelCenter;

	private EditAndCancelView prev = new EditAndCancelView();
	private static DefaultListModel<Appointment> appointments;
	private DefaultListModel<Appointment> a2 = new DefaultListModel<>();
	private JPanel panelCenterCenter;
	private JLabel lblUnicDay;
	private JDateChooser dateChooser;
	private JButton btnUnicDay;
	private JLabel lblBefore;
	private JDateChooser dateChooserBefore;
	private JButton btnBefore;
	private JLabel lblAfter;
	private JDateChooser dateChooserAfter;
	private JButton btnAfter;
	private JPanel panelCenterSur;
	private JLabel lblBetween;
	private JLabel lblAnd;
	private JButton btnFilter;
	private JDateChooser dateChooserFrom;
	private JDateChooser dateChooserTo;
	private JPanel panelTitle;
	private JLabel lblTitle;
	protected boolean from = false;
	protected boolean to = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FilterDayView dialog = new FilterDayView(appointments);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public FilterDayView(DefaultListModel<Appointment> appointments) throws Exception {
		this.appointments = appointments;
		a2 = this.appointments;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MedicalRecepcionistView.class.getResource("/img/descarga.jpg")));
		setTitle("Filter by day/s");
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
//		patients = ConnectionFactory.getPatients();
//		patientsReset = ConnectionFactory.getPatients();

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelButtons(), BorderLayout.SOUTH);
		getContentPane().add(getPanelCenter(), BorderLayout.CENTER);
		getContentPane().add(getPanelTitle(), BorderLayout.NORTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new BorderLayout(0, 0));
			panelButtons.add(getBtnCancel());
		}
		return panelButtons;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBackground(new Color(255, 0, 0));
		}
		return btnCancel;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout(0, 0));
			panelCenter.add(getPanelCenterCenter(), BorderLayout.CENTER);
			panelCenter.add(getPanelCenterSur(), BorderLayout.SOUTH);
		}
		return panelCenter;
	}

	private JPanel getPanelCenterCenter() {
		if (panelCenterCenter == null) {
			panelCenterCenter = new JPanel();
			panelCenterCenter.setLayout(new GridLayout(0, 3, 0, 0));
			panelCenterCenter.add(getLblUnicDay_1());
			panelCenterCenter.add(getDateChooser());
			panelCenterCenter.add(getBtnUnicDay_1());
			panelCenterCenter.add(getLblBefore_1());
			panelCenterCenter.add(getDateChooserBefore());
			panelCenterCenter.add(getBtnBefore_1());
			panelCenterCenter.add(getLblAfter_1());
			panelCenterCenter.add(getDateChooserAfter());
			panelCenterCenter.add(getBtnAfter_1());
		}
		return panelCenterCenter;
	}

	private JLabel getLblUnicDay_1() {
		if (lblUnicDay == null) {
			lblUnicDay = new JLabel("Day:");
		}
		return lblUnicDay;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser((Date) null);
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnUnicDay.setEnabled(true);
					btnFilter.setEnabled(false);
					btnBefore.setEnabled(false);
					btnAfter.setEnabled(false);
				}
			});
		}
		return dateChooser;
	}

	private JDateChooser getDateChooserFrom() {
		if (dateChooserFrom == null) {
			dateChooserFrom = new JDateChooser((Date) null);
			dateChooserFrom.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					from = true;
					if (from && to) {
						btnFilter.setEnabled(true);
					}
					btnUnicDay.setEnabled(false);
					btnBefore.setEnabled(false);
					btnAfter.setEnabled(false);
				}
			});
		}
		return dateChooserFrom;
	}

	private JDateChooser getDateChooserTo() {
		if (dateChooserTo == null) {
			dateChooserTo = new JDateChooser((Date) null);
			dateChooserTo.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					to = true;
					if (from && to)
						btnFilter.setEnabled(true);
					btnUnicDay.setEnabled(false);
					btnBefore.setEnabled(false);
					btnAfter.setEnabled(false);
				}
			});
		}
		return dateChooserTo;
	}

	private JButton getBtnUnicDay_1() {
		if (btnUnicDay == null) {
			btnUnicDay = new JButton("Filter");
			btnUnicDay.setEnabled(false);
			btnUnicDay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					SimpleDateFormat originalDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
					SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

					originalDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					String newFormattedDate = null;
					try {
						Date date = originalDateFormat.parse(getDateChooser().getDate().toString());
						newFormattedDate = outputDateFormat.format(date);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					appointments.clear();

					DefaultListModel<Appointment> x = null;
					try {
						x = ConnectionFactory.getAppointments();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Define el formato original
					SimpleDateFormat originalDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					for (int i = 0; i < x.size(); i++) {
						try {
							if (outputDateFormat.format(originalDateFormat2.parse(x.get(i).getStartdate()))
									.equals(newFormattedDate)) {
								appointments.addElement(x.get(i));
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}

			});
		}
		return btnUnicDay;
	}

	private JLabel getLblBefore_1() {
		if (lblBefore == null) {
			lblBefore = new JLabel("Before:");
		}
		return lblBefore;
	}

	private JDateChooser getDateChooserBefore() {
		if (dateChooserBefore == null) {
			dateChooserBefore = new JDateChooser((Date) null);
			dateChooserBefore.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnBefore.setEnabled(true);
					btnUnicDay.setEnabled(false);
					btnFilter.setEnabled(false);
					btnAfter.setEnabled(false);
				}
			});
		}
		return dateChooserBefore;
	}

	private JButton getBtnBefore_1() {
		if (btnBefore == null) {
			btnBefore = new JButton("Filter");
			btnBefore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleDateFormat originalDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
					SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

					originalDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					Date date = null;
					try {
						date = originalDateFormat.parse(getDateChooserBefore().getDate().toString());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					appointments.clear();

					DefaultListModel<Appointment> x = null;
					try {
						x = ConnectionFactory.getAppointments();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					for (int i = 0; i < x.size(); i++) {
						try {
							if (outputDateFormat.parse(x.get(i).getStartdate()).before(date)) {
								appointments.addElement(x.get(i));
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}
			});
			btnBefore.setEnabled(false);
		}
		return btnBefore;
	}

	private JLabel getLblAfter_1() {
		if (lblAfter == null) {
			lblAfter = new JLabel("After:");
		}
		return lblAfter;
	}

	private JDateChooser getDateChooserAfter() {
		if (dateChooserAfter == null) {
			dateChooserAfter = new JDateChooser((Date) null);
			dateChooserAfter.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAfter.setEnabled(true);
					btnUnicDay.setEnabled(false);
					btnFilter.setEnabled(false);
					btnBefore.setEnabled(false);
				}
			});
		}
		return dateChooserAfter;
	}

	private JButton getBtnAfter_1() {
		if (btnAfter == null) {
			btnAfter = new JButton("Filter");
			btnAfter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleDateFormat originalDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
					SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

					originalDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					Date date = null;
					try {
						date = originalDateFormat.parse(getDateChooserAfter().getDate().toString());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					appointments.clear();

					DefaultListModel<Appointment> x = null;
					try {
						x = ConnectionFactory.getAppointments();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					for (int i = 0; i < x.size(); i++) {
						try {
							if (outputDateFormat.parse(x.get(i).getStartdate()).after(date)) {
								appointments.addElement(x.get(i));
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}
			});
			btnAfter.setEnabled(false);
		}
		return btnAfter;
	}

	private JPanel getPanelCenterSur() {
		if (panelCenterSur == null) {
			panelCenterSur = new JPanel();
			panelCenterSur.setLayout(new GridLayout(0, 5, 0, 0));
			panelCenterSur.add(getLblBetween());
			panelCenterSur.add(getDateChooserFrom());
			panelCenterSur.add(getLblAnd());
			panelCenterSur.add(getDateChooserTo());
			panelCenterSur.add(getBtnFilter());
		}
		return panelCenterSur;
	}

	private JLabel getLblBetween() {
		if (lblBetween == null) {
			lblBetween = new JLabel("Between");
		}
		return lblBetween;
	}

	private JLabel getLblAnd() {
		if (lblAnd == null) {
			lblAnd = new JLabel("and");
			lblAnd.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblAnd;
	}

	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter");
			btnFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleDateFormat originalDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
					SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

					originalDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					Date dateFrom = null;
					Date dateTo = null;

					try {
						dateFrom = originalDateFormat.parse(getDateChooserFrom().getDate().toString());
						dateTo = originalDateFormat.parse(getDateChooserTo().getDate().toString());

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					appointments.clear();

					DefaultListModel<Appointment> x = null;
					try {
						x = ConnectionFactory.getAppointments();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					for (int i = 0; i < x.size(); i++) {
						try {
							if (outputDateFormat.parse(x.get(i).getStartdate()).before(dateTo)
									&& outputDateFormat.parse(x.get(i).getStartdate()).after(dateFrom)) {
								appointments.addElement(x.get(i));
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}

			});
			btnFilter.setEnabled(false);
		}
		return btnFilter;
	}

	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.setLayout(new BorderLayout(0, 0));
			panelTitle.add(getLblTitle());
		}
		return panelTitle;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Choose how do you want to filter the day");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblTitle;
	}
}