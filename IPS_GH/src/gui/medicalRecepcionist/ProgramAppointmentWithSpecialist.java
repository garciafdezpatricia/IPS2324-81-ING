package gui.medicalRecepcionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;

public class ProgramAppointmentWithSpecialist extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCenter;
	private JPanel panelSpecialization;
	private JScrollPane scrollPaneInformation;
	private JLabel lblSpecialization;
	private JScrollPane scrollPaneSpecializations;
	private JList listSpecializations;
	private JTextArea textAreaInformation;
	private JPanel panelButtons;
	private JButton btnSave;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgramAppointmentWithSpecialist frame = new ProgramAppointmentWithSpecialist();
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
	public ProgramAppointmentWithSpecialist() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelCenter());
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
	}
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getPanelSpecialization_1());
			panelCenter.add(getScrollPaneInformation_1());
		}
		return panelCenter;
	}
	private JPanel getPanelSpecialization_1() {
		if (panelSpecialization == null) {
			panelSpecialization = new JPanel();
			panelSpecialization.setLayout(new BorderLayout(0, 0));
			panelSpecialization.add(getLblSpecialization(), BorderLayout.NORTH);
			panelSpecialization.add(getScrollPaneSpecializations(), BorderLayout.CENTER);
		}
		return panelSpecialization;
	}
	private JScrollPane getScrollPaneInformation_1() {
		if (scrollPaneInformation == null) {
			scrollPaneInformation = new JScrollPane();
			scrollPaneInformation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneInformation.setViewportView(getTextAreaInformation());
		}
		return scrollPaneInformation;
	}
	private JLabel getLblSpecialization() {
		if (lblSpecialization == null) {
			lblSpecialization = new JLabel("Select the specialization/s:");
		}
		return lblSpecialization;
	}
	private JScrollPane getScrollPaneSpecializations() {
		if (scrollPaneSpecializations == null) {
			scrollPaneSpecializations = new JScrollPane();
			scrollPaneSpecializations.setViewportView(getListSpecializations());
		}
		return scrollPaneSpecializations;
	}
	private JList getListSpecializations() {
		if (listSpecializations == null) {
			listSpecializations = new JList();
		}
		return listSpecializations;
	}
	private JTextArea getTextAreaInformation() {
		if (textAreaInformation == null) {
			textAreaInformation = new JTextArea();
		}
		return textAreaInformation;
	}
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtons.add(getBtnCancel());
			panelButtons.add(getBtnSave());
		}
		return panelButtons;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
		}
		return btnSave;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
		}
		return btnCancel;
	}
}
