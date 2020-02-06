package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    private Joystick joystick = new Joystick(RobotMap.JOYSTICK_MAIN);

    public Joystick getJoystick() {
        return joystick;
    }
}