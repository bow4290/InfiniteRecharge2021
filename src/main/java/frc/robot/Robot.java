/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;
import frc.robot.dataStructures.RotationData;
import frc.robot.subsystems.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * methods corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
    public static ShootingSubsystem shootingSubsystem;
    public static DriveTrainSubsystem driveTrainSubsystem;
    //    public static ColorSensorSubsystem colorSensorSubsystem;
    public static LimelightSubsystem limelightSubsystem;
    public static ConveyorSubsystem conveyorSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static ClimberSubsystem climberSubsystem;
    public Robot robot;
    private Command autonomousCommand;
    private Compressor compressor;
    private DoubleSolenoid doubleSolenoid;


    boolean PositionPhase = true;
    boolean ShootPhase = false;
    boolean AwayPhase = false;
    public DoubleSolenoid intakeSolenoid;
    public DoubleSolenoid gearShiftSolenoid;
    public DoubleSolenoid fallStopSolenoid;

    double heightDifferenceInches = 78.75;

    private RobotContainer robotContainer;
    private static I2C.Port i2cPort = I2C.Port.kOnboard;


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override

    public void robotInit() {
        int leftVictorSPX1Channel = 12;
        int leftVictorSPX2Channel = 11;
        int leftVictorSPX3Channel = 10;
        int rightVictorSPX1Channel = 5;
        int rightVictorSPX2Channel = 3;
        int rightVictorSPX3Channel = 6;

        int leftShooterChannel = 8;
        int rightShooterChannel = 1;

        int climberMotorChannel = 4;

        int topMotorChannel = 9;
        int bottomMotorChannel = 7;

        int intakeMotorChannel = 13;

        int wheelSpinner = 2;

        compressor = new Compressor(0);

        boolean enabled = compressor.enabled();
        boolean pressureSwitch = compressor.getPressureSwitchValue();
        double current = compressor.getCompressorCurrent();

        intakeSolenoid = new DoubleSolenoid(0, 1);
        fallStopSolenoid = new DoubleSolenoid(2, 3);
        gearShiftSolenoid = new DoubleSolenoid(4, 5);


        final Color kBlueTarget = ColorMatch.makeColor(0, .3, .3);
        final Color kGreenTarget = ColorMatch.makeColor(0, .4, 0);
        final Color kRedTarget = ColorMatch.makeColor(.4, 0, 0);
        final Color kYellowTarget = ColorMatch.makeColor(.3, .3, 0);


        driveTrainSubsystem = new DriveTrainSubsystem(gearShiftSolenoid, leftVictorSPX1Channel, leftVictorSPX2Channel, leftVictorSPX3Channel,
                rightVictorSPX1Channel, rightVictorSPX2Channel, rightVictorSPX3Channel);
//        colorSensorSubsystem = new ColorSensorSubsystem(kBlueTarget, kGreenTarget, kRedTarget, kYellowTarget);
        shootingSubsystem = new ShootingSubsystem(leftShooterChannel, rightShooterChannel, this);

        conveyorSubsystem = new ConveyorSubsystem(topMotorChannel, bottomMotorChannel);

        climberSubsystem = new ClimberSubsystem(fallStopSolenoid, climberMotorChannel);

        limelightSubsystem = new LimelightSubsystem();

        intakeSubsystem = new IntakeSubsystem(intakeSolenoid, intakeMotorChannel);
        autonomousCommand = new AutoCommand(this, conveyorSubsystem, driveTrainSubsystem, limelightSubsystem, shootingSubsystem, intakeSubsystem);

        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        robotContainer = new RobotContainer();
    }

    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
//        System.out.println("here");

//        Color detectedColor = colorSensorSubsystem.getColor();

//        System.out.println("The color is " + colorSensorSubsystem.matchColor(detectedColor));

//        double getIR = colorSensorSubsystem.getIR();
//
//        SmartDashboard.putNumber("IR", getIR);
//        SmartDashboard.putNumber("Red", detectedColor.red);
//        SmartDashboard.putNumber("Green", detectedColor.green);
//        SmartDashboard.putNumber("Blue", detectedColor.blue);
//        SmartDashboard.putNumber("IR", getIR);

//        CameraServer.getInstance().startAutomaticCapture(0);

//        int getProximity = colorSensorSubsystem.getProximity();
//        SmartDashboard.putNumber("Proximity", getProximity);

    }

    /**
     * This method is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        System.out.println("I am inside autoinit!");

//        rotateWithTime(270);
//        wait(2);
//        move(6);
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            System.out.println("scheduling autocommand");
            autonomousCommand.schedule();
        }
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    public void rotateWithLime(double degrees) {
        double turnSpeed = .6;
        System.out.println("degrees is equal to... " + degrees);
        /**
         * if the target is to the right, move left side forward and right side back.
         * if the target is to the left, move right side forward and left side back.
         */
        if (degrees > 0) {
            driveTrainSubsystem.drive(turnSpeed, -turnSpeed);
            Timer.delay(1);
            driveTrainSubsystem.drive(0, 0);
        } else if  (degrees < 0) {
            driveTrainSubsystem.drive(-turnSpeed, turnSpeed);
            Timer.delay(1);
            driveTrainSubsystem.drive(0, 0);
        }
    }

    public void rotateWithTime(double degrees) {
        System.out.println("I'm inside the rotateWithTime method!");
        double turnSpeed = .6;
        double secondsPerDegree = 0.007;
        if (degrees < 0) {
            driveTrainSubsystem.drive(turnSpeed, -turnSpeed);
        } else if (degrees > 0) {
            driveTrainSubsystem.drive(-turnSpeed, turnSpeed);
        }
        Timer.delay(secondsPerDegree * degrees);
    }

    public double moveWithLime(double currentDistanceInches, double targetDistanceInches) {
        double turnSpeed = 0;
        double moveTime = 0;
        if (currentDistanceInches != 0) {
            double distanceDifference = targetDistanceInches - currentDistanceInches;

            if (distanceDifference < 0) {
                turnSpeed = .6;
            } else if (distanceDifference > 0) {
                turnSpeed = -.6;
            }
            driveTrainSubsystem.drive(-turnSpeed, -turnSpeed);

            double secondsPerInch = .03;
            moveTime = secondsPerInch * distanceDifference;
        }
        return moveTime;
    }


    public RotationData sense() {
        double degrees = limelightSubsystem.getTx();
        double verticalDegrees = limelightSubsystem.getTy();
        double distance = calculateDistance(verticalDegrees);
        double offset = calculateOffset(degrees);
        RotationData rotationData = new RotationData(degrees, distance, offset);
        return rotationData;
    }

    public double calculateDistance(double verticalDegrees) {
        double distance = heightDifferenceInches / Math.tan(verticalDegrees + 38.5);
        return distance;
    }

    public double calculateOffset(double degrees) {
        double offset = Math.tan(degrees) * calculateDistance(limelightSubsystem.getTy());
        return offset;
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        limelightSubsystem.limelightIsOn(false);
        limelightSubsystem.cameraMode(false);
    }

    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
