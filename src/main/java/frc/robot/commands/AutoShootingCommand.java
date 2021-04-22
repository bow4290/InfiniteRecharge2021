package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Set;

/**
 * Set mode to red zone and shot for four seconds.
 */

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
        startFirstTime = 0; //Allows a shooting timer to start when this command is called.
    }

    @Override
    public void execute() {

        if (startFirstTime == 0) {
            shootingCommandStartTime = Timer.getFPGATimestamp(); //The current time is noted as the start time for this command.
        }

        //Change shooting speed threshold depending on zone color.
        if (autoMode == "Green") {
            shooterSpeed = Constants.greenShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if (autoMode == "Yellow") {
            shooterSpeed = Constants.yellowShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b + 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if (autoMode == "Blue") {
            shooterSpeed = Constants.blueShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if (autoMode == "Red") {
            shooterSpeed = Constants.redShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("Automode: ", autoMode);

        //Shooter PID
        shooterSpeedError = rateSpeed - Robot.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        shootingSubsystem.dualShoot((shooterSpeed + shooterSpeedCorrection));

        //Conveys balls when the shooter is up to speed.
        if (Robot.shooterEncoder.getRate() >= (rateSpeed - 15000)) {
            conveyorSubsystem.conveyBall(1 / 1.1);
        } else conveyorSubsystem.conveyBall(0);

        currentShootingCommandTime = Timer.getFPGATimestamp();
        startFirstTime = 1;
    }

    @Override
    public boolean isFinished() {
        return (currentShootingCommandTime - shootingCommandStartTime >= 4); //Runs shooter for four seconds.
    }

    @Override
    public void end(boolean interrupted) {
        shootingSubsystem.dualShoot(0);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
