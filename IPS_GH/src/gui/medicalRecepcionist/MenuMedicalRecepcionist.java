package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.workPeriod.WorkPeriodView;

import java.awt.Toolkit;

public class MenuMedicalRecepcionist extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblWhatDoYouWant;
	private JPanel panelButtons;
	private JButton btnCreateApp;
	private JPanel panelBack;
	private JButton btnCancel;
	private JButton btnAssignWorkPeriod;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuMedicalRecepcionist frame = new MenuMedicalRecepcionist();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null); // centrar pantalla

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuMedicalRecepcionist() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblWhatDoYouWant(), BorderLayout.NORTH);
		contentPane.add(getPanelButtons(), BorderLayout.CENTER);
		contentPane.add(getPanelBack(), BorderLayout.SOUTH);
	}

	private JLabel getLblWhatDoYouWant() {
		if (lblWhatDoYouWant == null) {
			lblWhatDoYouWant = new JLabel("What do you want to do?");
			lblWhatDoYouWant.setHorizontalAlignment(SwingConstants.CENTER);
			lblWhatDoYouWant.setFont(new Font("Tahoma", Font.BOLD, 14));
		}
		return lblWhatDoYouWant;
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtons.add(getBtnCreateApp());
			panelButtons.add(getBtnAssignWorkPeriod());
		}
		return panelButtons;
	}

	private JButton getBtnCreateApp() {
		if (btnCreateApp == null) {
			btnCreateApp = new JButton("Create an appointment");
			btnCreateApp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MedicalRecepcionistView mr;
						try {
							mr = new MedicalRecepcionistView();
							mr.setVisible(true);
							mr.setLocationRelativeTo(null);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				}
			});
		}
		return btnCreateApp;
	}

	private JPanel getPanelBack() {
		if (panelBack == null) {
			panelBack = new JPanel();
			panelBack.setBackground(new Color(255, 0, 0));
			panelBack.setLayout(new BorderLayout(0, 0));
			panelBack.add(getBtnCancel());
		}
		return panelBack;
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

	private JButton getBtnAssignWorkPeriod() {
		if (btnAssignWorkPeriod == null) {
			btnAssignWorkPeriod = new JButton("Assign Workperiod");
			
			btnAssignWorkPeriod.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WorkPeriodView wp;
						try {
							wp = new WorkPeriodView();
							wp.setVisible(true);
							wp.setLocationRelativeTo(null);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				}
			});
		}
		return btnAssignWorkPeriod;
	}
}
