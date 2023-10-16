package gui.medicalRecord;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import db.MedicalRecord;
import util.MedicalRecordBLDto;

import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedicalRecordView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnSearch;
	private JTextField txtfSearch;
	private JButton btnSearch;
	private JPanel pnInfo;
	private JPanel pnDisease;
	private JPanel pnVaccines;
	private JPanel panel;
	private JButton btnBack;
	private JLabel lblNewLabel;
	private JTextArea txtADisease;
	private JTextArea txtaVaccines;
	private MedicalRecordBLDto selectedMedRec;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicalRecordView frame = new MedicalRecordView();
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
	public MedicalRecordView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnSearch(), BorderLayout.NORTH);
		contentPane.add(getPnInfo(), BorderLayout.CENTER);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
	}

	private JPanel getPnSearch() {
		if (pnSearch == null) {
			pnSearch = new JPanel();
			pnSearch.add(getLblNewLabel());
			pnSearch.add(getTxtfSearch());
			pnSearch.add(getBtnSearch());
		}
		return pnSearch;
	}
	private JTextField getTxtfSearch() {
		if (txtfSearch == null) {
			txtfSearch = new JTextField();
			
			txtfSearch.setColumns(20);
		}
		return txtfSearch;
	}
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("Search");
			btnSearch.addMouseListener(new MouseAdapter() {
				

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
					var num =Integer.parseInt(getTxtfSearch().getText());
					selectedMedRec = MedicalRecord.getRecord(num);
					if(selectedMedRec.disease != null)
						getTxtADisease().setText(String.join("\n",selectedMedRec.disease));
					if(selectedMedRec.vaccines != null)
						getTxtaVaccines().setText(String.join("\n",selectedMedRec.vaccines));
					}catch(Exception err) {
						err.printStackTrace();
						getTxtfSearch().setText("This ID does not exist");
					}
					getPnDisease().invalidate();
					getPnDisease().validate();
					getPnDisease().repaint();
					
					getPnVaccines().invalidate();
					getPnVaccines().validate();
					getPnVaccines().repaint();
					
				}
			});
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
		}
		return btnSearch;
	}
	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setLayout(new GridLayout(0, 2, 0, 0));
			pnInfo.add(getPnDisease());
			pnInfo.add(getPnVaccines());
		}
		return pnInfo;
	}
	private JPanel getPnDisease() {
		if (pnDisease == null) {
			pnDisease = new JPanel();
			pnDisease.setBackground(new Color(255, 255, 255));
			pnDisease.setBorder(new TitledBorder(null, "Disease", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDisease.setLayout(new BorderLayout(0, 0));
			pnDisease.add(getTxtADisease());
		}
		return pnDisease;
	}
	private JPanel getPnVaccines() {
		if (pnVaccines == null) {
			pnVaccines = new JPanel();
			pnVaccines.setBackground(new Color(255, 255, 255));
			pnVaccines.setBorder(new TitledBorder(null, "Vaccines", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnVaccines.setLayout(new BorderLayout(0, 0));
			pnVaccines.add(getTxtaVaccines());
		}
		return pnVaccines;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnBack());
		}
		return panel;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
		}
		return btnBack;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Select Patient ID");
		}
		return lblNewLabel;
	}
	private JTextArea getTxtADisease() {
		if (txtADisease == null) {
			txtADisease = new JTextArea();
			txtADisease.setEditable(false);
		}
		return txtADisease;
	}
	private JTextArea getTxtaVaccines() {
		if (txtaVaccines == null) {
			txtaVaccines = new JTextArea();
		}
		return txtaVaccines;
	}
}
