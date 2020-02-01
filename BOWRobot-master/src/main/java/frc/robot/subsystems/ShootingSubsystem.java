package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootingSubsystem extends SubsystemBase {
    private final CANSparkMax leftShooter;
    private final CANSparkMax rightShooter;

    private static final int leftDeviceID = 0;
    private static final int rightDeviceID = 1;

    public ShootingSubsystem() {
        leftShooter = new CANSparkMax(leftDeviceID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightShooter = new CANSparkMax(rightDeviceID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.set(shooterSpeed);
        rightShooter.setInverted(true);
        rightShooter.set(shooterSpeed);
    }
}

