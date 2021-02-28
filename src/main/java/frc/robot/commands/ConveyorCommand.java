package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
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

        double shooterEncoderRate = Robot.shooterEncoder.getRate();

        if(RobotContainer.xboxController.getStickButton(GenericHID.Hand.kLeft) &&
          (ConveyorSubsystem.conveyorButton1.get() == true) &&
          (ConveyorSubsystem.conveyorButton2.get() == true))
    {
            conveyorSubsystem.conveyBall(-0.25);
    }

        else
    {

            if(RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight)
                    && shooterEncoderRate >= ShootingCommand.rateSpeed - 10000)
            {
                conveyorSubsystem.conveyBall(1 / 1.1);
            }
            else
            {
                if ((ConveyorSubsystem.conveyorButton1.get() == false) || (ConveyorSubsystem.conveyorButton2.get() == false))
                    conveyorSubsystem.conveyBall(0.75 / 1.1);
                else
                    conveyorSubsystem.conveyBall(0);
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