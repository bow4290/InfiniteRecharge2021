package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Set;

import static frc.robot.Robot.climberSubsystem;

public class ShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private final Set<Subsystem> subsystems;
    
    public static double shooterSpeed = 1;
    public static double rateSpeed = 0;
    public static String mode = "IDLE";
    public int count = 0;

    public ShootingCommand(ShootingSubsystem shootingSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    public void execute() {

        while(count != 1)
        {
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
            count++;
        }

        if((mode == "IDLE" || mode == "Red") && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Green";
            shooterSpeed = 0.93;
            rateSpeed = 240000 * (shooterSpeed) - 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "Green" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Yellow";
            shooterSpeed = 0.7;
            rateSpeed = 240000 * (shooterSpeed) - 60000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "Yellow" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Blue";
            shooterSpeed = 0.89;
            rateSpeed = 240000 * (shooterSpeed) - 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if(mode == "Blue" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Red";
            shooterSpeed = 0.9;
            rateSpeed = 240000 * (shooterSpeed) - 40000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("ZONE COLOR", mode);

        shootingSubsystem.dualShoot(
                //RobotContainer.xboxController.getBumper(GenericHID.Hand.kLeft),
                false,
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
