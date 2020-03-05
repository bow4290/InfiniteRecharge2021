package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VictorSPXDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {

    private final VictorSPX leftVictorSPX1;
    private final VictorSPX leftVictorSPX2;
    private final VictorSPX leftVictorSPX3;
    private final VictorSPX rightVictorSPX1;
    private final VictorSPX rightVictorSPX2;
    private final VictorSPX rightVictorSPX3;
    private final DoubleSolenoid gearShiftSolenoid;

    public DriveTrainSubsystem(int leftVictorSPX1Channel, int leftVictorSPX2Channel, int leftVictorSPX3Channel,
                               int rightVictorSPX1Channel, int rightVictorSPX2Channel, int rightVictorSPX3Channel) {
        leftVictorSPX1 = new VictorSPX(leftVictorSPX1Channel);
        leftVictorSPX2 = new VictorSPX(leftVictorSPX2Channel);
        leftVictorSPX3 = new VictorSPX(leftVictorSPX3Channel);
        rightVictorSPX1 = new VictorSPX(rightVictorSPX1Channel);
        rightVictorSPX2 = new VictorSPX(rightVictorSPX2Channel);
        rightVictorSPX3 = new VictorSPX(rightVictorSPX3Channel);

        gearShiftSolenoid = new DoubleSolenoid(4, 5);
        setDefaultCommand(new VictorSPXDriveCommand(this));
    }

    /**
     * sets power of left and right speed controllers respectively.
     *
     * @param leftspeed
     * @param rightspeed
     */
    public void drive(double leftspeed, double rightspeed) {

        leftVictorSPX1.setInverted(true);
        leftVictorSPX2.setInverted(true);
        leftVictorSPX3.setInverted(true);
        rightVictorSPX1.setInverted(true);
        rightVictorSPX2.setInverted(true);
        rightVictorSPX3.setInverted(true);

        leftVictorSPX1.set(VictorSPXControlMode.PercentOutput, leftspeed);
        leftVictorSPX2.set(VictorSPXControlMode.PercentOutput, leftspeed);
        leftVictorSPX3.set(VictorSPXControlMode.PercentOutput, leftspeed);
        rightVictorSPX1.set(VictorSPXControlMode.PercentOutput, rightspeed);
        rightVictorSPX2.set(VictorSPXControlMode.PercentOutput, rightspeed);
        rightVictorSPX3.set(VictorSPXControlMode.PercentOutput, rightspeed);
    }

    public void shiftGear(boolean isForward) {
        System.out.println("trying to shift");
        if (isForward) {
            gearShiftSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            gearShiftSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
