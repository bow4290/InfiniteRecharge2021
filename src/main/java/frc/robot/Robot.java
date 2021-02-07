/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ShootingCommand;
import frc.robot.subsystems.*;
import edu.wpi.cscore.UsbCamera;
import frc.robot.commands.DriveForDistanceCommand;

public class Robot extends TimedRobot {
    private DriveForDistanceCommand autonomousDriveStraightCommand1;
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
    public static CameraServer server;

    private RobotContainer robotContainer;

    @Override
    public void robotInit() {
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
        
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(160, 120);

        compressor = new Compressor(Constants.compressorCANID);
        
        // Put Commands Here
        autonomousDriveStraightCommand1 = new DriveForDistanceCommand(driveTrainSubsystem, Constants.inchesToDriveForDriveForDistanceCommand1, 0);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("Shooter Encoder Rate:", shooterEncoder.getRate());

        SmartDashboard.putNumber("Left Encoder Distance" , driveTrainSubsystem.driveTrainLeftEncoder.getDistance());
        SmartDashboard.putNumber("Right Encoder Distance" , driveTrainSubsystem.driveTrainRightEncoder.getDistance());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        ShootingCommand.mode = "IntakeMode";
        if (autonomousDriveStraightCommand1 != null) {
            System.out.println("scheduling autocommand");
            autonomousDriveStraightCommand1.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (autonomousDriveStraightCommand1 != null) {
            autonomousDriveStraightCommand1.cancel();
        }

        ShootingCommand.mode = "IntakeMode";
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
