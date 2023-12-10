package gui.disesaseGraph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
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
	private JDateChooser dcStart;
	private JDateChooser dcEnd;
	private List<DiagnosisBLDto> allDiagnosis = new ArrayList<>();
	private List<DiagnosisBLDto> diagnosis = new ArrayList<>();
	private List<DiagnosisBLDto> selected = new ArrayList<>();
	private Date minDate = new Date(0);
	private Date maxDate = new Date();
	private JPanel pnChart;
	private JPanel pnDiagnostics;
	private JPanel pnAvailable;
	private JTextField textField;
	private JList list;
	private JPanel pnSelection;
	private JPanel pnManage;
	private JPanel pnPlot;
	private JList listSelected;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnPlot;

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
		this.allDiagnosis = GraphGenerator.getDiagnostics();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (var d : allDiagnosis) {
			try {
				if (!(diagnosis.contains(d)) && formatter.parse(d.initDate).after(minDate)
						&& formatter.parse(d.initDate).before(maxDate)) {
					diagnosis.add(d);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		getContentPane().add(getPanel(), BorderLayout.NORTH);
		getContentPane().add(getPnChart(), BorderLayout.CENTER);
		getContentPane().add(getPnDiagnostics(), BorderLayout.WEST);

		setSize(880, 563);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel.add(getLblStartDate());
			panel.add(getDcStart());
			panel.add(getLblEndDate());
			panel.add(getDcEnd());
			panel.add(getBtnFilter());
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
			btnFilter = new JButton("Show Diagnostics");
			btnFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minDate = getDcStart().getDate();
					maxDate = getDcEnd().getDate();
					diagnosis = new ArrayList<>();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					for (var d : allDiagnosis) {
						try {
							if (!(diagnosis.contains(d)) && formatter.parse(d.initDate).after(minDate)
									&& formatter.parse(d.initDate).before(maxDate)) {
								diagnosis.add(d);
							}
						} catch (ParseException rr) {
							rr.printStackTrace();
						}
						System.out.println(diagnosis.toString());
					}

					String[] values = new String[diagnosis.size()];
					for (int i = 0; i < values.length; i++) {
						values[i] = diagnosis.get(i).diagnosis;
					}
					getList().setModel(new AbstractListModel() {

						public int getSize() {
							return values.length;
						}

						public Object getElementAt(int index) {
							return values[index];
						}
					});

					getListSelected().setModel(new AbstractListModel() {
						String[] v = new String[0];

						public int getSize() {
							return v.length;
						}

						public Object getElementAt(int index) {
							return v[index];
						}
					});

					getPnChart().removeAll();

					getPnChart().add(createChart(new TimeSeriesCollection()));
					getPnChart().invalidate();
					getPnChart().validate();
					getPnChart().repaint();

				}
			});
		}
		return btnFilter;
	}

	private JDateChooser getDcStart() {
		if (dcStart == null) {
			dcStart = new JDateChooser();
			dcStart.setDate(minDate);
		}
		return dcStart;
	}

	private JDateChooser getDcEnd() {
		if (dcEnd == null) {
			dcEnd = new JDateChooser();
			dcEnd.setDate(maxDate);
		}
		return dcEnd;
	}

	private JPanel getPnChart() {
		if (pnChart == null) {
			pnChart = new JPanel();
			pnChart.setLayout(new BorderLayout(0, 0));
			pnChart.add(createChart(new TimeSeriesCollection()));
		}
		return pnChart;
	}

	private ChartPanel createChart(TimeSeriesCollection dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Diagnostics Tracking", "Timeline", "Amount", dataset);

		customizeChart(chart);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	private JPanel getPnDiagnostics() {
		if (pnDiagnostics == null) {
			pnDiagnostics = new JPanel();
			pnDiagnostics.setLayout(new GridLayout(0, 1, 0, 0));
			pnDiagnostics.add(getPnAvailable());
			pnDiagnostics.add(getPnSelection());
		}
		return pnDiagnostics;
	}

	private JPanel getPnAvailable() {
		if (pnAvailable == null) {
			pnAvailable = new JPanel();
			pnAvailable.setLayout(new BorderLayout(0, 0));
			pnAvailable.add(getTextField(), BorderLayout.NORTH);
			pnAvailable.add(getList(), BorderLayout.CENTER);
		}
		return pnAvailable;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
			textField.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                filterElements();
	            }
	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                filterElements();
	            }
	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                // Cambios en estilos, no se utiliza en este ejemplo
	            }
	        });
		}
		return textField;
	}
	
	private void filterElements() {
		DefaultListModel<String> model = new DefaultListModel();
		List<String> elements = new ArrayList<String>();
		for (DiagnosisBLDto item : diagnosis) {
				elements.add(item.diagnosis);
		}	
		String searchText = getTextField().getText().toLowerCase();

        List<String> filteredElements = elements.stream()
                .filter(element -> element.toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        for (String element : filteredElements) {
            model.addElement(element);
        }
        
        getList().setModel(model);
    }


	private JList getList() {
		if (list == null) {
			list = new JList();
			String[] values = new String[diagnosis.size()];
			for (int i = 0; i < values.length; i++) {
				values[i] = diagnosis.get(i).diagnosis;
			}
			list.setModel(new AbstractListModel() {

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});
		}
		return list;
	}

	private JPanel getPnSelection() {
		if (pnSelection == null) {
			pnSelection = new JPanel();
			pnSelection.setLayout(new BorderLayout(0, 0));
			pnSelection.add(getPnManage(), BorderLayout.NORTH);
			pnSelection.add(getPnPlot(), BorderLayout.SOUTH);
			pnSelection.add(getListSelected(), BorderLayout.CENTER);
		}
		return pnSelection;
	}

	private JPanel getPnManage() {
		if (pnManage == null) {
			pnManage = new JPanel();
			pnManage.add(getBtnAdd());
			pnManage.add(getBtnRemove());
		}
		return pnManage;
	}

	private JPanel getPnPlot() {
		if (pnPlot == null) {
			pnPlot = new JPanel();
			pnPlot.add(getBtnPlot());
		}
		return pnPlot;
	}

	private JList getListSelected() {
		if (listSelected == null) {
			listSelected = new JList();
		}
		return listSelected;
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					var index = getList().getSelectedIndex();
					selected.add(diagnosis.get(index));
					diagnosis.remove(index);

					String[] values = new String[diagnosis.size()];
					for (int i = 0; i < values.length; i++) {
						values[i] = diagnosis.get(i).diagnosis;
					}
					getList().setModel(new AbstractListModel() {

						public int getSize() {
							return values.length;
						}

						public Object getElementAt(int index) {
							return values[index];
						}
					});

					String[] values2 = new String[selected.size()];
					for (int i = 0; i < values2.length; i++) {
						values2[i] = selected.get(i).diagnosis;
					}
					getListSelected().setModel(new AbstractListModel() {

						public int getSize() {
							return values2.length;
						}

						public Object getElementAt(int index) {
							return values2[index];
						}
					});

				}
			});
		}
		return btnAdd;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
		}
		return btnRemove;
	}

	private JButton getBtnPlot() {
		if (btnPlot == null) {
			btnPlot = new JButton("Plot");
			btnPlot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getPnChart().removeAll();
					TimeSeriesCollection dataset = new TimeSeriesCollection();

					TimeSeries ts;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						for (int i = 0; i < selected.size(); i++) {
							var sel = selected.get(i);
							ts = new TimeSeries(sel.diagnosis);
							for (int j = 0; j < allDiagnosis.size(); j++) {
								var d = allDiagnosis.get(j);
								if (d.equals(sel) && formatter.parse(d.initDate).after(minDate)
										&& formatter.parse(d.initDate).before(maxDate)) {
									ts.add(new Day(formatter.parse(d.initDate)), d.amount);

								}
							}
							dataset.addSeries(ts);

						}
						getPnChart().add(createChart(dataset));
						getPnChart().invalidate();
						getPnChart().validate();
						getPnChart().repaint();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});

		}
		return btnPlot;
	}
}
