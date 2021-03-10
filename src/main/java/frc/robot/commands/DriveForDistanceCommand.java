package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
    private double lastDistanceError = 0;
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
        straightnessCorrection = Constants.straightkP*straightnessError;

        // Distance PID calculations
        distanceError = inchesToDrive - driveTrainSubsystem.driveTrainRightEncoder.getDistance();
        distanceDt = Timer.getFPGATimestamp() - lastTimestamp;
        
        // Integral Gain
        if(Math.abs(distanceError) < distanceRange){
            distanceSum += distanceError * distanceDt;
            } else{
                distanceSum = 0;
            }

        SmartDashboard.putNumber("Distance Error: ", distanceError);
        //SmartDashboard.putNumber("Straightness Correction: ", straightnessCorrection);

        // Derivative Gain
        distanceErrorRate = (distanceError - lastDistanceError) / distanceDt;
        
        // PID Gain
        distanceCorrection = Constants.distancekP*(distanceError) + Constants.distancekI*(distanceSum) + Constants.distancekD*(distanceErrorRate);
        //SmartDashboard.putNumber("Distance Correction: ", distanceCorrection);
        //SmartDashboard.putNumber("Distance P: ", Constants.distancekP*(distanceError));
        //SmartDashboard.putNumber("Distance I: ", Constants.distancekI*(distanceSum));
        //SmartDashboard.putNumber("Distance D: ", Constants.distancekD*(distanceErrorRate));
        

        // PID speed corrections
        correctedLeftMotorSpeed =  distanceCorrection - straightnessCorrection;
        correctedRightMotorSpeed = distanceCorrection + straightnessCorrection;
        motorSpeedRatio = correctedLeftMotorSpeed/correctedRightMotorSpeed;
        //SmartDashboard.putNumber("Corrected Left Speed: ", correctedLeftMotorSpeed);
        
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

        driveTrainSubsystem.drive(-correctedLeftMotorSpeed, -correctedRightMotorSpeed);
        // added negatives because joysticks give reverse values and drivetrain subsystem
        // is adjusted for joysticks to work as they incorrectly do

        lastTimestamp = Timer.getFPGATimestamp();
        lastDistanceError = distanceError;
    }

    @Override
    public boolean isFinished() {
        return ((-0.001 <= Constants.distancekD*(distanceErrorRate) && 0.001 >= Constants.distancekD*(distanceErrorRate)) &&
                (inchesToDrive - 5) <= driveTrainSubsystem.driveTrainRightEncoder.getDistance() &&
                (inchesToDrive + 5) >= driveTrainSubsystem.driveTrainRightEncoder.getDistance());
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        Timer.delay(1);
        intakeSubsystem.intakeBall(0);
        System.out.println("Done with auto drive command.");
    }
}