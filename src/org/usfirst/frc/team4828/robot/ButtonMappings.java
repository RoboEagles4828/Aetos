package org.usfirst.frc.team4828.robot;

public class ButtonMappings {
	private ButtonMappings() {
		throw new AssertionError("Can't instantiate class: ButtonMappings");
	}
	
	//stick 1
	public final static int armOpen = 1;
	public final static int armClose = 2;
	public final static int armFlipDown = 11;
	public final static int armFlipUp = 12;
	public final static int armLower = 4;
	public final static int armLift = 6;
	public final static int armLiftManual = 5;
	public final static int armLowerManual = 3;
	
	//stick 2
	public final static int stop = 8;
	public final static int armFlipUp2 = 6;
	public final static int armFlipDown2 = 4;
	public final static int conveyorForward = 11;
	public final static int conveyorBackward = 12;
	public final static int conveyorStop = 10;
	public final static int limitOverride = 7;
	public final static int conveyorFlipUp = 5;
	public final static int conveyorFlipDown = 3;
}
