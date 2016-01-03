package gui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;

public class GUI {

	public static JFrame frame = new JFrame("Team 4049");
	static ArrayList<String> output = new ArrayList<String>();
	
	public static void initFrame(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
	
	public static void addToList(String statement){
		output.add(statement);
		String[] outputStr = new String[output.size()];
		for(int i = 0; i < output.size();i++){
			outputStr[i] = output.get(i);
		}
		JList<String> list = new JList<String>(outputStr);
		frame.getContentPane().add(list, null);
		frame.repaint();
	}
	
}
