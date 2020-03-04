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

    public void climb(boolean climbingUp, boolean climbingDown) {
        if (climbingUp)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, .4);
        else if (climbingDown)
            climbingMotor.set(VictorSPXControlMode.PercentOutput, -.4);
        else
            climbingMotor.set(VictorSPXControlMode.PercentOutput, 0);
    }

}
