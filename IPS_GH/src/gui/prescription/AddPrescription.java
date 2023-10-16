package gui.prescription;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class AddPrescription extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel pnMedication;
	private JPanel pnVaccines;
	private JPanel pnOther;
	private JPanel panel;
	private JLabel lblPrescription;
	private JPanel panel_1;
	private JComboBox cbMedication;
	private JTextField txtfOther;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPanel panel_9;
	private JTextArea textArea;
	private JLabel lblNewLabel_3;
	private JSpinner spQuantity;
	private JSpinner spInterval;
	private JSpinner spDuration;
	private JPanel panel_10;
	private JButton btnPrescript;
	private JPanel panel_1_1;
	private JComboBox cbMedication_1;
	private JTextField textField;
	private JPanel panel_11;
	private JButton btnVaccine;
	private JTextArea textArea_1;
	private JButton btnOther;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPrescription frame = new AddPrescription();
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
	public AddPrescription() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getTabbedPane());
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Medication", null, getPnMedication(), null);
			tabbedPane.addTab("Vaccines", null, getPnVaccines(), null);
			tabbedPane.addTab("Other", null, getPnOther(), null);
		}
		return tabbedPane;
	}
	private JPanel getPnMedication() {
		if (pnMedication == null) {
			pnMedication = new JPanel();
			pnMedication.setLayout(new BorderLayout(0, 0));
			pnMedication.add(getPanel_1(), BorderLayout.NORTH);
			pnMedication.add(getPanel_2(), BorderLayout.CENTER);
			pnMedication.add(getPanel_10(), BorderLayout.SOUTH);
		}
		return pnMedication;
	}
	private JPanel getPnVaccines() {
		if (pnVaccines == null) {
			pnVaccines = new JPanel();
			pnVaccines.setLayout(new BorderLayout(0, 0));
			pnVaccines.add(getPanel_1_1());
			pnVaccines.add(getPanel_11(), BorderLayout.SOUTH);
		}
		return pnVaccines;
	}
	private JPanel getPnOther() {
		if (pnOther == null) {
			pnOther = new JPanel();
			pnOther.setLayout(new BorderLayout(0, 0));
			pnOther.add(getTextArea_1());
			pnOther.add(getBtnOther(), BorderLayout.SOUTH);
		}
		return pnOther;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblPrescription());
		}
		return panel;
	}
	private JLabel getLblPrescription() {
		if (lblPrescription == null) {
			lblPrescription = new JLabel("Add a Prescription");
		}
		return lblPrescription;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getCbMedication());
			panel_1.add(getTxtfOther());
		}
		return panel_1;
	}
	private JComboBox getCbMedication() {
		if (cbMedication == null) {
			cbMedication = new JComboBox();
			var data = new String[] {"Algidol", "Ibuprofeno", "Antibiotics", "Thrombocid", "Other..."};
			cbMedication.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbMedication.getSelectedIndex()==data.length-1) {
						getTxtfOther().setEnabled(true);
					}else {
						getTxtfOther().setEnabled(false);
					}
				}
			});
			cbMedication.setModel(new DefaultComboBoxModel(data));
		}
		return cbMedication;
	}
	private JTextField getTxtfOther() {
		if (txtfOther == null) {
			txtfOther = new JTextField();
			txtfOther.setEnabled(false);
			txtfOther.setColumns(10);
		}
		return txtfOther;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new GridLayout(0, 2, 0, 0));
			panel_2.add(getPanel_3());
			panel_2.add(getPanel_4());
			panel_2.add(getPanel_5());
			panel_2.add(getPanel_6());
			panel_2.add(getPanel_7());
			panel_2.add(getPanel_8());
			panel_2.add(getPanel_9());
			panel_2.add(getTextArea());
		}
		return panel_2;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.add(getLblNewLabel());
		}
		return panel_3;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.add(getSpQuantity());
		}
		return panel_4;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.add(getLblNewLabel_1());
		}
		return panel_5;
	}
	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			panel_6.add(getSpInterval());
		}
		return panel_6;
	}
	private JPanel getPanel_7() {
		if (panel_7 == null) {
			panel_7 = new JPanel();
			panel_7.add(getLblNewLabel_2());
		}
		return panel_7;
	}
	private JPanel getPanel_8() {
		if (panel_8 == null) {
			panel_8 = new JPanel();
			panel_8.add(getSpDuration());
		}
		return panel_8;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Quantity");
		}
		return lblNewLabel;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Interval");
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Duration");
		}
		return lblNewLabel_2;
	}
	private JPanel getPanel_9() {
		if (panel_9 == null) {
			panel_9 = new JPanel();
			panel_9.add(getLblNewLabel_3());
		}
		return panel_9;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Comments");
		}
		return lblNewLabel_3;
	}
	private JSpinner getSpQuantity() {
		if (spQuantity == null) {
			spQuantity = new JSpinner();
		}
		return spQuantity;
	}
	private JSpinner getSpInterval() {
		if (spInterval == null) {
			spInterval = new JSpinner();
		}
		return spInterval;
	}
	private JSpinner getSpDuration() {
		if (spDuration == null) {
			spDuration = new JSpinner();
		}
		return spDuration;
	}
	private JPanel getPanel_10() {
		if (panel_10 == null) {
			panel_10 = new JPanel();
			panel_10.add(getBtnPrescript());
		}
		return panel_10;
	}
	private JButton getBtnPrescript() {
		if (btnPrescript == null) {
			btnPrescript = new JButton("Add Prescription");
		}
		return btnPrescript;
	}
	private JPanel getPanel_1_1() {
		if (panel_1_1 == null) {
			panel_1_1 = new JPanel();
			panel_1_1.add(getCbMedication_1());
			panel_1_1.add(getTextField());
		}
		return panel_1_1;
	}
	private JComboBox getCbMedication_1() {
		if (cbMedication_1 == null) {
			cbMedication_1 = new JComboBox();
			var data = new String[] {"Covid-19 vaccine", "Tetanos vaccine", "Spanish flu vaccine", "Viruela vaccine", "Other..."};
			cbMedication_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbMedication_1.getSelectedIndex()==data.length-1) {
						getTextField().setEnabled(true);
					}else {
						getTextField().setEnabled(false);
					}
				}
			});
			cbMedication_1.setModel(new DefaultComboBoxModel(data));
		}
		return cbMedication_1;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEnabled(false);
			textField.setColumns(10);
		}
		return textField;
	}
	private JPanel getPanel_11() {
		if (panel_11 == null) {
			panel_11 = new JPanel();
			panel_11.add(getBtnVaccine());
		}
		return panel_11;
	}
	private JButton getBtnVaccine() {
		if (btnVaccine == null) {
			btnVaccine = new JButton("Add prescription");
		}
		return btnVaccine;
	}
	private JTextArea getTextArea_1() {
		if (textArea_1 == null) {
			textArea_1 = new JTextArea();
		}
		return textArea_1;
	}
	private JButton getBtnOther() {
		if (btnOther == null) {
			btnOther = new JButton("Add Prescription");
		}
		return btnOther;
	}
}
