package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ShootingSubsystem;
import java.util.Scanner;

import java.util.Set;

public class AutoShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private final Set<Subsystem> subsystems;

    public static double shooterSpeed = 1;
    public static double shooterSpeedError;
    public static double shooterSpeedCorrection;
    public static double shooterSpeedKP = 0.03;
    public static double shooterSpeedSetPoint = 15000;
    public static double rateSpeed = 0;
    private String mode;
    public int m = 240000;
    public int b = 25000;
    public double shootingCommandStartTime;
    public double currentShootingCommandTime;
    public int startFirstTime = 0;
    public static boolean shootingIsFinished = false;

    public AutoShootingCommand(ShootingSubsystem shootingSubsystem, String mode) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
        this.mode = mode.toLowerCase();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        if(startFirstTime == 0){
            shootingCommandStartTime = Timer.getFPGATimestamp();
        }

        if(mode == "green"){
            shooterSpeed = 0.93;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "yellow"){ ;
            shooterSpeed = 0.66;
            rateSpeed = m * (shooterSpeed) - b + 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(mode == "blue"){
            shooterSpeed = 0.91;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if(mode == "red"){
            shooterSpeed = 0.865;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }


        shooterSpeedError = rateSpeed - Robot.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        shootingSubsystem.dualShoot(
                true,
                (shooterSpeed + shooterSpeedCorrection)
        );

        currentShootingCommandTime = Timer.getFPGATimestamp();
        startFirstTime = 1;
    }

    @Override
    public boolean isFinished() {
        if(currentShootingCommandTime - shootingCommandStartTime >= 5)
        {
            shootingIsFinished = true;
        return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        shootingSubsystem.dualShoot(
                false,
                0);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
