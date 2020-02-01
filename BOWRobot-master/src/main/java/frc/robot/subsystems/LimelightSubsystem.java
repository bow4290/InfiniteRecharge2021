package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * this subsystem creates the main functions for the limelight
 */
public class LimelightSubsystem {

    public NetworkTable networkTable;

    public LimelightSubsystem() {

        networkTable = NetworkTableInstance.getDefault().getTable("limelight");


    }

    /**
     * This returns the entry for the specific entry code
     *
     * @param networktableentry this is a general entry that can be replaced for any variable on the table
     * @return the value of the entry
     */
    public double getEntry(String networktableentry) {

        return networkTable.getEntry(networktableentry).getDouble(0);

    }
}
