package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.*;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class AutoCommandGroup extends ParallelCommandGroup {

        public AutoCommandGroup(DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, ShootingSubsystem shootingSubsystem) {
                addCommands(
                        new ConveyorCommand(conveyorSubsystem),
                        new AutoSequentialCommandGroup(driveTrainSubsystem, conveyorSubsystem, intakeSubsystem, shootingSubsystem)
                );
        }
}

