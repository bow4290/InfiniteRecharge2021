package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SparkDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {
    private final Spark leftSpark1;
    private final Spark leftSpark2;
    private final Spark rightSpark1;
    private final Spark rightSpark2;


    public void drive(double leftspeed) {
        System.out.println("Left value is "+leftspeed);
        leftSpark1.setSpeed(leftspeed);
        leftSpark2.setSpeed(leftspeed);
        rightSpark1.setSpeed(leftspeed);
        rightSpark2.setSpeed(leftspeed);
    }

    public DriveTrainSubsystem() {
        leftSpark1 = new Spark(0);
        leftSpark2 = new Spark(1);
        rightSpark1 = new Spark(2);
        rightSpark2 = new Spark(3);
        setDefaultCommand(new SparkDriveCommand(this));
    }
}
