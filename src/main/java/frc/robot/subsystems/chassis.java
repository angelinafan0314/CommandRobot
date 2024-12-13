// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.chassisID;

public class chassis extends SubsystemBase {
  private final WPI_VictorSPX FrontLeft = new WPI_VictorSPX(chassisID.FL);
  private final WPI_VictorSPX FrontRight = new WPI_VictorSPX(chassisID.FR);
  private final WPI_VictorSPX RearLeft = new WPI_VictorSPX(chassisID.RL);
  private final WPI_VictorSPX RearRight = new WPI_VictorSPX(chassisID.RR);

  private DifferentialDrive tank = new DifferentialDrive(FrontLeft, FrontRight);

  private boolean TagDetected;
  private double TagArea;
  private double TagXPose;

  private double outputX;
  private double outputY;

  public chassis() {
    FrontLeft.configFactoryDefault();
    FrontRight.configFactoryDefault();
    RearLeft.configFactoryDefault();
    RearRight.configFactoryDefault();

    FrontRight.setInverted(true);
    RearRight.setInverted(true);

    RearLeft.follow(FrontLeft);
    RearRight.follow(FrontRight);
  }

  public void drive(double X, double Y){
    tank.arcadeDrive(-X, Y);
  }
  
  @Override
  public void periodic(){
    TagDetected = SmartDashboard.getBoolean("Deteced?", false);
    // TagArea = SmartDashboard.getNumber("TagArea", 0);
    TagArea = LimelightHelpers.getTA("");
    // TagXPose = SmartDashboard.getNumber("TagXPose", 0);
    TagXPose = LimelightHelpers.getTX("");

    SmartDashboard.putNumber("Output_X", outputX);
    SmartDashboard.putNumber("Output_Y", outputY);
  }
  
  public void TagAim(){
    boolean isDeteced = TagDetected;
    double Area = TagArea;
    double XPose = TagXPose;

    System.out.println(Area);
    
    //drive
    if(Area > 0.0 & Area < 10.0){
        outputX = -0.35;
    }
    else if (Area > 15.0) {
        outputX = 0;
    }
    else if(!isDeteced){
        outputX = 0;
    }

   //angle
    if(XPose > 330.0){
        outputY = -0.4;
    }
    else if(XPose > 0 & XPose < 300.0){
        outputY = 0.4;
    }
    else if(!isDeteced){
        outputY = 0;
    }

    tank.arcadeDrive(-outputX, outputY);
  }
  
  public void StopChassis(){
    outputX = 0;
    outputY = 0;

    tank.arcadeDrive(-outputX, outputY);
  }
}
