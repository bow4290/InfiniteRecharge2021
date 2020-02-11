package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.ColorSensorSubsystem;
import frc.robot.subsystems.WheelSpinnerSubsystem;

import java.util.Set;

public class ColorSensorCommand extends CommandBase {

    public ColorSensorSubsystem colorSensorSubsystem;
    public final Set<Subsystem> subsystems;
    WheelSpinnerSubsystem colorWheelSpinner;
    int colorCount;
    /**
     * this establishes the color sensor subsystem
     * @param colorSensorSubsystem
     */
    public ColorSensorCommand(ColorSensorSubsystem colorSensorSubsystem){

        this.colorSensorSubsystem = colorSensorSubsystem;
        this.subsystems = Set.of(colorSensorSubsystem);
    }

    public void wheelSpin(){
        do {
            if (colorSensorSubsystem.getColor() == colorSensorSubsystem.getkRedTarget()){
                colorCount++;
            }
        } while (colorCount <7);
    }

//    public void stopOnColor(){
//        if(Robot.getBooleanProperty() == 'B'){
//
//        }
//
//    }
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Set<Subsystem> getRequirements() {

        return this.subsystems;
    }
    public void execute(){

    }
}