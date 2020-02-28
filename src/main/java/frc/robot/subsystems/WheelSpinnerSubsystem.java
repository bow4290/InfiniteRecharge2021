package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WheelSpinnerSubsystem extends SubsystemBase {
    public final VictorSPX wheelSpinner;

    /**
     * @param wheelSpinnerChannel
     */
    public WheelSpinnerSubsystem(int wheelSpinnerChannel) {
        wheelSpinner = new VictorSPX(wheelSpinnerChannel);
    }

    /**
     * @param spinSpeed
     */
    public void spin(double spinSpeed) {
        wheelSpinner.set(VictorSPXControlMode.PercentOutput, spinSpeed);
    }
}