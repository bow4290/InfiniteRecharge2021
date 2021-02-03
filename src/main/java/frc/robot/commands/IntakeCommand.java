package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;

import java.util.Set;

public class IntakeCommand extends CommandBase {
    private IntakeSubsystem intakeSubsystem;
    public final Set<Subsystem> subsystems;

    public IntakeCommand(IntakeSubsystem intakeSubsystem){
        this.intakeSubsystem = intakeSubsystem;
        this.subsystems = Set.of(intakeSubsystem);
    }

    public void execute() {
        intakeSubsystem.intakeBall(RobotContainer.xboxController.getTriggerAxis(GenericHID.Hand.kRight));
        intakeSubsystem.liftIntake(RobotContainer.xboxController.getBButtonPressed());
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