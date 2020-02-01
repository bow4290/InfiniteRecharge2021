package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SparkDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {
    private final Spark leftSpark1;
    private final Spark leftSpark2;
    private final Spark leftSpark3;
    private final Spark rightSpark1;
    private final Spark rightSpark2;
    private final Spark rightSpark3;

    public DriveTrainSubsystem(int leftSpark1Channel, int leftSpark2Channel, int leftSpark3Channel,
                               int rightSpark1Channel, int rightSpark2Channel, int rightSpark3Channel) {
        leftSpark1 = new Spark(leftSpark1Channel);
        leftSpark2 = new Spark(leftSpark2Channel);
        leftSpark3 = new Spark(leftSpark3Channel);
        rightSpark1 = new Spark(rightSpark1Channel);
        rightSpark2 = new Spark(rightSpark2Channel);
        rightSpark3 = new Spark(rightSpark3Channel);

      setDefaultCommand(new SparkDriveCommand(this));
    }

    /**
     * sets power of left and right speed controllers respectively.
     * @param leftspeed
     * @param rightspeed
     */
    public void drive(double leftspeed, double rightspeed) {
//        System.out.println("Left value is "+leftspeed);
//        System.out.println("Right value is "+rightspeed);
        leftSpark1.setSpeed(leftspeed);
        leftSpark2.setSpeed(leftspeed);
        leftSpark3.setSpeed(leftspeed);
        rightSpark1.setSpeed(rightspeed);
        rightSpark2.setSpeed(rightspeed);
        rightSpark3.setSpeed(rightspeed);

    }

}
