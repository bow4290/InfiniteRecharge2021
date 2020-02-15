/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ColorSensorCommand;
import frc.robot.subsystems.ColorSensorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShootingSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * methods corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot
{
    public static ShootingSubsystem shootingSubsystem;
    public static DriveTrainSubsystem driveTrainSubsystem;
    public static ColorSensorSubsystem colorSensorSubsystem;
    public Robot robot;
    private Command autonomousCommand;


    private RobotContainer robotContainer;
    private static I2C.Port i2cPort = I2C.Port.kOnboard;


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override

    public void robotInit() {
        int leftVictorSPChannel1 = 1;
        int leftVictorSPChannel2 = 2;
        int leftVictorSPChannel3 = 3;
        int leftVictorSPChannel4 = 3;
        int rightVictorSPChannel1 = 4;
        int rightVictorSPChannel2 = 5;
        int rightVictorSPChannel3 = 6;
        int rightVictorSPChannel4 = 7;

        int intakeMotorChannel = 7;

        int wheelSpinner = 9;

        final Color kBlueTarget = ColorMatch.makeColor(0, .3, .3);
        final Color kGreenTarget = ColorMatch.makeColor(0, .4, 0);
        final Color kRedTarget = ColorMatch.makeColor(.4, 0, 0);
        final Color kYellowTarget = ColorMatch.makeColor(.3, .3, 0);



        driveTrainSubsystem = new DriveTrainSubsystem(leftVictorSPChannel1, leftVictorSPChannel2, leftVictorSPChannel3,
                leftVictorSPChannel4, rightVictorSPChannel1, rightVictorSPChannel2,
                rightVictorSPChannel3, rightVictorSPChannel4);
        colorSensorSubsystem = new ColorSensorSubsystem(kBlueTarget, kGreenTarget, kRedTarget, kYellowTarget);
        shootingSubsystem = new ShootingSubsystem();

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

    }

    public void rotate(double degrees){
        //rotate robot x degrees
    }

    public void move(double distance){
        //move x units far
    }

    public void sense(){
        //call various methods from limelight subsystem
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
