package frc.robot.subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSP intakeMotor;

    public IntakeSubsystem(int intakeMotorChannel){
        intakeMotor = new VictorSP(intakeMotorChannel);
    }

    public void intakeBall(double intakeSpeed) {
        intakeMotor.set(intakeSpeed);
    }
}
