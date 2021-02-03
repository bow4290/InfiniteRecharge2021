package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberSubsystem;

import java.util.Set;

public class ClimberCommand extends CommandBase {
    private ClimberSubsystem climberSubsystem;
    public final Set<Subsystem> subsystems;

    public ClimberCommand(ClimberSubsystem climberSubsystem) {
        this.climberSubsystem = climberSubsystem;
        this.subsystems = Set.of(climberSubsystem);
    }

    public void execute() {
        climberSubsystem.climb(RobotContainer.xboxController.getYButton(), RobotContainer.xboxController.getXButton());
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

