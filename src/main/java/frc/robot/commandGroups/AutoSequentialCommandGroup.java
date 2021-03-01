package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoSequentialCommandGroup extends SequentialCommandGroup {

  public AutoSequentialCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall) {
      
    addCommands(
      new DriveForDistanceCommand(drive, intakeBall, 60)
    );

    if (ConveyorCommand.ballCount == 2){
      addCommands(
        new TurnAngleCommand(drive, -90) // turn left
      );
    }
    else if (ConveyorCommand.ballCount == 1){
      addCommands(
        new TurnAngleCommand(drive, 90) // turn right
      );
    }
    else{
      addCommands(
        new DriveForDistanceCommand(drive, intakeBall, -30) // drive backwards
      );
    }
  }
}