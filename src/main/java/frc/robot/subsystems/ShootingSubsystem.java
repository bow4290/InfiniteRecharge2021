package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.ShootingCommand;

public class ShootingSubsystem extends SubsystemBase {
    private final VictorSPX leftShooter;
    private final VictorSPX rightShooter;
    public Robot robot;
    public DoubleSolenoid shooterSolenoid;
    public DoubleSolenoid.Value intakeStatus;



    public ShootingSubsystem(DoubleSolenoid shooterSolenoid, int leftShooterChannel, int rightShooterChannel, Robot robot) {
        leftShooter = new VictorSPX(leftShooterChannel);
        rightShooter = new VictorSPX(rightShooterChannel);
        setDefaultCommand(new ShootingCommand(this));
        this.robot = robot;
        this.shooterSolenoid = shooterSolenoid;
        intakeStatus = DoubleSolenoid.Value.kReverse;
    }

    public void shootBall(double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
        rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
    }


    public void dualShoot(boolean manualActive, double shooterSpeed) {
        leftShooter.setInverted(true);
        rightShooter.setInverted(true);
        if (manualActive){
            if (shooterSpeed > 1) {
                shooterSpeed = 1;
            }
            if (shooterSpeed < 0) {
                shooterSpeed = 0;
            }
            if(ShootingCommand.mode == "IDLE"){
                leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
                rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
            } else{
            leftShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed);
            rightShooter.set(VictorSPXControlMode.PercentOutput, shooterSpeed*0.85);
           }
        }
        else{
            leftShooter.set(VictorSPXControlMode.PercentOutput, 0);
            rightShooter.set(VictorSPXControlMode.PercentOutput, 0);
        }
    }

    public void moveConveyor(boolean buttonPressed){
        if (buttonPressed) {
            swapSolenoidPosition();
        }
    }

    private void swapSolenoidPosition() {

        if (intakeStatus == DoubleSolenoid.Value.kForward) {
            intakeStatus = DoubleSolenoid.Value.kReverse;
        } else {
            intakeStatus = DoubleSolenoid.Value.kForward;
        }
        shooterSolenoid.set(intakeStatus);
    }
}
