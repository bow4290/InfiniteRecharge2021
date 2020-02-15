package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.ColorSensorCommand;

/**
 * this are the main functions for the color sensor
 */
public class ColorSensorSubsystem extends SubsystemBase {
    private static I2C.Port i2cPort = I2C.Port.kOnboard;

    public ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public ColorMatch m_colorMatcher = new ColorMatch();

    public Color kBlueTarget;
    public Color kGreenTarget;
    public Color kRedTarget;
    public Color kYellowTarget;

    /**
     * this constructs the color values
     *
     * @param kBlueTarget   this makes the blue value
     * @param kGreenTarget  this makes the green value
     * @param kRedTarget    this makes the red value
     * @param kYellowTarget this makes the yellow value
     */
    public ColorSensorSubsystem(Color kBlueTarget, Color kGreenTarget, Color kRedTarget, Color kYellowTarget) {

        this.kBlueTarget = kBlueTarget;
        this.kGreenTarget = kGreenTarget;
        this.kRedTarget = kRedTarget;
        this.kYellowTarget = kYellowTarget;

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    /**
     * this is comparing the color values and connecting them to the correct color
     *
     * @param match this is matching the colors with the color values
     * @return the color that is found
     */
    public String matchColor(Color match) {

//        System.out.println("red is " + match.red);
//        System.out.println("green is" + match.green);
//        System.out.println("blue is" + match.blue);

        String colorString;

        if (match.blue >= this.kBlueTarget.blue && match.green >= this.kBlueTarget.green) {
            colorString = "Blue";
        } else if (match.red >= this.kRedTarget.red) {
            colorString = "Red";
        } else if (match.red >= this.kYellowTarget.red && match.green >= this.kYellowTarget.green) {
            colorString = "Yellow";
        } else if (match.green >= this.kGreenTarget.green) {
            colorString = "Green";

        } else {
            colorString = "Unknown";
        }
        System.out.println("The color is" + colorString);

        return colorString;

    }

    /**
     * this is getting the color that is being sensed
     *
     * @return the color that sensed
     */
    public Color getColor() {

        return m_colorSensor.getColor();
    }

    /**
     * this is getting the proximity from the sensed color
     *
     * @return the proximity value
     */
    public int getProximity() {

        return m_colorSensor.getProximity();
    }

    /**
     * this is getting the IR values
     *
     * @return the IR value
     */
    public double getIR() {

        return m_colorSensor.getIR();
    }

    public Color getkRedTarget(){
        return this.kRedTarget;
    }

        public Color getGameSpecificMethod(){
            char gameDataColor;
            Color gameColor;
            gameColor = Color.kBlack;
            String gameData;
            gameData = DriverStation.getInstance().getGameSpecificMessage();
            if(gameData.length() > 0)
            {
                switch (gameData.charAt(0))
                {
                    case 'B' :
                        gameDataColor = 'B';
                        gameColor = kBlueTarget;
                        break;

                    case 'G' :
                        gameDataColor = 'G';
                        gameColor = kGreenTarget;
                        break;

                    case 'R' :
                        gameDataColor = 'R';
                        gameColor = kRedTarget;
                        break;

                    case 'Y' :
                        gameDataColor = 'Y';
                        gameColor = kYellowTarget;
                        break;

                    default :
                        //This is corrupt data
                        break;
                }
            } else {
                //Code for no data received yet
            }
            return gameColor;
        }
}
