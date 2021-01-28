package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.ShootingCommand;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSPX leftShooter;
    private final VictorSPX rightShooter;
    public static LimelightSubsystem limelightSubsystem;
    public Robot robot;
    public DoubleSolenoid shooterSolenoid;
    public DoubleSolenoid.Value intakeStatus;



    public ShootingSubsystem(DoubleSolenoid shooterSolenoid, int leftShooterChannel, int rightShooterChannel, Robot robot) {
        leftShooter = new VictorSPX(leftShooterChannel);
        rightShooter = new VictorSPX(rightShooterChannel);
        setDefaultCommand(new ShootingCommand(this));
        limelightSubsystem = new LimelightSubsystem();
        this.robot = robot;
        this.shooterSolenoid = shooterSolenoid;
        intakeStatus = DoubleSolenoid.Value.kReverse;
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
        rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
    }


    public void dualShoot(boolean autoActive, boolean manualActive, double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);

        if (!autoActive && !manualActive) {
            limelightSubsystem.limelightIsOn(false);

            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        }

        else if (autoActive && !manualActive) {
            limelightSubsystem.limelightIsOn(true);

            robot.rotateWithLime(limelightSubsystem.getTx());
            robot.move(robot.calculateDistance(limelightSubsystem.getTy()));
            robot.shoot(1, 5000);
            robot.wait(1);
            robot.convey(1, 5000);
            robot.convey(0, 0050);
            robot.shoot(0, 0050);
        }

        else if (!autoActive && manualActive) {
            limelightSubsystem.limelightIsOn(false);

            if (shooterSpeed > 1) {
                shooterSpeed = 1;
            }
            if(ShootingCommand.mode == "IDLE"){
                leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
                rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
            } else{
            leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
            rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed*0.9);
        }
        }

        else if (autoActive && manualActive) {
            limelightSubsystem.limelightIsOn(true);

            robot.rotateWithLime(limelightSubsystem.getTx());
            robot.move(robot.calculateDistance(limelightSubsystem.getTy()));
            robot.shoot(1, 5000);
            robot.wait(1);
            robot.convey(1, 5000);
            robot.convey(0, 0050);
            robot.shoot(0, 0050);
        } else {
            limelightSubsystem.limelightIsOn(false);
        }

    }

    public void moveConveyor(boolean buttonPressed){
        if (buttonPressed) {
            swapSolenoidPosition();
        }
    }

    private void swapSolenoidPosition() {

        if (intakeStatus == DoubleSolenoid.Value.kForward) {
            intakeStatus = DoubleSolenoid.Value.kReverse;
        } else {
            intakeStatus = DoubleSolenoid.Value.kForward;
        }
        shooterSolenoid.set(intakeStatus);
    }
}

