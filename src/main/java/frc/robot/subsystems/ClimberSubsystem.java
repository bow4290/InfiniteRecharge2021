package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.ShootingCommand;

import javax.swing.*;

public class ClimberSubsystem extends SubsystemBase {


    private final VictorSPX climbingMotor;
    private  final DoubleSolenoid fallStopSolenoid;
    public DoubleSolenoid.Value intakeStatus;

    public ClimberSubsystem(DoubleSolenoid fallStopSolenoid, int climbingMotorChannel) {
        this.fallStopSolenoid = fallStopSolenoid;
        climbingMotor = new VictorSPX(climbingMotorChannel);
        this.intakeStatus = DoubleSolenoid.Value.kForward;
        setDefaultCommand(new ClimberCommand(this));
    }

    public void climb(boolean climbingUp, boolean climbingDown) {
        if (climbingUp)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, 1);
        else if (climbingDown)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, -1);
        else
            climbingMotor.set(VictorSPXControlMode.PercentOutput, 0);
    }
    public void stopClimber(boolean buttonPressed){
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
        fallStopSolenoid.set(this.intakeStatus);
    }
}
