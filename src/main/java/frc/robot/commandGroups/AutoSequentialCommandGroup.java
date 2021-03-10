package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoShootingCommand;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.DriveForDistanceCommand;
import frc.robot.commands.TurnAngleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

import java.sql.SQLOutput;

public class AutoSequentialCommandGroup extends SequentialCommandGroup {

  public AutoSequentialCommandGroup(DriveTrainSubsystem drive, ConveyorSubsystem conveyorSubsystem, IntakeSubsystem intakeBall, ShootingSubsystem shootingSubsystem) {
      
    addCommands(
      new DriveForDistanceCommand(drive, intakeBall, 17.5 * 12) //Drive to B7
    );

    if (ConveyorCommand.ballCount == 2){ //Red Path B
      System.out.println("Path Red B");
      addCommands(
        new TurnAngleCommand(drive, Math.toDegrees(Math.atan(5 / 3) + 90)),         // Right turn
        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50)),              // Drive to D5
        new TurnAngleCommand(drive, Math.toDegrees(-(Math.atan(5 / 3) + 90))),     // Left Turn
        new DriveForDistanceCommand(drive, intakeBall, 16 * 12)         // Drive to End
      );
    }
    else if (ConveyorCommand.ballCount == 1){ //Blue Path A
      System.out.println("Path Blue A");
      addCommands(
        new TurnAngleCommand(drive, Math.toDegrees(Math.atan(2.5 / 7.5) + 90)),                                           // Right Turn
        new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5 , 2) + Math.pow(7.5 , 2))),                 // Drive to E6
        new TurnAngleCommand(drive, Math.toDegrees(((-(180 - Math.atan(7.5 / 2.5)) - Math.atan(5 / 7.5))))),              // Left Turn
        new DriveForDistanceCommand(drive, intakeBall,                                                                    // Drive Through C9 to End
            Math.sqrt(Math.pow(5 , 2) + Math.pow(7.5 , 2)) + Math.sqrt(Math.pow(2.5 , 2) + Math.pow(5 , 2)))
      );
    }
    else{
      addCommands(
        new DriveForDistanceCommand(drive, intakeBall, 2.5 * 12) // Drive to B8
      );
      if(ConveyorCommand.ballCount == 1){ //Blue Path B
        System.out.println("Path Blue B");
        addCommands(
          new TurnAngleCommand(drive, Math.toDegrees(Math.atan(5 / 5))),             // Right Turn
          new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(50)),             // Drive to D6
          new TurnAngleCommand(drive, Math.toDegrees(-(Math.atan(5 / 5) + 90))),     // Left Turn
          new DriveForDistanceCommand(drive, intakeBall, 13.5 * 12)       // Drive Through D10 to End
        );
      }
      else{ //Red Path A
        System.out.println("Path Red A");
        addCommands(
          new TurnAngleCommand(drive, Math.toDegrees(-(180 - Math.atan(2.5 / 5)))),                              // Turn Left
          new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5, 2) + Math.pow(5, 2))),           // Drive to A6
          new TurnAngleCommand(drive, Math.toDegrees(-Math.atan(2.5 / 5) - Math.atan(5 / 7.5))),                 // Turn Left
          new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(5, 2) + Math.pow(7.5, 2))),          // Drive to C3
          new TurnAngleCommand(drive, Math.toDegrees(-Math.atan(7.5 / 5) - Math.atan(5 / 2.5))),                 // Turn Left
          new DriveForDistanceCommand(drive, intakeBall, Math.sqrt(Math.pow(2.5, 2) + Math.pow(5, 2))),          // Drive to D5
          new TurnAngleCommand(drive, Math.toDegrees(-Math.atan(2.5 / 5))),                                      // Turn Left
          new DriveForDistanceCommand(drive, intakeBall, 16* 12)                                     // Drive to End
        );
      }
    }
  }
}