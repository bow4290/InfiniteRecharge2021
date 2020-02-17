package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import java.util.Set;

public class ConveyorCommand extends CommandBase {
    private ConveyorSubsystem conveyorSubsystem;
    public final Set<Subsystem> subsystems;

    public ConveyorCommand(ConveyorSubsystem conveyorSubsystem) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.subsystems = Set.of(conveyorSubsystem);
    }
    public void execute() {
        conveyorSubsystem.conveyBall(RobotContainer.xboxController.getTriggerAxis(GenericHID.Hand.kLeft));
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