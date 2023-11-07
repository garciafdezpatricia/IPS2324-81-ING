package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import db.Doctor;
import oracle.sql.DATE;
import util.ConnectionFactory;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextArea;

public class SelectDateForEdition extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelCenter;
	private JPanel panelCenterFilters;
	private JPanel panelSur;
	private JPanel panelCenterList;
	private JPanel panelCenterNorth;
	private JPanel panelCenterSur;
	private JButton btnPrevious;
	private JButton btnNext;
	private JScrollPane scrollPaneDates;
	private JDateChooser dateChooser;
	private JLabel lblSelectDate;
	private JPanel panelCenterFiltersGrid;
	private JLabel lblFrom;
	private JTextField textFieldFrom;
	private JLabel lblTo;
	private JTextField textFieldTo;
	private JButton btnCancel;
	private JButton btnSelect;
	private String from;
	private String to;
	private Date day;

	private static List<Doctor> selectedDoctors;
	private static String startDate, endDate;
	private JTextArea textAreaDates;

	/**
	 * Create the dialog.
	 */
	public SelectDateForEdition(List<Doctor> s, String startDate, String endDate) {
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
		selectedDoctors = s;
		this.startDate = startDate;
		this.endDate = endDate;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelCenter(), BorderLayout.CENTER);
		getContentPane().add(getPanelSur(), BorderLayout.SOUTH);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setFrom(startDate);
		setTo(endDate);
		setDay(getDateChooser().getDate());

	}

	private Date getDay(String d) {
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

		Date day = new Date();
		try {
			day = sdf3.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return day;
	}

	private String getHour(String d) {
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date day = new Date();
		try {
			day = sdf3.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (day.getHours() >= 10 && day.getMinutes() >= 10) {
			return day.getHours() + ":" + day.getMinutes();

		} else if (day.getHours() < 10 && day.getMinutes() >= 10) {
			return "0" + day.getHours() + ":" + day.getMinutes();
		} else if (day.getHours() < 10 && day.getMinutes() < 10)
			return "0" + day.getHours() + ":0" + day.getMinutes();
		else if (day.getHours() >= 10 && day.getMinutes() < 10)
			return day.getHours() + ":0" + day.getMinutes();
		return day.getHours() + ":" + day.getMinutes();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectDateForEdition dialog = new SelectDateForEdition(selectedDoctors, startDate, endDate);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(getDay(startDate));
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnNext.setEnabled(true);
					getTextAreaDates().removeAll();
					try {
						getTextAreaDates().setText(ConnectionFactory.getFreeHours(selectedDoctors,
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			dateChooser.setMinSelectableDate(new Date());

		}
		return dateChooser;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getPanelCenterFilters());
			panelCenter.add(getPanelCenterList());
		}
		return panelCenter;
	}

	private JPanel getPanelCenterFilters() {
		if (panelCenterFilters == null) {
			panelCenterFilters = new JPanel();
			panelCenterFilters.setLayout(new BorderLayout(0, 0));
			panelCenterFilters.add(getLblSelectDate(), BorderLayout.NORTH);
			panelCenterFilters.add(getPanelCenterFiltersGrid(), BorderLayout.CENTER);
		}
		return panelCenterFilters;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new GridLayout(0, 2, 0, 0));
			panelSur.add(getBtnCancel());
			panelSur.add(getBtnSelect());
		}
		return panelSur;
	}

	private JPanel getPanelCenterList() {
		if (panelCenterList == null) {
			panelCenterList = new JPanel();
			panelCenterList.setLayout(new BorderLayout(0, 0));
			panelCenterList.add(getPanelCenterNorth(), BorderLayout.NORTH);
			panelCenterList.add(getPanelCenterSur(), BorderLayout.SOUTH);
			panelCenterList.add(getScrollPaneDates(), BorderLayout.CENTER);
		}
		return panelCenterList;
	}

	private JPanel getPanelCenterNorth() {
		if (panelCenterNorth == null) {
			panelCenterNorth = new JPanel();
			panelCenterNorth.setLayout(new BorderLayout(0, 0));
			panelCenterNorth.add(getDateChooser());
		}
		return panelCenterNorth;
	}

	private JPanel getPanelCenterSur() {
		if (panelCenterSur == null) {
			panelCenterSur = new JPanel();
			panelCenterSur.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenterSur.add(getBtnPrevious());
			panelCenterSur.add(getBtnNext());
		}
		return panelCenterSur;
	}

	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton("Previous");
			btnPrevious.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Obtén la fecha actual seleccionada en el JDateChooser
					Date currentDate = dateChooser.getDate();

					// Crea un objeto Calendar y configúralo con la fecha actual
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currentDate);

					// Agrega un día al Calendar
					calendar.add(Calendar.DAY_OF_MONTH, -1);

					// Obtén la nueva fecha después de agregar un día
					Date newDate = calendar.getTime();

					// Establece la nueva fecha en el JDateChooser
					dateChooser.setDate(newDate);

					getTextAreaDates().removeAll();
					try {
						getTextAreaDates().setText(ConnectionFactory.getFreeHours(selectedDoctors,
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println(getDateChooser().getDate());
					System.out.println(new Date());
					if (getDateChooser().getDate().getDay() == new Date().getDay()
							&& getDateChooser().getDate().getMonth() == new Date().getMonth()
							&& getDateChooser().getDate().getYear() == new Date().getYear()) {
						btnPrevious.setEnabled(false);
					}
				}
			});
			btnPrevious.setEnabled(false);
		}
		return btnPrevious;
	}

	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Obtén la fecha actual seleccionada en el JDateChooser
					Date currentDate = dateChooser.getDate();

					// Crea un objeto Calendar y configúralo con la fecha actual
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currentDate);

					// Agrega un día al Calendar
					calendar.add(Calendar.DAY_OF_MONTH, 1);

					// Obtén la nueva fecha después de agregar un día
					Date newDate = calendar.getTime();

					// Establece la nueva fecha en el JDateChooser
					dateChooser.setDate(newDate);
					btnPrevious.setEnabled(true);

					getTextAreaDates().removeAll();
					try {
						getTextAreaDates().setText(ConnectionFactory.getFreeHours(selectedDoctors,
								new java.sql.Date(getDateChooser().getDate().getTime())));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
		}
		return btnNext;
	}

	private JScrollPane getScrollPaneDates() {
		if (scrollPaneDates == null) {
			scrollPaneDates = new JScrollPane();
			scrollPaneDates.setViewportView(getTextAreaDates());
		}
		return scrollPaneDates;
	}

//	listDates = new JList(ConnectionFactory.getFreeHours(selectedDoctors,
//			new java.sql.Date(getDateChooser().getDate().getTime())));

	private JLabel getLblSelectDate() {
		if (lblSelectDate == null) {
			lblSelectDate = new JLabel("Select date");
			lblSelectDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		}
		return lblSelectDate;
	}

	private JPanel getPanelCenterFiltersGrid() {
		if (panelCenterFiltersGrid == null) {
			panelCenterFiltersGrid = new JPanel();
			panelCenterFiltersGrid.setLayout(new GridLayout(2, 2, 0, 0));
			panelCenterFiltersGrid.add(getLblFrom());
			panelCenterFiltersGrid.add(getTextFieldFrom());
			panelCenterFiltersGrid.add(getLblTo());
			panelCenterFiltersGrid.add(getTextFieldTo());
		}
		return panelCenterFiltersGrid;
	}

	private JLabel getLblFrom() {
		if (lblFrom == null) {
			lblFrom = new JLabel("From:");
		}
		return lblFrom;
	}

	public JTextField getTextFieldFrom() {
		if (textFieldFrom == null) {
			textFieldFrom = new JTextField();
			System.out.println(startDate);
			textFieldFrom.setText(getHour(startDate).toString());

			textFieldFrom.setColumns(10);
			textFieldFrom.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
//					if (validarFormatoHora(textFieldFrom.getText())) {
//			            System.out.println("La hora es válida.");
//			            
//			        } else {
//			        	JOptionPane.showMessageDialog(SelectDate.this,
//								"The format of the 'from' hour is not correct.", "Warning",
//								JOptionPane.INFORMATION_MESSAGE);
//			        	getTextFieldFrom().setText("00:00");
//			        }
				}
			});
		}
		return textFieldFrom;
	}

	// Valida el formato de hora HH:mm
	public static boolean validarFormatoHora(String hora) {
		if (hora == null)
			return false;
		return hora.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
	}

	private JLabel getLblTo() {
		if (lblTo == null) {
			lblTo = new JLabel("To:");
		}
		return lblTo;
	}

	public JTextField getTextFieldTo() {
		if (textFieldTo == null) {
			textFieldTo = new JTextField();
			textFieldTo.setColumns(10);
			textFieldTo.setText(getHour(endDate).toString());

			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");

			textFieldTo.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
//					try {
//						if (validarFormatoHora(textFieldFrom.getText())) {
//				            System.out.println("La hora es válida.");
//				            if (sdf3.parse(textFieldFrom.getText()+ ":00").after(sdf3.parse(textFieldTo.getText()+ ":00"))) {
//								JOptionPane.showMessageDialog(SelectDate.this,
//										"The end hour of the appointment must be later than the start one.", "Warning",
//										JOptionPane.INFORMATION_MESSAGE);
//								getTextFieldTo().setText(getTextFieldFrom().getText());
//								getTextFieldTo().requestFocus();
//							}
//				            
//				        } else {
//				        	JOptionPane.showMessageDialog(SelectDate.this,
//									"The format of the 'to' hour is not correct.", "Warning",
//									JOptionPane.INFORMATION_MESSAGE);
//							getTextFieldTo().requestFocus();
//							getTextFieldTo().setText("00:00");
//				        }
//						
//						
//					} catch (HeadlessException | ParseException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}

				}
			});
		}
		return textFieldTo;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
		}
		return btnCancel;
	}

	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton("Select");
			btnSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setFrom(getTextFieldFrom().getText());
					setTo(getTextFieldTo().getText());
					setDay(getDateChooser().getDate());
					dispose();

				}

			});
		}
		return btnSelect;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	private JTextArea getTextAreaDates() {
		if (textAreaDates == null) {
			textAreaDates = new JTextArea();
			textAreaDates.setEditable(false);
			try {
				textAreaDates.setText(ConnectionFactory.getFreeHours(selectedDoctors,
						new java.sql.Date(dateChooser.getDate().getTime())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return textAreaDates;
	}
}
