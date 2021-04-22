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
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commandGroups.AutoCommandGroup;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.cscore.UsbCamera;

public class Robot extends TimedRobot {
    private AutoCommandGroup autoCommandGroup;
    private VictorSPXDriveCommand teleopVictorSPXDriveCommand;
    private ShootingCommand teleopShootingCommand;
    private ConveyorCommand teleopConveyorCommand;
    private IntakeCommand teleopIntakeCommand;
    private ClimberCommand teleopClimberCommand;
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

        driveTrainSubsystem.driveGyro.calibrate();

        autoCommandGroup = new AutoCommandGroup(driveTrainSubsystem, conveyorSubsystem, intakeSubsystem, shootingSubsystem);

        teleopVictorSPXDriveCommand = new VictorSPXDriveCommand(driveTrainSubsystem);
        teleopShootingCommand = new ShootingCommand(shootingSubsystem);
        teleopConveyorCommand = new ConveyorCommand(conveyorSubsystem);
        teleopIntakeCommand = new IntakeCommand(intakeSubsystem);
        teleopClimberCommand = new ClimberCommand(climberSubsystem);

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putBoolean("Button Value 1: ", ConveyorSubsystem.conveyorButton1.get());
        SmartDashboard.putBoolean("Button Value 2: ", ConveyorSubsystem.conveyorButton2.get());
        SmartDashboard.putNumber("Shooter Encoder Rate: ", shooterEncoder.getRate());
        SmartDashboard.putNumber("Left Encoder Distance: ", driveTrainSubsystem.driveTrainLeftEncoder.getDistance());
        SmartDashboard.putNumber("Right Encoder Distance: ", driveTrainSubsystem.driveTrainRightEncoder.getDistance());
        SmartDashboard.putNumber("Gyro Angle: ", driveTrainSubsystem.driveGyro.getAngle());
        SmartDashboard.putBoolean("Button State: ", ConveyorCommand.buttonState);
        SmartDashboard.putNumber("Ball Count: ", ConveyorCommand.ballCount);
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
        ConveyorCommand.ballCount = 0;
        intakeSubsystem.swapIntakeSolenoidPosition();

        System.out.println("scheduling autocommand");

        if (autoCommandGroup != null) {
            autoCommandGroup.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        ShootingCommand.mode = "IntakeMode";
        autoCommandGroup.cancel();
    }

    @Override
    public void teleopPeriodic() {
        teleopVictorSPXDriveCommand.schedule();
        teleopShootingCommand.schedule();
        teleopConveyorCommand.schedule();
        teleopIntakeCommand.schedule();
        teleopClimberCommand.schedule();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
