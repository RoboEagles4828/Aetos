package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class MecanumDrive{
	RobotDrive drive;
	
	public enum Direction{
		FORWARD, BACKWARD, LEFT, RIGHT;
	}
	
	private CanTalonSRX frontLeft, backLeft, frontRight, backRight;
	
	public MecanumDrive(int flport, int blport, int frport, int brport) {
		frontLeft = new CanTalonSRX(flport);
		backLeft = new CanTalonSRX(blport);
		frontRight = new CanTalonSRX(frport);
		backRight = new CanTalonSRX(brport);
	}
	
	public void stop(){
		frontLeft.Set(0);
		backLeft.Set(0);
		frontRight.Set(0);
		backRight.Set(0);
	}
	
	public void resetEncoders(){
		//TODO
	}
	
	public String getEncoderOutput(){
		//TODO
		return "output";
	}
	
	public void mecanumDrive_Polar(double magnitude, double direction, double twist){
		drive.mecanumDrive_Polar(magnitude, direction, twist);
	}
	
	public void move(Direction direction, double speed){
		if(direction == Direction.FORWARD){
			frontLeft.Set(speed);
			frontRight.Set(speed);
			backLeft.Set(speed);
			backRight.Set(speed);
		} else if(direction == Direction.BACKWARD){
			frontLeft.Set(-speed);
			frontRight.Set(-speed);
			backLeft.Set(-speed);
			backRight.Set(-speed);
		} else if(direction == Direction.LEFT){
			frontLeft.Set(-speed);
			frontRight.Set(speed);
			backLeft.Set(speed);
			backRight.Set(-speed);
		} else if(direction == Direction.RIGHT){
			frontLeft.Set(speed);
			frontRight.Set(-speed);
			backLeft.Set(-speed);
			backRight.Set(speed);
		}
	}
}