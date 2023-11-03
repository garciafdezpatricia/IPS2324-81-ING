package gui.medicalRecepcionist.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
		}
		return dateChooser;
	}

	private JDateChooser getDateChooserFrom() {
		if (dateChooserFrom == null) {
			dateChooserFrom = new JDateChooser((Date) null);
		}
		return dateChooserFrom;
	}

	private JDateChooser getDateChooserTo() {
		if (dateChooserTo == null) {
			dateChooserTo = new JDateChooser((Date) null);
		}
		return dateChooserTo;
	}

	private JButton getBtnUnicDay_1() {
		if (btnUnicDay == null) {
			btnUnicDay = new JButton("Filter");
			btnUnicDay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel<Appointment> appointments2 = new DefaultListModel<>();

					Date p = getDateChooser().getDate();
					System.out.println(getDateChooser().getDate().toString());
					appointments.clear();

					try {
						DefaultListModel<Appointment> a = ConnectionFactory.getAppointments();
				        SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			            String formattedDate = format.format(inputFormat.parse(p.toString()));

						for (int i = 0; i < a.size(); i++) {
							if (format.parse(a.get(i).getStartdate()).equals(formattedDate))
								System.out.println("f" + formattedDate);
								System.out.println(a.get(i).getStartdate());
								appointments.addElement(a.get(i));

						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		}
		return dateChooserBefore;
	}

	private JButton getBtnBefore_1() {
		if (btnBefore == null) {
			btnBefore = new JButton("Filter");
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
		}
		return dateChooserAfter;
	}

	private JButton getBtnAfter_1() {
		if (btnAfter == null) {
			btnAfter = new JButton("Filter");
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
		}
		return btnFilter;
	}
}
