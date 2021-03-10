package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoShootingCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class AutoSequentialCommandGroup extends SequentialCommandGroup {

  public AutoSequentialCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall, ShootingSubsystem shootingSubsystem) {
      
    addCommands(
      new DriveForDistanceCommand(drive, intakeBall, 17.5 * 12) //Drive to B7
    );

    if (ConveyorCommand.ballCount == 2){ //Red Path B
      addCommands(
        new TurnAngleCommand(drive, Math.atan(5 / 3) + 90),
        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50)),
        new TurnAngleCommand(drive, -(Math.atan(5 / 3) + 90)),
        new DriveForDistanceCommand(drive, intakeBall, 16 * 12)
      );
    }
    else if (ConveyorCommand.ballCount == 1){ //Blue Path A
      addCommands(
        new TurnAngleCommand(drive, Math.atan(2.5 / 7.5) + 90),
        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5 , 2) + Math.pow(7.5 , 2))),
        new TurnAngleCommand(drive, ((-(180 - Math.atan(7.5 / 2.5)) - Math.atan(5 / 7.5)))),
        new DriveForDistanceCommand(drive, intakeBall,
            Math.sqrt(Math.pow(5 , 2) + Math.pow(7.5 , 2)) + Math.sqrt(Math.pow(2.5 , 2) + Math.pow(5 , 2)))
      );
    }
    else{
      addCommands(
        new DriveForDistanceCommand(drive, intakeBall, 2.5 * 12)
      );
      if(ConveyorCommand.ballCount == 1){ //Blue Path B
        addCommands(
          new TurnAngleCommand(drive, Math.atan(5 / 5)),
          new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50)),
          new TurnAngleCommand(drive, -(Math.atan(5 / 5) + 90)),
          new DriveForDistanceCommand(drive, intakeBall, 13.5 * 12)
        );
      }
      else{ //Red Path A

      }
    }
  }
}