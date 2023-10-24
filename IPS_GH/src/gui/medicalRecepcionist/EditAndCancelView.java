package gui.medicalRecepcionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class EditAndCancelView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelButtons;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnCancel;
	private JPanel panelNorth;
	private JPanel panelFilters;
	private JScrollPane scrollPaneAppointments;
	private JList listAppointments;
	private JPanel panelFilterByPatient;
	private JLabel lblByPatient;
	private JTextField textFieldPatient;
	private JPanel panelDoctor;
	private JLabel lblDoctor;
	private JTextField textFieldDoctor;
	private JPanel panelUrgent;
	private JCheckBox chckbxUrgent;
	private JCheckBox chckbxNotUrgent;
	private JPanel panelAttended;
	private JCheckBox chckbxAttended;
	private JCheckBox chckbxNotAttended;
	private JPanel panelTimeSlot;
	private JLabel lblTimeSlot;
	private JTextField textFieldFrom;
	private JLabel lblGuion;
	private JTextField textFieldTo;
	private JPanel panelOffice;
	private JLabel lblOffice;
	private JTextField textField;
	private JPanel panelButtonsReset;
	private JButton btnFilter;
	private JButton btnResetFilters;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAndCancelView frame = new EditAndCancelView();
					frame.setLocationRelativeTo(null);

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
	public EditAndCancelView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelNorth(), BorderLayout.CENTER);
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtons.add(getBtnCancel());
			panelButtons.add(getBtnEdit());
			panelButtons.add(getBtnRemove());
		}
		return panelButtons;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Edit");
		}
		return btnEdit;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
		}
		return btnRemove;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setBackground(new Color(255, 0, 0));
		}
		return btnCancel;
	}

	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new GridLayout(1, 0, 0, 0));
			panelNorth.add(getPanelFilters());
			panelNorth.add(getScrollPaneAppointments());
		}
		return panelNorth;
	}

	private JPanel getPanelFilters() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters
					.setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelFilters.setLayout(new GridLayout(7, 0, 0, 0));
			panelFilters.add(getPanelFilterByPatient());
			panelFilters.add(getPanelDoctor());
			panelFilters.add(getPanelUrgent());
			panelFilters.add(getPanelAttended());
			panelFilters.add(getPanelTimeSlot());
			panelFilters.add(getPanelOffice());
			panelFilters.add(getPanelButtonsReset());
		}
		return panelFilters;
	}

	private JScrollPane getScrollPaneAppointments() {
		if (scrollPaneAppointments == null) {
			scrollPaneAppointments = new JScrollPane();
			scrollPaneAppointments.setViewportView(getListAppointments());
		}
		return scrollPaneAppointments;
	}

	private JList getListAppointments() {
		if (listAppointments == null) {
			listAppointments = new JList();
		}
		return listAppointments;
	}

	private JPanel getPanelFilterByPatient() {
		if (panelFilterByPatient == null) {
			panelFilterByPatient = new JPanel();
			panelFilterByPatient.setLayout(new GridLayout(1, 0, 0, 0));
			panelFilterByPatient.add(getLblByPatient());
			panelFilterByPatient.add(getTextFieldPatient());
		}
		return panelFilterByPatient;
	}

	private JLabel getLblByPatient() {
		if (lblByPatient == null) {
			lblByPatient = new JLabel("By patient:");
		}
		return lblByPatient;
	}

	private JTextField getTextFieldPatient() {
		if (textFieldPatient == null) {
			textFieldPatient = new JTextField();
			textFieldPatient.setColumns(10);
		}
		return textFieldPatient;
	}

	private JPanel getPanelDoctor() {
		if (panelDoctor == null) {
			panelDoctor = new JPanel();
			panelDoctor.setLayout(new GridLayout(0, 2, 0, 0));
			panelDoctor.add(getLblDoctor());
			panelDoctor.add(getTextFieldDoctor());
		}
		return panelDoctor;
	}

	private JLabel getLblDoctor() {
		if (lblDoctor == null) {
			lblDoctor = new JLabel("By doctor:");
		}
		return lblDoctor;
	}

	private JTextField getTextFieldDoctor() {
		if (textFieldDoctor == null) {
			textFieldDoctor = new JTextField();
			textFieldDoctor.setColumns(10);
		}
		return textFieldDoctor;
	}

	private JPanel getPanelUrgent() {
		if (panelUrgent == null) {
			panelUrgent = new JPanel();
			panelUrgent.setLayout(new GridLayout(0, 2, 0, 0));
			panelUrgent.add(getChckbxUrgent());
			panelUrgent.add(getChckbxNotUrgent());
		}
		return panelUrgent;
	}

	private JCheckBox getChckbxUrgent() {
		if (chckbxUrgent == null) {
			chckbxUrgent = new JCheckBox("Urgent");
		}
		return chckbxUrgent;
	}

	private JCheckBox getChckbxNotUrgent() {
		if (chckbxNotUrgent == null) {
			chckbxNotUrgent = new JCheckBox("Not urgent");
		}
		return chckbxNotUrgent;
	}

	private JPanel getPanelAttended() {
		if (panelAttended == null) {
			panelAttended = new JPanel();
			panelAttended.setLayout(new GridLayout(1, 0, 0, 0));
			panelAttended.add(getChckbxAttended());
			panelAttended.add(getChckbxNotAttended());
		}
		return panelAttended;
	}

	private JCheckBox getChckbxAttended() {
		if (chckbxAttended == null) {
			chckbxAttended = new JCheckBox("Attended");
		}
		return chckbxAttended;
	}

	private JCheckBox getChckbxNotAttended() {
		if (chckbxNotAttended == null) {
			chckbxNotAttended = new JCheckBox("Not attended");
		}
		return chckbxNotAttended;
	}

	private JPanel getPanelTimeSlot() {
		if (panelTimeSlot == null) {
			panelTimeSlot = new JPanel();
			panelTimeSlot.setLayout(new GridLayout(1, 0, 0, 0));
			panelTimeSlot.add(getLblTimeSlot());
			panelTimeSlot.add(getTextFieldFrom());
			panelTimeSlot.add(getLblGuion());
			panelTimeSlot.add(getTextFieldTo());
		}
		return panelTimeSlot;
	}

	private JLabel getLblTimeSlot() {
		if (lblTimeSlot == null) {
			lblTimeSlot = new JLabel("By time:");
		}
		return lblTimeSlot;
	}

	private JTextField getTextFieldFrom() {
		if (textFieldFrom == null) {
			textFieldFrom = new JTextField();
			textFieldFrom.setColumns(10);
		}
		return textFieldFrom;
	}

	private JLabel getLblGuion() {
		if (lblGuion == null) {
			lblGuion = new JLabel("-");
			lblGuion.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblGuion;
	}

	private JTextField getTextFieldTo() {
		if (textFieldTo == null) {
			textFieldTo = new JTextField();
			textFieldTo.setColumns(10);
		}
		return textFieldTo;
	}

	private JPanel getPanelOffice() {
		if (panelOffice == null) {
			panelOffice = new JPanel();
			panelOffice.setLayout(new GridLayout(1, 0, 0, 0));
			panelOffice.add(getLblOffice());
			panelOffice.add(getTextField());

		}
		return panelOffice;
	}

	private JLabel getLblOffice() {
		if (lblOffice == null) {
			lblOffice = new JLabel("By office:");
		}
		return lblOffice;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}

	private JPanel getPanelButtonsReset() {
		if (panelButtonsReset == null) {
			panelButtonsReset = new JPanel();
			panelButtonsReset.setLayout(new GridLayout(1, 0, 0, 0));
			panelButtonsReset.add(getBtnFilter());
			panelButtonsReset.add(getBtnResetFilters());
		}
		return panelButtonsReset;
	}

	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter");
		}
		return btnFilter;
	}

	private JButton getBtnResetFilters() {
		if (btnResetFilters == null) {
			btnResetFilters = new JButton("Reset filters");
		}
		return btnResetFilters;
	}
}
