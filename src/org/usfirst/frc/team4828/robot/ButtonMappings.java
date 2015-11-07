package org.usfirst.frc.team4828.robot;

public class ButtonMappings {
	private ButtonMappings() {
		throw new AssertionError("Can't instantiate ButtonMappings");
	}
	
	public static int armOpen = 1;
	public static int armClose = 2;
	public static int armFlipDown = 4;
	public static int armFlipUp = 6;
	public static int armLower = 6;
	public static int armLift = 5;
	
	public static int conveyorForward = 11;
	public static int conveyorBackward = 12;
	public static int conveyorStop = 10;
	
	public static int conveyorFlipUp = 0;
	public static int conveyorFlipDown = 0;
	
	public static int stop = 8;
	
	public static int resetEncoders = 7;
}
