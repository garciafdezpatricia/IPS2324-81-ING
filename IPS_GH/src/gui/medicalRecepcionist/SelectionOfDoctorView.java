package gui.medicalRecepcionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JList;
import db.Doctor;
import javax.swing.ListModel;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class SelectionOfDoctorView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelButtons;
	private JPanel panelTitle;
	private JPanel panelCenter;
	private JButton btnSave;
	private JLabel lblChooseDoctor;
	private JPanel panel_doctor;
	private JPanel panelSur;
	private JPanel panelSurDoctor;
	private JRadioButton rdbtnUrgent;
	private JButton btnReset;
	private JPanel panel_doctor_Center;
	private JPanel panelNameAndNumber;
	private JLabel lblTypeDoctor;
	private JTextField textField;
	private JButton btnFilterName;
	private JLabel lblSurnameDoctor;
	private JTextField textField_1;
	private JButton btnSurnameDoctor;
	private JLabel lblRegistrationNumber;
	private JTextField textField_2;
	private JButton btnRegNumber;
	private JScrollPane scrollPaneDoctor;
	private JList<Doctor> listDoctor;
	private JPanel panelDoctorAvailability;
	private JDateChooser dateChooser;
	private JScrollPane scrollPaneDoctorAvailability;
	private JTextArea textAreaDoctorAvailability;
	private JPanel panelButtonsDate;
	private JButton btnPrev;
	private JButton btnNext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectionOfDoctorView frame = new SelectionOfDoctorView();
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
	public SelectionOfDoctorView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelTitle(), BorderLayout.NORTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
			panelButtons.add(getBtnSave());
		}
		return panelButtons;
	}
	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.setLayout(new GridLayout(0, 1, 0, 0));
			panelTitle.add(getLblChooseDoctor());
		}
		return panelTitle;
	}
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(1, 0, 0, 0));
			panelCenter.add(getPanel_doctor());
			panelCenter.add(getPanelDoctorAvailability());
		}
		return panelCenter;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
		}
		return btnSave;
	}
	private JLabel getLblChooseDoctor() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Choose doctor");
			lblChooseDoctor.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblChooseDoctor;
	}
	private JPanel getPanel_doctor() {
		if (panel_doctor == null) {
			panel_doctor = new JPanel();
			panel_doctor.setBorder(new TitledBorder(null, "Doctor ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_doctor.setLayout(new BorderLayout(0, 0));
			panel_doctor.add(getPanelSur(), BorderLayout.SOUTH);
			panel_doctor.add(getPanel_doctor_Center(), BorderLayout.CENTER);
		}
		return panel_doctor;
	}
	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getPanelSurDoctor(), BorderLayout.NORTH);
		}
		return panelSur;
	}
	private JPanel getPanelSurDoctor() {
		if (panelSurDoctor == null) {
			panelSurDoctor = new JPanel();
			panelSurDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelSurDoctor.add(getRdbtnUrgent());
			panelSurDoctor.add(getBtnReset());
		}
		return panelSurDoctor;
	}
	private JRadioButton getRdbtnUrgent() {
		if (rdbtnUrgent == null) {
			rdbtnUrgent = new JRadioButton("Urgent");
		}
		return rdbtnUrgent;
	}
	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset Filters");
		}
		return btnReset;
	}
	private JPanel getPanel_doctor_Center() {
		if (panel_doctor_Center == null) {
			panel_doctor_Center = new JPanel();
			panel_doctor_Center.setLayout(new GridLayout(0, 2, 0, 0));
			panel_doctor_Center.add(getPanelNameAndNumber());
			panel_doctor_Center.add(getScrollPaneDoctor());
		}
		return panel_doctor_Center;
	}
	private JPanel getPanelNameAndNumber() {
		if (panelNameAndNumber == null) {
			panelNameAndNumber = new JPanel();
			panelNameAndNumber.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelNameAndNumber.setLayout(new GridLayout(0, 3, 0, 0));
			panelNameAndNumber.add(getLblTypeDoctor());
			panelNameAndNumber.add(getTextField());
			panelNameAndNumber.add(getBtnFilterName());
			panelNameAndNumber.add(getLblSurnameDoctor());
			panelNameAndNumber.add(getTextField_1());
			panelNameAndNumber.add(getBtnSurnameDoctor());
			panelNameAndNumber.add(getLblRegistrationNumber());
			panelNameAndNumber.add(getTextField_2());
			panelNameAndNumber.add(getBtnRegNumber());
		}
		return panelNameAndNumber;
	}
	private JLabel getLblTypeDoctor() {
		if (lblTypeDoctor == null) {
			lblTypeDoctor = new JLabel("By name:");
			lblTypeDoctor.setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		return lblTypeDoctor;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtnFilterName() {
		if (btnFilterName == null) {
			btnFilterName = new JButton("Filter");
		}
		return btnFilterName;
	}
	private JLabel getLblSurnameDoctor() {
		if (lblSurnameDoctor == null) {
			lblSurnameDoctor = new JLabel("By surname:");
			lblSurnameDoctor.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblSurnameDoctor;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setColumns(10);
		}
		return textField_1;
	}
	private JButton getBtnSurnameDoctor() {
		if (btnSurnameDoctor == null) {
			btnSurnameDoctor = new JButton("Filter");
		}
		return btnSurnameDoctor;
	}
	private JLabel getLblRegistrationNumber() {
		if (lblRegistrationNumber == null) {
			lblRegistrationNumber = new JLabel("By doctor's registration number:");
			lblRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 10));
		}
		return lblRegistrationNumber;
	}
	private JTextField getTextField_2() {
		if (textField_2 == null) {
			textField_2 = new JTextField();
			textField_2.setColumns(10);
		}
		return textField_2;
	}
	private JButton getBtnRegNumber() {
		if (btnRegNumber == null) {
			btnRegNumber = new JButton("Filter");
		}
		return btnRegNumber;
	}
	private JScrollPane getScrollPaneDoctor() {
		if (scrollPaneDoctor == null) {
			scrollPaneDoctor = new JScrollPane((Component) null);
			scrollPaneDoctor.setViewportView(getListDoctor());
		}
		return scrollPaneDoctor;
	}
	private JList<Doctor> getListDoctor() {
		if (listDoctor == null) {
			listDoctor = new JList<Doctor>();
		}
		return listDoctor;
	}
	private JPanel getPanelDoctorAvailability() {
		if (panelDoctorAvailability == null) {
			panelDoctorAvailability = new JPanel();
			panelDoctorAvailability.setLayout(new BorderLayout(0, 0));
			panelDoctorAvailability.add(getDateChooser(), BorderLayout.NORTH);
			panelDoctorAvailability.add(getScrollPaneDoctorAvailability(), BorderLayout.CENTER);
			panelDoctorAvailability.add(getPanelButtonsDate(), BorderLayout.SOUTH);
		}
		return panelDoctorAvailability;
	}
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser((Date) null);
		}
		return dateChooser;
	}
	private JScrollPane getScrollPaneDoctorAvailability() {
		if (scrollPaneDoctorAvailability == null) {
			scrollPaneDoctorAvailability = new JScrollPane();
			scrollPaneDoctorAvailability.setViewportView(getTextAreaDoctorAvailability());
		}
		return scrollPaneDoctorAvailability;
	}
	private JTextArea getTextAreaDoctorAvailability() {
		if (textAreaDoctorAvailability == null) {
			textAreaDoctorAvailability = new JTextArea();
			textAreaDoctorAvailability.setText((String) null);
			textAreaDoctorAvailability.setEditable(false);
		}
		return textAreaDoctorAvailability;
	}
	private JPanel getPanelButtonsDate() {
		if (panelButtonsDate == null) {
			panelButtonsDate = new JPanel();
			panelButtonsDate.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtonsDate.add(getBtnPrev());
			panelButtonsDate.add(getBtnNext());
		}
		return panelButtonsDate;
	}
	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton("Previous");
			btnPrev.setEnabled(false);
		}
		return btnPrev;
	}
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
		}
		return btnNext;
	}
}
