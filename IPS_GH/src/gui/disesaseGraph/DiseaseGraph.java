package gui.disesaseGraph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import com.toedter.calendar.JDateChooser;

import db.GraphGenerator;
import util.DiagnosisBLDto;

public class DiseaseGraph extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public XYSeriesCollection data = null;
	private JPanel panel;
	private JLabel lblEndDate;
	private JLabel lblStartDate;
	private JButton btnFilter;
	private JComboBox cbDisease;
	private JButton btnAddDisease;
	private JDateChooser dcStart;
	private JDateChooser dcEnd;
	private List<DiagnosisBLDto> diagnosis;
	private List<String> names = new ArrayList<>();
	private JButton btnReset;
	private Date minDate = new Date(0);
	private Date maxDate = new Date();
	private JPanel pnChart;

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

		super("Diagnosis Chart"); // calls the super class constructor
		this.diagnosis = GraphGenerator.getDiagnostics();

		getContentPane().add(getPanel(), BorderLayout.NORTH);
		getContentPane().add(getPnChart(), BorderLayout.CENTER);

		setSize(880, 563);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createChartPanel() { // this method will create the chart panel containin the graph
		String chartTitle = "Diseases Tracking";
		String xAxisLabel = "Date";
		String yAxisLabel = "Number of patients";

		XYDataset dataset = createDataset();

		JFreeChart chart = ChartFactory.createTimeSeriesChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

		customizeChart(chart);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset() {
		List<TimeSeries> tsl = new ArrayList<>();
		List<String> names = new ArrayList<>();
		TimeSeries ts;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (var d : this.diagnosis) {
			try {
				System.out.println(minDate);
				System.out.println(maxDate);
				if (formatter.parse(d.initDate).after(minDate) && formatter.parse(d.initDate).before(maxDate)) {
					if (!names.contains(d.diagnosis)) {
						names.add(d.diagnosis);
						ts = new TimeSeries(d.diagnosis);

						Date date;

						date = formatter.parse(d.initDate);
						ts.add(new Day(date), d.amount);
						tsl.add(ts);

					} else {
						ts = tsl.get(names.indexOf(d.diagnosis));
						Date date;

						date = formatter.parse(d.initDate);
						ts.add(new Day(date), d.amount);

					}
				}
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		this.names = names;
		System.out.println(names);
		getCbDisease().setModel(new DefaultComboBoxModel<String>(names.toArray(new String[0])));
		getCbDisease().invalidate();
		getCbDisease().validate();
		getCbDisease().repaint();

		for (var times : tsl) {
			dataset.addSeries(times);
		}
		return dataset;
	}

	private void customizeChart(JFreeChart chart) { // here we make some customization
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setDefaultStroke(new BasicStroke(5));

		// sets renderer for lines
		plot.setRenderer(renderer);

		// sets plot background
		plot.setBackgroundPaint(Color.DARK_GRAY);

		// sets paint color for the grid lines
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(2, 4, 0, 0));
			panel.add(getLblStartDate());
			panel.add(getDcStart());
			panel.add(getLblEndDate());
			panel.add(getDcEnd());
			panel.add(getBtnFilter());
			panel.add(getCbDisease());
			panel.add(getBtnAddDisease());
			panel.add(getBtnReset());
		}
		return panel;
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
			btnFilter = new JButton("Show");
			btnFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minDate = getDcStart().getDate();
					maxDate = getDcEnd().getDate();

					getPnChart().removeAll();

					getPnChart().add(createChartPanel());
					getPnChart().invalidate();
					getPnChart().validate();
					getPnChart().repaint();

				}
			});
		}
		return btnFilter;
	}

	private JComboBox getCbDisease() {
		if (cbDisease == null) {
			cbDisease = new JComboBox();

			cbDisease.setModel(new DefaultComboBoxModel<String>(names.toArray(new String[0])));
		}
		return cbDisease;
	}

	private JButton getBtnAddDisease() {
		if (btnAddDisease == null) {
			btnAddDisease = new JButton("Add Disease");
		}
		return btnAddDisease;
	}

	private JDateChooser getDcStart() {
		if (dcStart == null) {
			dcStart = new JDateChooser();
		}
		return dcStart;
	}

	private JDateChooser getDcEnd() {
		if (dcEnd == null) {
			dcEnd = new JDateChooser();
		}
		return dcEnd;
	}

	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton("Reset");
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String chartTitle = "Diseases Tracking";
					String xAxisLabel = "Date";
					String yAxisLabel = "Number of patients";

					XYDataset dataset = new TimeSeriesCollection();

					JFreeChart chart = ChartFactory.createTimeSeriesChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

					customizeChart(chart);

					getPnChart().removeAll();

					getPnChart().add(new ChartPanel(chart));
					getPnChart().invalidate();
					getPnChart().validate();
					getPnChart().repaint();
				}
			});
		}
		return btnReset;
	}

	private JPanel getPnChart() {
		if (pnChart == null) {
			pnChart = new JPanel();
			pnChart.setLayout(new BorderLayout(0, 0));
			JPanel chartPanel = createChartPanel();
			pnChart.add(chartPanel);
		}
		return pnChart;
	}
}
