package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VictorSPDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {
    private final VictorSP leftVictorSP1;
    private final VictorSP leftVictorSP2;
    private final VictorSP leftVictorSP3;
    private final VictorSP rightVictorSP1;
    private final VictorSP rightVictorSP2;
    private final VictorSP rightVictorSP3;
    private final VictorSPX victorSPX;

    public DriveTrainSubsystem(int leftVictorSP1Channel, int leftVictorSP2Channel, int leftVictorSP3Channel,
                               int rightVictorSP1Channel, int rightVictorSP2Channel, int rightVictorSP3Channel) {
        leftVictorSP1 = new VictorSP(leftVictorSP1Channel);
        leftVictorSP2 = new VictorSP(leftVictorSP2Channel);
        leftVictorSP3 = new VictorSP(leftVictorSP3Channel);
        rightVictorSP1 = new VictorSP(rightVictorSP1Channel);
        rightVictorSP2 = new VictorSP(rightVictorSP2Channel);
        rightVictorSP3 = new VictorSP(rightVictorSP3Channel);
        victorSPX = new VictorSPX(0);

      setDefaultCommand(new VictorSPDriveCommand(this));
    }

    /**
     * sets power of left and right speed controllers respectively.
     * @param leftspeed
     * @param rightspeed
     */
    public void drive(double leftspeed, double rightspeed) {
        leftVictorSP1.setSpeed(leftspeed);
        leftVictorSP2.setSpeed(leftspeed);
        leftVictorSP3.setSpeed(leftspeed);
        rightVictorSP1.setSpeed(rightspeed);
        rightVictorSP2.setSpeed(rightspeed);
        rightVictorSP3.setSpeed(rightspeed);
    }

}
