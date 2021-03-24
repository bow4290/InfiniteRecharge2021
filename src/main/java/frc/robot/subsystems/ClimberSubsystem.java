package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {


    private final VictorSPX climbingMotor;
    private final VictorSPX wheelMotor;

    public ClimberSubsystem(int climbingMotorChannel, int wheelSpinnerChannel) {
        climbingMotor = new VictorSPX(climbingMotorChannel);
        wheelMotor = new VictorSPX(wheelSpinnerChannel);
    }

    public void climb(boolean climbingUp, boolean climbingDown) {
        if (climbingUp)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, 1);
        else if (climbingDown)
            wheelMotor.set(VictorSPXControlMode.PercentOutput, 1);
        else
            climbingMotor.set(VictorSPXControlMode.PercentOutput, 0);
            wheelMotor.set(VictorSPXControlMode.PercentOutput, 0);
    }

}
