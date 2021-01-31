package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShootingSubsystem;
import frc.robot.commands.ShootingCommand;

import java.util.Set;

public class ConveyorCommand extends CommandBase {
    private ConveyorSubsystem conveyorSubsystem;
    public final Set<Subsystem> subsystems;

    public ConveyorCommand(ConveyorSubsystem conveyorSubsystem) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.subsystems = Set.of(conveyorSubsystem);
    }

    public void execute() {

        double shooterEncoderRate = conveyorSubsystem.shooterEncoder.getRate();

        if(RobotContainer.xboxController.getStickButtonPressed(GenericHID.Hand.kRight))
        {
            conveyorSubsystem.conveyBall(-1/1.1);
        }

        else {
            if (RobotContainer.xboxController.getTriggerAxis(GenericHID.Hand.kLeft) > 0
                    && RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight)
                    && shooterEncoderRate < ShootingCommand.rateSpeed) {
                conveyorSubsystem.conveyBall(0.0);
            } else {
                if (ConveyorSubsystem.conveyorButton.get() == false) {
                    conveyorSubsystem.conveyBall(1 / 1.1);
                } else {
                    conveyorSubsystem.conveyBall(RobotContainer.xboxController.getTriggerAxis(GenericHID.Hand.kLeft));
                }
            }
        }
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