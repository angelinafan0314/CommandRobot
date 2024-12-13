// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.chassisID;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.candle;
import frc.robot.subsystems.chassis;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final candle m_Candle = new candle();
  private final chassis m_Chassis = new chassis();
  private final Limelight m_Limelight = new Limelight();

  private XboxController xboxController = new XboxController(0);

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    // Configure the trigger bindings
    configureBindings();
    m_Chassis.setDefaultCommand(
      new RunCommand(() -> m_Chassis.drive(xboxController.getLeftX() * 0.7, xboxController.getRightY() * 0.7), m_Chassis));
    // m_Chassis.setDefaultCommand(
    //     new RunCommand(() -> m_Chassis.drive(xboxController.getLeftX()*0.7, xboxController.getRightY()*0.7, m_Chassis))
        // );
  }

  private void configureBindings() {
    new JoystickButton(xboxController, 1).whileTrue(new InstantCommand(m_Chassis::TagAim)).onFalse(new InstantCommand(m_Chassis::StopChassis));
    new JoystickButton(xboxController, 2).onTrue(new InstantCommand(m_Candle::SetDefault));
    new JoystickButton(xboxController, 3).whileTrue(new InstantCommand(m_Candle::TagDetected)).onFalse(new InstantCommand(m_Candle::SetDefault));
  }
}
