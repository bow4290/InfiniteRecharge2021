package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.IntakeCommand;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSPX intakeMotor;
    private final DoubleSolenoid intakeSolenoid;

    public IntakeSubsystem(int intakeMotorChannel) {
        intakeMotor = new VictorSPX(intakeMotorChannel);
        setDefaultCommand(new IntakeCommand(this));
        intakeSolenoid = new DoubleSolenoid(0,1);
    }

    public void intakeBall(double intakeSpeed) {
        if (intakeSpeed > .75) {
            intakeSpeed = .75;
        } else {
            intakeMotor.set(VictorSPXControlMode.PercentOutput, intakeSpeed);
        }
    }

    public void liftIntake(boolean isIntakeUp){
        if (isIntakeUp) {
            intakeSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
