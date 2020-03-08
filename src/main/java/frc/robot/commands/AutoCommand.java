package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.dataStructures.RotationData;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShootingSubsystem;


public class AutoCommand extends CommandBase {
    private final ConveyorSubsystem conveyorSubsystem;
    private final DriveTrainSubsystem driveTrainSubsystem;
    private final LimelightSubsystem limelightSubsystem;
    private final ShootingSubsystem shootingSubsystem;
    private Robot robot;


    public AutoCommand(Robot robot, ConveyorSubsystem conveyorSubsystem, DriveTrainSubsystem driveTrainSubsystem, LimelightSubsystem limelightSubsystem, ShootingSubsystem shootingSubsystem) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.limelightSubsystem = limelightSubsystem;
        this.shootingSubsystem = shootingSubsystem;
        this.robot = robot;
        addRequirements(conveyorSubsystem, driveTrainSubsystem, limelightSubsystem, shootingSubsystem);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        System.out.println("autoinitialized");
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {

        /** time-based movement protocol*/
        /** run up to low-port and put in 3 balls*/
//        driveTrainSubsystem.drive(-.6, -.6);
//        Timer.delay(3.6);
//
//        driveTrainSubsystem.drive(0, 0);
//        shootingSubsystem.shootBall(.2);
//        conveyorSubsystem.conveyBall(1);
//        Timer.delay(5);
//
//        shootingSubsystem.shootBall(0);
//        conveyorSubsystem.conveyBall(0);



        /** lime-based movement protocol*/
        /** shoot into hex-port from multiple positions*/
//        RotationData rotationData = robot.sense();
//        robot.rotateWithLime(rotationData.getDegrees());
//        robot.moveWithLime(rotationData.getDistance(), );
//
//        driveTrainSubsystem.drive(0, 0);
//        shootingSubsystem.shootBall(.8);
//        conveyorSubsystem.conveyBall(0);
//        Timer.delay(1.5);
//
//        shootingSubsystem.shootBall(.8);
//        conveyorSubsystem.conveyBall(1);
//        Timer.delay(3);

        /**testing auto method*/
        shootingSubsystem.shootBall(.8);
        Timer.delay(600);
    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        return true;
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when  it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {

    }
}
