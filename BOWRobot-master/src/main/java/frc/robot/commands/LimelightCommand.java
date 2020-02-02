package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.LimelightSubsystem;

import java.util.Set;

public class LimelightCommand extends CommandBase {

    private LimelightSubsystem limelightSubsystem;
    private final Set<Subsystem> subsystems;

    public LimelightCommand(LimelightSubsystem limelightSubsystem){
        this.limelightSubsystem = limelightSubsystem;
        this.subsystems = Set.of(limelightSubsystem);
    }

    public void execute() {

    }
}
