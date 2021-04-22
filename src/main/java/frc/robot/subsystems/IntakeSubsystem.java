package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSPX intakeMotor;
    private final DoubleSolenoid intakeSolenoid;
    public DoubleSolenoid.Value intakeStatus;


    public IntakeSubsystem(DoubleSolenoid intakeSolenoid, int intakeMotorChannel) {
        this.intakeSolenoid = intakeSolenoid;
        intakeMotor = new VictorSPX(intakeMotorChannel);
        this.intakeStatus = DoubleSolenoid.Value.kForward;
    }

    public void intakeBall(double intakeSpeed) {
        if (intakeSpeed > 1) {
            intakeSpeed = 1;
        }
        intakeMotor.set(VictorSPXControlMode.PercentOutput, intakeSpeed);
    }

    public void liftIntake(boolean buttonPressed) {
        if (buttonPressed) {
            swapIntakeSolenoidPosition();
        }
    }

    public void swapIntakeSolenoidPosition() {
        if (this.intakeStatus == DoubleSolenoid.Value.kForward) {
            this.intakeStatus = DoubleSolenoid.Value.kReverse;
        } else {
            this.intakeStatus = DoubleSolenoid.Value.kForward;
        }
        intakeSolenoid.set(this.intakeStatus);
    }
}
