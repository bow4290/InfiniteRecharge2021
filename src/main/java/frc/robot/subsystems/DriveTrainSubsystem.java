package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.VictorSPXDriveCommand;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {

    private final VictorSPX leftVictorSPX1;
    private final VictorSPX leftVictorSPX2;
    private final VictorSPX leftVictorSPX3;
    private final VictorSPX rightVictorSPX1;
    private final VictorSPX rightVictorSPX2;
    private final VictorSPX rightVictorSPX3;
    private final DoubleSolenoid gearShiftSolenoid;
    public DoubleSolenoid.Value intakeStatus;

    private double speedMultiplier = 1; // If less than one, drive speed is proportionally reduced

    public Encoder driveTrainLeftEncoder;
    public Encoder driveTrainRightEncoder;

    public ADXRS450_Gyro driveGyro;

    public DriveTrainSubsystem(DoubleSolenoid gearShiftSolenoid, int leftVictorSPX1Channel, int leftVictorSPX2Channel, int leftVictorSPX3Channel,
                               int rightVictorSPX1Channel, int rightVictorSPX2Channel, int rightVictorSPX3Channel) {
        this.gearShiftSolenoid = gearShiftSolenoid;
        leftVictorSPX1 = new VictorSPX(leftVictorSPX1Channel);
        leftVictorSPX2 = new VictorSPX(leftVictorSPX2Channel);
        leftVictorSPX3 = new VictorSPX(leftVictorSPX3Channel);
        rightVictorSPX1 = new VictorSPX(rightVictorSPX1Channel);
        rightVictorSPX2 = new VictorSPX(rightVictorSPX2Channel);
        rightVictorSPX3 = new VictorSPX(rightVictorSPX3Channel);
        this.intakeStatus = DoubleSolenoid.Value.kForward;

        driveTrainLeftEncoder = new Encoder(Constants.driveTrainLeftEncoderChannelA, Constants.driveTrainLeftEncoderChannelB, false, CounterBase.EncodingType.k4X);
        driveTrainLeftEncoder.setSamplesToAverage(Constants.driveTrainLeftEncoderAverageSamples);
        driveTrainLeftEncoder.setMinRate(Constants.driveTrainLeftEncoderMinRate);
        driveTrainLeftEncoder.setDistancePerPulse(Constants.driveTrainLeftEncoderPulseDistance);

        driveTrainRightEncoder = new Encoder(Constants.driveTrainRightEncoderChannelA, Constants.driveTrainRightEncoderChannelB, true, CounterBase.EncodingType.k4X);
        driveTrainRightEncoder.setSamplesToAverage(Constants.driveTrainRightEncoderAverageSamples);
        driveTrainRightEncoder.setMinRate(Constants.driveTrainRightEncoderMinRate);
        driveTrainRightEncoder.setDistancePerPulse(Constants.driveTrainRightEncoderPulseDistance);

        driveGyro = new ADXRS450_Gyro();
    }

    public void drive(double leftspeed, double rightspeed) {

        //Setting a deadband for the joysticks.
        if (Math.abs(leftspeed) < 0.05) {
            leftspeed = 0;
        }
        if (Math.abs(rightspeed) < 0.05) {
            rightspeed = 0;
        }

        leftspeed = leftspeed * speedMultiplier;
        rightspeed = rightspeed * speedMultiplier;

        leftVictorSPX1.setInverted(false);
        leftVictorSPX2.setInverted(false);
        leftVictorSPX3.setInverted(false);
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

    public void shiftGear(boolean buttonPressed) {
        if (buttonPressed) {
            swapSolenoidPosition();
        }
    }

    private void swapSolenoidPosition() {
        if (this.intakeStatus == DoubleSolenoid.Value.kForward) {
            this.intakeStatus = DoubleSolenoid.Value.kReverse;
        } else {
            this.intakeStatus = DoubleSolenoid.Value.kForward;
        }
        gearShiftSolenoid.set(this.intakeStatus);
    }
}
