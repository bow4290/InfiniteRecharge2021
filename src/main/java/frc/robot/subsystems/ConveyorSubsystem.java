package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ConveyorCommand;

public class ConveyorSubsystem extends SubsystemBase {
    private final VictorSPX topConveyor;
    private final VictorSPX bottomConveyor;

    double speedFactor = 1.1;

    public ConveyorSubsystem(int topMotorChannel, int bottomMotorChannel) {
        topConveyor = new VictorSPX(topMotorChannel);
        bottomConveyor = new VictorSPX(bottomMotorChannel);
        setDefaultCommand(new ConveyorCommand(this));
    }


    public void conveyBall(double conveyorSpeed){
        if (conveyorSpeed > .75) {
            conveyorSpeed = .75;
        } else {
            bottomConveyor.set(VictorSPXControlMode.PercentOutput, conveyorSpeed);
            topConveyor.setInverted(true);
            topConveyor.set(VictorSPXControlMode.PercentOutput, speedFactor * conveyorSpeed);
        }
    }
}