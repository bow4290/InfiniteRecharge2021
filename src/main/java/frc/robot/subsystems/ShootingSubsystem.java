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

            RotationData rotationData = robot.sense();
            robot.rotateWithLime(rotationData.getDegrees());

            //200 is a placeholder value for the shooting distance
            robot.moveWithLime(rotationData.getDistance(), 210);
            double moveTime = robot.moveWithLime(rotationData.getDistance(), 210);
            Timer.delay(moveTime);

            driveTrainSubsystem.drive(0, 0);
            shootingSubsystem.shootBall(1);
            conveyorSubsystem.conveyBall(0);
            Timer.delay(1.5);

            shootingSubsystem.shootBall(1);
            conveyorSubsystem.conveyBall(1);
            Timer.delay(3);

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

            RotationData rotationData = robot.sense();
            robot.rotateWithLime(rotationData.getDegrees());

            robot.moveWithLime(rotationData.getDistance(), 210);
            double moveTime = robot.moveWithLime(rotationData.getDistance(), 210);
            Timer.delay(moveTime);

            driveTrainSubsystem.drive(0, 0);
            shootingSubsystem.shootBall(1);
            conveyorSubsystem.conveyBall(0);
            Timer.delay(1.5);

            shootingSubsystem.shootBall(1);
            conveyorSubsystem.conveyBall(1);
            Timer.delay(3);

            limelightSubsystem.limelightIsOn(false);
        } else {
            limelightSubsystem.limelightIsOn(false);
        }

    }
}

