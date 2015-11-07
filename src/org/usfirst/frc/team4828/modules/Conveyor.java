package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class Conveyor {
	Talon conveyorMotor;
	Talon liftMotor;
	
	private static final double rollSpeed = 0.5;
	private static final double flipUpSpeed = 0.8;
	private static final double flipDownSpeed = -0.4;
	private static final double flipUpSpeedSlow = 0.3;
	private static final double flipHoldSpeed = 0.1;
	
	public Conveyor(int conveyorPort) {
		conveyorMotor = new Talon(conveyorPort);
	}
	
	public Conveyor(int conveyorPort, int flipPort){
		conveyorMotor = new Talon(conveyorPort);
		liftMotor = new Talon(flipPort);
	}
	
	public void forwards(){
		conveyorMotor.set(rollSpeed);
	}
	
	public void backwards(){
		conveyorMotor.set(-rollSpeed);
	}

	public void stopConveyor(){
		conveyorMotor.set(0);
	}

	public void flipUp(){
		liftMotor.set(flipUpSpeed);
	}
	
	public void flipDown(){
		liftMotor.set(flipDownSpeed);
	}
	
	public void flipUpSlow(){
		liftMotor.set(flipUpSpeedSlow);
	}
	
	public void flipHold(){
		liftMotor.set(flipHoldSpeed);
	}
	
	public void stopFlip(){
		liftMotor.set(0);
	}
}
