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

import db.Appointment;
import oracle.sql.DATE;
import util.AppointmentBLDto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ScrollPaneConstants;

public class Schedule extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel lblDescription;
	private JScrollPane pnAppointments;
	private JCalendar calendar;
	private JLabel lblDate;
	private JButton btnViewInfo;
	private List<AppointmentBLDto> appointments = null;
	private List<AppointmentBLDto> filteredApmnts = null;
	private static int doctorId = 1;
	
	private JPanel panel;
	private JButton btnBack;
	private JButton btnFilter;

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

	private void filterAppointments() {
		var filtered = new ArrayList<AppointmentBLDto>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(getCalendar().getDate());
		for(AppointmentBLDto apmnt: appointments) {		
	        var apDate = apmnt.startDate.split(" ");
			if(apDate[0].equals(format)) {
				filtered.add(apmnt);
			
			}
		}
		this.filteredApmnts = filtered;
		
		getPnAppointments().removeAll();
		for(AppointmentBLDto p : filteredApmnts) {
			System.out.println(p);
			getPnAppointments().add(newButton(p));
		}
	}
	
	
	private JButton newButton(AppointmentBLDto p) {
		JButton button = new JButton(p.toString());
		button.setVisible(true);
		return button;
	}
	/**
	 * Create the frame.
	 */
	public Schedule() {

		appointments = Appointment.getAppointmentsByDoctorId(doctorId);	
		filterAppointments();
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
			leftPanel.add(getPanel(), BorderLayout.SOUTH);
		}
		return leftPanel;
	}
	private JPanel getRightPanel() {
		if (rightPanel == null) {
			rightPanel = new JPanel();
			rightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			rightPanel.setLayout(new BorderLayout(0, 0));
			rightPanel.add(getLblDescription(), BorderLayout.NORTH);
			rightPanel.add(getPnAppointments(), BorderLayout.CENTER);
		
		}
		return rightPanel;
	}
	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel("Selected Days Appointments");
		}
		return lblDescription;
	}
	private JScrollPane getPnAppointments() {
		if (pnAppointments == null) {
			pnAppointments = new JScrollPane();
			pnAppointments.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			pnAppointments.removeAll();
			for(AppointmentBLDto p : filteredApmnts) {
				System.out.println(p);
				pnAppointments.add(newButton(p));
			}
		}
		return pnAppointments;
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
	
	

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnBack());
			panel.add(getBtnFilter());
		}
		return panel;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
		}
		return btnBack;
	}
	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter Appointments");
			btnFilter.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					filterAppointments();
			        
			       
				}
			});
		}
		return btnFilter;
	}
}
