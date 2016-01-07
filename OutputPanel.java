import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.List;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;

public class OutputPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	static JList list;
	static JScrollPane scrollPane;
	
	public static OutputPanel outWindow = new OutputPanel();
	
	static DefaultListModel outputModel = new DefaultListModel();
	
	/**
	 * Create the frame.
	 */
	public OutputPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/FRC_logo.png"));
		setTitle("Team 4049");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 396);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setPreferredSize(new Dimension(250, 10));
		contentPane.setMinimumSize(new Dimension(250, 10));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 250, 295);
		scrollPane.setPreferredSize(new Dimension(250, 2));
		contentPane.add(scrollPane);
		list = new JList();
		list.setName("lst_output");
		list.setBackground(SystemColor.controlHighlight);
		list.setFont(new Font("Courier New", Font.PLAIN, 11));
		list.setMinimumSize(new Dimension(250, 336));
		scrollPane.setViewportView(list);
		
		JLabel label = new JLabel("");
		label.setBounds(278, -12, 310, 369);
		label.setIcon(new ImageIcon("res/FRC_logo.png"));//"C:\\Users\\Nathaniel\\Desktop\\Programming\\Java\\Eclipse\\JFrame\\res\\FIRST_Robotics_Competition_Logo.png"));
		contentPane.add(label);
		
		JLabel lblRobotOutput = new JLabel("Robot Output");
		lblRobotOutput.setForeground(new Color(65, 105, 225));
		lblRobotOutput.setFont(new Font("Nirmala UI", Font.BOLD, 23));
		lblRobotOutput.setBounds(10, 11, 273, 29);
		contentPane.add(lblRobotOutput);
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
