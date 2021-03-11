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

  AutoSequentialCommandGroup autoSequentialCommandGroup;

  public AutoCommandGroup(DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, ShootingSubsystem shootingSubsystem) {
    autoSequentialCommandGroup = new AutoSequentialCommandGroup(driveTrainSubsystem, conveyorSubsystem, intakeSubsystem, shootingSubsystem);
    
    addCommands(
      new ConveyorCommand(conveyorSubsystem),
      autoSequentialCommandGroup
    );
  }

  public void clearPath(){
    autoSequentialCommandGroup.clearPath1();
  }
}
