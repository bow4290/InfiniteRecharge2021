package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.IntakeCommand;

public class IntakeSubsystem extends SubsystemBase {

    private final VictorSPX intakeMotor;
    private final DoubleSolenoid intakeSolenoid;
    public DoubleSolenoid.Value intakeStatus;


    public IntakeSubsystem(DoubleSolenoid intakeSolenoid, int intakeMotorChannel) {
        this.intakeSolenoid = intakeSolenoid;
        intakeMotor = new VictorSPX(intakeMotorChannel);
        this.intakeStatus = DoubleSolenoid.Value.kForward;
        setDefaultCommand(new IntakeCommand(this));
    }

    public void intakeBall(double intakeSpeed) {
        if (intakeSpeed > .75) {
            intakeSpeed = .75;
        }
        intakeMotor.set(VictorSPXControlMode.PercentOutput, intakeSpeed);
    }

    public void liftIntake(boolean buttonPressed){
        if (buttonPressed) {
            swapSolenoidPosition();
        }
    }

    private void swapSolenoidPosition() {
        if (this.intakeStatus == DoubleSolenoid.Value.kForward){
            this.intakeStatus = DoubleSolenoid.Value.kReverse;
        }
        else {
            this.intakeStatus = DoubleSolenoid.Value.kForward;
        }
        intakeSolenoid.set(this.intakeStatus);
    }
}
