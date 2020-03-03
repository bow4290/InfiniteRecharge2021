package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import javax.swing.*;

public class ClimberSubsystem extends SubsystemBase {


    private final VictorSPX climbingMotor;

    public ClimberSubsystem(int climbingMotorChannel){
        climbingMotor = new VictorSPX(climbingMotorChannel);
    }

    public void climbUp(boolean intakeSpeed) {
        if (intakeSpeed)
        climbingMotor.set(VictorSPXControlMode.PercentOutput, .4);
        else{}
    }

    public void climbDown(boolean intakeSpeed) {
        if (intakeSpeed)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, -.4);
        else{}
    }

}
