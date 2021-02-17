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
    private double distanceErrorRate;
    private double lastDistanceError = 0;
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
        
        // Integral Gain
        distanceSum += distanceError * distanceDt;
        SmartDashboard.putNumber("Distance Error: ", distanceError);

        // Derivative Gain
        distanceErrorRate = (distanceError - lastDistanceError) / distanceDt;
        
        // PID Gain
        distanceCorrection = Constants.distancekP*(distanceError) + Constants.distancekI*(distanceSum) + Constants.distancekD*(distanceErrorRate);
        SmartDashboard.putNumber("Distance Correction: ", distanceCorrection);
        SmartDashboard.putNumber("Distance P: ", Constants.distancekP*(distanceError));
        SmartDashboard.putNumber("Distance I: ", Constants.distancekI*(distanceSum));
        SmartDashboard.putNumber("Distance D: ", Constants.distancekD*(distanceErrorRate));
        

        // PID speed corrections
        correctedLeftMotorSpeed =  distanceCorrection - straightnessCorrection;
        correctedRightMotorSpeed = distanceCorrection + straightnessCorrection;
        motorSpeedRatio = correctedLeftMotorSpeed/correctedRightMotorSpeed;
        SmartDashboard.putNumber("Corrected Left Speed: ", correctedLeftMotorSpeed);
        
        // Saturate motor speed if higher than max auto speed
        if (correctedLeftMotorSpeed > Constants.autoDriveSpeed){
            correctedLeftMotorSpeed = Constants.autoDriveSpeed;
        }
        if (correctedLeftMotorSpeed < -Constants.autoDriveSpeed){
            correctedLeftMotorSpeed = -Constants.autoDriveSpeed;
        }
        if (correctedRightMotorSpeed > Constants.autoDriveSpeed){
            correctedRightMotorSpeed = Constants.autoDriveSpeed;
        }
        if (correctedRightMotorSpeed < -Constants.autoDriveSpeed){
            correctedRightMotorSpeed = -Constants.autoDriveSpeed;
        }
        correctedRightMotorSpeed = correctedRightMotorSpeed/motorSpeedRatio;

        // Command motors to move with a speed
        driveTrainSubsystem.drive(-correctedLeftMotorSpeed, -correctedRightMotorSpeed);
        // added negatives because joysticks give reverse values and drivetrain subsystem
        // is adjusted for joysticks to work as they incorrectly do

        lastTimestamp = Timer.getFPGATimestamp();
        lastDistanceError = distanceError;
    }

    @Override
    public boolean isFinished() {
        return ((-0.001 <= Constants.distancekD*(distanceErrorRate) && 0.001 >= Constants.distancekD*(distanceErrorRate)) &&
                (-0.08 <= Constants.distancekP*(distanceError) && 0.08 >= Constants.distancekP*(distanceError)) &&
                (inchesToDrive - 5) <= driveTrainSubsystem.driveTrainRightEncoder.getDistance() &&
                (inchesToDrive + 5) >= driveTrainSubsystem.driveTrainRightEncoder.getDistance());
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        System.out.println("Done with auto drive command.");
    }
}