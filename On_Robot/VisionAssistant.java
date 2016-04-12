package org.usfirst.frc.team4049.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionAssistant {

	static NetworkTable table;
	static NetworkTable guiTable;
	static double[] defaultValue = new double[0];
	static double target_centerX, target_centerY, target_width, target_area = 1;
	static double timerDelay = 0.1;
	static Servo camX = new Servo(8);
	static Servo camY = new Servo(9);
	
	public VisionAssistant(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		guiTable = NetworkTable.getTable("Frames");
	}
	
	public static void updateTable(){
		int index = getMax();
		double[] area = table.getNumberArray("area", defaultValue);
		if(area.length > 0){
			
			target_area = area[index];
		}else{
			target_area = 0;
		}
		
		double[] centerX = table.getNumberArray("centerX", defaultValue);
    	if(centerX.length > 0){
    		target_centerX = centerX[index];
    	}else{
    		target_centerX = 0;
    	}
    	guiTable.putNumber("centerX", target_centerX);
    	
    	
		double[] centerY = table.getNumberArray("centerY", defaultValue);
		if(centerY.length > 0){
    		target_centerY = centerY[index];
    	}else{
    		target_centerY = 0;
    	}
		
		double[] width = table.getNumberArray("width", defaultValue);
		if(width.length > 0){
			target_width = area[index];
		}else{
			target_width = 0;
		}
	}

	public static boolean isRight(double centerX){
		if(centerX > 205 && centerX != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isCloseRight(double centerX){
		if(centerX > 198 && centerX != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isLeft(double centerX){
		if(centerX < 190 && centerX != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isCloseLeft(double centerX){
		if(centerX < 194 && centerX != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isCentered(double centerX){
		if(centerX > 193 && centerX < 198){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFarCentered(double centerX){
		if(centerX > 170 && centerX < 200){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static int getMax(){ //Get index of the object with the largest area
		int greatest = 0;
		double[] area = table.getNumberArray("area", defaultValue);
		if(area.length > 0){
			for(int i = 0; i < area.length;i++){
				if(area[greatest] < area[i]){
					greatest = i;
				}
			}
		}//if
		return greatest;
	}
	
	public static void camYUp(){
		camY.set(camY.get() + .01);
	}
	
	public static void camYDown(){
		camY.set(camY.get() - .01);
	}
	
	public static void camXRight(){
		camX.set(camX.get() + .01);
	}
	
	public static void camXLeft(){
		camX.set(camX.get() - .01);
	}

	public static void centerY(){
		updateTable();
		//target_centerY, camY.get(), camY.set()
		if(target_centerY < 80 && target_centerY != 0){
			camYUp();
		}
		else if(target_centerY > 120 && target_centerY != 0){
			camYDown();
		}
		Timer.delay(0.05);
	}
	
	public static void stopBot(){
		Timer.delay(timerDelay);
		Robot.drive.drive(0,0);
	}
	
	public static void centerMeFar(){
		updateTable();
		double x = target_centerX;
		
		if(x < 170){
			Robot.drive.tankDrive(-rotate, rotate);
		}else if(x > 200){
			Robot.drive.tankDrive(rotate, -rotate);
		}else{
			Robot.drive.drive(0, 0);
		}
		
	}

	static double rotate = 0.5;
	static double rotateSlow = 0.3;
	
	public static void centerMe(){
		updateTable();
		double x = target_centerX;
			if(isRight(x)){ //IS RIGHT
				System.out.println("Too far right");
				//Robot.drive.mecanumDrive_Cartesian(0, 0, rotate, 0);
				Robot.drive.tankDrive(rotate, -rotate);
				Robot.xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 0.1);
			}else if(isCloseRight(x)){ //IS CLOSE RIGHT
				System.out.println("Close right");
				//Robot.drive.mecanumDrive_Cartesian(0, 0, rotateSlow, 0);
				Robot.drive.tankDrive(rotateSlow, -rotateSlow);
			}else if(isLeft(x)){ //IS LEFT
				//Too far left
				System.out.println("Too far left");
				//Robot.drive.mecanumDrive_Cartesian(0, 0, -rotate, 0);
				Robot.drive.tankDrive(-rotate, rotate);
				Robot.xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 0.1);
			}else if(isCloseLeft(x)){ //IS CLOSE LEFT
				//Too far left
				System.out.println("Close left");
				//Robot.drive.mecanumDrive_Cartesian(0, 0, -rotateSlow, 0);
				Robot.drive.tankDrive(-rotateSlow, rotateSlow);
			}else if(x == 0){
				System.out.println("No Target Found");
				Robot.drive.drive(0,0);
			}else{
				//Centered
				System.out.println("Centered");
				Robot.xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 1.0);
				Robot.xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 1.0);
				
				Robot.drive.drive(0,0);
			}
			stopBot();
			Robot.xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 0.0);
			Robot.xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 0.0);
		}//end centerMe
}
 