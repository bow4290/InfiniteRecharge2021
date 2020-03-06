package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberSubsystem;

import java.util.Set;

public class ClimberCommand extends CommandBase {
    private ClimberSubsystem climberSubsystem;
    public final Set<Subsystem> subsystems;

    /**
     * establishes the intake  subsystem
     *
     * @param climberSubsystem
     */
    public ClimberCommand(ClimberSubsystem climberSubsystem) {
        this.climberSubsystem = climberSubsystem;
        this.subsystems = Set.of(climberSubsystem);
    }

    /**
     * this makes the intake activate when left joystick is move
     */
    public void execute() {
        climberSubsystem.climb(RobotContainer.xboxController.getYButton(), RobotContainer.xboxController.getXButton());
        climberSubsystem.stopClimber(RobotContainer.xboxController.getAButtonPressed());
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

