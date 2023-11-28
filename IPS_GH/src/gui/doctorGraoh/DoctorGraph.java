package gui.doctorGraoh;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import db.GraphGenerator;
import util.DoctorGraphBLDto;

public class DoctorGraph extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<DoctorGraphBLDto> doctors;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGraph frame = new DoctorGraph();
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
	public DoctorGraph() {
		doctors = GraphGenerator.getDoctorsGraph();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		JFreeChart barChart = ChartFactory.createBarChart("Amount of Doctors", "Category", "Score", createDataset(),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
//		setContentPane(contentPane);
		setContentPane(chartPanel);
	}

	 private CategoryDataset createDataset( ) {
	         
	      final DefaultCategoryDataset dataset = 
	      new DefaultCategoryDataset( );  
	      for(var doctor:doctors)
	    	  dataset.addValue( doctor.amount , doctor.specialization , "Specialization" );        
	                   

	      return dataset; 
	   }

}
