package org.usfirst.frc.team4828.robot;

import org.usfirst.frc.team4828.modules.Arm;
import org.usfirst.frc.team4828.modules.Conveyor;
import org.usfirst.frc.team4828.modules.DIManager;
import org.usfirst.frc.team4828.modules.DriveStick;
import org.usfirst.frc.team4828.modules.MecanumDrive;
import org.usfirst.frc.team4828.modules.Arm.LiftDirection;
import org.usfirst.frc.team4828.modules.MecanumDrive.Direction;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Aetos extends IterativeRobot{
	private DriveStick stick, conveyorStick;
	private MecanumDrive drive;
	private Compressor compressor;
	private Conveyor conveyor;
	private Arm arm;
	private DIManager digitalInputs;
	
	public void robotInit(){
		stick = new DriveStick(Ports.stick);
		conveyorStick = new DriveStick(Ports.conveyorStick);
		drive = new MecanumDrive(Ports.flTalon, Ports.blTalon, Ports.frTalon, Ports.brTalon);
		compressor = new Compressor(Ports.compressor);
		conveyor = new Conveyor(Ports.conveyor, Ports.conveyorFlip);
		arm = new Arm(Ports.armSolenoidOpen, Ports.armSolenoidClose, Ports.armLift, Ports.armFlip);
		digitalInputs = DIManager.INSTANCE;
		
		digitalInputs.add("armFlipDown", Ports.limitArmFlipDown);
		digitalInputs.add("armFlipUp", Ports.limitArmFlipUp);
		digitalInputs.add("conveyorFlipDown", Ports.limitConveyorFlipDown);
		digitalInputs.add("conveyorFlipUp", Ports.limitConveyorFlipUp);
		digitalInputs.add("armLiftUp", Ports.limitArmLiftUp);
		digitalInputs.add("armLiftDown", Ports.limitArmLiftDown);
		digitalInputs.add("halleffect", Ports.diHalleffect);
		digitalInputs.add("proximity", Ports.diProximity);
		
		compressor.setClosedLoopControl(true);
	}
	
	public void autonomousInit(){
		arm.open();
		Timer.delay(0.3);
		/*while(!digitalInputs.get("armFlipDown")){
			arm.flipDown();
		}*/
		//arm.stopFlip();
		Timer.delay(0.3);
		while(!digitalInputs.get("armLiftDown")){
			arm.lower();
		}
		//Timer.delay(4.25);
		arm.stopLift();
		Timer.delay(0.3);
		arm.lift();
		Timer.delay(1.7);
		arm.stopLift();
		Timer.delay(0.3);
		arm.close();
		Timer.delay(1);
		while(!digitalInputs.get("armLiftUp")){
			arm.lift();
		}
		arm.stopLift();
	}
	
	public void autonomousPeriodic(){
		
	}
	
	private boolean override = false;
	private LiftDirection liftDirection = LiftDirection.HOLD;
	private double throttle = 0;
	private boolean betweenHalleffect = false;
	private boolean halleffectReading = false;
	public void teleopPeriodic(){
		//emergency limit override
		if(conveyorStick.getButton(ButtonMappings.limitOverride)) 
			override = true;
		else
			override = false;
		
		//arm open/close
		if(stick.getButton(ButtonMappings.armOpen))
			arm.open();
		if(stick.getButton(ButtonMappings.armClose))
			arm.close();
		//arm lift/lower
		halleffectReading = !digitalInputs.get("halleffect");
		System.out.println(halleffectReading);
		if(stick.getButton(ButtonMappings.armLift) && (!digitalInputs.get("armLiftUp") || override)){
			betweenHalleffect = false;
			liftDirection = LiftDirection.UP;
			System.out.println("Going up");
		}
		else if(stick.getButton(ButtonMappings.armLower) && (!digitalInputs.get("armLiftDown") || override)){
			betweenHalleffect = false;
			liftDirection = LiftDirection.DOWN;
			System.out.println("Going down");
		}
		if(!halleffectReading && liftDirection != LiftDirection.HOLD){
			betweenHalleffect = true;
			System.out.println("We are between halleffect");
		}
		if(halleffectReading && betweenHalleffect == true){
			liftDirection = LiftDirection.HOLD;
			betweenHalleffect = false;
			System.out.println("We are on a halleffect");
		}
		
		if(digitalInputs.get("armLiftUp") && liftDirection == LiftDirection.UP){
			liftDirection = LiftDirection.HOLD;
			System.out.println("hit top limit");
		}
		if(digitalInputs.get("armLiftDown") && liftDirection == LiftDirection.DOWN){
			liftDirection = LiftDirection.HOLD;
			System.out.println("hit bottom limit");
		}
		if(liftDirection == LiftDirection.UP)
			arm.lift();
		else if(liftDirection == LiftDirection.DOWN)
			arm.lower();
		else
			arm.stopLift();
		
		
		if(stick.getButton(ButtonMappings.armLiftManual) && (!digitalInputs.get("armLiftUp") || override))
			arm.lift();
		else if(stick.getButton(ButtonMappings.armLowerManual) && (!digitalInputs.get("armLiftDown") || override))
			arm.lower();
		else
			if(liftDirection == LiftDirection.HOLD) arm.stopLift();
		
		//arm flip up/down
		if(stick.getButton(ButtonMappings.armFlipUp) && (!digitalInputs.get("armFlipUp") || override))
			arm.flipUp();
		else if(stick.getButton(ButtonMappings.armFlipDown) && (!digitalInputs.get("armFlipDown") || override))
			arm.flipDown();
		else if(conveyorStick.getButton(ButtonMappings.armFlipUp2) && (!digitalInputs.get("armFlipUp") || override))
			arm.flipUp();
		else if(conveyorStick.getButton(ButtonMappings.armFlipDown2) && (!digitalInputs.get("armFlipDown") || override))
			arm.flipDown();
		else
			arm.stopFlip();
		
		//conveyor forward/backward
		if(conveyorStick.getButton(ButtonMappings.conveyorForward))
			conveyor.forwards();
		if(conveyorStick.getButton(ButtonMappings.conveyorBackward))
			conveyor.backwards();
		if(conveyorStick.getButton(ButtonMappings.conveyorStop))
			conveyor.stopConveyor();
	
		System.out.println("conv up limit: " + !digitalInputs.get("conveyorFlipUp") + "    conv down limit: " + !digitalInputs.get("conveyorFlipDown"));
		if(conveyorStick.getButton(ButtonMappings.conveyorFlipUp) && (!digitalInputs.get("conveyorFlipUp") || override)){
			conveyor.flipUp();
			System.out.println("conv flipup");
		}
		else if(conveyorStick.getButton(ButtonMappings.conveyorFlipDown) && (!digitalInputs.get("conveyorFlipDown") || override)){
			conveyor.flipDown(); 	
			System.out.println("conv flipdown");
		}
		else
			conveyor.stopFlip();
		
		if(stick.getPOV() == 0)
			drive.move(Direction.FORWARD, 0.4);
		else if(stick.getPOV() == 90)
			drive.move(Direction.RIGHT, 0.5);
		else if(stick.getPOV() == 180)
			drive.move(Direction.BACKWARD, 0.4);
		else if(stick.getPOV() == 270)
			drive.move(Direction.LEFT, 0.5);
		else{
			throttle = (-stick.getThrottle() + 1)/2;
			drive.mecanumDrive_Polar(stick.getMagitude() * throttle, stick.getDirectionDegrees(), stick.getTwist() * throttle, stick);
		}
		
		if(conveyorStick.getButton(ButtonMappings.stop)){
			System.out.println("Emergency stop!");
			conveyor.stopFlip();
			conveyor.stopConveyor();
			arm.stopFlip();
			arm.stopLift();
			arm.open();
			drive.stop();
		}
		
		Timer.delay(0.05);
	}
	
	@Override
	public void testPeriodic(){
		if(stick.getButton(ButtonMappings.armLiftManual) && (!digitalInputs.get("armLiftUp")))
			arm.liftSlower();
		else if(stick.getButton(ButtonMappings.armLowerManual))
			arm.lowerSlower();
		else
			if(liftDirection == LiftDirection.HOLD) arm.stopLift();
		
		if(conveyorStick.getButton(ButtonMappings.conveyorFlipUp) && (!digitalInputs.get("conveyorFlipUp") || override)){
			conveyor.flipUpSlow();
			System.out.println("conv flipup");
		}
		else if(conveyorStick.getButton(ButtonMappings.conveyorFlipDown) && (!digitalInputs.get("conveyorFlipDown") || override)){
			conveyor.flipDown(); 	
			System.out.println("conv flipdown");
		}
		else
			conveyor.stopFlip();
	}
}
