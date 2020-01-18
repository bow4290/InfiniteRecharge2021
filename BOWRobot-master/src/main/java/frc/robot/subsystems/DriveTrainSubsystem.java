package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SparkDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {
    private final Spark leftSpark1;
//    private final Spark leftSpark2;
    private final Spark rightSpark1;
//    private final Spark rightSpark2;

    public DriveTrainSubsystem() {
        leftSpark1 = new Spark(1);
//        leftSpark2 = new Spark(0);
        rightSpark1 = new Spark(0);
//        rightSpark2 = new Spark(0);
        setDefaultCommand(new SparkDriveCommand(this));
    }

    /**
     * sets power of left and right speed controllers respectively.
     * @param leftspeed
     * @param rightspeed
     */
    public void drive(double leftspeed, double rightspeed) {
        System.out.println("Left value is "+leftspeed);
        System.out.println("Right value is "+rightspeed);
        leftSpark1.setSpeed(leftspeed);
//        leftSpark2.setSpeed(leftspeed);
        rightSpark1.setSpeed(rightspeed);
//        rightSpark2.setSpeed(rightspeed);
    }

}
