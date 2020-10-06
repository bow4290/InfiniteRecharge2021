package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.ShootingCommand;
import frc.robot.dataStructures.RotationData;

import static frc.robot.Robot.*;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSPX leftShooter;
    private final VictorSPX rightShooter;
    public static LimelightSubsystem limelightSubsystem;
    public Robot robot;


    public ShootingSubsystem(int leftShooterChannel, int rightShooterChannel, Robot robot) {
        leftShooter = new VictorSPX(leftShooterChannel);
        rightShooter = new VictorSPX(rightShooterChannel);
        setDefaultCommand(new ShootingCommand(this));
        limelightSubsystem = new LimelightSubsystem();
        this.robot = robot;
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
        rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
    }


    public void dualShoot(boolean autoActive, boolean manualActive) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);

        if (!autoActive && !manualActive) {
            limelightSubsystem.limelightIsOn(false);

            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        } else if (autoActive && !manualActive) {
            /**
             * limeShooting temporarily disabled until I know the target distance.
             */
            limelightSubsystem.limelightIsOn(true);

//            for (int i = 0; i < 10; i++) {
//                RotationData rotationData = robot.sense();
//                System.out.println("the degrees is... " + rotationData.getDegrees());
//                robot.rotate(rotationData.getDegrees());
//            }
//
//            for (int i = 0; i < 10; i++) {
//                RotationData rotationData = robot.sense();
//                System.out.println("the degrees is... " + rotationData.getDegrees());
//                robot.moveWithLime(rotationData.getDistance(), 210);
//            }
////
//            driveTrainSubsystem.drive(0, 0);
//            shootingSubsystem.shootBall(1);
//            conveyorSubsystem.conveyBall(0);
//            Timer.delay(1.5);
//
//            shootingSubsystem.shootBall(1);
//            conveyorSubsystem.conveyBall(1);
//            Timer.delay(3);
//
//            shootingSubsystem.shootBall(0);
//            conveyorSubsystem.conveyBall(0);
//            Timer.delay(3);

            limelightSubsystem.limelightIsOn(false);
        } else if (!autoActive && manualActive) {
            limelightSubsystem.limelightIsOn(false);

            leftShooter.set(VictorSPXControlMode.PercentOutput, 1);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 1);
        } else if (autoActive && manualActive) {
            /**
             * limeShooting temporarily disabled until I know the target distance.
             */
            limelightSubsystem.limelightIsOn(true);

//            driveTrainSubsystem.drive(0, 0);
//            shootingSubsystem.shootBall(0);
//            conveyorSubsystem.conveyBall(0);
//            Timer.delay(1.5);
//            for (int i = 0; i < 10; i++) {
//                RotationData rotationData = robot.sense();
//                System.out.println("the degrees is... " + rotationData.getDegrees());
//                robot.rotate(rotationData.getDegrees());
//            }
//
//            for (int i = 0; i < 10; i++) {
//                RotationData rotationData = robot.sense();
//                System.out.println("the degrees is... " + rotationData.getDegrees());
//                robot.moveWithLime(rotationData.getDistance(), 210);
//            }
//
//            driveTrainSubsystem.drive(0, 0);
//            shootingSubsystem.shootBall(1);
//            conveyorSubsystem.conveyBall(0);
//            Timer.delay(1.5);
//
//            shootingSubsystem.shootBall(1);
//            conveyorSubsystem.conveyBall(1);
//            Timer.delay(3);
//
//            shootingSubsystem.shootBall(0);
//            conveyorSubsystem.conveyBall(0);
//            Timer.delay(3);

            limelightSubsystem.limelightIsOn(false);
        } else {
            limelightSubsystem.limelightIsOn(false);
        }

    }
}

