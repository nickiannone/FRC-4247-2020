package frc.robot.subcomponents;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.utils.Utils;

public class DriveTrain {

    public static final double K_FORWARD = 0.9;
    public static final double K_TURN = 0.75;

    private TalonSRX leftMotor = new TalonSRX(RobotMap.MOTOR_DRIVETRAIN_LEFT);
    private TalonSRX rightMotor = new TalonSRX(RobotMap.MOTOR_DRIVETRAIN_RIGHT);

	public void update(Robot robot) {
        // Get the inputs from the OI
        Joystick j = robot.getOI().getJoystick();
        double leftX = j.getRawAxis(RobotMap.AXIS_LEFT_X);
        double leftY = j.getRawAxis(RobotMap.AXIS_LEFT_Y);
        
        // Calculate the drive motor
        // TODO Verify this!
        double leftDrivePct = Utils.clamp(-(leftY * K_FORWARD) + (leftX * K_TURN), -1.0, 1.0);
        double rightDrivePct = Utils.clamp(-(leftY * K_FORWARD) - (leftX * K_TURN), -1.0, 1.0);

        // Output to the drive motors
        leftMotor.set(ControlMode.PercentOutput, leftDrivePct);
        rightMotor.set(ControlMode.PercentOutput, rightDrivePct);
	}
    
}