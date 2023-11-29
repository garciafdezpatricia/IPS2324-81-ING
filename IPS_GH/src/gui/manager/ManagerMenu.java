package gui.manager;

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
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import gui.disesaseGraph.DiseaseGraph;
import gui.doctorGraoh.DoctorGraph;

public class ManagerMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblWhatDoYouWant;
	private JPanel panelButtons;
	private JPanel panelBack;
	private JButton btnCancel;
	private JButton btnEditWp;
	private JButton btnEditAndCancel;
	private JButton btnEmployeesInfo;
	private JButton btnDoctorsGraph;
	private JButton btnDiagnosisGraph;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerMenu frame = new ManagerMenu();
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
	public ManagerMenu() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ManagerMenu.class.getResource("/img/descarga.jpg")));
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 553, 363);
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

			panelButtons.add(getBtnEmployeesInfo());
			panelButtons.add(getBtnDoctorsGraph());
			panelButtons.add(getBtnDiagnosisGraph());
		}
		return panelButtons;
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



	private JButton getBtnWorking() {
		if (btnEditAndCancel == null) {
			btnEditAndCancel = new JButton("Edit or cancel appointment");
			
		}
		return btnEditAndCancel;
	}
	private JButton getBtnEmployeesInfo() {
		if (btnEmployeesInfo == null) {
			btnEmployeesInfo = new JButton("Schedules Information");
			btnEmployeesInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					ManagerView mv = new ManagerView();
					mv.setVisible(true);
					mv.setLocationRelativeTo(null);
				}
			});
		}
		return btnEmployeesInfo;
	}
	private JButton getBtnDoctorsGraph() {
		if (btnDoctorsGraph == null) {
			btnDoctorsGraph = new JButton("Doctors Spec Graph");
			btnDoctorsGraph.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DoctorGraph mv = new DoctorGraph();
					mv.setVisible(true);
					mv.setLocationRelativeTo(null);
				}
			});
		}
		return btnDoctorsGraph;
	}
	private JButton getBtnDiagnosisGraph() {
		if (btnDiagnosisGraph == null) {
			btnDiagnosisGraph = new JButton("DiagnosisGraph");
			btnDiagnosisGraph.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DiseaseGraph mv = new DiseaseGraph();
					mv.setVisible(true);
					mv.setLocationRelativeTo(null);
				}
			});
		}
		return btnDiagnosisGraph;
	}
}
