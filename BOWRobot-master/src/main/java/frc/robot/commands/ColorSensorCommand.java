package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.subsystems.ColorSensorSubsystem;
import frc.robot.subsystems.WheelSpinnerSubsystem;
import edu.wpi.first.wpilibj.VictorSP;


import java.util.Set;

public class ColorSensorCommand extends CommandBase {

    public ColorSensorSubsystem colorSensorSubsystem;
    public final Set<Subsystem> subsystems;
    public WheelSpinnerSubsystem wheelSpinnerSubsystem;
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
            if (colorSensorSubsystem.getColor() == colorSensorSubsystem.getkRedTarget()) {
                colorCount++;

                wheelSpinnerSubsystem.wheelSpinner.setSpeed(.65);

            }
        } while (colorCount <7);

        wheelSpinnerSubsystem.wheelSpinner.setSpeed(0);
}

    public void stopOnColor(){
        do {
            wheelSpinnerSubsystem.wheelSpinner.setSpeed(.25);
        }while (colorSensorSubsystem.getGameSpecificMethod() != colorSensorSubsystem.getColor());
    }

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