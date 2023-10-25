package gui.doctor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import creator.CausesCreator;
import util.AppointmentBLDto;
import util.ConnectionFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import creator.CausesCreator;
import javax.swing.JTabbedPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JTree;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Toolkit;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class DoctorAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel patientInfoPanel;
	private JPanel buttonsPanel;
	private JButton btnExit;
	private JButton btnSave;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private CausesCreator causesCreator;
	private Map<String, List<String>> causes;

	private DefaultListModel<String> finalCauses = new DefaultListModel<String>();
	private DefaultListModel<String> finalPrescription = new DefaultListModel<String>();
	private String[] vaccines = new String[] {"Covid-19 vaccine", "Tetanos vaccine", "Spanish flu vaccine", "Viruela vaccine", "Other..."};
	
	private AppointmentBLDto appointment;
	private JTabbedPane tabbedPane;
	private JPanel appointmentOptionsPanel;
	private JLabel lblListCauses;
	private JButton btnAddCause;
	private JLabel lblCustomCause;
	private JScrollPane scrollPane_1;
	private JButton btnAddCustomCause;
	private JLabel lblSelectedCauses;
	private JButton btnRemoveCause;
	private JPanel pnHeader;
	private JLabel lblAppointmentPatient;
	private JLabel lblAppointmentDate;
	private JPanel pnBody;
	private JLabel lblCheckInTime;
	private JTextField txtCheckinTime;
	private JLabel lblCheckOutTime;
	private JTextField txtCheckoutTime;
	private JButton btnCheckInTime;
	private JButton btnCheckOutTime;
	private JLabel lblAttendance;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private JRadioButton rdbtnDontAnswer;
	private JTextArea txtAreaOtherCauses;
	private JPanel pnDiagnosis;
	private JPanel pnPrescription;
	private JTree tree;
	private JScrollPane scrollPane;
	private JLabel lblMedication;
	private JComboBox comboBoxMedication;
	private JButton btnAddMedication;
	private JLabel lblQuantity;
	private JLabel lblInterval;
	private JLabel lblDurationday;
	private JTextField txtQuantity;
	private JTextField txtInterval;
	private JTextField txtDuration;
	private JLabel lblComments;
	private JTextArea txtAreaComments;
	private JScrollPane scrollPane_2;
	private JLabel lblVaccines;
	private JComboBox comboBoxVaccines;
	private JButton btnAddVaccine;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblOther;
	private JTextArea txtAreaOther;
	private JScrollPane scrollPane_3;
	private JLabel lblPrescriptionState;
	private JList listPrescription;
	private JScrollPane scrollPane_4;
	private JButton btnAddOther;
	private JButton btnRemove;
	private JButton btnClearAll;
	private JScrollPane scrollPane_5;
	private JList listCauses;
	private JList listSelectedCauses;
	private JScrollPane scrollPane_7;
	private JList causesList;
	private JScrollPane scrollPane_6;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorAppointmentView frame = new DoctorAppointmentView(null);
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
	public DoctorAppointmentView(AppointmentBLDto appointment) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorAppointmentView.class.getResource("/img/descarga.jpg")));
		setTitle("Appointment");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("width: " + e.getComponent().getBounds().width);
				System.out.println("height: " + e.getComponent().getBounds().height);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO: make confirmation message
				setVisible(false);
			}
		});
		this.appointment = appointment;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 668, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.getLookAndFeelDefaults().put("nimbusBase", new Color(51, 153, 255)); // Cambiar el color bases
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPatientInfoPanel(), BorderLayout.NORTH);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		contentPane.add(getTabbedPane());
	}

	private JPanel getPatientInfoPanel() {
		if (patientInfoPanel == null) {
			patientInfoPanel = new JPanel();
			patientInfoPanel.setLayout(new BorderLayout(0, 0));
			patientInfoPanel.add(getPnHeader(), BorderLayout.NORTH);
			patientInfoPanel.add(getPnBody(), BorderLayout.CENTER);
		}
		return patientInfoPanel;
	}

	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonsPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			buttonsPanel.add(getBtnExit());
			buttonsPanel.add(getBtnSave());
		}
		return buttonsPanel;
	}

	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
		}
		return btnExit;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnYes.isSelected())
						appointment.attended = 1;
					else if (rdbtnNo.isSelected())
						appointment.attended = 0;
					else if (rdbtnDontAnswer.isSelected())
						appointment.attended = 2;
					appointment.checkIn = txtCheckinTime.getText();
					appointment.checkOut = txtCheckoutTime.getText();
					//TODO: add causes to DB
					ConnectionFactory.updateAppointment(appointment);
				}
			});
		}
		return btnSave;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Appointment causes", null, getAppointmentOptionsPanel_1(), null);
			tabbedPane.addTab("Diagnosis", null, getPnDiagnosis(), null);
			tabbedPane.addTab("Prescription", null, getPnPrescription(), null);
		}
		return tabbedPane;
	}
	private JPanel getAppointmentOptionsPanel_1() {
		if (appointmentOptionsPanel == null) {
			appointmentOptionsPanel = new JPanel();
			appointmentOptionsPanel.setLayout(null);
			appointmentOptionsPanel.add(getLblListCauses_1());
			appointmentOptionsPanel.add(getBtnAddCause_1());
			appointmentOptionsPanel.add(getLblCustomCause_1());
			appointmentOptionsPanel.add(getScrollPane_1_1());
			appointmentOptionsPanel.add(getBtnAddCustomCause_1());
			appointmentOptionsPanel.add(getLblSelectedCauses_1());
			appointmentOptionsPanel.add(getBtnRemoveCause_1());
			appointmentOptionsPanel.add(getScrollPane_5());
			appointmentOptionsPanel.add(getScrollPane_7());
			appointmentOptionsPanel.add(getScrollPane_6());
		}
		return appointmentOptionsPanel;
	}
	private JLabel getLblListCauses_1() {
		if (lblListCauses == null) {
			lblListCauses = new JLabel("Select appointment cause(s):");
			lblListCauses.setBounds(63, 41, 192, 14);
		}
		return lblListCauses;
	}
	private JButton getBtnAddCause_1() {
		if (btnAddCause == null) {
			btnAddCause = new JButton(">>");
			btnAddCause.setBounds(302, 76, 49, 23);
			btnAddCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> causesSelected = getCausesList().getSelectedValuesList();
					System.out.println(causesSelected.get(0));
					for (String cause : causesSelected) {
						System.out.println(cause);
						if (!finalCauses.contains(cause)) {
							finalCauses.addElement(cause);
						}
					}
				}
			});
		}
		return btnAddCause;
	}
	private JLabel getLblCustomCause_1() {
		if (lblCustomCause == null) {
			lblCustomCause = new JLabel("Other cause(s):");
			lblCustomCause.setBounds(62, 219, 148, 14);
		}
		return lblCustomCause;
	}
	private JScrollPane getScrollPane_1_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(330, 15, 2, 2);
		}
		return scrollPane_1;
	}
	private JButton getBtnAddCustomCause_1() {
		if (btnAddCustomCause == null) {
			btnAddCustomCause = new JButton("Add");
			btnAddCustomCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalCauses.addElement(getTxtAreaOtherCauses().getText());
				}
			});
			btnAddCustomCause.setBounds(344, 283, 51, 23);
		}
		return btnAddCustomCause;
	}
	private JLabel getLblSelectedCauses_1() {
		if (lblSelectedCauses == null) {
			lblSelectedCauses = new JLabel("Selected cause(s):");
			lblSelectedCauses.setBounds(380, 41, 217, 14);
		}
		return lblSelectedCauses;
	}
	private JButton getBtnRemoveCause_1() {
		if (btnRemoveCause == null) {
			btnRemoveCause = new JButton("<<");
			btnRemoveCause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> selectedItems = getListSelectedCauses().getSelectedValuesList();
					for (String item : selectedItems) {
						finalCauses.removeElement(item);
					}
				}
			});
			btnRemoveCause.setBounds(302, 129, 49, 23);
		}
		return btnRemoveCause;
	}
	private JPanel getPnHeader() {
		if (pnHeader == null) {
			pnHeader = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnHeader.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnHeader.add(getLblAppointmentPatient_1());
			pnHeader.add(getLblAppointmentDate_1());
		}
		return pnHeader;
	}
	private JLabel getLblAppointmentPatient_1() {
		if (lblAppointmentPatient == null) {
			lblAppointmentPatient = new JLabel();
			lblAppointmentPatient.setFont(new Font("Tahoma", Font.PLAIN, 16));
			//lblAppointmentPatient.setText(appointment.patientName.toUpperCase() + " " + appointment.patientSurname.toUpperCase());
		}
		return lblAppointmentPatient;
	}
	private JLabel getLblAppointmentDate_1() {
		if (lblAppointmentDate == null) {
			lblAppointmentDate = new JLabel();
			lblAppointmentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
			//lblAppointmentDate.setText(this.appointment.startDate);
		}
		return lblAppointmentDate;
	}
	private JPanel getPnBody() {
		if (pnBody == null) {
			pnBody = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBody.getLayout();
			flowLayout.setVgap(15);
			pnBody.setPreferredSize(new Dimension(50, 100));
			flowLayout.setHgap(25);
			pnBody.add(getLblAttendance());
			pnBody.add(getRdbtnYes());
			pnBody.add(getRdbtnNo());
			pnBody.add(getRdbtnDontAnswer());
			pnBody.add(getLblCheckInTime());
			pnBody.add(getTextField_2());
			pnBody.add(getBtnCheckInTime());
			pnBody.add(getLblCheckOutTime());
			pnBody.add(getTextField_1_1());
			pnBody.add(getBtnCheckOutTime());
		}
		return pnBody;
	}
	private JLabel getLblCheckInTime() {
		if (lblCheckInTime == null) {
			lblCheckInTime = new JLabel("Check-in:");
		}
		return lblCheckInTime;
	}
	private JTextField getTextField_2() {
		if (txtCheckinTime == null) {
			txtCheckinTime = new JTextField();
			txtCheckinTime.setColumns(6);
		}
		return txtCheckinTime;
	}
	private JLabel getLblCheckOutTime() {
		if (lblCheckOutTime == null) {
			lblCheckOutTime = new JLabel("Check-out:");
		}
		return lblCheckOutTime;
	}
	private JTextField getTextField_1_1() {
		if (txtCheckoutTime == null) {
			txtCheckoutTime = new JTextField();
			txtCheckoutTime.setColumns(6);
		}
		return txtCheckoutTime;
	}
	private JButton getBtnCheckInTime() {
		if (btnCheckInTime == null) {
			btnCheckInTime = new JButton("Auto-fill in time");
			btnCheckInTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTextField_2().setText(currentTime);
				}
			});
		}
		return btnCheckInTime;
	}
	private JButton getBtnCheckOutTime() {
		if (btnCheckOutTime == null) {
			btnCheckOutTime = new JButton("Auto-fill out time");
			btnCheckOutTime.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					String currentTime = dateFormat.format(currentDate);
					// Update the text field with the current time
					getTextField_1_1().setText(currentTime);
				}
			});
		}
		return btnCheckOutTime;
	}
	private JLabel getLblAttendance() {
		if (lblAttendance == null) {
			lblAttendance = new JLabel("Did the patient attend the appointment?");
		}
		return lblAttendance;
	}
	private JRadioButton getRdbtnYes() {
		if (rdbtnYes == null) {
			rdbtnYes = new JRadioButton("Attended");
			buttonGroup.add(rdbtnYes);
			rdbtnYes.setSelected(true);
		}
		return rdbtnYes;
	}
	private JRadioButton getRdbtnNo() {
		if (rdbtnNo == null) {
			rdbtnNo = new JRadioButton("Not attended");
			buttonGroup.add(rdbtnNo);
		}
		return rdbtnNo;
	}
	private JRadioButton getRdbtnDontAnswer() {
		if (rdbtnDontAnswer == null) {
			rdbtnDontAnswer = new JRadioButton("Don't answer");
			buttonGroup.add(rdbtnDontAnswer);
		}
		return rdbtnDontAnswer;
	}
	private JTextArea getTxtAreaOtherCauses() {
		if (txtAreaOtherCauses == null) {
			txtAreaOtherCauses = new JTextArea();
		}
		return txtAreaOtherCauses;
	}
	private JPanel getPnDiagnosis() {
		if (pnDiagnosis == null) {
			pnDiagnosis = new JPanel();
			pnDiagnosis.setLayout(null);
			pnDiagnosis.add(getScrollPane());
		}
		return pnDiagnosis;
	}
	private JPanel getPnPrescription() {
		if (pnPrescription == null) {
			pnPrescription = new JPanel();
			pnPrescription.setLayout(null);
			pnPrescription.add(getLblMedication());
			pnPrescription.add(getComboBoxMedication());
			pnPrescription.add(getBtnAddMedication());
			pnPrescription.add(getLblQuantity());
			pnPrescription.add(getLblInterval());
			pnPrescription.add(getLblDurationday());
			pnPrescription.add(getTxtQuantity());
			pnPrescription.add(getTxtInterval());
			pnPrescription.add(getTextField_2_1());
			pnPrescription.add(getLblComments());
			pnPrescription.add(getScrollPane_2());
			pnPrescription.add(getLblVaccines());
			pnPrescription.add(getComboBoxVaccines());
			pnPrescription.add(getBtnAddVaccine());
			pnPrescription.add(getSeparator());
			pnPrescription.add(getSeparator_1());
			pnPrescription.add(getLblOther());
			pnPrescription.add(getScrollPane_3());
			pnPrescription.add(getLblPrescriptionState());
			pnPrescription.add(getScrollPane_4());
			pnPrescription.add(getBtnAddOther());
			pnPrescription.add(getBtnRemove());
			pnPrescription.add(getBtnClearAll());
		}
		return pnPrescription;
	}
	private JTree getTree() {
		if (tree == null) {
			tree = new JTree();
			loadTreeData();
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
	                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	                System.out.println(selectedNode);
	                if (selectedNode != null && selectedNode.isLeaf()) {
	                    // Verificar si el nodo seleccionado debe expandirse según los datos de la base de datos
	                    boolean shouldExpand = consultaBaseDeDatosParaExpansion(selectedNode);

	                    if (shouldExpand) {
	                        // Agregar subnodos al nodo seleccionado (simulación)
	                        DefaultMutableTreeNode subNode1 = new DefaultMutableTreeNode("Subnode 1");
	                        DefaultMutableTreeNode subNode2 = new DefaultMutableTreeNode("Subnode 2");
	                        selectedNode.add(subNode1);
	                        selectedNode.add(subNode2);

	                        // Notificar al modelo del árbol que la estructura ha cambiado
	                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	                        model.nodeStructureChanged(selectedNode);

	                        // Expandir el nodo seleccionado
	                        tree.expandPath(new TreePath(selectedNode.getPath()));
	                    }
	                }
				}
			});
		}
		return tree;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 249, 309);
			scrollPane.setViewportView(getTree());
		}
		return scrollPane;
	}
	
	private void loadTreeData() {
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root"); // Nodo raíz
	    DefaultTreeModel model = new DefaultTreeModel(root);
	    tree.setModel(model);
	    
	    // Simula la carga de datos desde tu fuente de datos (base de datos, archivos, etc.)
	    // En este ejemplo, creamos nodos de ejemplo manualmente
	    DefaultMutableTreeNode nodeA = new DefaultMutableTreeNode("Node A");
	    DefaultMutableTreeNode nodeB = new DefaultMutableTreeNode("Node B");
	    DefaultMutableTreeNode nodeC = new DefaultMutableTreeNode("Node C");

	    root.add(nodeA);
	    root.add(nodeB);
	    root.add(nodeC);
	    // Continúa agregando nodos según tu estructura de datos real
    }

    private boolean consultaBaseDeDatosParaExpansion(DefaultMutableTreeNode node) {
        return true;
    }
	private JLabel getLblMedication() {
		if (lblMedication == null) {
			lblMedication = new JLabel("Medication:");
			lblMedication.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblMedication.setBounds(23, 26, 110, 14);
		}
		return lblMedication;
	}
	private JComboBox getComboBoxMedication() {
		if (comboBoxMedication == null) {
			comboBoxMedication = new JComboBox();
			comboBoxMedication.setBounds(23, 51, 94, 22);
			var data = new String[] {"Algidol", "Ibuprofeno", "Antibiotics", "Thrombocid", "Other..."};
			comboBoxMedication.setModel(new DefaultComboBoxModel(data));
			comboBoxMedication.setSelectedIndex(0);
		}
		return comboBoxMedication;
	}
	private JButton getBtnAddMedication() {
		if (btnAddMedication == null) {
			btnAddMedication = new JButton("Add");
			btnAddMedication.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selMed = (String) getComboBoxMedication().getModel().
							getElementAt(getComboBoxMedication().getSelectedIndex());
					String quantity = getTxtQuantity().getText();
					String interval = getTxtInterval().getText();
					String duration = getTextField_2_1().getText();
					String comments = getTxtAreaComments().getText();
					String medication = "Medication: " + selMed + ". Quantity: " + quantity +
							". Interval: " + interval + ". Duration: " + duration + ". Comments: "
							+ comments;
					finalPrescription.addElement(medication);
				}
			});
			btnAddMedication.setBounds(143, 51, 62, 23);
		}
		return btnAddMedication;
	}
	private JLabel getLblQuantity() {
		if (lblQuantity == null) {
			lblQuantity = new JLabel("Quantity:");
			lblQuantity.setBounds(23, 101, 62, 14);
		}
		return lblQuantity;
	}
	private JLabel getLblInterval() {
		if (lblInterval == null) {
			lblInterval = new JLabel("Interval:");
			lblInterval.setBounds(23, 137, 80, 14);
		}
		return lblInterval;
	}
	private JLabel getLblDurationday() {
		if (lblDurationday == null) {
			lblDurationday = new JLabel("Duration:");
			lblDurationday.setBounds(23, 177, 62, 14);
		}
		return lblDurationday;
	}
	private JTextField getTxtQuantity() {
		if (txtQuantity == null) {
			txtQuantity = new JTextField();
			txtQuantity.setBounds(82, 84, 62, 28);
			txtQuantity.setColumns(10);
		}
		return txtQuantity;
	}
	private JTextField getTxtInterval() {
		if (txtInterval == null) {
			txtInterval = new JTextField();
			txtInterval.setColumns(10);
			txtInterval.setBounds(82, 123, 62, 28);
		}
		return txtInterval;
	}
	private JTextField getTextField_2_1() {
		if (txtDuration == null) {
			txtDuration = new JTextField();
			txtDuration.setColumns(10);
			txtDuration.setBounds(82, 162, 62, 28);
		}
		return txtDuration;
	}
	private JLabel getLblComments() {
		if (lblComments == null) {
			lblComments = new JLabel("Comments:");
			lblComments.setBounds(23, 232, 110, 14);
		}
		return lblComments;
	}
	private JTextArea getTxtAreaComments() {
		if (txtAreaComments == null) {
			txtAreaComments = new JTextArea();
		}
		return txtAreaComments;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(23, 258, 182, 82);
			scrollPane_2.setViewportView(getTxtAreaComments());
		}
		return scrollPane_2;
	}
	private JLabel getLblVaccines() {
		if (lblVaccines == null) {
			lblVaccines = new JLabel("Vaccines:");
			lblVaccines.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVaccines.setBounds(232, 26, 110, 14);
		}
		return lblVaccines;
	}
	private JComboBox getComboBoxVaccines() {
		if (comboBoxVaccines == null) {
			comboBoxVaccines = new JComboBox(vaccines);
			comboBoxVaccines.setBounds(231, 51, 117, 22);
		}
		return comboBoxVaccines;
	}
	private JButton getBtnAddVaccine() {
		if (btnAddVaccine == null) {
			btnAddVaccine = new JButton("Add");
			btnAddVaccine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selVacc = vaccines[getComboBoxVaccines().getSelectedIndex()];
					finalPrescription.addElement(selVacc);
				}
			});
			btnAddVaccine.setBounds(358, 51, 51, 23);
		}
		return btnAddVaccine;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(215, 26, 12, 314);
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setBounds(419, 26, 12, 314);
		}
		return separator_1;
	}
	private JLabel getLblOther() {
		if (lblOther == null) {
			lblOther = new JLabel("Other:");
			lblOther.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblOther.setBounds(226, 207, 62, 14);
		}
		return lblOther;
	}
	private JTextArea getTxtAreaOther() {
		if (txtAreaOther == null) {
			txtAreaOther = new JTextArea();
		}
		return txtAreaOther;
	}
	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(232, 230, 177, 110);
			scrollPane_3.setViewportView(getTxtAreaOther());
		}
		return scrollPane_3;
	}
	private JLabel getLblPrescriptionState() {
		if (lblPrescriptionState == null) {
			lblPrescriptionState = new JLabel("Prescription state:");
			lblPrescriptionState.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblPrescriptionState.setBounds(432, 27, 171, 14);
		}
		return lblPrescriptionState;
	}
	private JList getListPrescription() {
		if (listPrescription == null) {
			listPrescription = new JList(finalPrescription);
		}
		return listPrescription;
	}
	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setBounds(432, 53, 202, 253);
			scrollPane_4.setViewportView(getListPrescription());
		}
		return scrollPane_4;
	}
	private JButton getBtnAddOther() {
		if (btnAddOther == null) {
			btnAddOther = new JButton("Add");
			btnAddOther.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalPrescription.addElement(getTxtAreaOther().getText());
				}
			});
			btnAddOther.setBounds(358, 204, 51, 23);
		}
		return btnAddOther;
	}
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> selItems = getListPrescription().getSelectedValuesList(); 
					for (String item : selItems) {
						if (finalPrescription.contains(item))
							finalPrescription.removeElement(item);
					}
				}
			});
			btnRemove.setBounds(442, 317, 80, 23);
		}
		return btnRemove;
	}
	private JButton getBtnClearAll() {
		if (btnClearAll == null) {
			btnClearAll = new JButton("Clear all");
			btnClearAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalPrescription.clear();
				}
			});
			btnClearAll.setBounds(547, 317, 80, 23);
		}
		return btnClearAll;
	}
	private JScrollPane getScrollPane_5() {
		if (scrollPane_5 == null) {
			scrollPane_5 = new JScrollPane();
			scrollPane_5.setBounds(61, 240, 271, 66);
			scrollPane_5.setViewportView(getTxtAreaOtherCauses());
		}
		return scrollPane_5;
	}

	private JList getListSelectedCauses() {
		if (listSelectedCauses == null) {
			listSelectedCauses = new JList(finalCauses);
		}
		return listSelectedCauses;
	}
	private JScrollPane getScrollPane_7() {
		if (scrollPane_7 == null) {
			scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(381, 66, 216, 128);
			scrollPane_7.setViewportView(getListSelectedCauses());
		}
		return scrollPane_7;
	}
	private JList getCausesList() {
		DefaultListModel<String> model = new DefaultListModel();
		this.causesCreator = new CausesCreator();
		this.causes = causesCreator.getListsOfCauses();

		for (List<String> list : this.causes.values())
			for (String item : list) {
				model.addElement(item);
		}	

		if (causesList == null) {
			causesList = new JList(model);
		}
		return causesList;
	}
	private JScrollPane getScrollPane_6() {
		if (scrollPane_6 == null) {
			scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(63, 66, 200, 128);
			scrollPane_6.setViewportView(getCausesList());
		}
		return scrollPane_6;
	}
}
