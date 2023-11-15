package gui.doctor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.Patient;
import util.ConnectionFactory;

import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class AppointmentRequestView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<Patient> patients = new DefaultListModel<>();
	private JPanel panelCenter;
	private JLabel lblComments;
	private JTextField txtComments;
	private JRadioButton rdbtnUrgent;
	private JPanel panelSouthButtons;
	private JButton btnCancel;
	private JButton btnSend;
	private JPanel panelNorth;
	private JPanel panelNorthCenter;
	private JLabel lblPatientName;
	private JTextField textField;
	private JLabel lblPatientSurname;
	private JTextField textField_1;
	private JLabel lblSocialSecurityNum_1;
	private JTextField textField_2;
	private JLabel lblDni;
	private JTextField textField_3;
	private JPanel panelNorthEast;
	private JList listPatients;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppointmentRequestView frame = new AppointmentRequestView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AppointmentRequestView() throws Exception {
		patients = ConnectionFactory.getPatients();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppointmentRequestView.class.getResource("/img/descarga.jpg")));
		setTitle("Appointment request");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
		contentPane.add(getPanelSouthButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelNorth(), BorderLayout.NORTH);
	}
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(4, 1, 0, 0));
			panelCenter.add(getLabel_1());
			panelCenter.add(getTxtComments());
			panelCenter.add(getRdbtnUrgent());
		}
		return panelCenter;
	}
	private JLabel getLabel_1() {
		if (lblComments == null) {
			lblComments = new JLabel("Comments:");
		}
		return lblComments;
	}
	private JTextField getTxtComments() {
		if (txtComments == null) {
			txtComments = new JTextField();
			txtComments.setColumns(10);
		}
		return txtComments;
	}
	private JRadioButton getRdbtnUrgent() {
		if (rdbtnUrgent == null) {
			rdbtnUrgent = new JRadioButton("Urgent");
		}
		return rdbtnUrgent;
	}
	private JPanel getPanelSouthButtons() {
		if (panelSouthButtons == null) {
			panelSouthButtons = new JPanel();
			panelSouthButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelSouthButtons.add(getBtnCancel());
			panelSouthButtons.add(getBtnSend());
		}
		return panelSouthButtons;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
		}
		return btnCancel;
	}
	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton("Send");
		}
		return btnSend;
	}
	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new BorderLayout(0, 0));
			panelNorth.add(getPanelNorthCenter(), BorderLayout.CENTER);
			panelNorth.add(getPanelNorthEast(), BorderLayout.EAST);
		}
		return panelNorth;
	}
	private JPanel getPanelNorthCenter() {
		if (panelNorthCenter == null) {
			panelNorthCenter = new JPanel();
			panelNorthCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelNorthCenter.add(getLblPatientName_1());
			panelNorthCenter.add(getTextField());
			panelNorthCenter.add(getLblPatientSurname_1());
			panelNorthCenter.add(getTextField_1());
			panelNorthCenter.add(getLblSocialSecurityNum_1_1());
			panelNorthCenter.add(getTextField_2());
			panelNorthCenter.add(getLblDni_1());
			panelNorthCenter.add(getTextField_3());
		}
		return panelNorthCenter;
	}
	private JLabel getLblPatientName_1() {
		if (lblPatientName == null) {
			lblPatientName = new JLabel("Patient name:");
		}
		return lblPatientName;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	private JLabel getLblPatientSurname_1() {
		if (lblPatientSurname == null) {
			lblPatientSurname = new JLabel("Patient surname:");
		}
		return lblPatientSurname;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setColumns(10);
		}
		return textField_1;
	}
	private JLabel getLblSocialSecurityNum_1_1() {
		if (lblSocialSecurityNum_1 == null) {
			lblSocialSecurityNum_1 = new JLabel("Social security num:");
		}
		return lblSocialSecurityNum_1;
	}
	private JTextField getTextField_2() {
		if (textField_2 == null) {
			textField_2 = new JTextField();
			textField_2.setColumns(10);
		}
		return textField_2;
	}
	private JLabel getLblDni_1() {
		if (lblDni == null) {
			lblDni = new JLabel("Patient DNI:");
		}
		return lblDni;
	}
	private JTextField getTextField_3() {
		if (textField_3 == null) {
			textField_3 = new JTextField();
			textField_3.setColumns(10);
		}
		return textField_3;
	}
	private JPanel getPanelNorthEast() {
		if (panelNorthEast == null) {
			panelNorthEast = new JPanel();
			panelNorthEast.setLayout(new GridLayout(0, 1, 0, 0));
			panelNorthEast.add(getListPatients_1());
		}
		return panelNorthEast;
	}
	private JList getListPatients_1() {
		if (listPatients == null) {
			listPatients = new JList<>(patients);
			listPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listPatients;
	}
}
