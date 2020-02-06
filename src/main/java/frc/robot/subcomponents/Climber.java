package frc.robot.subcomponents;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Climber {

    private TalonSRX elevatorMotor = new TalonSRX(RobotMap.MOTOR_CLIMBER_ELEVATOR);
    private TalonSRX winchMotor = new TalonSRX(RobotMap.MOTOR_CLIMBER_WINCH);
    private Solenoid winchClutch = new Solenoid(RobotMap.SOLENOID_CLIMBER_CLUTCH);
    private DigitalInput baseLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_BASE);
    private DigitalInput topLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_TOP);
    private DigitalInput clawLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_CLAW);

    private static final double ELEVATOR_LOWER_VELOCITY = -0.6;
    private static final double ELEVATOR_RAISE_VELOCITY = 0.6;
    private static final double ELEVATOR_SLOW_LOWER_VELOCITY = -0.3;

    private static final double WINCH_RETRACT_VELOCITY = 0.5;

    public void update(Robot robot) {
        Joystick j = robot.getOI().getJoystick();
        if (clawLimitSwitch.get()) {
            // The claw is disconnected, engage the clutch and auto-drop the elevator.
            winchClutch.set(true);

            // Drop the elevator slowly until it hits the bottom.
            if (baseLimitSwitch.get()) {
                elevatorMotor.set(ControlMode.Velocity, ELEVATOR_SLOW_LOWER_VELOCITY);
            } else {
                elevatorMotor.set(ControlMode.Velocity, 0.0);
            }

            // If the winch retract button is pressed, pull in the winch, otherwise hold it in place.
            if (j.getRawButton(RobotMap.BUTTON_RB)) {
                winchMotor.set(ControlMode.Velocity, WINCH_RETRACT_VELOCITY);
            } else {
                winchMotor.set(ControlMode.Velocity, 0.0);
            }
        } else {
            // The claw is still connected, disengage the clutch (to let the line out) and control the elevator with the buttons.
            winchClutch.set(false);

            // Run the elevator up or down.
            if (j.getRawButton(RobotMap.BUTTON_Y)) {
                if (topLimitSwitch.get()) {
                    elevatorMotor.set(ControlMode.Velocity, ELEVATOR_RAISE_VELOCITY);
                } else {
                    // Maxed out
                    elevatorMotor.set(ControlMode.Velocity, 0.0);
                }
            } else if (j.getRawButton(RobotMap.BUTTON_X)) {
                if (baseLimitSwitch.get()) {
                    elevatorMotor.set(ControlMode.Velocity, ELEVATOR_LOWER_VELOCITY);
                } else {
                    // Bottomed out
                    elevatorMotor.set(ControlMode.Velocity, 0.0);
                }
            } else {
                // Nothing pressed
                elevatorMotor.set(ControlMode.Velocity, 0.0);
            }
        }
    }
}