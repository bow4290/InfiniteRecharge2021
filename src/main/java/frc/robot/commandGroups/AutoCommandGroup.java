package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoCommandGroup extends ParallelCommandGroup {

  public AutoCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall) {
    addCommands(
      new ConveyorCommand(conveyorSubsystem),
      new AutoSequentialCommandGroup(drive, conveyorSubsystem, intakeBall)
    );
  }
}
