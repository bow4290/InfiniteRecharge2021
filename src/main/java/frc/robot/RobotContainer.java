/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class RobotContainer {


    public static Joystick joystickLeft;
    public static Joystick joystickRight;
    public static XboxController xboxController;

    public RobotContainer() {
        joystickLeft = new Joystick(Constants.LEFT_JOYSTICK);
        joystickRight = new Joystick(Constants.RIGHT_JOYSTICK);
        xboxController = new XboxController(Constants.XBOX_CONTROLLER);
        // Configure the button bindings
        configureButtonBindings();
    }
    private void configureButtonBindings() {

    }
}
