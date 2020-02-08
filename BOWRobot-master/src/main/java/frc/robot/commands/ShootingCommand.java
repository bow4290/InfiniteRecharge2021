package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Set;

public class ShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private final Set<Subsystem> subsystems;

    public ShootingCommand(ShootingSubsystem shootingSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    public void execute() {
        shootingSubsystem.shootBall(RobotContainer.joystickLeft.getY());

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }

//    @Override
//    public void end(boolean interrupted) {
//        shootingSubsystem.shootBall(0);
//    }
}
