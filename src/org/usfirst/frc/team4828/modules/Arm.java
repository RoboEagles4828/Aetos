package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class Arm {
	PneumaticPiston piston;
	CanTalonSRX lift;
	Talon flip;
	
	public enum LiftDirection{
		UP, DOWN, HOLD;
	}
	
	private static final double liftUpSpeed = 1;
	private static final double slowerLiftUpSpeed = 0.3;
	private static final double liftDownSpeed = -0.85;
	private static final double slowerLiftDownSpeed = -0.25;
	private static final double flipUpSpeed = 0.45;
	private static final double flipDownSpeed = -0.25;
	
	public Arm(int solenoidOpen, int solenoidClose, int liftPort, int flipPort) {
		piston = new PneumaticPiston(solenoidOpen, solenoidClose);
		lift = new CanTalonSRX(liftPort);
		flip = new Talon(flipPort);
	}
	
	public void lift(){
		lift.Set(liftUpSpeed);
	}
	
	public void liftSlower(){
		lift.Set(slowerLiftUpSpeed);
	}
	
	public void lower(){
		lift.Set(liftDownSpeed);
	}
	
	public void lowerSlower(){
		lift.Set(slowerLiftDownSpeed);
	}
	
	public void stopLift(){
		lift.Set(0);
	}
	
	public void flipUp(){
		flip.set(flipUpSpeed);
	}
	
	public void flipDown(){
		flip.set(flipDownSpeed);
	}

	public void stopFlip(){
		flip.set(0);
	}
	
	public void open(){
		piston.open();
	}
	
	public void close(){
		piston.close();
	}
}
