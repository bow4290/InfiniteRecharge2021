package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveForDistanceCommand extends CommandBase {
    
    private final DriveTrainSubsystem driveTrainSubsystem;
    private double inchesToDrive;
    private double driveOffset;
    private double straightnessError;
    private double straightnessCorrection;
    private double distanceError;
    private double distanceCorrection;
    private double distanceSum;
    private double distanceDt;
    private double lastTimestamp;
    private double motorSpeedRatio;
    private double correctedLeftMotorSpeed;
    private double correctedRightMotorSpeed;

    public DriveForDistanceCommand(DriveTrainSubsystem driveTrainSubsystem, double inchesToDrive, double driveOffset) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.inchesToDrive = inchesToDrive;
        this.driveOffset = driveOffset;
    }

    @Override
    public void initialize() {
        driveTrainSubsystem.driveTrainLeftEncoder.reset();
        driveTrainSubsystem.driveTrainRightEncoder.reset();
    }

    @Override
    public void execute() {
        // Straightness PID calculations
        straightnessError = driveTrainSubsystem.driveTrainLeftEncoder.getDistance() - driveTrainSubsystem.driveTrainRightEncoder.getDistance();
        straightnessCorrection = Constants.straightkP*(straightnessError - driveOffset);

        // Distance PID calculations
        distanceError = inchesToDrive - driveTrainSubsystem.driveTrainRightEncoder.getDistance();
        distanceDt = Timer.getFPGATimestamp() - lastTimestamp;
        distanceSum = distanceError * distanceDt;
        SmartDashboard.putNumber("Distance Error: ", distanceSum);
        distanceCorrection = Constants.distancekP*(distanceError) + Constants.distancekD*(distanceSum);

        // PID speed corrections
        correctedLeftMotorSpeed =  distanceCorrection - straightnessCorrection;
        correctedRightMotorSpeed = distanceCorrection + straightnessCorrection;
        motorSpeedRatio = correctedLeftMotorSpeed/correctedRightMotorSpeed;
        
        // Saturate motor speed if higher than max auto speed
        if (correctedLeftMotorSpeed > Constants.autoSpeed){
            correctedLeftMotorSpeed = Constants.autoSpeed;
        }
        if (correctedLeftMotorSpeed < -Constants.autoSpeed){
            correctedLeftMotorSpeed = -Constants.autoSpeed;
        }
        if (correctedRightMotorSpeed > Constants.autoSpeed){
            correctedRightMotorSpeed = Constants.autoSpeed;
        }
        if (correctedRightMotorSpeed < -Constants.autoSpeed){
            correctedRightMotorSpeed = -Constants.autoSpeed;
        }
        correctedRightMotorSpeed = correctedRightMotorSpeed/motorSpeedRatio;

        // Command motors to move with a speed
        driveTrainSubsystem.drive(-correctedLeftMotorSpeed, -correctedRightMotorSpeed);
        // added negatives because joysticks give reverse values and drivetrain subsystem
        // is adjusted for joysticks to work as they incorrectly do

        lastTimestamp = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {          // if robot rolls past target, add derivative gain. End when derivative error is low
        return ((inchesToDrive - 1) <= driveTrainSubsystem.driveTrainLeftEncoder.getDistance() &&
                (inchesToDrive + 1) >= driveTrainSubsystem.driveTrainLeftEncoder.getDistance() &&
                (inchesToDrive - 1) <= driveTrainSubsystem.driveTrainRightEncoder.getDistance() &&
                (inchesToDrive + 1) >= driveTrainSubsystem.driveTrainRightEncoder.getDistance());
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        System.out.println("Done with auto command.");
    }
}