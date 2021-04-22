package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;

/**
 * Uses Gyro to turn the robot a certain angle.
 */

public class TurnAngleCommand extends CommandBase {

    private final DriveTrainSubsystem driveTrainSubsystem;
    private double degreesToTurn;
    private double speedToDriveLeft;
    private double speedToDriveRight;
    private double turnError;
    private double turnSum;
    private double turnRange = 5;
    private double turnErrorRate;
    private double lastTurnError;
    private double lastTimestamp;
    private double turnDt;
    private double turnCorrection;

    public TurnAngleCommand(DriveTrainSubsystem driveTrainSubsystem, double degreesToTurn) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.degreesToTurn = degreesToTurn;
    }

    @Override
    public void initialize() {
        driveTrainSubsystem.driveGyro.reset();
    }

    @Override
    public void execute() {
        //Turn PID calculations
        turnError = degreesToTurn - driveTrainSubsystem.driveGyro.getAngle();
        SmartDashboard.putNumber("Turn Error: ", turnError);
        turnDt = Timer.getFPGATimestamp() - lastTimestamp;

        //Creates a range for integral calculations.
        if (Math.abs(turnError) < turnRange) {
            turnSum += turnError * turnDt;
        } else {
            turnSum = 0;
        }

        turnErrorRate = (turnError - lastTurnError) / turnDt;
        turnCorrection = (Constants.turnkP * turnError) + (Constants.turnkI * turnSum) + (Constants.turnkD * turnErrorRate);

        // Turn speed corrections
        speedToDriveLeft = -turnCorrection;
        speedToDriveRight = turnCorrection;

        // Saturate motor speed to auto speed
        if (speedToDriveLeft > Constants.autoTurnSpeed) {
            speedToDriveLeft = Constants.autoTurnSpeed;
        }
        if (speedToDriveLeft < -Constants.autoTurnSpeed) {
            speedToDriveLeft = -Constants.autoTurnSpeed;
        }
        if (speedToDriveRight > Constants.autoTurnSpeed) {
            speedToDriveRight = Constants.autoTurnSpeed;
        }
        if (speedToDriveRight < -Constants.autoTurnSpeed) {
            speedToDriveRight = -Constants.autoTurnSpeed;
        }
        driveTrainSubsystem.drive(speedToDriveLeft, speedToDriveRight);

        lastTimestamp = Timer.getFPGATimestamp();
        lastTurnError = turnError;
    }

    @Override
    public boolean isFinished() { // Finishes when our turn error is within =-0.5 degrees and the derivative is low.
        return (turnError <= 0.5 && turnError >= -0.5 &&
                driveTrainSubsystem.driveGyro.getRate() <= 1 && driveTrainSubsystem.driveGyro.getRate() >= -1);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        System.out.println("Done with auto turn command.");
    }
}