package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.Doctor;
import util.ConnectionFactory;

public class IdentificationWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblChooseDoctor;
	private JList<Doctor> listDoctors;

	private DefaultListModel<Doctor> doctors = new DefaultListModel<>();

	private Doctor selectedDoctor;
	private JButton btnNext;
	private JButton btnCancel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IdentificationWindow dialog = new IdentificationWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IdentificationWindow() {
		selectedDoctor = null;
		setIconImage(Toolkit.getDefaultToolkit().getImage(IdentificationWindow.class.getResource("/img/descarga.jpg")));
		setTitle("Doctor selection");
		try {
			doctors = ConnectionFactory.getDoctors();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblChooseDoctor());
		contentPanel.add(getListDoctors());
		contentPanel.add(getBtnNext());
		contentPanel.add(getBtnCancel());
	}


	private JLabel getLblChooseDoctor() {
		if (lblChooseDoctor == null) {
			lblChooseDoctor = new JLabel("Select the doctor to assign a work period");
			lblChooseDoctor.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblChooseDoctor.setBounds(90, 30, 238, 26);
		}
		return lblChooseDoctor;
	}

	private JList<Doctor> getListDoctors() {
		if (listDoctors == null) {
			listDoctors = new JList<Doctor>(doctors);
			listDoctors.setBounds(10, 59, 414, 158);

			listDoctors.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					Doctor d = (Doctor) getListDoctors().getSelectedValue();

					if (d != null) {
						selectedDoctor = d;
					}
				}

			});
		}
		return listDoctors;
	}

	
	public boolean isDoctorSelected() {
		if (selectedDoctor == null)
			return false;
		return true;
		
	}
	
	public Doctor getSelectedDoctor() {
		if (isDoctorSelected())
			return selectedDoctor;
		return null;
	}
	
	
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selectedDoctor != null)
						dispose();
				}
			});
			btnNext.setBounds(335, 228, 89, 23);
		}
		return btnNext;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBounds(239, 228, 89, 23);
		}
		return btnCancel;
	}
}
