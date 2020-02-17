package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
    private final VictorSP topConveyorShooter;
    private final VictorSP bottomConveyorShooter;

    double speedFactor = 1.1;
    public ConveyorSubsystem(){
        topConveyorShooter = new VictorSP(1);
        bottomConveyorShooter = new VictorSP(0);
    }

    public void conveyBall(double conveyorSpeed){
        bottomConveyorShooter.set(conveyorSpeed);
        topConveyorShooter.set(speedFactor*conveyorSpeed);
    }

}