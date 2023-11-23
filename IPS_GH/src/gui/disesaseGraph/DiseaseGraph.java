package gui.disesaseGraph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DiseaseGraph extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public XYSeriesCollection data =null;
	private JPanel panel;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JLabel lblEndDate;
	private JLabel lblStartDate;
	private JButton btnFilter;
	private JComboBox cbDisease;
	private JButton btnAddDisease;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiseaseGraph frame = new DiseaseGraph();
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
	public DiseaseGraph() {
		 super("XY Line Chart Example with JFreechart"); // calls the super class constructor
		    
		    JPanel chartPanel = createChartPanel();       
		    getContentPane().add(chartPanel, BorderLayout.CENTER);
		    getContentPane().add(getPanel(), BorderLayout.NORTH);
		    
		  
		    
		    setSize(640, 480);
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setLocationRelativeTo(null);
	}
	
	private JPanel createChartPanel() { // this method will create the chart panel containin the graph 
	    String chartTitle = "Disease between x and y";
	    String xAxisLabel = "Time";
	    String yAxisLabel = "Number of patients";
	    
	    XYDataset dataset = createDataset();
	    
	    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, 
	            xAxisLabel, yAxisLabel, dataset);

	    customizeChart(chart);
	    

	    
	    return new ChartPanel(chart);
	}

	private XYDataset createDataset() {    // this method creates the data as time seris 
	    XYSeriesCollection dataset = new XYSeriesCollection();
	    XYSeries series1 = new XYSeries("Spanish flu");
	    XYSeries series2 = new XYSeries("Covid");
	    XYSeries series3 = new XYSeries("Salmonela");
	    
	    series1.add(22, 2);
	    series1.add(23, 3);
	    series1.add(24, 2);
	    series1.add(25, 3);
	    series1.add(26, 6);
	    
	    series2.add(22, 7);
	    series2.add(23, 4);
	    series2.add(24, 1);
	    series2.add(25, 9);
	    series2.add(26, 6);
	    
	    series3.add(22, 1);
	    series3.add(23, 2);
	    series3.add(24, 5);
	    series3.add(25, 2);
	    series3.add(26, 5);
	    
	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	    
	    return dataset;
	}

	private void customizeChart(JFreeChart chart) {   // here we make some customization
	    XYPlot plot = chart.getXYPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	    // sets paint color for each series
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.GREEN);
	    renderer.setSeriesPaint(2, Color.YELLOW);

	    // sets thickness for series (using strokes)
	    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
	    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
	    

	    
	    // sets renderer for lines
	    plot.setRenderer(renderer);
	    
	    // sets plot background
	    plot.setBackgroundPaint(Color.LIGHT_GRAY);
	    
	    // sets paint color for the grid lines
	    plot.setRangeGridlinesVisible(true);
	    plot.setRangeGridlinePaint(Color.BLACK);
	    
	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.BLACK);
	    
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblStartDate());
			panel.add(getComboBox());
			panel.add(getLblEndDate());
			panel.add(getComboBox_1());
			panel.add(getBtnFilter());
			panel.add(getCbDisease());
			panel.add(getBtnAddDisease());
		}
		return panel;
	}
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
		}
		return comboBox;
	}
	private JComboBox getComboBox_1() {
		if (comboBox_1 == null) {
			comboBox_1 = new JComboBox();
		}
		return comboBox_1;
	}
	private JLabel getLblEndDate() {
		if (lblEndDate == null) {
			lblEndDate = new JLabel("End Date");
		}
		return lblEndDate;
	}
	private JLabel getLblStartDate() {
		if (lblStartDate == null) {
			lblStartDate = new JLabel("Start Date");
		}
		return lblStartDate;
	}
	private JButton getBtnFilter() {
		if (btnFilter == null) {
			btnFilter = new JButton("Filter");
		}
		return btnFilter;
	}
	private JComboBox getCbDisease() {
		if (cbDisease == null) {
			cbDisease = new JComboBox();
			cbDisease.setModel(new DefaultComboBoxModel(new String[] {"Spanish Flu", "Covid", "Salmonela", "Gastroentetiris", "Cough"}));
		}
		return cbDisease;
	}
	private JButton getBtnAddDisease() {
		if (btnAddDisease == null) {
			btnAddDisease = new JButton("Add Disease");
		}
		return btnAddDisease;
	}
}
