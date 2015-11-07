package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class Arm {
	PneumaticPiston piston;
	CanTalonSRX lift;
	Talon flip;
	
	private final double liftUpSpeed = 1;
	private final double liftDownSpeed = 0.8;
	private final double flipUpSpeed = 0.4;
	private final double flipDownSpeed = 0.3;
	
	public Arm(int solenoidOpen, int solenoidClose, int liftPort, int flipPort) {
		piston = new PneumaticPiston(solenoidOpen, solenoidClose);
		lift = new CanTalonSRX(liftPort);
		flip = new Talon(flipPort);
	}
	
	public void lift(){
		lift.Set(liftUpSpeed);
	}
	
	public void lower(){
		lift.Set(liftDownSpeed);
	}
	
	public void stopLift(){
		lift.Set(0);
	}
	
	public void flipUp(){
		lift.Set(flipUpSpeed);
	}
	
	public void flipDown(){
		lift.Set(flipDownSpeed);
	}

	public void stopFlip(){
		lift.Set(0);
	}
	
	public void open(){
		piston.open();
	}
	
	public void close(){
		piston.close();
	}
}
