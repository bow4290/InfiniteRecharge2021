package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VictorSPDriveCommand;

public class DriveTrainSubsystem extends SubsystemBase {
    private final VictorSP leftVictorSP1;
    private final VictorSP leftVictorSP2;
    private final VictorSP leftVictorSP3;
    private final VictorSP leftVictorSP4;
    private final VictorSP rightVictorSP1;
    private final VictorSP rightVictorSP2;
    private final VictorSP rightVictorSP3;
    private final VictorSP rightVictorSP4;

    public DriveTrainSubsystem(int leftVictorSP1Channel, int leftVictorSP2Channel, int leftVictorSP3Channel,
                               int leftVictorSP4Channel, int rightVictorSP1Channel, int rightVictorSP2Channel,
                               int rightVictorSP3Channel, int rightVictorSP4Channel) {
        leftVictorSP1 = new VictorSP(leftVictorSP1Channel);
        leftVictorSP2 = new VictorSP(leftVictorSP2Channel);
        leftVictorSP3 = new VictorSP(leftVictorSP3Channel);
        leftVictorSP4 = new VictorSP(leftVictorSP4Channel);
        rightVictorSP1 = new VictorSP(rightVictorSP1Channel);
        rightVictorSP2 = new VictorSP(rightVictorSP2Channel);
        rightVictorSP3 = new VictorSP(rightVictorSP3Channel);
        rightVictorSP4 = new VictorSP(rightVictorSP4Channel);

      setDefaultCommand(new VictorSPDriveCommand(this));
    }

    /**
     * sets power of left and right speed controllers respectively.
     * @param leftspeed
     * @param rightspeed
     */
    public void drive(double leftspeed, double rightspeed) {
//        System.out.println("Left value is "+leftspeed);
//        System.out.println("Right value is "+rightspeed);
        leftVictorSP1.setSpeed(leftspeed);
        leftVictorSP2.setSpeed(leftspeed);
        leftVictorSP3.setSpeed(leftspeed);
        leftVictorSP4.setSpeed(leftspeed);
        rightVictorSP1.setSpeed(rightspeed);
        rightVictorSP2.setSpeed(rightspeed);
        rightVictorSP3.setSpeed(rightspeed);
        rightVictorSP4.setSpeed(rightspeed);
    }

}
