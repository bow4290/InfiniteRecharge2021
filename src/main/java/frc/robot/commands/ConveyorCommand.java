package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.commands.AutoShootingCommand;

import java.util.Set;

public class ConveyorCommand extends CommandBase {
    private ConveyorSubsystem conveyorSubsystem;
    public final Set<Subsystem> subsystems;
    public static int ballCount;
    public static boolean buttonState;
    public static boolean lastButtonState;
    private AutoShootingCommand autoShootingCommand;

    public ConveyorCommand(ConveyorSubsystem conveyorSubsystem) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.subsystems = Set.of(conveyorSubsystem);
    }

    public ConveyorCommand(ConveyorSubsystem conveyorSubsystem, AutoShootingCommand autoShootingCommand) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.subsystems = Set.of(conveyorSubsystem);
        this.autoShootingCommand = autoShootingCommand;
    }

    public void initialize() {
        lastButtonState = false;
    }

    public void execute() {

        double shooterEncoderRate = Robot.shooterEncoder.getRate();

        //If either button is pressed, button state = true and increment the ball count
        if (!ConveyorSubsystem.conveyorButton1.get() || !ConveyorSubsystem.conveyorButton2.get()) {
            buttonState = true;
        } else {
            buttonState = false;
        }
        if (buttonState == true && lastButtonState == false) {
            ballCount++;
        }

        lastButtonState = buttonState;

        //Conveyor moves backwards if neither conveyor button is touched.
        if (RobotContainer.xboxController.getStickButton(GenericHID.Hand.kLeft) &&
                (ConveyorSubsystem.conveyorButton1.get() == true) &&
                (ConveyorSubsystem.conveyorButton2.get() == true)) {
            conveyorSubsystem.conveyBall(-0.25);
        } else {
            //If we hit the bumper and the shooter is up to speed, convey and shoot the balls
            if (RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight)
                    && shooterEncoderRate >= ShootingCommand.rateSpeed - 10000) {
                conveyorSubsystem.conveyBall(1 / 1.1);
            } else {
                //If one of the buttons is touched by a ball, index the ball.
                if ((ConveyorSubsystem.conveyorButton1.get() == false) || (ConveyorSubsystem.conveyorButton2.get() == false))
                    conveyorSubsystem.conveyBall(0.8275 / 1.1);
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
    public void end(boolean interrupted) {
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
