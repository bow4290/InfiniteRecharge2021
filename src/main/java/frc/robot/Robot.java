/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.IntakeCommand;
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
    public static ColorSensorSubsystem colorSensorSubsystem;
    public static LimelightSubsystem limelightSubsystem;
    public static ConveyorSubsystem conveyorSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public Robot robot;
    private Command autonomousCommand;


    boolean PositionPhase = true;
    boolean ShootPhase = false;
    boolean AwayPhase = false;

    double heightDifferenceInches = 83.25;

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

        int topMotorChannel = 9;
        int bottomMotorChannel = 7;

        int intakeMotorChannel = 13;

        int wheelSpinner = 2;

        final Color kBlueTarget = ColorMatch.makeColor(0, .3, .3);
        final Color kGreenTarget = ColorMatch.makeColor(0, .4, 0);
        final Color kRedTarget = ColorMatch.makeColor(.4, 0, 0);
        final Color kYellowTarget = ColorMatch.makeColor(.3, .3, 0);


        driveTrainSubsystem = new DriveTrainSubsystem(leftVictorSPX1Channel, leftVictorSPX2Channel, leftVictorSPX3Channel,
                rightVictorSPX1Channel, rightVictorSPX2Channel, rightVictorSPX3Channel);
        colorSensorSubsystem = new ColorSensorSubsystem(kBlueTarget, kGreenTarget, kRedTarget, kYellowTarget);
        shootingSubsystem = new ShootingSubsystem(leftShooterChannel, rightShooterChannel);

        conveyorSubsystem = new ConveyorSubsystem(topMotorChannel, bottomMotorChannel);

        limelightSubsystem = new LimelightSubsystem();

        intakeSubsystem = new IntakeSubsystem(intakeMotorChannel);

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

        Color detectedColor = colorSensorSubsystem.getColor();

        System.out.println("The color is " + colorSensorSubsystem.matchColor(detectedColor));

        double getIR = colorSensorSubsystem.getIR();

        SmartDashboard.putNumber("IR", getIR);
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", getIR);

        int getProximity = colorSensorSubsystem.getProximity();
        SmartDashboard.putNumber("Proximity", getProximity);

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
        autonomousCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        System.out.println("I am inside autoPeriodic!");
        rotateWithTime(270);
        wait(1000);
        move(12);

//        // uncomment when testing is done.
//        do {
//            limelightSubsystem.turnOnLED();
//            RotationData rotationData = sense();
//            rotateWithTime(90);
//            move(calculateOffset(limelightSubsystem.getTx()));
//            rotateWithLime(limelightSubsystem.getTx());
//            move(calculateDistance(limelightSubsystem.getTy()));
//
//            ShootPhase = true;
//            PositionPhase = false;
//        } while (PositionPhase = true);
//
//        do {
//            shoot(.6);
//            wait(1000);
//            convey(.6);
//            shoot(0);
//            convey(0);
//            //shoot'n stuff
//
//            AwayPhase = true;
//            ShootPhase = false;
//        } while (ShootPhase = true);
//
//        do {
//            rotateWithTime(90);
//            move(45);
//            rotateWithTime(-90);
//            limelightSubsystem.turnOffLED();
//
//            AwayPhase = false;
//        } while (AwayPhase = true);
    }

    public void rotateWithLime(double degrees) {
        double turnSpeed = .5;

        /**
         * if the target is to the right, move left side forward and right side back.
         * if the target is to the left, move right side forward and left side back.
         */
        if (degrees > 0) {
            do {
                driveTrainSubsystem.drive(turnSpeed, -turnSpeed);
            } while (degrees != 0);
        } else if (degrees < 0) {
            do {
                driveTrainSubsystem.drive(-turnSpeed, turnSpeed);
            } while (degrees != 0);
        }
    }

    public void rotateWithTime(double degrees) {
        System.out.println("I'm inside the rotateWithTime method!");
        double turnSpeed = .6;
        double degreeTime = 41 / 6000;
        //for time, measure how long it take to turn 360 degrees, then divide by 360.

        long t = System.currentTimeMillis();
        double degreeToTime = degreeTime * degrees;
        long time = (long) degreeToTime;
        long end = t + time;
        System.out.println("the time it should take is... " + degreeToTime);
        System.out.println(degreeToTime + "should be equal to... " + time);
        System.out.println("the rotation end time is... " + end);
        while (System.currentTimeMillis() < end) {
            driveTrainSubsystem.drive(turnSpeed, -turnSpeed);
        }
    }

    public void shoot(double speedPercentage) {
        shootingSubsystem.shootBall(speedPercentage);
    }

    public void convey(double speedPercentage) {
        conveyorSubsystem.conveyBall(speedPercentage);

    }


    public void wait(int timeInMillis) {
        long t = System.currentTimeMillis();
        long end = t + timeInMillis;

        while (System.currentTimeMillis() < end) {
            conveyorSubsystem.conveyBall(0);
        }

    }

    public void move(double inches) {
        System.out.println("I'm inside the move method!");
        double turnSpeed = -.6;
        double inchTime = 323 / 12000;

        //for time, measure how long it take to move 10 feet, then divide by 120.

        long t = System.currentTimeMillis();
        double distanceToTime = inchTime * inches;
        long time = (long) distanceToTime;
        long end = t + time;
        System.out.println("the time it should take is... " + distanceToTime);
        System.out.println(distanceToTime + "should be equal to... " + time);
        System.out.println("the move end time is... " + end);
        while (System.currentTimeMillis() < end) {
            driveTrainSubsystem.drive(turnSpeed, turnSpeed);
        }
    }

    public RotationData sense() {
        double degrees = limelightSubsystem.getTx();
        double verticalDegrees = limelightSubsystem.getTy();
        double distance = calculateDistance(verticalDegrees);
        double offset = calculateOffset(distance);
        RotationData rotationData = new RotationData(degrees, distance);
        return rotationData;
    }

    public double calculateDistance(double verticalDegrees) {

        double distance = heightDifferenceInches / Math.tan(verticalDegrees);
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
    }

    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
//        System.out.println("Raw color is" + colorSensorSubsystem.m_colorSensor.getRawColor());
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

}
