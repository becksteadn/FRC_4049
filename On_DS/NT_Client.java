import java.awt.Color;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NT_Client implements ITableListener{
	static Centering c;
	static OutputPanel output;
	double target_centerX;
	boolean shoot = false;
	String target;
	NetworkTable table, guiTable;
	
	public static void main(String[] args){
		
		OutputPanel.initFrame();
		
		c = new Centering();
		c.setVisible(true);
		//c.setAlwaysOnTop(true); //or this
		c.toFront();
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		c.setSize((int) width, 150);
		
		new NT_Client().run();
	}
	
	public void run(){
		boolean init = false;
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-4049-frc.local");
		table = NetworkTable.getTable("GRIP/myContoursReport");
		guiTable = NetworkTable.getTable("Frames");
		
		guiTable.putBoolean("shoot", false);
		guiTable.putString("target", "");
		guiTable.putNumber("centerX", 0);
		
		
		
		while(true){

			if(guiTable.isConnected() && !init){
				OutputPanel.addToList("Connected to guiTable");
			}
			
			if(table.isConnected() && !init){
				OutputPanel.addToList("Connected to table");
				init = true;
			}
			
			target_centerX = guiTable.getNumber("centerX");
			target = guiTable.getString("target");
			shoot = guiTable.getBoolean("shoot");
			guiTable.putNumber("autoNum", OutputPanel.autoNum);
			//guiTable.putNumber("Auto", OutputPanel.autoNum);
			//guiTable.putString("autoNumStr", Integer.toString(OutputPanel.autoNum));
			
			
			try{
				OutputPanel.lblCenterX.setText(Double.toString(target_centerX));
				OutputPanel.lblAlignment.setText(target);
			}catch(Exception e){
				//System.out.println("Target is null");
			}
			
			
			try{
				Thread.sleep(100);
			}catch(InterruptedException ex){
				Logger.getLogger(NT_Client.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			//SHOOTING ------------
			if(shoot){
				OutputPanel.outWindow.setBackground(Color.GREEN);
				OutputPanel.list.setBackground(Color.GREEN);
				OutputPanel.contentPane.setBackground(Color.GREEN);
				//OutputPanel.addToList("Shooter On");
				//OutputPanel.setBackground(Color.GREEN);
			}else{
				OutputPanel.outWindow.setBackground(Color.RED);
				OutputPanel.contentPane.setBackground(Color.RED);
				OutputPanel.list.setBackground(Color.RED);
				//OutputPanel.addToList("Shooter Off");
			}
			
			switch(target){
			case "left":
				c.pnLeft.setBackground(Color.RED);
				c.pnCentered.setBackground(Color.WHITE);
				c.pnRight.setBackground(Color.WHITE);
				break;
			case "right":
				c.pnLeft.setBackground(Color.WHITE);
				c.pnCentered.setBackground(Color.WHITE);
				c.pnRight.setBackground(Color.RED);
				break;
			case "centered":
				c.pnLeft.setBackground(Color.WHITE);
				c.pnCentered.setBackground(Color.GREEN);
				c.pnRight.setBackground(Color.WHITE);
				break;
			default:
				c.pnLeft.setBackground(Color.WHITE);
				c.pnCentered.setBackground(Color.WHITE);
				c.pnRight.setBackground(Color.WHITE);
			}
			
			checkCentered();
			
		}
	}
	
	private void checkCentered(){
		if(target_centerX == 0){
			guiTable.putString("target", "NA");
		}else if(target_centerX < 193){
			guiTable.putString("target", "left");
		}else if(target_centerX > 198){
			guiTable.putString("target", "right");
		}else{
			guiTable.putString("target", "centered");
		}
	}

	@Override
	public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3) {
		System.out.println("String: " + arg1 + " Value: " + arg2 + " new: " + arg3);
		
	}
	
	
	
	
}
