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
import java.awt.Font;
import javax.swing.JTextField;

public class JustificationView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCause;
	private JTextField txtCause;
	private JTextField textField;

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
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblCause());
		contentPanel.add(getTxtCause());
		{
			JLabel lblOtherComments = new JLabel("Comments:");
			lblOtherComments.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblOtherComments.setBounds(10, 75, 211, 20);
			contentPanel.add(lblOtherComments);
		}
		{
			textField = new JTextField();
			textField.setBounds(10, 94, 416, 128);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton saveButton = new JButton("Save");
				saveButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
		}
	}
	private JLabel getLblCause() {
		if (lblCause == null) {
			lblCause = new JLabel("Introduce the cause of the change in the work period:");
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
}
