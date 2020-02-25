package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WheelSpinnerSubsystem extends SubsystemBase {
    public final VictorSP wheelSpinner;

    /**
     * @param wheelSpinnerChannel
     */
    public WheelSpinnerSubsystem(int wheelSpinnerChannel) {
        wheelSpinner = new VictorSP(wheelSpinnerChannel);
    }

    /**
     * @param spinSpeed
     */
    public void spin(double spinSpeed) {
        wheelSpinner.setSpeed(spinSpeed);
    }
}