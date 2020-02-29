package frc.robot.dataStructures;

import java.util.Set;

public class RotationData {
    private double degrees;
    private double distance;

    public RotationData(double degrees, double distance) {
        this.degrees = degrees;
        this.distance = distance;
    }

    public double getDegrees() {
        return degrees;
    }

    public double getDistance() {
        return distance;
    }


}
