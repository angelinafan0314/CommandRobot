package frc.robot.subsystems;

import java.util.concurrent.atomic.DoubleAdder;

import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.LimelightConstants;

public class Limelight extends SubsystemBase{
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable(LimelightConstants.Name); 
    
    boolean Detected;
    double ID;
    double tA;
    double tX;
    
    public double getFiducialID(){
        double ID = NetworkTableInstance.getDefault().getTable("").getEntry("tid").getDouble(0); // Get Fiducial ID
        if (ID >= 1 & ID <= 16){
            return ID;    
        }
        else{
            return 0;
        }
    }
    
    public double getTA(){
        double tA = LimelightHelpers.getTA(""); // Target area (0% to 100% of image)
        return tA;
    }

    public double getTX(){
        double tX = LimelightHelpers.getTX("");  // Horizontal offset from crosshair to target in degrees
        return tX;
    }
    
    //!
    @Override
    public void periodic(){
        // 3D transform of the camera in the coordinate system of the robot (array (6))[tx, ty, tz, pitch, yaw, roll] (meters, degrees)
        NetworkTableInstance.getDefault().getTable("").getEntry("camerapose_robotspace").getDoubleArray(new double[6]);
    
        if(ID >= 1 & ID <= 16){
            SmartDashboard.putBoolean("Deteced?", true);
            SmartDashboard.putNumber("Fiducial_ID", getFiducialID());
            SmartDashboard.putNumber("TagArea", getTA());
            SmartDashboard.putNumber("TagXPose", getTX());
        }
        else{
            SmartDashboard.putBoolean("Deteced?", false);
        }
    }
}