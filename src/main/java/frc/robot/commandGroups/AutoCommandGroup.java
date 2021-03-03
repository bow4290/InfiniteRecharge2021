package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class AutoCommandGroup extends ParallelCommandGroup {

  public AutoCommandGroup(DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, ShootingSubsystem shootingSubsystem, AutoShootingCommand autoShootingCommand) {
    addCommands(
      new ConveyorCommand(conveyorSubsystem, autoShootingCommand),
      //new AutoSequentialCommandGroup(driveTrainSubsystem, conveyorSubsystem, intakeSubsystem)

      //Auto award video commands:
      new AutoAwardVideoGroupCommandGroup(driveTrainSubsystem, conveyorSubsystem, intakeSubsystem, shootingSubsystem, autoShootingCommand)
    );
  }
}
