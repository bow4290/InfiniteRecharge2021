package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ShootingCommand;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSPX leftShooter;
    private final VictorSPX rightShooter;

    public ShootingSubsystem(int leftShooterChannel, int rightShooterChannel) {
        leftShooter = new VictorSPX(leftShooterChannel);
        rightShooter = new VictorSPX(rightShooterChannel);
        setDefaultCommand(new ShootingCommand(this));
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
//        rightShooter.setInverted(true);
        rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
    }

    public void manualShoot(boolean shooterActive){
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        if(shooterActive) {
            leftShooter.set(VictorSPXControlMode.PercentOutput, .9);
            rightShooter.set(VictorSPXControlMode.PercentOutput, .9);
        } else {
            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        }
    }
}

