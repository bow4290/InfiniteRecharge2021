package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;

import java.util.List;
import java.util.Set;

public class VictorSPDriveCommand extends CommandBase {

    private DriveTrainSubsystem driveTrainSubsystem;
    private final Set<Subsystem> subsystems;

    /**
     * this establishes the drivetrain subsystem
     * @param driveTrainSubsystem
     */

    public VictorSPDriveCommand(DriveTrainSubsystem driveTrainSubsystem) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.subsystems = Set.of(driveTrainSubsystem);
    }

    /**
     * this moves the sides of the robot in the direction of their assigned joystick
     */
    public void execute() {
        driveTrainSubsystem.drive(RobotContainer.joystickLeft.getY(), RobotContainer.joystickRight.getY());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
