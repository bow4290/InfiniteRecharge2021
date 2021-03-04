package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoShootingCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.ShootingCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class AutoAwardVideoGroupCommandGroup extends SequentialCommandGroup {
    public AutoAwardVideoGroupCommandGroup(DriveTrainSubsystem driveTrainSubsystem, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeSubsystem, ShootingSubsystem shootingSubsystem) {
        addCommands(
                new DriveForDistanceCommand(driveTrainSubsystem, intakeSubsystem, 12 * 19.5),
                new AutoShootingCommand(shootingSubsystem, conveyorSubsystem),
                new TurnAngleCommand(driveTrainSubsystem, -135),
                new DriveForDistanceCommand(driveTrainSubsystem, intakeSubsystem, 12 * 5),
                new TurnAngleCommand(driveTrainSubsystem, 120),
                new AutoShootingCommand(shootingSubsystem, conveyorSubsystem)
        );
    }
}