package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANdleCOnstants;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;

public class candle extends SubsystemBase{
    private final CANdle candle = new CANdle(CANdleCOnstants.ID);

    public RainbowAnimation RainbowState = new RainbowAnimation(0, 0, 0); // Rainbow
    public LarsonAnimation TagDectected = new LarsonAnimation(225, 0, 225); // Purple  // sends a pocket of light across the LED strip
    public StrobeAnimation TagAligning = new StrobeAnimation(225, 255, 0); // Yellow  // strobes the LEDs a specified color
    public StrobeAnimation TagAligned = new StrobeAnimation(0, 225, 225); // Blue

    public static final double BlinkSpeed = 0.4; //To Do

    public candle(){
        candle.configFactoryDefault();
        candle.configLEDType(LEDStripType.RGB);
    }

    public void SetDefault(){  // Rainbow
        candle.animate(RainbowState);
        RainbowState.setSpeed(0.4);   //To Do
        RainbowState.setNumLed(8); //To Do
    }
    
    public void TagDetected(){  // Purple
        candle.animate(TagDectected);
        TagDectected.setSpeed(0.4); //To Do
        TagDectected.setNumLed(8); //To Do
        TagDectected.setSize(5); 
    }

    public void TagAligning(){  // Yellow
        candle.animate(TagAligning);
        TagAligning.setSpeed(BlinkSpeed);
    }

    public void TagAligned(){  // Blue
        candle.animate(TagAligned);
        TagAligning.setSpeed(BlinkSpeed);
    }
    
    @Override
    public void periodic(){
        Boolean isDetected = SmartDashboard.getBoolean("Deteced?",false);
        double FiducialID = SmartDashboard.getNumber("Fiducial_ID", 0);
        double TagArea = SmartDashboard.getNumber("TagArea", 0);

        double Output_X = SmartDashboard.getNumber("Output_X", 0);
        double Output_Y = SmartDashboard.getNumber("Output_Y", 0);

        if(isDetected = false){
            SetDefault();
        }
        else if(isDetected = true){
            TagDetected();

            if(Output_X != 0 || Output_Y != 0){
                TagAligning();
            }
            else{
                TagAligned();
            }
        }
    }
}
