package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses PID (through encoder and gyro) to drive forward a distance
 */

public class DriveForDistanceCommand extends CommandBase {

    private final DriveTrainSubsystem driveTrainSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private double inchesToDrive;
    private double straightnessError;
    private double straightnessCorrection;
    private double distanceError;
    private double distanceCorrection;
    private double distanceRange = 12;
    private double distanceSum;
    private double distanceDt;
    private double distanceErrorRate;
    private double lastDistanceError;
    private double lastTimestamp;
    private double motorSpeedRatio;
    private double correctedLeftMotorSpeed;
    private double correctedRightMotorSpeed;


    public DriveForDistanceCommand(DriveTrainSubsystem driveTrainSubsystem, IntakeSubsystem intakeSubsystem, double inchesToDrive) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.inchesToDrive = -inchesToDrive;
    }

    @Override
    public void initialize() {
        driveTrainSubsystem.driveGyro.reset();
        driveTrainSubsystem.driveTrainRightEncoder.reset();
    }

    @Override
    public void execute() {
        intakeSubsystem.intakeBall(1);

        // Straightness PID calculations
        straightnessError = driveTrainSubsystem.driveGyro.getAngle();

        if (Math.abs(distanceError) >= 12) {
            straightnessCorrection = Constants.straightkP * straightnessError;
        } else {
            straightnessCorrection = 0;
        }

        // Distance PID calculations
        distanceError = inchesToDrive - driveTrainSubsystem.driveTrainRightEncoder.getDistance();
        SmartDashboard.putNumber("Distance Error: ", distanceError);
        distanceDt = Timer.getFPGATimestamp() - lastTimestamp;

        // Integral Gain
        if (Math.abs(distanceError) < distanceRange) {
            distanceSum += distanceError * distanceDt;
        } else {
            distanceSum = 0;
        }

        // Derivative Gain
        distanceErrorRate = (distanceError - lastDistanceError) / distanceDt;

        distanceCorrection = Constants.distancekP * (distanceError) + Constants.distancekI * (distanceSum) + Constants.distancekD * (distanceErrorRate);


        // PID speed corrections
        correctedLeftMotorSpeed = distanceCorrection - straightnessCorrection;
        correctedRightMotorSpeed = distanceCorrection + straightnessCorrection;
        motorSpeedRatio = correctedLeftMotorSpeed / correctedRightMotorSpeed;

        // Saturate motor speed to auto speed
        if (correctedLeftMotorSpeed > Constants.autoDriveSpeed) {
            correctedLeftMotorSpeed = Constants.autoDriveSpeed;
        }
        if (correctedLeftMotorSpeed < -Constants.autoDriveSpeed) {
            correctedLeftMotorSpeed = -Constants.autoDriveSpeed;
        }
        if (correctedRightMotorSpeed > Constants.autoDriveSpeed) {
            correctedRightMotorSpeed = Constants.autoDriveSpeed;
        }
        if (correctedRightMotorSpeed < -Constants.autoDriveSpeed) {
            correctedRightMotorSpeed = -Constants.autoDriveSpeed;
        }

        //Maintains speed ratio if motor speeds are saturated
        correctedRightMotorSpeed = correctedRightMotorSpeed / motorSpeedRatio;

        // Added negatives because joysticks are inverted
        driveTrainSubsystem.drive(-correctedLeftMotorSpeed, -correctedRightMotorSpeed);

        lastTimestamp = Timer.getFPGATimestamp();
        lastDistanceError = distanceError;
    }

    @Override
    public boolean isFinished() { //Finishes when our error is +- 5 inches and the derivative is low.
        return (((-0.001 <= Constants.distancekD * (distanceErrorRate) && 0.001 >= Constants.distancekD * (distanceErrorRate)) &&
                (inchesToDrive - 5) <= driveTrainSubsystem.driveTrainRightEncoder.getDistance() &&
                (inchesToDrive + 5) >= driveTrainSubsystem.driveTrainRightEncoder.getDistance()));
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        intakeSubsystem.intakeBall(0);
        System.out.println("Done with auto drive command.");
    }
}