package frc.robot.utils;

public class Utils {

    public static double clamp(double input, double min, double max) {
        if (input > max) {
            return max;
        }
        if (input < min) {
            return min;
        }
        return input;
    }
}