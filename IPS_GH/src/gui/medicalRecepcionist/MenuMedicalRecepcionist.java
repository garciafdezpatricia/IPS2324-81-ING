package gui.medicalRecepcionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import gui.workPeriod.EditWorkPeriodView;
import gui.workPeriod.WorkPeriodView;

public class MenuMedicalRecepcionist extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblWhatDoYouWant;
	private JPanel panelButtons;
	private JButton btnCreateApp;
	private JPanel panelBack;
	private JButton btnCancel;
	private JButton btnAssignWorkPeriod;
	private JButton btnEditWp;
	private JButton btnEditAndCancel;
	private JButton btnProgram;

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
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		UIManager.getLookAndFeelDefaults().put("nimbusBase", new Color(51, 153, 255)); // Cambiar el color bases
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

			panelButtons.setLayout(new GridLayout(0, 3, 0, 0));
			panelButtons.add(getBtnEditAndCancel());

			panelButtons.add(getBtnCreateApp());
			panelButtons.add(getBtnAssignWorkPeriod());
			panelButtons.add(getBtnEditWp());
			panelButtons.add(getBtnProgram());
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

	private JButton getBtnEditWp() {
		if (btnEditWp == null) {
			btnEditWp = new JButton("Edit workperiod");
			btnEditWp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

//					EditWorkPeriodView editWp;
//					try {
//						editWp = new EditWorkPeriodView();
//						editWp.setVisible(true);
//						editWp.setLocationRelativeTo(null);
//
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
				}
			});
		}
		return btnEditWp;
	}

	private JButton getBtnEditAndCancel() {
		if (btnEditAndCancel == null) {
			btnEditAndCancel = new JButton("Edit or cancel appointment");
			btnEditAndCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EditAndCancelView editWp;
					try {
						editWp = new EditAndCancelView();
						editWp.setVisible(true);
						editWp.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnEditAndCancel;
	}

	private JButton getBtnProgram() {
		if (btnProgram == null) {
			btnProgram = new JButton("Program Appointment with a specialit");
			btnProgram.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProgramAppointmentWithSpecialist editWp;
					try {
						editWp = new ProgramAppointmentWithSpecialist();
						editWp.setVisible(true);
						editWp.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnProgram;
	}
}
