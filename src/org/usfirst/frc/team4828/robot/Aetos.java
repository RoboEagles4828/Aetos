package org.usfirst.frc.team4828.robot;

import org.usfirst.frc.team4828.modules.Arm;
import org.usfirst.frc.team4828.modules.Conveyor;
import org.usfirst.frc.team4828.modules.DIManager;
import org.usfirst.frc.team4828.modules.DriveStick;
import org.usfirst.frc.team4828.modules.MecanumDrive;
import org.usfirst.frc.team4828.modules.MecanumDrive.Direction;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Aetos extends IterativeRobot{
	DriveStick stick;
	MecanumDrive drive;
	Compressor compressor;
	Conveyor conveyor;
	Arm arm;
	DIManager digitalInputs;
	
	public void robotInit(){
		stick = new DriveStick(Ports.stick);
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
		digitalInputs.add("hallEffect", Ports.diHallEffect);
		digitalInputs.add("proximity", Ports.diProximity);
		
		compressor.setClosedLoopControl(true);
		arm.close();
	}
	
	public void autonomousPeriodic(){
		//TODO
	}
	
	double throttle = 0;
	public void teleopInit(){
		//arm open/close
		if(stick.getButton(ButtonMappings.armOpen))
			arm.open();
		if(stick.getButton(ButtonMappings.armClose))
			arm.close();
		
		//arm lift/lower
		if(stick.getButton(ButtonMappings.armLift) && !digitalInputs.get("armLiftUp"))
			arm.lift();
		else if(stick.getButton(ButtonMappings.armLower) && !digitalInputs.get("armLiftDown"))
			arm.lower();
		else
			arm.stopLift();
		
		//arm flip up/down
		if(stick.getButton(ButtonMappings.armFlipUp) && !digitalInputs.get("armFlipUp"))
			arm.flipUp();
		else if(stick.getButton(ButtonMappings.armFlipDown) && !digitalInputs.get("armFlipDown"))
			arm.flipDown();
		else
			arm.stopFlip();
		
		//conveyor forward/backward
		if(stick.getButton(ButtonMappings.conveyorForward))
			conveyor.forwards();
		if(stick.getButton(ButtonMappings.conveyorBackward))
			conveyor.backwards();
		if(stick.getButton(ButtonMappings.conveyorStop))
			conveyor.stopConveyor();
	
		//conveyor lift/lower
		if(stick.getButton(ButtonMappings.conveyorFlipUp) && !digitalInputs.get("conveyorFlipUp"))
			conveyor.flipUp();
		else if(stick.getButton(ButtonMappings.conveyorFlipUp) && !digitalInputs.get("conveyorFlipDown"))
			conveyor.flipDown();
		else
			conveyor.stopFlip();
		
		if(stick.getPOV() == 0)
			drive.move(Direction.FORWARD, 0.2);
		else if(stick.getPOV() == 90)
			drive.move(Direction.RIGHT, 0.7);
		else if(stick.getPOV() == 180)
			drive.move(Direction.BACKWARD, 0.2);
		else if(stick.getPOV() == 270)
			drive.move(Direction.LEFT, 0.7);
		else{
			throttle = (-stick.getThrottle() + 1)/2;
			drive.mecanumDrive_Polar(stick.getMagitude() * throttle, stick.getDirectionDegrees(), stick.getTwist() * throttle);
		}
		
		Timer.delay(0.05);
	}
}
