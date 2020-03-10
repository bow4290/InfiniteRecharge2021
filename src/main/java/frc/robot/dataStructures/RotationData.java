package frc.robot.dataStructures;

import java.util.Set;

public class RotationData {
    private double degrees;
    private double distance;
    private double offset;

    public RotationData(double degrees, double distance, double offset) {
        this.degrees = degrees;
        this.distance = distance;
        this.offset = offset;
    }

    public double getDegrees() {
        return degrees;
    }

    public double getDistance() {
        return distance;
    }

    public double getOffset() {
        return offset;
    }


}
