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
    private ConveyorSubsystem conveyorSubsystem;
    private final Set<Subsystem> subsystems;
    
    public static double shooterSpeed = 1;
    public static double shooterSpeedError;
    public static double shooterSpeedCorrection;
    public static double shooterSpeedKP = 0.01;
    public static double shooterSpeedSetPoint = 15000;
    public static double rateSpeed = 0;
    public static String mode = "IDLE";
    public int count = 0;
    public int m = 240000;
    public int b = 20000;

    public ShootingCommand(ShootingSubsystem shootingSubsystem, ConveyorSubsystem conveyorSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
        this.conveyorSubsystem = conveyorSubsystem;
    }

    public void execute() {
        shootingSubsystem.moveConveyor(RobotContainer.xboxController.getAButtonPressed());

        while(count != 1)
        {
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
            count++;
        }

        if((mode == "IDLE" || mode == "Red") && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Green";
            shooterSpeed = 0.93;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "Green" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Yellow";
            shooterSpeed = 0.7;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "Yellow" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Blue";
            shooterSpeed = 0.89;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if(mode == "Blue" && RobotContainer.xboxController.getStickButtonPressed(Hand.kLeft)){
            mode = "Red";
            shooterSpeed = 0.9;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("ZONE COLOR", mode);

        shooterSpeedError = rateSpeed - conveyorSubsystem.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        shootingSubsystem.dualShoot(
                //RobotContainer.xboxController.getBumper(GenericHID.Hand.kLeft),
                false,
                RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight),
                (shooterSpeed + shooterSpeedCorrection)
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
