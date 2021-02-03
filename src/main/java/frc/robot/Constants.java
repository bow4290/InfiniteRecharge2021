/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public final class Constants {
        //shooter encoder constants:
        public static final int shooterEncoderChannelA = 0;
        public static final int shooterEncoderChannelB = 1;
        public static final int shooterEncoderAverageSamples = 127;

        public static final int conveyorButtonPort = 3;

        //Motor Channels:
        public static final int wheelSpinnerChannel = 2;
        public static final int leftVictorSPX1Channel = 12;
        public static final int leftVictorSPX2Channel = 11;
        public static final int leftVictorSPX3Channel = 10;
        public static final int rightVictorSPX1Channel = 5;
        public static final int rightVictorSPX2Channel = 3;
        public static final int rightVictorSPX3Channel = 6;
        public static final int leftShooterChannel = 8;
        public static final int rightShooterChannel = 1;
        public static final int climberMotorChannel = 4;
        public static final int topMotorChannel = 9;
        public static final int bottomMotorChannel = 7;
        public static final int intakeMotorChannel = 13;


        //USB Ports:
        public static final int LEFT_JOYSTICK = 0;
        public static final int RIGHT_JOYSTICK = 1;
        public static final int XBOX_CONTROLLER = 2;

        //Pneumatics:
        public static int intakeSolenoidForwardChannel = 0;
        public static int intakeSolenoidReverseChannel = 1;
        public static int shooterSolenoidForwardChannel = 2;
        public static int shooterSolenoidReverseChannel = 3;
        public static int gearShiftSolenoidForwardChannel = 4;
        public static int gearShiftSolenoidReverseChannel = 5;
        public static int compressorCANID = 0;
}