package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class ColorSensorSubsystem extends SubsystemBase {
    private static I2C.Port i2cPort = I2C.Port.kOnboard;

    public ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public ColorMatch m_colorMatcher = new ColorMatch();

    private Color kBlueTarget;
    private Color kGreenTarget;
    private Color kRedTarget;
    private Color kYellowTarget;

    public ColorSensorSubsystem( Color kBlueTarget, Color kGreenTarget, Color kRedTarget, Color kYellowTarget){

        this.kBlueTarget = kBlueTarget;
        this.kGreenTarget = kGreenTarget;
        this.kRedTarget = kRedTarget;
        this.kYellowTarget = kYellowTarget;

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    public String matchColor(ColorMatchResult match) {

        String colorString;

         if(match.color == this.kBlueTarget) {
             colorString = "Blue";
         }  else if(match.color == this.kRedTarget) {
             colorString = "Red";
         }  else if(match.color == this.kGreenTarget) {
             colorString = "Green";
         } else if(match.color == this.kYellowTarget) {
             colorString = "Yellow";
         } else {
             colorString = "Unknown";
         }
         System.out.println("The color is" + colorString);

        return colorString;

     }
}
