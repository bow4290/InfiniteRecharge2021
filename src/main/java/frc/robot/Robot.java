/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.ShootingCommand;
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
    public DoubleSolenoid shooterSolenoid;

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

        intakeSolenoid = new DoubleSolenoid(0,1);
        shooterSolenoid = new DoubleSolenoid(2,3);
        gearShiftSolenoid = new DoubleSolenoid(4, 5);


        final Color kBlueTarget = ColorMatch.makeColor(0, .3, .3);
        final Color kGreenTarget = ColorMatch.makeColor(0, .4, 0);
        final Color kRedTarget = ColorMatch.makeColor(.4, 0, 0);
        final Color kYellowTarget = ColorMatch.makeColor(.3, .3, 0);


        driveTrainSubsystem = new DriveTrainSubsystem(gearShiftSolenoid, leftVictorSPX1Channel, leftVictorSPX2Channel, leftVictorSPX3Channel,
                rightVictorSPX1Channel, rightVictorSPX2Channel, rightVictorSPX3Channel);
//        colorSensorSubsystem = new ColorSensorSubsystem(kBlueTarget, kGreenTarget, kRedTarget, kYellowTarget);
        shootingSubsystem = new ShootingSubsystem(shooterSolenoid, leftShooterChannel, rightShooterChannel, this.robot);

        conveyorSubsystem = new ConveyorSubsystem(topMotorChannel, bottomMotorChannel);

        climberSubsystem = new ClimberSubsystem(climberMotorChannel);

        limelightSubsystem = new LimelightSubsystem();

        intakeSubsystem = new IntakeSubsystem(intakeSolenoid, intakeMotorChannel);
        autonomousCommand = new AutoCommand(this, conveyorSubsystem, driveTrainSubsystem, limelightSubsystem, shootingSubsystem);

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
        SmartDashboard.putNumber("Shooter Encoder Rate:", conveyorSubsystem.shooterEncoder.getRate());
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
//        System.out.println("I am inside autoPeriodic!");
//        rotateWithTime(270);
//        wait(2);
//        move(6);

        // uncomment when testing is done.
//        do {
//            limelightSubsystem.limelightIsOn(true);
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
//            shoot(1, 5000);
//            wait(1000);
//            convey(1, 5000);
//            shoot(0, 0050);
//            convey(0, 0050);
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
//            limelightSubsystem.limelightIsOn(false);
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
        double secondsPerDegree = 0.007;
        double millisPerDegree = secondsPerDegree * 1000;
        //for time, measure how long it take to turn 360 degrees, then divide by 360.

        long t = System.currentTimeMillis();
        double degreeToTime = millisPerDegree * degrees;
        long time = (long) degreeToTime;
        long end = t + time;
        System.out.println("the time it should take is... " + degreeToTime + "\n\n\n");
        System.out.println(degreeToTime + "should be equal to... " + time+ "\n\n\n");
        System.out.println("the rotation end time is... " + end+ "\n\n\n");
        do {
            System.out.println("current time is: " + System.currentTimeMillis());
            driveTrainSubsystem.drive(turnSpeed, -turnSpeed);
        } while (System.currentTimeMillis() < end);
    }

    public void shoot(double speedPercentage, int shootTimeMillis) {
        long t = System.currentTimeMillis();
        long end = t + shootTimeMillis;

        while (System.currentTimeMillis() < end) {
            shootingSubsystem.shootBall(speedPercentage);
        }
    }

    public void convey(double speedPercentage, int conveyTimeMillis) {
        long t = System.currentTimeMillis();
        long end = t + conveyTimeMillis;

        while (System.currentTimeMillis() < end) {
            conveyorSubsystem.conveyBall(speedPercentage);
        }

    }


    public void wait(int timeInSeconds) {
        long t = System.currentTimeMillis();
        long timeInMillis =  timeInSeconds *1000;
        long end = t + timeInMillis;

        while (System.currentTimeMillis() < end) {
            conveyorSubsystem.conveyBall(0);
        }

    }

    public void move(double inches) {
        System.out.println("I'm inside the move method!");
        double turnSpeed = .6;
        double secondsPerInch = .03;
        double millisPerInch = secondsPerInch * 1000;

        //for time, measure how long it take to move 10 feet, then divide by 120.

        long t = System.currentTimeMillis();
        double distanceToTime = millisPerInch * inches;
        long time = (long) distanceToTime;
        long end = t + time;
        System.out.println("the time it should take is... " + distanceToTime);
        System.out.println(distanceToTime + "should be equal to... " + time);
        System.out.println("the move end time is... " + end);
        do {
            driveTrainSubsystem.drive(turnSpeed, turnSpeed);
        } while (System.currentTimeMillis() < end);
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
