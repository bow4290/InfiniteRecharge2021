package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Set;

public class IntakeCommand extends CommandBase{
    private IntakeSubsystem intakeSubsystem;
    public final Set<Subsystem> subsystems;

    private IntakeCommand(IntakeSubsystem intakeSubsystem){
        this.intakeSubsystem = intakeSubsystem;
        this.subsystems = Set.of(intakeSubsystem);
    }

    public void execute() {
        intakeSubsystem.eatBall(RobotContainer.joystickLeft.getY());
    }

    @Override
    public boolean isFinished() {return false;}

    @Override
    public Set<Subsystem> getRequirements() {return this.subsystems;}
}