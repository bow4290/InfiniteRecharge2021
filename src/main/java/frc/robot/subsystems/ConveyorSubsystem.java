package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private final VictorSPX topConveyor;
    private final VictorSPX bottomConveyor;

    double speedFactor = 1.1;
    public ConveyorSubsystem(int topMotorChannel, int bottomMotorChannel){
        topConveyor = new VictorSPX(topMotorChannel);
        bottomConveyor = new VictorSPX(bottomMotorChannel);
    }

    public void conveyBall(double conveyorSpeed){
        bottomConveyor.set(VictorSPXControlMode.PercentOutput, conveyorSpeed);
        topConveyor.setInverted(true);
        topConveyor.set(VictorSPXControlMode.PercentOutput, speedFactor*conveyorSpeed);
    }

}