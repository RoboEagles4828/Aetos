//package org.usfirst.frc.team4828.modules;
//
//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.hal.CanTalonSRX;
//
//public class MecanumDrive{
//	RobotDrive drive;
//	
//	public enum Direction{
//		FORWARD, BACKWARD, LEFT, RIGHT;
//	}
//	
//	private CanTalonSRX frontLeft, backLeft, frontRight, backRight;
//	
//	public MecanumDrive(int flport, int blport, int frport, int brport) {
//		frontLeft = new CanTalonSRX(flport);
//		backLeft = new CanTalonSRX(blport);
//		frontRight = new CanTalonSRX(frport);
//		backRight = new CanTalonSRX(brport);
//		//drive = new RobotDrive(flport, blport, frport, brport);
//	}
//	
//	public void stop(){
//		frontLeft.Set(0);
//		backLeft.Set(0);
//		frontRight.Set(0);
//		backRight.Set(0);
//	}
//	
//	public void resetEncoders(){
//		//TODO
//	}
//	
//	public String getEncoderOutput(){
//		//TODO
//		return "output";
//	}
//	
//	public void mecanumDrive_Polar(double magnitude, double direction, double twist){
//		//drive.mecanumDrive_Polar(magnitude, direction, twist);
//	}
//	
//	public void move(Direction direction, double speed){
//		if(direction == Direction.FORWARD){
//			frontLeft.Set(speed);
//			frontRight.Set(speed);
//			backLeft.Set(speed);
//			backRight.Set(speed);
//		} else if(direction == Direction.BACKWARD){
//			frontLeft.Set(-speed);
//			frontRight.Set(-speed);
//			backLeft.Set(-speed);
//			backRight.Set(-speed);
//		} else if(direction == Direction.LEFT){
//			frontLeft.Set(-speed);
//			frontRight.Set(speed);
//			backLeft.Set(speed);
//			backRight.Set(-speed);
//		} else if(direction == Direction.RIGHT){
//			frontLeft.Set(speed);
//			frontRight.Set(-speed);
//			backLeft.Set(-speed);
//			backRight.Set(speed);
//		}
//	}
//}

package org.usfirst.frc.team4828.modules;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class MecanumDrive {
	public enum Direction {
		FORWARD, BACKWARD, LEFT, RIGHT, SPINRIGHT, SPINLEFT;
	}

	private CanTalonSRX frontLeft;
	private CanTalonSRX rearLeft;
	private CanTalonSRX frontRight;
	private CanTalonSRX rearRight;

	protected static final int kMaxNumberOfMotors = 4;

	public MecanumDrive(int flport, int rlport, int frport, int rrport) {
		frontLeft = new CanTalonSRX(flport);
		rearLeft = new CanTalonSRX(rlport);
		frontRight = new CanTalonSRX(frport);
		rearRight = new CanTalonSRX(rrport);
	}

	protected static double limit(double num) {
		if (num > 1.0) {
			return 1.0;
		}
		if (num < -1.0) {
			return -1.0;
		}
		return num;
	}

	public void stop() {
		frontLeft.Set(0);
		rearLeft.Set(0);
		frontRight.Set(0);
		rearRight.Set(0);
	}

	public static class MotorType {
		public final int value;
		static final int kFrontLeft_val = 0;
		static final int kFrontRight_val = 1;
		static final int kRearLeft_val = 2;
		static final int kRearRight_val = 3;

		public static final MotorType kFrontLeft = new MotorType(kFrontLeft_val);
		public static final MotorType kFrontRight = new MotorType(
				kFrontRight_val);
		public static final MotorType kRearLeft = new MotorType(kRearLeft_val);
		public static final MotorType kRearRight = new MotorType(kRearRight_val);

		private MotorType(int value) {
			this.value = value;
		}
	}

	protected static void normalize(double wheelSpeeds[]) {
		double maxMagnitude = Math.abs(wheelSpeeds[0]);
		int i;
		for (i = 1; i < kMaxNumberOfMotors; i++) {
			double temp = Math.abs(wheelSpeeds[i]);
			if (maxMagnitude < temp)
				maxMagnitude = temp;
		}
		if (maxMagnitude > 1.0) {
			for (i = 0; i < kMaxNumberOfMotors; i++) {
				wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
			}
		}
	}

	public void mecanumDrive_Polar(double magnitude, double direction,
			double rotation, DriveStick stick) {
		// Normalized for full power along the Cartesian axes.
		magnitude = limit(magnitude) * Math.sqrt(2.0);
		// The rollers are at 45 degree angles.
		double dirInRad = (direction + 45.0) * Math.PI / 180.0;
		double cosD = Math.cos(dirInRad);
		double sinD = Math.sin(dirInRad);

		double wheelSpeeds[] = new double[kMaxNumberOfMotors];
		wheelSpeeds[MotorType.kFrontLeft_val] = (sinD * magnitude + rotation);
		wheelSpeeds[MotorType.kFrontRight_val] = (cosD * magnitude - rotation);
		wheelSpeeds[MotorType.kRearLeft_val] = (cosD * magnitude + rotation);
		wheelSpeeds[MotorType.kRearRight_val] = (sinD * magnitude - rotation);

		normalize(wheelSpeeds);

		if ((stick.getX() > 0.05 || stick.getX() < -0.05)
				|| (stick.getY() > 0.05 || stick.getY() < -0.05)
				|| (rotation > 0.05 || rotation < -0.05)) {
			frontLeft.Set(wheelSpeeds[MotorType.kFrontLeft_val]);
			frontRight.Set(wheelSpeeds[MotorType.kFrontRight_val]);
			rearLeft.Set(wheelSpeeds[MotorType.kRearLeft_val]);
			rearRight.Set(wheelSpeeds[MotorType.kRearRight_val]);
		} else
			stop();
	}

	public void move(Direction direction, double speed) {
		if (direction == Direction.FORWARD) {
			frontLeft.Set(speed);
			frontRight.Set(speed);
			rearLeft.Set(speed);
			rearRight.Set(speed);
		} else if (direction == Direction.BACKWARD) {
			frontLeft.Set(-speed);
			frontRight.Set(-speed);
			rearLeft.Set(-speed);
			rearRight.Set(-speed);
		} else if (direction == Direction.LEFT) {
			frontLeft.Set(-speed);
			frontRight.Set(speed);
			rearLeft.Set(speed);
			rearRight.Set(-speed);
		} else if (direction == Direction.RIGHT) {
			frontLeft.Set(speed);
			frontRight.Set(-speed);
			rearLeft.Set(-speed);
			rearRight.Set(speed);
		}
	}

}