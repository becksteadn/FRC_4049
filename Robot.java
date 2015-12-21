
package org.usfirst.frc.team4049.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	///////////////////////////////////////////////////////////////////////
	
	//Necessary
	
	Joystick xbox = new Joystick(0);
	
	boolean a_button = xbox.getRawButton(1);
	boolean b_button = xbox.getRawButton(2);
	boolean x_button = xbox.getRawButton(3);
	boolean y_button = xbox.getRawButton(4);
	boolean select_button = xbox.getRawButton(10); //TODO: Change select and start button numbers
	boolean start_button = xbox.getRawButton(8);
	int dPad = xbox.getPOV();
	double leftStick_x = xbox.getRawAxis(0);
	double leftStick_y = xbox.getRawAxis(1);
	double rightStick_x = xbox.getRawAxis(2);
	double rightStick_y = xbox.getRawAxis(3);
			
	Talon front_left = new Talon(0);
	Talon front_right = new Talon(1);
	Talon back_left = new Talon(2);
	Talon back_right = new Talon(3);
	
	public RobotDrive main_drive = new RobotDrive(front_left, back_left, front_right, back_right);
	
	//Extras
	
	Talon lift = new Talon(4);
	
	Joystick controlPanel = new Joystick(1);
	boolean a_ctrl = controlPanel.getRawButton(0);
	boolean b_ctrl = controlPanel.getRawButton(1);
	boolean x_ctrl = controlPanel.getRawButton(2);
	boolean y_ctrl = controlPanel.getRawButton(3);
	boolean select_ctrl = controlPanel.getRawButton(7); //TODO: Change select and start button numbers
	boolean start_ctrl = controlPanel.getRawButton(8);
	int dPad_ctrl = controlPanel.getPOV();
	
	Encoder enc_lift = new Encoder(0,1);	
	///////////////////////////////////////////////////////////////////////
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autoTime();
    }
    
    public void autoTime(){
    	//With time
    	Timer time = new Timer();
    	time.start();
    	while(time.get() < 1000){
    		main_drive.drive(0.3, 0.0);
    	}
    	time.stop();
    	time.reset();
    	return; //stop autonomous
    }
    
    public void autoEnc(){
    	enc_lift.reset();
    	while(enc_lift.get() < 100){
    		lift.set(0.4);
    	}
    	lift.set(0.0);
    	enc_lift.reset();
    	while(enc_lift.get() > -100){
    		lift.set(0.2);
    	}
    	lift.set(0.0);
    	enc_lift.reset();
    	return;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        main_drive.mecanumDrive_Cartesian(leftStick_x, leftStick_y, rightStick_x, 0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
