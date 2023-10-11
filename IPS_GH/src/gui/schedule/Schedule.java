package gui.schedule;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Schedule extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel lblDescription;
	private JScrollPane scrollPane;
	private JCalendar calendar;
	private JLabel lblDate;
	private JButton btnGoBack;
	private JList list;
	private JButton btnViewInfo;
	private List<String> appointments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Schedule frame = new Schedule();
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
	public Schedule() {
		setBounds(100, 100, 498, 340);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getLeftPanel());
		getContentPane().add(getRightPanel());

	}
	private JPanel getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new JPanel();
			leftPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			leftPanel.setLayout(new BorderLayout(0, 0));
			leftPanel.add(getCalendar());
			leftPanel.add(getLblDate(), BorderLayout.NORTH);
			leftPanel.add(getBtnGoBack(), BorderLayout.SOUTH);
		}
		return leftPanel;
	}
	private JPanel getRightPanel() {
		if (rightPanel == null) {
			rightPanel = new JPanel();
			rightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			rightPanel.setLayout(new BorderLayout(0, 0));
			rightPanel.add(getLblDescription(), BorderLayout.NORTH);
			rightPanel.add(getScrollPane(), BorderLayout.CENTER);
			rightPanel.add(getBtnViewInfo(), BorderLayout.SOUTH);
		}
		return rightPanel;
	}
	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel("Selected Days Appointments");
		}
		return lblDescription;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBorder(new EmptyBorder(25, 25, 25, 25));
			calendar.setTodayButtonVisible(true);
		}
		return calendar;
	}
	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel("Calendar View");
		}
		return lblDate;
	}
	private JButton getBtnGoBack() {
		if (btnGoBack == null) {
			btnGoBack = new JButton("Back to Menu");
		}
		return btnGoBack;
	}
	private JList getList() {
		if (list == null) {
			list = new JList();
			list.setModel(new AbstractListModel() {
				String[] values = new String[] {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "a", "a"};
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return list;
	}
	private JButton getBtnViewInfo() {
		if (btnViewInfo == null) {
			btnViewInfo = new JButton("Open Appointment");
			btnViewInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					loadSelectedAppointment();
				}

				
			});
		}
		return btnViewInfo;
	}
	
	private void loadSelectedAppointment() {
		if(getList().getSelectedIndex() != -1) {
			//loadAppointment(appointments.get(getList().getSelectedIndex()));
		}
	}

	
}
