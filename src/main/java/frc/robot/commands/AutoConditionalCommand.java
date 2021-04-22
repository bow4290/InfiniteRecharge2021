package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

/**
 * Determined what path the robot was on and added commands based on that.
 */

public class AutoConditionalCommand extends CommandBase {

    public SequentialCommandGroup pathCommands = new SequentialCommandGroup();
    DriveTrainSubsystem drive;
    ConveyorSubsystem conveyorSubsystem;
    IntakeSubsystem intakeBall;
    ShootingSubsystem shootingSubsystem;
    private boolean isDone = false;

    public AutoConditionalCommand(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem,
                                  IntakeSubsystem intakeBall, ShootingSubsystem shootingSubsystem) {
        this.drive = drive;
        this.conveyorSubsystem = conveyorSubsystem;
        this.intakeBall = intakeBall;
        this.shootingSubsystem = shootingSubsystem;
    }

    @Override
    public void execute() {

        if (ConveyorCommand.ballCount == 2) { //Red Path B
            System.out.println("Path Red B");
            pathCommands.addCommands(
                    new TurnAngleCommand(drive, Math.toDegrees(Math.atan(5 / 5)) + 90),         // Right turn
                    new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50) * 12),              // Drive to D5
                    new TurnAngleCommand(drive, -(Math.toDegrees(Math.atan(5 / 5)) + 90)),     // Left Turn
                    new DriveForDistanceCommand(drive, intakeBall, 16 * 12)         // Drive to End
            );
        } else if (ConveyorCommand.ballCount == 1) { //Blue Path A
            System.out.println("Path Blue A");
            pathCommands.addCommands(
                    new TurnAngleCommand(drive, Math.toDegrees(Math.atan(2.5 / 7.5)) + 90 - 3),                                           // Right Turn
                    new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5, 2) + Math.pow(7.5, 2)) * 12),                 // Drive to E6
                    new TurnAngleCommand(drive, Math.toDegrees(-Math.atan(2.5 / 7.5) - (Math.PI / 2) - Math.atan(5 / 7.5)) - 5),              // Left Turn
                    new DriveForDistanceCommand(drive, intakeBall,                                                                    // Drive Through C9 to End
                            (Math.sqrt(Math.pow(5, 2) + Math.pow(7.5, 2)) + Math.sqrt(Math.pow(2.5, 2) + Math.pow(5, 2))) * 12)
            );
        } else {
            pathCommands.addCommands(
                    new DriveForDistanceCommand(drive, intakeBall, 2.5 * 12) // Drive to B8
            );
            if (ConveyorCommand.ballCount == 1) { //Blue Path B
                System.out.println("Path Blue B");
                pathCommands.addCommands(
                        new TurnAngleCommand(drive, Math.toDegrees(Math.atan(5 / 5))),             // Right Turn
                        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50) * 12),             // Drive to D6
                        new TurnAngleCommand(drive, (Math.toDegrees(Math.atan(5 / 5)) + 90)),     // Left Turn
                        new DriveForDistanceCommand(drive, intakeBall, 13.5 * 12)       // Drive Through D10 to End
                );
            } else { //Red Path A
                System.out.println("Path Red A");
                pathCommands.addCommands(
                        new TurnAngleCommand(drive, -(180 - Math.toDegrees(Math.atan(2.5 / 5))) - 3),                              // Turn Left
                        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5, 2) + Math.pow(5, 2)) * 12),           // Drive to A6
                        new TurnAngleCommand(drive, -Math.toDegrees(Math.atan(2.5 / 5)) - Math.toDegrees(Math.atan(5 / 7.5))),                 // Turn Left
                        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(5, 2) + Math.pow(7.5, 2)) * 12),          // Drive to C3
                        new TurnAngleCommand(drive, -Math.toDegrees(Math.atan(7.5 / 5)) - Math.toDegrees(Math.atan(5 / 2.5)) - 5),                 // Turn Left
                        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5, 2) + Math.pow(5, 2)) * 12),          // Drive to D5
                        new TurnAngleCommand(drive, -Math.toDegrees(Math.atan(2.5 / 5)) - 10),                                      // Turn Left
                        new DriveForDistanceCommand(drive, intakeBall, 16 * 12)                                     // Drive to End
                );
            }
        }
        isDone = true;
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

    public SequentialCommandGroup returnPathCommand() {
        System.out.println("RETURNING PATH COMMANDS");
        return pathCommands;
    }

}