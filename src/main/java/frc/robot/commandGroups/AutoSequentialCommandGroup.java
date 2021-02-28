package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoSequentialCommandGroup extends SequentialCommandGroup {

  public AutoSequentialCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall) {
      
    addCommands(
      new DriveForDistanceCommand(drive, intakeBall, Constants.distanceCommand1Inches)
    );

    if (ConveyorSubsystem.ballCount == 2){
      addCommands(
        new TurnAngleCommand(drive, Constants.turnAngleCommand2Angle) // turn right
      );
    }
    else if (ConveyorSubsystem.ballCount == 1){
      addCommands(
        new TurnAngleCommand(drive, Constants.turnAngleCommand1Angle) // turn left
      );
    }
    else{
      addCommands(
        new DriveForDistanceCommand(drive, intakeBall, Constants.distanceCommand2Inches)
      );
    }
  }
}