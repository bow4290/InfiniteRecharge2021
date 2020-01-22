package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class ColorSensorSubsystem extends SubsystemBase {
    private static I2C.Port i2cPort = I2C.Port.kOnboard;

    public static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public static ColorMatch m_colorMatcher = new ColorMatch();
}
