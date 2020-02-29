package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSPX intakeMotor;

    public IntakeSubsystem(int intakeMotorChannel){
        intakeMotor = new VictorSPX(intakeMotorChannel);
    }

    public void intakeBall(double intakeSpeed) {
        intakeMotor.set(VictorSPXControlMode.PercentOutput, intakeSpeed);
    }
}
