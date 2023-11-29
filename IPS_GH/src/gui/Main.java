package gui;

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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import gui.doctor.DoctorIdentificationView;
import gui.doctor.MenuDoctor;
import gui.manager.ManagerMenu;
import gui.medicalRecepcionist.MenuMedicalRecepcionist;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnNorth;
	private JPanel panelCenter;
	private JLabel lblDocOrRece;
	private JButton btnDoctor;
	private JButton btnMedicalRecepcionist;
	private JButton btnManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
//		setIconImage(
//				Toolkit.getDefaultToolkit().getImage(MenuMedicalRecepcionist.class.getResource("/img/descarga.jpg")));
		setTitle("Main menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		contentPane.add(getPnNorth(), BorderLayout.NORTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getLblDocOrRece());
		}
		return pnNorth;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 2, 0, 0));
			panelCenter.add(getBtnDoctor());
			panelCenter.add(getBtnManager());
			panelCenter.add(getBtnMedicalRecepcionist());
		}
		return panelCenter;
	}

	private JLabel getLblDocOrRece() {
		if (lblDocOrRece == null) {
			lblDocOrRece = new JLabel("Who are you?");
			lblDocOrRece.setHorizontalAlignment(SwingConstants.CENTER);
			lblDocOrRece.setFont(new Font("Tahoma", Font.BOLD, 17));
		}
		return lblDocOrRece;
	}

	private JButton getBtnDoctor() {
		if (btnDoctor == null) {
			btnDoctor = new JButton("Doctor");
			btnDoctor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DoctorIdentificationView div = new DoctorIdentificationView();
					div.setVisible(true);
					div.setLocationRelativeTo(null);
				}
			});
		}
		return btnDoctor;
	}

	private JButton getBtnMedicalRecepcionist() {
		if (btnMedicalRecepcionist == null) {
			btnMedicalRecepcionist = new JButton("Medical recepcionist");
			btnMedicalRecepcionist.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuMedicalRecepcionist mr;
					try {
						mr = new MenuMedicalRecepcionist();
						mr.setVisible(true);
						mr.setLocationRelativeTo(null);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnMedicalRecepcionist;
	}
	private JButton getBtnManager() {
		if (btnManager == null) {
			btnManager = new JButton("Manager");
			btnManager.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ManagerMenu mv;
					try {
						mv = new ManagerMenu();
						mv.setVisible(true);
						mv.setLocationRelativeTo(null);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnManager;
	}
}
