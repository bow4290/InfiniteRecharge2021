package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSP leftShooter;
    private final VictorSP rightShooter;

    public ShootingSubsystem(int leftShooterChannel, int rightShooterChannel) {
        leftShooter = new VictorSP(leftShooterChannel);
        rightShooter = new VictorSP(rightShooterChannel);
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.set(shooterSpeed);
        rightShooter.setInverted(true);
        rightShooter.set(shooterSpeed);
    }

    public void manualShoot(boolean shooterActive){
           leftShooter.set(.65);
           rightShooter.setInverted(true);
           rightShooter.set(.65);
    }
}

