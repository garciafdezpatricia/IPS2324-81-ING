package gui.doctor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.ConnectionFactory;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorIdentificationView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtMedicalID;
	private JLabel lblIdentification;
	private JButton okButton;
	private JPanel buttonPane;
	private JButton cancelButton;
	private String docID;
	private CreateRequestAppointmentView crav;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DoctorIdentificationView dialog = new DoctorIdentificationView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DoctorIdentificationView() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(DoctorIdentificationView.class.getResource("/img/descarga.jpg")));
		setTitle("Doctor identification");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblIdentification());
		contentPanel.add(getTxtMedicalID());
		getContentPane().add(getButtonPane(), BorderLayout.SOUTH);
		getButtonPane().add(getCancelButton());
		getButtonPane().add(getOkButton());
		getRootPane().setDefaultButton(getOkButton());

	}

	private JLabel getLblIdentification() {
		if (lblIdentification == null) {
			lblIdentification = new JLabel("Introduce your personal ID:");
			lblIdentification.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblIdentification.setBounds(124, 79, 215, 27);
		}
		return lblIdentification;
	}

	private JPanel getButtonPane() {
		if (buttonPane == null) {
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		return buttonPane;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getTxtMedicalID().getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "The ID is not valid. Try again.");
					} else {
						docID = getTxtMedicalID().getText();
						try {
							checkDoctor();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			});
			okButton.setActionCommand("OK");
		}
		return okButton;
	}

	/**
	 * if the doctor exists in the data base, a new window of create request
	 * appointment is created and the id of the doctor is passed as a parameter, so
	 * the doctor is selected by default when the new window is opened
	 */
	private void checkDoctor() throws Exception {
		if (ConnectionFactory.checkIfDoctorIDExists(docID) != -1) {
			MenuDoctor md;
			try {
				md = new MenuDoctor(docID);
				md.setVisible(true);
				md.setLocationRelativeTo(null);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(DoctorIdentificationView.this,
					"The introduced ID does not correspond to any doctor in the data base.", "Warning",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
		}
		return cancelButton;
	}

	private JTextField getTxtMedicalID() {
		if (txtMedicalID == null) {
			txtMedicalID = new JTextField();
			txtMedicalID.setBounds(85, 116, 266, 27);
			contentPanel.add(txtMedicalID);
			txtMedicalID.setColumns(10);
		}
		return txtMedicalID;
	}

	public String getDocID() {
		return this.docID;
	}

}
