import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class OutputPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	static JPanel contentPane;
	
	static JList list;
	static JScrollPane scrollPane;
	static JLabel lblAutoSelect, lblCenterX, lblAlignment;
	public static int autoNum = 0;
	
	public static OutputPanel outWindow = new OutputPanel();
	
	static DefaultListModel outputModel = new DefaultListModel();
	/**
	 * @wbp.nonvisual location=712,369
	 */
	private final ButtonGroup autoGroup = new ButtonGroup();
	
	/**
	 * Create the frame.
	 */
	public OutputPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/FRC_logo.png"));
		setTitle("Team 4049");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 425);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setPreferredSize(new Dimension(250, 10));
		contentPane.setMinimumSize(new Dimension(250, 10));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(0, 150);
		toFront();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 250, 295);
		scrollPane.setPreferredSize(new Dimension(250, 2));
		contentPane.add(scrollPane);
		list = new JList();
		list.setName("lst_output");
		list.setBackground(SystemColor.menu);
		list.setFont(new Font("Courier New", Font.PLAIN, 11));
		list.setMinimumSize(new Dimension(250, 336));
		scrollPane.setViewportView(list);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e){
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		
		JLabel lblRobotOutput = new JLabel("Robot Output");
		lblRobotOutput.setForeground(new Color(65, 105, 225));
		lblRobotOutput.setFont(new Font("Nirmala UI", Font.BOLD, 23));
		lblRobotOutput.setBounds(10, 11, 273, 29);
		contentPane.add(lblRobotOutput);
		
		JPanel panel = new JPanel();
		panel.setBounds(270, 11, 250, 203);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JRadioButtonMenuItem rdCross = new JRadioButtonMenuItem("Long Cross");
		rdCross.setHorizontalTextPosition(SwingConstants.LEFT);
		rdCross.setHorizontalAlignment(SwingConstants.LEFT);
		rdCross.setBounds(66, 83, 125, 22);
		panel.add(rdCross);
		
		JRadioButtonMenuItem rdShoot = new JRadioButtonMenuItem("Short Cross");
		rdShoot.setHorizontalTextPosition(SwingConstants.LEFT);
		rdShoot.setHorizontalAlignment(SwingConstants.LEFT);
		rdShoot.setBounds(66, 116, 125, 22);
		panel.add(rdShoot);
		
		JRadioButtonMenuItem rdReach = new JRadioButtonMenuItem("Reach Defense");
		rdReach.setHorizontalTextPosition(SwingConstants.LEFT);
		rdReach.setHorizontalAlignment(SwingConstants.LEFT);
		rdReach.setBounds(66, 50, 125, 22);
		panel.add(rdReach);
		
		lblAutoSelect = new JLabel("Choose Auto");
		lblAutoSelect.setBounds(10, 11, 228, 32);
		panel.add(lblAutoSelect);
		lblAutoSelect.setFont(new Font("Courier New", Font.BOLD, 18));
		
		autoGroup.add(rdReach);
		autoGroup.add(rdCross);
		autoGroup.add(rdShoot);
		rdReach.setSelected(true);
		
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(66, 149, 89, 23);
		panel.add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdReach.isSelected()){
					lblAutoSelect.setText("Reach the Defense");
					JOptionPane.showMessageDialog(null, "Reach Auto Selected!");
					autoNum = 0;
					OutputPanel.addToList(Integer.toString(autoNum));
				}else if(rdCross.isSelected()){
					lblAutoSelect.setText("Cross the Defense");
					JOptionPane.showMessageDialog(null, "Long Auto Selected!");
					autoNum = 1;
					OutputPanel.addToList(Integer.toString(autoNum));
				}else if(rdShoot.isSelected()){
					lblAutoSelect.setText("Short Cross Auto");
					JOptionPane.showMessageDialog(null, "Short Auto Selected!");
					autoNum = 2;
					OutputPanel.addToList(Integer.toString(autoNum));
				}else{
					autoNum = 0;
					OutputPanel.addToList(Integer.toString(autoNum));
				}
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(270, 225, 219, 96);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTCenterX = new JLabel("Target CenterX: ");
		lblTCenterX.setBounds(9, 5, 81, 14);
		panel_1.add(lblTCenterX);
		
		JLabel lblTAlignment = new JLabel("Target Alignment:");
		lblTAlignment.setBounds(9, 34, 86, 14);
		panel_1.add(lblTAlignment);
		
		lblCenterX = new JLabel("CenterX");
		lblCenterX.setBounds(151, 5, 46, 14);
		panel_1.add(lblCenterX);
		
		JLabel lblAlignment = new JLabel("Alignment");
		lblAlignment.setBounds(151, 34, 46, 14);
		panel_1.add(lblAlignment);
		
	}
	
	public static void addToList(String statement){
		outputModel.addElement(statement);
		list = new JList(outputModel);
		outWindow.getContentPane().add(list, null);
		scrollPane.setViewportView(list);
	}
	
	public static void initFrame(){
		outWindow.setVisible(true);
	}
}
