package gui.medicalRecepcionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.ConnectionFactory;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InBoxView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panelPendingOfAssigning;
	private JPanel panelTitle;
	private JLabel lblTitle;
	private JPanel panelAppointments;
	private JPanel panelButtons;
	private JButton btnAssignDoctor;
	private JList listAppointments;
	private int selectedIndex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InBoxView frame = new InBoxView();
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
	public InBoxView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getTabbedPane());
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					selectedIndex = tabbedPane.getSelectedIndex();
//					if (selectedIndex == 0) {
//						getListSpecialization().clearSelection();
//
//					}
//					if (selectedIndex == 1) {
//						getListDoctor().clearSelection();
//					}
				}
			});
			tabbedPane.addTab("Pending of assigning doctor", null, getPanelPendingOfAssigning(), null);
		}
		return tabbedPane;
	}
	private JPanel getPanelPendingOfAssigning() {
		if (panelPendingOfAssigning == null) {
			panelPendingOfAssigning = new JPanel();
			panelPendingOfAssigning.setLayout(new BorderLayout(0, 0));
			panelPendingOfAssigning.add(getPanelTitle(), BorderLayout.NORTH);
			panelPendingOfAssigning.add(getPanelAppointments(), BorderLayout.CENTER);
			panelPendingOfAssigning.add(getPanelButtons(), BorderLayout.SOUTH);
		}
		return panelPendingOfAssigning;
	}
	private JPanel getPanelTitle() {
		if (panelTitle == null) {
			panelTitle = new JPanel();
			panelTitle.add(getLblTitle());
		}
		return panelTitle;
	}
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Pending appointments");
		}
		return lblTitle;
	}
	private JPanel getPanelAppointments() {
		if (panelAppointments == null) {
			panelAppointments = new JPanel();
			panelAppointments.setLayout(new BorderLayout(0, 0));
			panelAppointments.add(getListAppointments(), BorderLayout.NORTH);
		}
		return panelAppointments;
	}
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnAssignDoctor());
		}
		return panelButtons;
	}
	private JButton getBtnAssignDoctor() {
		if (btnAssignDoctor == null) {
			btnAssignDoctor = new JButton("Assign doctor");
			btnAssignDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnAssignDoctor;
	}
	private JList getListAppointments() {
		if (listAppointments == null) {
			listAppointments = new JList(ConnectionFactory.getAppointmentsPendingOfAssigning());
		}
		return listAppointments;
	}
}
