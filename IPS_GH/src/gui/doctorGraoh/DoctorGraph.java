package gui.doctorGraoh;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	private List<DoctorGraphBLDto> selected;
	private JPanel pnChart;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTextField textField;
	private JList listBefore;
	private JPanel panel_4;
	private JButton btnUp;
	private JButton btnRemove;
	private JPanel panel_5;
	private JButton btnShow;
	private JList listAfter;

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
		selected = new ArrayList<>();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1204, 1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(getPnChart(), BorderLayout.CENTER);

		getContentPane().add(getPanel(), BorderLayout.WEST);
	}



	private JPanel getPnChart() {
		if (pnChart == null) {
			pnChart = new JPanel();
			pnChart.setLayout(new BorderLayout(0, 0));
			pnChart.add(createChart(new DefaultCategoryDataset() ));
		}
		return pnChart;
	}

	private  ChartPanel createChart(CategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart("Amount of Doctors", "Category", "Score", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		return chartPanel;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			panel.add(getPanel_1());
			panel.add(getPanel_2());
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getPanel_3(), BorderLayout.NORTH);
			panel_1.add(getList_1(), BorderLayout.CENTER);
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new BorderLayout(0, 0));
			panel_2.add(getPanel_4(), BorderLayout.NORTH);
			panel_2.add(getPanel_5(), BorderLayout.SOUTH);
			panel_2.add(getList_1_1(), BorderLayout.CENTER);
		}
		return panel_2;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.add(getTextField());
		}
		return panel_3;
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
		for (DoctorGraphBLDto item : doctors) {
				elements.add(item.specialization);
		}	
		String searchText = getTextField().getText().toLowerCase();

        List<String> filteredElements = elements.stream()
                .filter(element -> element.toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        for (String element : filteredElements) {
            model.addElement(element);
        }
        
        getList_1().setModel(model);
    }

	
	private JList getList_1() {
		if (listBefore == null) {
			listBefore = new JList();
			String[] values = new String[doctors.size()];
			for(int i = 0; i<values.length;i++) {
				values[i]=doctors.get(i).specialization;
			}
			listBefore.setModel(new AbstractListModel() {
				
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
		}
		return listBefore;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.add(getBtnUp());
			panel_4.add(getBtnRemove());
		}
		return panel_4;
	}
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton("Add");
			btnUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					var index = getList_1().getSelectedIndex();
					selected.add(doctors.get(index));
					doctors.remove(index);
					
					String[] values = new String[doctors.size()];
					for(int i = 0; i<values.length;i++) {
						values[i]=doctors.get(i).specialization;
					}
					getList_1().setModel(new AbstractListModel() {
						
						public int getSize() {
							return values.length;
						}
						public Object getElementAt(int index) {
							return values[index];
						}
					});
					
					String[] values2 = new String[selected.size()];
					for(int i = 0; i<values2.length;i++) {
						values2[i]=selected.get(i).specialization;
					}
					getList_1_1().setModel(new AbstractListModel() {
						
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
		return btnUp;
	}
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					var index = getList_1_1().getSelectedIndex();
					doctors.add(selected.get(index));
					selected.remove(index);
					
					String[] values = new String[doctors.size()];
					for(int i = 0; i<values.length;i++) {
						values[i]=doctors.get(i).specialization;
					}
					getList_1().setModel(new AbstractListModel() {
						
						public int getSize() {
							return values.length;
						}
						public Object getElementAt(int index) {
							return values[index];
						}
					});
					
					String[] values2 = new String[selected.size()];
					for(int i = 0; i<values2.length;i++) {
						values2[i]=selected.get(i).specialization;
					}
					getList_1_1().setModel(new AbstractListModel() {
						
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
		return btnRemove;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.add(getBtnShow());
		}
		return panel_5;
	}
	private JButton getBtnShow() {
		if (btnShow == null) {
			btnShow = new JButton("Show");
			btnShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getPnChart().removeAll();
					 DefaultCategoryDataset dataset = 
						      new DefaultCategoryDataset( );  
						      for(var doctor:selected)
						    	  dataset.addValue( doctor.amount , doctor.specialization , "" );      
				
					getPnChart().add(createChart(dataset));
					getPnChart().invalidate();
					getPnChart().validate();
					getPnChart().repaint();
				}
			});
		}
		return btnShow;
	}
	private JList getList_1_1() {
		if (listAfter == null) {
			listAfter = new JList();
		}
		return listAfter;
	}
}
