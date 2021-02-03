package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ConveyorCommand;

public class ConveyorSubsystem extends SubsystemBase {
    private final VictorSPX topConveyor;
    private final VictorSPX bottomConveyor;
    public Constants constants = new Constants();
    public static DigitalInput conveyorButton;

    double speedFactor = 1.1;

    public ConveyorSubsystem(int topMotorChannel, int bottomMotorChannel) {
        conveyorButton = new DigitalInput(Constants.conveyorButtonPort);
        topConveyor = new VictorSPX(topMotorChannel);
        bottomConveyor = new VictorSPX(bottomMotorChannel);
        setDefaultCommand(new ConveyorCommand(this));
    }


    public void conveyBall(double conveyorSpeed){
        if (conveyorSpeed > 1) {
            conveyorSpeed = 1;
        }
            bottomConveyor.set(VictorSPXControlMode.PercentOutput, conveyorSpeed);
            topConveyor.setInverted(true);
            topConveyor.set(VictorSPXControlMode.PercentOutput, speedFactor * conveyorSpeed);
    }
}