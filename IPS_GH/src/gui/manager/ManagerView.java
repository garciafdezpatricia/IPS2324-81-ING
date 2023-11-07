package gui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import util.AppointmentBLDto;
import util.ConnectionFactory;

public class ManagerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnButtons;
	private JPanel pnCount;
	private JLabel lblPatients;
	private JTextField textField;
	private JButton btnClear;
	private List<AppointmentBLDto> patients;
	private List<AppointmentBLDto> appointments = null;

	public LocalDate selDate;
	private JDateChooser dateChooser;
	private JButton btnFilter;
	private JScrollPane spPatients;
	private JPanel panel;
	private JPanel newPatient;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerView frame = new ManagerView();
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
	public ManagerView() {
		try {
			this.patients = new ArrayList<>();
			appointments = ConnectionFactory.getAppointmentsList();
		} catch (Exception e) {

			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnButtons(), BorderLayout.NORTH);
		contentPane.add(getSpPatients(), BorderLayout.CENTER);
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getDateChooser());
			pnButtons.add(getBtnFilter());
			pnButtons.add(getBtnClear());
			pnButtons.add(getPnCount());
		}
		return pnButtons;
	}

	private JPanel getPnCount() {
		if (pnCount == null) {
			pnCount = new JPanel();
			pnCount.add(getLblPatients());
			pnCount.add(getTextField());
		}
		return pnCount;
	}

	private JLabel getLblPatients() {
		if (lblPatients == null) {
			lblPatients = new JLabel("Patients:");
		}
		return lblPatients;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEditable(false);
			textField.setColumns(10);
		}
		return textField;
	}

	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("Clear");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					patients = new ArrayList<>();
					getPanel().removeAll();
					for (AppointmentBLDto apmnt : appointments) {

						if (!patients.contains(apmnt)) {
							patients.add(apmnt);
							panel.add(getNewPatient(apmnt));
						}
					}
					getTextField().setText(String.valueOf(patients.size()));
					getSpPatients().invalidate();
					getSpPatients().validate();
					getSpPatients().repaint();
				}
			});
		}
		return btnClear;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setDate(Date.valueOf(LocalDate.now()));
			dateChooser.setDateFormatString("yyyy-MM-dd");
		}
		return dateChooser;
	}

	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter");
			btnFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filterPatients();
				}

			});
		}
		return btnFilter;
	}

	private void filterPatients() {
		patients = new ArrayList<>();
		getPanel().removeAll();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(getDateChooser().getDate());
		for (AppointmentBLDto apmnt : appointments) {
			var apDate = apmnt.startDate.split(" ");
			if (apDate[0].equals(format) && !patients.contains(apmnt)) {
				patients.add(apmnt);
				panel.add(getNewPatient(apmnt));
			}
		}
		getTextField().setText(String.valueOf(patients.size()));
		getSpPatients().invalidate();
		getSpPatients().validate();
		getSpPatients().repaint();
	}

	private JScrollPane getSpPatients() {
		if (spPatients == null) {
			spPatients = new JScrollPane();
			spPatients.setViewportView(getPanel());
		}
		return spPatients;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(0, 1, 0, 0));

		}
		return panel;
	}

	private JPanel getNewPatient(AppointmentBLDto a) {

		newPatient = new JPanel();

		if (a.checkIn == null || a.checkIn.equals("1")) {
			newPatient.setBackground(new Color(159, 234, 124));
			if (a.checkOut != null && a.checkOut.equals("0")) {
				newPatient.setBackground(new Color(172, 245, 142));
			} else
				newPatient.setBackground(new Color(159, 234, 124));
		} else
			newPatient.setBackground(new Color(241, 116, 245));

		newPatient.setLayout(new GridLayout(0, 2, 0, 0));
		newPatient.add(new JLabel("Patient:"));
		newPatient.add(new JLabel((a.patientName + " " + a.patientSurname).toUpperCase()));

		return newPatient;
	}

}
