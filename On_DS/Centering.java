import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Centering extends JFrame {

	private JPanel contentPane;
	JPanel pnLeft, pnCentered, pnRight;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Centering frame = new Centering();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Centering() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		setLocation(0, 0);
		
		pnLeft = new JPanel();
		pnLeft.setBackground(Color.WHITE);
		contentPane.add(pnLeft);
		pnLeft.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblLeft = new JLabel("Left");
		lblLeft.setFont(new Font("Courier New", Font.BOLD, 21));
		lblLeft.setHorizontalAlignment(SwingConstants.CENTER);
		pnLeft.add(lblLeft);
		
		pnCentered = new JPanel();
		pnCentered.setBackground(Color.WHITE);
		contentPane.add(pnCentered);
		pnCentered.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblCentered = new JLabel("Centered");
		lblCentered.setFont(new Font("Courier New", Font.BOLD, 21));
		lblCentered.setHorizontalAlignment(SwingConstants.CENTER);
		pnCentered.add(lblCentered);
		
		pnRight = new JPanel();
		pnRight.setBackground(Color.WHITE);
		contentPane.add(pnRight);
		pnRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblRight = new JLabel("Right");
		lblRight.setFont(new Font("Courier New", Font.BOLD, 21));
		lblRight.setHorizontalAlignment(SwingConstants.CENTER);
		pnRight.add(lblRight);
	}

}
