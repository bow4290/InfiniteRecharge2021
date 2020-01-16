/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class ExampleSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private VictorSP leftFrontMotor;
    private VictorSP leftBackMotor;
    private VictorSP rightFrontMotor;
    private VictorSP rightBackMotor;

    public ExampleSubsystem(){
        leftFrontMotor = new VictorSP(0);
        leftBackMotor = new VictorSP(1);
        rightFrontMotor = new VictorSP(2);
        rightBackMotor = new VictorSP(3);
    }


    public void drive(){
        motor.setSpeed(5);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
