package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ShootingSubsystem;

import java.util.Scanner;

import java.util.Set;

public class ShootingCommand extends CommandBase {

    private ShootingSubsystem shootingSubsystem;
    private final Set<Subsystem> subsystems;

    public static double shooterSpeed = 1;
    public static double shooterSpeedError;
    public static double shooterSpeedCorrection;
    public static double shooterSpeedKP = 0.03;
    public static double shooterSpeedSetPoint = 15000;
    public static double rateSpeed = 0;
    public static String mode = "Red";
    public int m = 240000;
    public int b = 25000;
    Scanner scanner = new Scanner(System.in);
    public static int dPadValue = -1;

    public ShootingCommand(ShootingSubsystem shootingSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    public void execute() {
        shootingSubsystem.moveConveyor(RobotContainer.xboxController.getAButtonPressed()); //A button runs conveyor.
        dPadValue = RobotContainer.xboxController.getPOV(); //Dpad selects mode.

        if (RobotContainer.xboxController.getBumperPressed(Hand.kLeft) || mode == "IntakeMode") {
            mode = "IntakeMode";
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
            shooterSpeed = 0;
            rateSpeed = 0;
        }

        //Change shooting speed threshold depending on zone color.
        if (dPadValue == 0) {
            mode = "Green";
            shooterSpeed = Constants.greenShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if (dPadValue == 90) {
            mode = "Yellow";
            shooterSpeed = Constants.yellowShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b + 20000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if (dPadValue == 180) {
            mode = "Blue";
            shooterSpeed = Constants.blueShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if (dPadValue == 270) {
            mode = "Red";
            shooterSpeed = Constants.redShooterSpeed;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("Mode: ", mode);

        //Shooter PID
        shooterSpeedError = rateSpeed - Robot.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        if (RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight)) {
            shootingSubsystem.dualShoot((shooterSpeed + shooterSpeedCorrection));
        } else {
            shootingSubsystem.dualShoot(0);
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
