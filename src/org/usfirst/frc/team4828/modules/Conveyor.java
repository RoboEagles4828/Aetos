package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Talon;

public class Conveyor {
	Talon conveyorMotor;
	Talon liftMotor;
	
	private final double rollSpeed = 0.7;
	private final double flipSpeed = 0.3;
	
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
		liftMotor.set(flipSpeed);
	}
	
	public void flipDown(){
		liftMotor.set(-flipSpeed);
	}
	
	public void stopFlip(){
		liftMotor.set(0);
	}
}
