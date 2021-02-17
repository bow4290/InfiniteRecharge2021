package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;


public class TurnAngleCommand extends CommandBase {

    private final DriveTrainSubsystem driveTrainSubsystem;
    private double degreesToTurn;
    private double speedToDriveLeft;
    private double speedToDriveRight;
    private double turnError;
    private double turnCorrection;
    private double turnSpeedRatio;

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
        turnError = degreesToTurn - driveTrainSubsystem.driveGyro.getAngle();
        turnCorrection = Constants.turnkP * turnError;
        speedToDriveLeft = turnCorrection;
        speedToDriveRight = -turnCorrection;

        if (speedToDriveLeft > Constants.autoTurnSpeed){
            speedToDriveLeft = Constants.autoTurnSpeed;
        }
        if (speedToDriveLeft < -Constants.autoTurnSpeed){
            speedToDriveLeft = -Constants.autoTurnSpeed;
        }
        if (speedToDriveRight > Constants.autoTurnSpeed){
            speedToDriveRight = Constants.autoTurnSpeed;
        }
        if (speedToDriveRight < -Constants.autoTurnSpeed){
            speedToDriveRight = -Constants.autoTurnSpeed;
        }
        driveTrainSubsystem.drive(speedToDriveLeft, speedToDriveRight);
    }

    @Override
    public boolean isFinished() {
        //if(turnError <= 1 && turnError >= 1 && driveTrainSubsystem.driveGyro.getRate() <= 2){}
        //else{
            return false;
        //}
    }

    @Override
    public void end(boolean interrupted) {
        driveTrainSubsystem.drive(0, 0);
        System.out.println("Done with auto turn command.");
    }
}
