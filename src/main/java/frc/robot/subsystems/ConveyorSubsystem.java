package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private final VictorSPX topConveyorShooter;
    private final VictorSPX bottomConveyorShooter;

    double speedFactor = 1.1;
    public ConveyorSubsystem(int topMotorChannel, int bottomMotorChannel){
        topConveyorShooter = new VictorSPX(topMotorChannel);
        bottomConveyorShooter = new VictorSPX(bottomMotorChannel);
    }

    public void conveyBall(double conveyorSpeed){
        bottomConveyorShooter.set(VictorSPXControlMode.PercentOutput ,conveyorSpeed);
        topConveyorShooter.set(VictorSPXControlMode.PercentOutput ,speedFactor*conveyorSpeed);
    }

}