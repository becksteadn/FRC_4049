
package org.usfirst.frc.team4049.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String reachAuto = "Reach";
    final String crossAuto = "Cross";
    final String shootAuto = "Shoot";
    String autoSelected;
    int selectedAuto;
    SendableChooser chooser;
	
    static Joystick xbox = new Joystick(0);
    
    static VictorSP leftOne = new VictorSP(0);
    static VictorSP leftTwo = new VictorSP(1);
    static VictorSP rightOne = new VictorSP(2);
    static VictorSP rightTwo = new VictorSP(3);
    static RobotDrive drive = new RobotDrive(leftOne, leftTwo, rightOne, rightTwo);
    boolean slowSpeed = false;
    
    static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    Timer autoTime = new Timer();
    
    
    static VictorSP shooterBottom = new VictorSP(4);
    static VictorSP shooterTop = new VictorSP(5);
    boolean shoot = false;
    
    static VictorSP feeder = new VictorSP(6);
    double feederSpeed = 0.8;
    boolean feedIn = false;
    boolean feedOut = false;
    
    VictorSP upDown = new VictorSP(7);
    Encoder enc = new Encoder(0,1);
    
    double cameraX = 0.54, cameraY = 0.85;
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Reach", reachAuto);
        chooser.addObject("Cross", crossAuto);
        chooser.addObject("Shoot", shootAuto);
        SmartDashboard.putData("Auto choices", chooser);
        
        VisionAssistant.table = NetworkTable.getTable("GRIP/myContoursReport");
        VisionAssistant.guiTable = NetworkTable.getTable("Frames");
        
        shoot = false;
        VisionAssistant.guiTable.putBoolean("shoot", shoot);
        
        gyro.reset();
        gyro.calibrate();
        enc.reset();
    }
    
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
    	gyro.reset();
		System.out.println("Auto selected: " + autoSelected);
		Auto.ctMove = 0;
		
		//If GUI program is not running or sumthin
		try{
			selectedAuto = (int) VisionAssistant.guiTable.getInt("autoNum");
		}catch(Exception e){
			System.out.println("Auto undefined");
			selectedAuto = 0;
		}
		
		
    }

    public void autonomousPeriodic() {
    	//Move Time Left Right
    	
    	switch(selectedAuto){
    		
    	case 0:
    		//Reach defense
    		Auto.straight(0, 2, 0.3);
    		break;
    	case 1:
    		//Cross defense
    		Auto.straight(0, 4, 1);
    		break;
    	case 2:
    		//Short cross
    		Auto.straight(0, 3, 1);
    		//Auto.shoot();
    		break;
		default:
			Auto.straight(0, 2, 0.3);
    	}
    	
    	/*switch(autoSelected) {
    	case shootAuto:
    		//Auto.move(0, 5, 1, 1);
    		Auto.straight(0, 5, 1);
    		Auto.shoot();
    		break;
    	case reachAuto:
    		//Auto.move(0, 2, 0.6, 0.6);
    		Auto.straight(0, 2, 0.3);
    		break;
    	case crossAuto:
    		Auto.straight(0, 2, 1);
    		//Auto.move(0, 4, 1, 1);
            break;
    	case defaultAuto:
    		Auto.straight(0, 2, 0.3);
    		//Auto.move(0, 2, 0.6, 0.6); //Move forward 2 seconds
    		break;
    	default:
    		Auto.straight(0, 2, 0.3);
    		//Auto.move(0, 2, 0.6, 0.6);
            break;
    	}*/
    	
    }

    public void teleopInit(){
    	slowSpeed = false;
    	shoot = false;
    	feedIn = false;
    	feedOut = false;
    	enc.reset();
    	
    	VisionAssistant.camX.set(cameraX);
    	VisionAssistant.camY.set(cameraY);
    }
    
    public void teleopPeriodic() {
    	
    	//SmartDashboard.putNumber("Left Speed", xbox.getRawAxis(1));
    	//SmartDashboard.putNumber("Right Speed", xbox.getRawAxis(5));
    	
    	VisionAssistant.updateTable();
    	VisionAssistant.guiTable.putNumber("centerX", VisionAssistant.target_centerX);
    	
    	if(xbox.getRawButton(3)){
    		VisionAssistant.camX.set(cameraX);
    		VisionAssistant.camY.set(cameraY);
    	}
    	
    	
    	upDown();
    	
    	if(!xbox.getRawButton(5)){
    		drive();
    	}else if(xbox.getRawButton(5)){
    		VisionAssistant.centerMe();
    	}
    	
    	feed();
    	shoot();
    	
    	Timer.delay(0.005);
    }//teleop periodic
    
    //drive, feeder, shoot, and arm
    public void drive(){
    	drive.tankDrive(-xbox.getRawAxis(1), -xbox.getRawAxis(5));
    }
    public void feed(){
    	if(xbox.getRawAxis(3) > 0.1){
    		feeder.set(feederSpeed);
    	}else if(xbox.getRawAxis(2) > 0.1){
    		feeder.set(-1.0);
    	}else{
    		feeder.set(0);
    	}
    }

    public void shoot(){
    	if(xbox.getRawButton(6)){ //Start/stop shooter
    		if(shoot){
    			shoot = false;
    		}else{
    			shoot = true;
    		}
    		Timer.delay(0.2);
    	}
    	
    	if(shoot){ //Shoot/Dont shoot
    		shooterBottom.set(-0.725);
    		shooterTop.set(0.775);
    		VisionAssistant.guiTable.putBoolean("shoot", shoot);
    		xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 0.2);
    		xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 0.2);
    	}else{
    		shooterBottom.set(0);
    		shooterTop.set(0);
    		VisionAssistant.guiTable.putBoolean("shoot", shoot);
    		xbox.setRumble(Joystick.RumbleType.kLeftRumble, (float) 0.0);
    		xbox.setRumble(Joystick.RumbleType.kRightRumble, (float) 0.0);
    	}
    }
    public void upDown(){
    	
    	if(xbox.getRawButton(8)){
    		enc.reset();
    	}
    	
    	if(xbox.getPOV() == 0){
    		upDown.set(-0.6);
    	}else if(xbox.getPOV() == 180){
    		upDown.set(0.3);
    	}/*else if(enc.get() < -51 && enc.get() > -160){
    		upDown.set(-0.3);
    	}*/else if(enc.get() > -100){
    		upDown.set(-0.1);
    	}else{
    		upDown.set(0.0);
    	}
    
    }
    
    public void testInit(){
       	VisionAssistant.camX.set(cameraX);
    	VisionAssistant.camY.set(cameraY);
    }
    
    public void testPeriodic() {
    	if(xbox.getRawButton(1)){
    		leftOne.set(1);
    	}else if(xbox.getRawButton(2)){
    		leftTwo.set(1);
    	}else if(xbox.getRawButton(3)){
    		rightOne.set(1);
    	}else if(xbox.getRawButton(4)){
    		rightTwo.set(1);
    	}else{
        	leftOne.set(0);
        	leftTwo.set(0);
        	rightOne.set(0);
        	rightTwo.set(0);
    	}

    }
    
}
