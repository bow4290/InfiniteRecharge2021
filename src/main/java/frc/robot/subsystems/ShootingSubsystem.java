package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.ShootingCommand;
import frc.robot.dataStructures.RotationData;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSPX leftShooter;
    private final VictorSPX rightShooter;
    public static LimelightSubsystem limelightSubsystem;
    public Robot robot;


    public ShootingSubsystem(int leftShooterChannel, int rightShooterChannel) {
        leftShooter = new VictorSPX(leftShooterChannel);
        rightShooter = new VictorSPX(rightShooterChannel);
        setDefaultCommand(new ShootingCommand(this));
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
        rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
    }

//    public void manualShoot(boolean shooterActive) {
//        leftShooter.setInverted(true);
//        rightShooter.setInverted(true);
//        if (shooterActive) {
//            leftShooter.set(VictorSPXControlMode.PercentOutput, .9);
//            rightShooter.set(VictorSPXControlMode.PercentOutput, .9);
//        } else {
//            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
//            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
//        }
//    }
//
//    public void autoShoot(boolean autoShooterActive) {
//        leftShooter.setInverted(true);
//        rightShooter.setInverted(true);
//        if (autoShooterActive) {
//            limelightSubsystem.turnOnLED();
//            robot.rotateWithLime(limelightSubsystem.getTx());
//            robot.move(robot.calculateDistance(limelightSubsystem.getTy()));
//            robot.shoot(.6);
//            robot.wait(1000);
//            robot.convey(.6);
//            robot.convey(0);
//            robot.shoot(0);
//
//        } else {
//            limelightSubsystem.turnOffLED();
//        }
//    }

    public void dualShoot(boolean autoActive, boolean manualActive) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);

        if (!autoActive && !manualActive) {
            limelightSubsystem.turnOffLED();

            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        }

        else if (autoActive && !manualActive) {
            limelightSubsystem.turnOnLED();

            robot.rotateWithLime(limelightSubsystem.getTx());
            robot.move(robot.calculateDistance(limelightSubsystem.getTy()));
            robot.shoot(1, 5000);
            robot.wait(1);
            robot.convey(1, 5000);
            robot.convey(0, 0050);
            robot.shoot(0, 0050);
        }

        else if (!autoActive && manualActive) {
            limelightSubsystem.turnOffLED();

            leftShooter.set(VictorSPXControlMode.PercentOutput, 1);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 1);
        }

        else if (autoActive && manualActive) {
            limelightSubsystem.turnOnLED();

            robot.rotateWithLime(limelightSubsystem.getTx());
            robot.move(robot.calculateDistance(limelightSubsystem.getTy()));
            robot.shoot(1, 5000);
            robot.wait(1);
            robot.convey(1, 5000);
            robot.convey(0, 0050);
            robot.shoot(0, 0050);
        } else {
            limelightSubsystem.turnOffLED();

            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        }

    }
}

