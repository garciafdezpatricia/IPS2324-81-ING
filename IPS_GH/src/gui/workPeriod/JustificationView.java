package gui.workPeriod;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class JustificationView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCause;
	private JTextField txtCause;
	private JTextField txtComments;
	private JLabel lblOtherComments;
	private JPanel panelButton;
	private JButton btnCancel;
	private JButton btnSave;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JustificationView dialog = new JustificationView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JustificationView() {
		setTitle("Justification form");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JustificationView.class.getResource("/img/descarga.jpg")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.add(getLblCause());
		contentPanel.add(getTxtCause());
		contentPanel.add(getLblOtherComments());
		contentPanel.add(getTxtComments());
		getContentPane().add(getPanelButton(), BorderLayout.SOUTH);
		{

		}
	}

	private JLabel getLblCause() {
		if (lblCause == null) {
			lblCause = new JLabel("Introduce the cause(s) of the change in the work period:");
			lblCause.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblCause.setBounds(10, 21, 369, 20);
		}
		return lblCause;
	}

	private JTextField getTxtCause() {
		if (txtCause == null) {
			txtCause = new JTextField();
			txtCause.setBounds(10, 45, 416, 20);
			txtCause.setColumns(10);
		}
		return txtCause;
	}

	private JLabel getLblOtherComments() {
		if (lblOtherComments == null) {
			lblOtherComments = new JLabel("Comments:");
			lblOtherComments.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblOtherComments.setBounds(10, 75, 211, 20);
		}
		return lblOtherComments;
	}

	private JTextField getTxtComments() {
		if (txtComments == null) {
			txtComments = new JTextField();
			txtComments.setBounds(10, 94, 416, 128);
			contentPanel.add(txtComments);
			txtComments.setColumns(10);
		}
		return txtComments;
	}

	private JPanel getPanelButton() {
		if (panelButton == null) {
			panelButton = new JPanel();
			panelButton.setBorder(null);
			panelButton.setLayout(new GridLayout(0, 2, 0, 0));
			panelButton.add(getBtnCancel());
			panelButton.add(getBtnSave());
		}
		return panelButton;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(btnSave,
							"Are you sure you want to ezit the edition of the justification?") == JOptionPane.YES_OPTION) {
						dispose();
					}
				}
			});
		}
		return btnCancel;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

		}
		return btnSave;
	}
}
