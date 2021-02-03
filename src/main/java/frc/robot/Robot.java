/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ShootingCommand;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
    public static ShootingSubsystem shootingSubsystem;
    public static DriveTrainSubsystem driveTrainSubsystem;
    public static ConveyorSubsystem conveyorSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static ClimberSubsystem climberSubsystem;
    public Robot robot;
    private Compressor compressor;
    private DoubleSolenoid doubleSolenoid;
    public static Encoder shooterEncoder;
    public DoubleSolenoid intakeSolenoid;
    public DoubleSolenoid gearShiftSolenoid;
    public DoubleSolenoid shooterSolenoid;

    private RobotContainer robotContainer;
    private static I2C.Port i2cPort = I2C.Port.kOnboard;

    @Override
    public void robotInit() {
        compressor = new Compressor(Constants.compressorCANID);

        boolean enabled = compressor.enabled();
        boolean pressureSwitch = compressor.getPressureSwitchValue();
        double current = compressor.getCompressorCurrent();

        intakeSolenoid = new DoubleSolenoid(Constants.intakeSolenoidForwardChannel, Constants.intakeSolenoidReverseChannel);
        shooterSolenoid = new DoubleSolenoid(Constants.shooterSolenoidForwardChannel, Constants.shooterSolenoidReverseChannel);
        gearShiftSolenoid = new DoubleSolenoid(Constants.gearShiftSolenoidForwardChannel, Constants.gearShiftSolenoidReverseChannel);


        driveTrainSubsystem = new DriveTrainSubsystem(gearShiftSolenoid, Constants.leftVictorSPX1Channel, Constants.leftVictorSPX2Channel, Constants.leftVictorSPX3Channel,
                Constants.rightVictorSPX1Channel, Constants.rightVictorSPX2Channel, Constants.rightVictorSPX3Channel);
        shootingSubsystem = new ShootingSubsystem(shooterSolenoid, Constants.leftShooterChannel, Constants.rightShooterChannel, this.robot);

        conveyorSubsystem = new ConveyorSubsystem(Constants.topMotorChannel, Constants.bottomMotorChannel);

        climberSubsystem = new ClimberSubsystem(Constants.climberMotorChannel, Constants.wheelSpinnerChannel);

        intakeSubsystem = new IntakeSubsystem(intakeSolenoid, Constants.intakeMotorChannel);

        shooterEncoder = new Encoder(Constants.shooterEncoderChannelA, Constants.shooterEncoderChannelB, true, CounterBase.EncodingType.k4X);
        shooterEncoder.setSamplesToAverage(Constants.shooterEncoderAverageSamples);

        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("Shooter Encoder Rate:", shooterEncoder.getRate());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        ShootingCommand.mode = "IDLE";
        //if (autonomousCommand != null) {
            //System.out.println("scheduling autocommand");
            //autonomousCommand.schedule();
        //}
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        //if (autonomousCommand != null) {
            //autonomousCommand.cancel();
        //}

        ShootingCommand.mode = "IDLE";
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
