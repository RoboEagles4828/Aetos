package org.usfirst.frc.team4828.robot;

public class Ports {
	private Ports() {
		throw new AssertionError("Can't instantiate Class: Ports");
	}
	
	//USB port
	public static final int stick = 0;
	public static final int conveyorStick = 1;
	
	//CANs
	public static final int flTalon = 3;
	public static final int blTalon = 4;
	public static final int frTalon = 1;
	public static final int brTalon = 2;

	public static final int compressor = 0;

	//PWM
	public static final int conveyor = 9;
	public static final int conveyorFlip = 5;

	public static final int armSolenoidOpen = 1;
	public static final int armSolenoidClose = 0;
	public static final int armLift = 6;
	public static final int armFlip = 1;
	
	//digital inputs
	public static final int limitArmFlipDown = 3;
	public static final int limitArmFlipUp = 5;
	public static final int limitConveyorFlipDown = 2;
	public static final int limitConveyorFlipUp = 1;
	public static final int limitArmLiftUp = 10;
	public static final int limitArmLiftDown = 15;
	public static final int diHalleffect = 18;
	public static final int diProximity = 16;
}
