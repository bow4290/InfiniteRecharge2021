package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveForDistanceCommand extends CommandBase {
    
    private final DriveTrainSubsystem driveTrainSubsystem;
    private double inchesToDrive;
    private double driveOffset;
    private double straightnessError;
    private double straightnessCorrection;
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
        straightnessError = driveTrainSubsystem.driveTrainLeftEncoder.getDistance() - driveTrainSubsystem.driveTrainRightEncoder.getDistance();
        straightnessCorrection = Constants.kP*(straightnessError - driveOffset);
        correctedLeftMotorSpeed = Constants.autoSpeed - straightnessCorrection;
        correctedRightMotorSpeed = Constants.autoSpeed + straightnessCorrection;

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
        
        driveTrainSubsystem.drive(-correctedLeftMotorSpeed, -correctedRightMotorSpeed);
    }

    @Override
    public boolean isFinished() {
        return (driveTrainSubsystem.driveTrainLeftEncoder.getDistance() > inchesToDrive || driveTrainSubsystem.driveTrainRightEncoder.getDistance() > inchesToDrive);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        System.out.println("Done with auto command.");
    }
}