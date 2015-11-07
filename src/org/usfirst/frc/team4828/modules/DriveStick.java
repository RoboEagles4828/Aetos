package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Joystick;

public class DriveStick {
	Joystick stick;
	
	public DriveStick(int port) {
		stick = new Joystick(port);
	}
	
	public int getPOV(){
		return stick.getPOV();
	}
	
	public boolean getButton(int button){
		return stick.getRawButton(button);
	}
	
	public boolean getTrigger(){
		return stick.getTrigger();
	}
	
	public double getThrottle(){
		return stick.getThrottle();
	}
	
	public double getMagitude(){
		return stick.getMagnitude();
	}
	
	public double getDirectionDegrees(){
		return stick.getDirectionDegrees();
	}
	
	public double getTwist(){
		return stick.getTwist();
	}

}
