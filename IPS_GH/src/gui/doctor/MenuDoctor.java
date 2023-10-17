package gui.doctor;

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

import gui.medicalRecepcionist.MenuMedicalRecepcionist;
import gui.prescription.AddPrescription;
import gui.schedule.Schedule;

public class MenuDoctor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorth;
	private JLabel lblToDo;
	private JPanel panelCenter;
	private JButton btnSch;
	private JButton btnCreateRecipe;
	private JPanel panelSouth;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDoctor frame = new MenuDoctor();

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
	public MenuDoctor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Doctor menu");
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
		contentPane.add(getPanelNorth(), BorderLayout.NORTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
		contentPane.add(getPanelSouth(), BorderLayout.SOUTH);
	}

	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new BorderLayout(0, 0));
			panelNorth.add(getLblToDo(), BorderLayout.NORTH);
		}
		return panelNorth;
	}
	private JLabel getLblToDo() {
		if (lblToDo == null) {
			lblToDo = new JLabel("What do you want to do?");
			lblToDo.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblToDo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblToDo;
	}
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getBtnSch());
			panelCenter.add(getBtnCreateRecipe());
		}
		return panelCenter;
	}
	private JButton getBtnSch() {
		if (btnSch == null) {
			btnSch = new JButton("Consult the schedule");
			btnSch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Schedule mr;
					try {
						mr = new Schedule();
						mr.setVisible(true);
						mr.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnSch;
	}
	private JButton getBtnCreateRecipe() {
		if (btnCreateRecipe == null) {
			btnCreateRecipe = new JButton("Create prescription");
			btnCreateRecipe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddPrescription mr;
					try {
						mr = new AddPrescription();
						mr.setVisible(true);
						mr.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnCreateRecipe;
	}
	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout(0, 0));
			panelSouth.add(getBtnCancel(), BorderLayout.NORTH);
		}
		return panelSouth;
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
}
