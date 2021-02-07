package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
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
    public static String mode = "IntakeMode";
    public int m = 240000;
    public int b = 25000;
    Scanner scanner = new Scanner(System.in);
    public static int dPadValue = -1;

    public ShootingCommand(ShootingSubsystem shootingSubsystem) {
        this.shootingSubsystem = shootingSubsystem;
        this.subsystems = Set.of(shootingSubsystem);
    }

    public void execute() {
        shootingSubsystem.moveConveyor(RobotContainer.xboxController.getAButtonPressed());
        dPadValue = RobotContainer.xboxController.getPOV();

        if(RobotContainer.xboxController.getBumperPressed(Hand.kLeft) || mode == "IntakeMode")
        {
            mode = "IntakeMode";
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
            shooterSpeed = 0;
            rateSpeed = 0;
        }

        if(dPadValue == 0){
            mode = "Green";
            shooterSpeed = 0.93;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(dPadValue == 90){
            mode = "Yellow";
            shooterSpeed = 0.66;
            rateSpeed = m * (shooterSpeed) - b + 25000;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(dPadValue == 180){
            mode = "Blue";
            shooterSpeed = 0.91;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        if(dPadValue == 270){
            mode = "Red";
            shooterSpeed = 0.865;
            rateSpeed = m * (shooterSpeed) - b;
            shootingSubsystem.shooterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        SmartDashboard.putString("Mode: ", mode);

        shooterSpeedError = rateSpeed - Robot.shooterEncoder.getRate();
        shooterSpeedCorrection = shooterSpeedKP * (shooterSpeedError + shooterSpeedSetPoint);
        shooterSpeedCorrection = (shooterSpeedCorrection + b) / m;

        shootingSubsystem.dualShoot(
                RobotContainer.xboxController.getBumper(GenericHID.Hand.kRight),
                (shooterSpeed + shooterSpeedCorrection)
        );
        //SmartDashboard.putNumber("Speed Correction: ", shooterSpeedCorrection);
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
