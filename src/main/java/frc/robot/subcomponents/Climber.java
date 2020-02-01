package frc.robot.subcomponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.utils.State;
import frc.robot.utils.StateMachine;

public class Climber {

    private TalonSRX elevatorMotor = new TalonSRX(RobotMap.MOTOR_CLIMBER_ELEVATOR);
    private TalonSRX winchMotor = new TalonSRX(RobotMap.MOTOR_CLIMBER_WINCH);
    private Solenoid winchClutch = new Solenoid(RobotMap.SOLENOID_CLIMBER_CLUTCH);
    private DigitalInput baseLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_BASE);
    private DigitalInput topLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_TOP);
    private DigitalInput clawLimitSwitch = new DigitalInput(RobotMap.DIN_CLIMBER_CLAW);

    private StateMachine<Climber> stateMachine;

    public Climber() {
        List<State<Climber>> states = new ArrayList<>();
        State<Climber> initialState = new State<Climber>("Stopped"){
        
            @Override
            protected void onUpdate(Robot r, Climber c) {}
        
            @Override
            protected void onEnter(Robot r, Climber c) {
                c.elevatorMotor.set(ControlMode.Velocity, 0.0);
            }
        
            @Override
            protected Optional<String> checkTransition(Robot r, Climber c) {
                // TODO Transition if the button is pressed!
                return Optional.empty();
            }
        };
    }
}