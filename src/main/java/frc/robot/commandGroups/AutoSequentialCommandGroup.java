package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoConditionalCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class AutoSequentialCommandGroup extends SequentialCommandGroup {

  AutoConditionalCommand autoConditionalCommand;

  public AutoSequentialCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall, ShootingSubsystem shootingSubsystem) {
    autoConditionalCommand = new AutoConditionalCommand(drive, conveyorSubsystem, intakeBall, shootingSubsystem);

    addCommands(
            new DriveForDistanceCommand(drive, intakeBall, 167), //Drive to B7 (169 inches minus 2 overshoot inches)
            autoConditionalCommand,
            autoConditionalCommand.returnPathCommand()
    );
  }
}