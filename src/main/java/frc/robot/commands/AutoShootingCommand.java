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
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShootingSubsystem;
import java.util.Scanner;

import java.util.Set;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

public class AutoShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private double shootingCommandStartTime;
    private double currentShootingCommandTime;
    private double startFirstTime;
    private final Set<Subsystem> subsystems;
    public static double shooterSpeedError;
    public static double shooterSpeedCorrection;
    public static double shooterSpeedKP = 0.03;
    public static double shooterSpeedSetPoint = 15000;
    public static double rateSpeed = 0;
    public int m = 240000;
    public int b = 25000;
    public String autoMode = "Red";
    private double shooterSpeed;

    public AutoShootingCommand(ShootingSubsystem shootingSubsystem, ConveyorSubsystem conveyorSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.conveyorSubsystem = conveyorSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    @Override
    public void initialize() {
        autoMode = "Red";
        startFirstTime = 0;
    }

    @Override
    public void execute() {

        if(startFirstTime == 0){
            shootingCommandStartTime = Timer.getFPGATimestamp();
        }

        if(autoMode == "Green"){
            shooterSpeed = 0.93;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(autoMode == "Yellow"){
            shooterSpeed = 0.66;
            rateSpeed = m * (shooterSpeed) - b + 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(autoMode == "Blue"){
            shooterSpeed = 0.91;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if(autoMode == "Red"){
            shooterSpeed = 0.865;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("Automode: ", autoMode);

        shooterSpeedError = rateSpeed - Robot.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        shootingSubsystem.dualShoot(
                true,
                (shooterSpeed + shooterSpeedCorrection));


        if(Robot.shooterEncoder.getRate() >= (rateSpeed - 15000)) {
                conveyorSubsystem.conveyBall(1 / 1.1);
            } else conveyorSubsystem.conveyBall(0);
        
        currentShootingCommandTime = Timer.getFPGATimestamp();
        startFirstTime = 1;
    }

    @Override
    public boolean isFinished() {
        return(currentShootingCommandTime - shootingCommandStartTime >= 4);
    }

    @Override
    public void end(boolean interrupted) {
        shootingSubsystem.dualShoot(false, 0);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
