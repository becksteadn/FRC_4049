package org.usfirst.frc.team4049.robot;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
	
	static Timer timer = new Timer();
	static int ctMove = 0;
	
	public static void stop(){
	}
	
	public static void straight(int move, double time, double speed){
		timer.start();
		while(ctMove == move){
			if(timer.get() < time){
				Robot.drive.drive(speed, -Robot.gyro.getAngle() * 0.03);
			}else{
				Robot.drive.drive(0, 0);
				timer.stop();
				timer.reset();
				ctMove++;
			}
			Timer.delay(0.004);
		}
	}
	
	public static void move(int move,double time, double left, double right){
		timer.start();
		while(ctMove == move){
			if(timer.get() < time){
				System.out.println("Moving " + left + " " + right);
				Robot.drive.tankDrive(left, right);
				//Robot.drive.drive(left,0);
			}else{
				Robot.drive.drive(0,0);
				timer.stop();
				timer.reset();
				ctMove++;
			}
		}
	}
	
	public static void center(){
		VisionAssistant.updateTable();
		while(VisionAssistant.target_centerX > 200 || VisionAssistant.target_centerX < 150){
			VisionAssistant.centerMe();
		}
	}
	
	public static void shoot(){
		VisionAssistant.updateTable();
		
		while(!VisionAssistant.isFarCentered(VisionAssistant.target_centerX)){
			VisionAssistant.centerMeFar();
		}
		while(!(VisionAssistant.target_width < 80)){
			Robot.drive.drive(0, -Robot.gyro.getAngle() * 0.03);
		}
		while(!VisionAssistant.isCentered(VisionAssistant.target_centerX)){
			VisionAssistant.centerMe();
		}
		Robot.shooterTop.set(0.95);
		Robot.shooterBottom.set(-0.95);
		Timer.delay(1);
		Robot.feeder.set(0.8);
		
	}
	
    public static void defaultAuto(){
    
    	while(ctMove == 0){
    		if(timer.get() < 1.0){
    			//drive.drive(-0.4, 0.5);
    			System.out.println("Forward " + timer.get());
    		}else{
    			//drive.drive(0.0, 0.0);
    			System.out.println("Stop " + timer.get());
    			timer.stop();
    			timer.reset();
    			ctMove++;
    			timer.start();
    		}
    	}
    	while(ctMove == 1){
    		if(timer.get() < 1.0){
    			//drive.drive(0.4, 0.5);
    			System.out.println("Backward " + timer.get());
    		}else{
    			//drive.drive(0.0, 0.0);
    			System.out.println("Stop " + timer.get());
    			timer.stop();
    			timer.reset();
    			ctMove++;
    			//timer.start();
    		}
    	}
    	
    	
    }
}
