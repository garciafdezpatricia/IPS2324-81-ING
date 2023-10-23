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
	private JList causesList;

	private CausesCreator causesCreator;
	private Map<String, List<String>> causes;

	private DefaultListModel<String> finalCauses = new DefaultListModel<String>();

	
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
	private JTextArea textArea;
	private JPanel pnDiagnosis;
	private JPanel pnPrescription;
	private JTree tree;
	private JScrollPane scrollPane;
	private JLabel lblMedication;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JLabel lblIntervalh;
	private JLabel lblDurationdays;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblComments;
	private JTextArea textArea_1;
	private JScrollPane scrollPane_2;
	private JLabel lblVaccines;
	private JComboBox comboBox_1;
	private JButton btnNewButton_2;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblNewLabel_1;
	private JTextArea textArea_2;
	private JScrollPane scrollPane_3;
	private JLabel lblNewLabel_2;
	private JList list_1;
	private JScrollPane scrollPane_4;
	private JButton btnNewButton_2_1;
	private JButton btnNewButton_2_2;
	private JButton btnNewButton_2_3;
	private JScrollPane scrollPane_5;
	private JList list_2;
	private JScrollPane scrollPane_6;
	private JList list_3;
	private JScrollPane scrollPane_7;
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

	private JList getCausesList() {
		DefaultListModel<String> model = new DefaultListModel();
		this.causesCreator = new CausesCreator();
		this.causes = causesCreator.getListsOfCauses();

		for (List<String> list : this.causes.values())
			for (String item : list) {
				model.addElement(item);
			}
		if (causesList == null) {
			causesList = new JList<>(model);
		}
		return causesList;
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
			appointmentOptionsPanel.add(getScrollPane_6());
			appointmentOptionsPanel.add(getScrollPane_7());
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
		}
		return btnCheckInTime;
	}
	private JButton getBtnCheckOutTime() {
		if (btnCheckOutTime == null) {
			btnCheckOutTime = new JButton("Auto-fill out time");
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
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
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
			pnPrescription.add(getComboBox());
			pnPrescription.add(getBtnNewButton());
			pnPrescription.add(getLblNewLabel());
			pnPrescription.add(getLblIntervalh());
			pnPrescription.add(getLblDurationdays());
			pnPrescription.add(getTextField());
			pnPrescription.add(getTextField_1());
			pnPrescription.add(getTextField_2_1());
			pnPrescription.add(getLblComments());
			pnPrescription.add(getScrollPane_2());
			pnPrescription.add(getLblVaccines());
			pnPrescription.add(getComboBox_1());
			pnPrescription.add(getBtnNewButton_2());
			pnPrescription.add(getSeparator());
			pnPrescription.add(getSeparator_1());
			pnPrescription.add(getLblNewLabel_1());
			pnPrescription.add(getScrollPane_3());
			pnPrescription.add(getLblNewLabel_2());
			pnPrescription.add(getScrollPane_4());
			pnPrescription.add(getBtnNewButton_2_1());
			pnPrescription.add(getBtnNewButton_2_2());
			pnPrescription.add(getBtnNewButton_2_3());
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
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setBounds(23, 51, 110, 22);
		}
		return comboBox;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Add");
			btnNewButton.setBounds(143, 51, 62, 23);
		}
		return btnNewButton;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Quantity:");
			lblNewLabel.setBounds(23, 101, 62, 14);
		}
		return lblNewLabel;
	}
	private JLabel getLblIntervalh() {
		if (lblIntervalh == null) {
			lblIntervalh = new JLabel("Interval:");
			lblIntervalh.setBounds(23, 137, 80, 14);
		}
		return lblIntervalh;
	}
	private JLabel getLblDurationdays() {
		if (lblDurationdays == null) {
			lblDurationdays = new JLabel("Duration:");
			lblDurationdays.setBounds(23, 177, 62, 14);
		}
		return lblDurationdays;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(95, 98, 62, 20);
			textField.setColumns(10);
		}
		return textField;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(95, 134, 62, 20);
		}
		return textField_1;
	}
	private JTextField getTextField_2_1() {
		if (textField_2 == null) {
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(95, 174, 62, 20);
		}
		return textField_2;
	}
	private JLabel getLblComments() {
		if (lblComments == null) {
			lblComments = new JLabel("Comments:");
			lblComments.setBounds(23, 232, 110, 14);
		}
		return lblComments;
	}
	private JTextArea getTextArea_1() {
		if (textArea_1 == null) {
			textArea_1 = new JTextArea();
		}
		return textArea_1;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(23, 258, 192, 82);
			scrollPane_2.setViewportView(getTextArea_1());
		}
		return scrollPane_2;
	}
	private JLabel getLblVaccines() {
		if (lblVaccines == null) {
			lblVaccines = new JLabel("Vaccines:");
			lblVaccines.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVaccines.setBounds(258, 25, 110, 14);
		}
		return lblVaccines;
	}
	private JComboBox getComboBox_1() {
		if (comboBox_1 == null) {
			comboBox_1 = new JComboBox();
			comboBox_1.setBounds(257, 50, 111, 22);
		}
		return comboBox_1;
	}
	private JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("Add");
			btnNewButton_2.setBounds(378, 50, 51, 23);
		}
		return btnNewButton_2;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(235, 26, 12, 314);
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setBounds(440, 26, 12, 314);
		}
		return separator_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Other:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1.setBounds(252, 206, 62, 14);
		}
		return lblNewLabel_1;
	}
	private JTextArea getTextArea_2() {
		if (textArea_2 == null) {
			textArea_2 = new JTextArea();
		}
		return textArea_2;
	}
	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBounds(252, 230, 177, 110);
			scrollPane_3.setViewportView(getTextArea_2());
		}
		return scrollPane_3;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Prescription state:");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_2.setBounds(450, 28, 171, 14);
		}
		return lblNewLabel_2;
	}
	private JList getList_1() {
		if (list_1 == null) {
			list_1 = new JList();
		}
		return list_1;
	}
	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setBounds(450, 53, 184, 253);
			scrollPane_4.setViewportView(getList_1());
		}
		return scrollPane_4;
	}
	private JButton getBtnNewButton_2_1() {
		if (btnNewButton_2_1 == null) {
			btnNewButton_2_1 = new JButton("Add");
			btnNewButton_2_1.setBounds(378, 202, 51, 23);
		}
		return btnNewButton_2_1;
	}
	private JButton getBtnNewButton_2_2() {
		if (btnNewButton_2_2 == null) {
			btnNewButton_2_2 = new JButton("Remove");
			btnNewButton_2_2.setBounds(462, 317, 80, 23);
		}
		return btnNewButton_2_2;
	}
	private JButton getBtnNewButton_2_3() {
		if (btnNewButton_2_3 == null) {
			btnNewButton_2_3 = new JButton("Clear all");
			btnNewButton_2_3.setBounds(552, 317, 80, 23);
		}
		return btnNewButton_2_3;
	}
	private JScrollPane getScrollPane_5() {
		if (scrollPane_5 == null) {
			scrollPane_5 = new JScrollPane();
			scrollPane_5.setBounds(61, 240, 271, 66);
			scrollPane_5.setViewportView(getTextArea());
		}
		return scrollPane_5;
	}
	private JList getList_2() {
		if (list_2 == null) {
			list_2 = new JList();
		}
		return list_2;
	}
	private JScrollPane getScrollPane_6() {
		if (scrollPane_6 == null) {
			scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(63, 66, 214, 130);
			scrollPane_6.setViewportView(getList_2());
		}
		return scrollPane_6;
	}
	private JList getList_3() {
		if (list_3 == null) {
			list_3 = new JList();
		}
		return list_3;
	}
	private JScrollPane getScrollPane_7() {
		if (scrollPane_7 == null) {
			scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(381, 66, 216, 128);
			scrollPane_7.setViewportView(getList_3());
		}
		return scrollPane_7;
	}
}
