package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Set;

public class ShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private final Set<Subsystem> subsystems;
    public double shooterSpeed = 1;
    public String mode = "Green";

    public ShootingCommand(ShootingSubsystem shootingSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    /**
     * this will display the y value of the left joystcik
     */
    public void execute() {
        if(mode == "Red" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Green";
            shooterSpeed = 0.95;
        }
        if(mode == "Green" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Yellow";
            shooterSpeed = 0.82;
        }
        if(mode == "Yellow" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Blue";
            shooterSpeed = 0.92;
        }
        if(mode == "Blue" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Red";
            shooterSpeed = 0.98;
        }
        
        SmartDashboard.putString("ZONE COLOR", mode);

        shootingSubsystem.dualShoot(
                //RobotContainer.xboxController.getBumper(GenericHID.Hand.kLeft),
                0,
                RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight),
                shooterSpeed
        );
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
