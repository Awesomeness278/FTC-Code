package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class WobbleGrab {
    DcMotor arm;
    Servo grip;

    int armStartPosition = -54;

    int armVerticalPosition = armStartPosition - 152;
    int armHorizontalPosition = armStartPosition - 286;

    boolean leftTriggerPressed = false;
    boolean xPressed = false;
    boolean gripToggle = false;
    int armTargetPosition = armStartPosition;

    public void ArmLogic(){
        if(gamepad2.left_trigger > 0.05){
            arm.setPower(0.5);
            if(!leftTriggerPressed) {
                if(armTargetPosition==armVerticalPosition){
                    armTargetPosition = armHorizontalPosition;
                }else{
                    armTargetPosition = armVerticalPosition;
                }
            }
            leftTriggerPressed = true;
        } else {
            leftTriggerPressed = false;
        }
        arm.setTargetPosition(armTargetPosition);

        if(gamepad2.x){
            if(!xPressed) {
                gripToggle = !gripToggle;
            }
            xPressed = true;
        } else xPressed = false;

        if(gripToggle){
            grip.setPosition(-0.15);
        }else{
            grip.setPosition(0.25);
        }

        telemetry.addData("Arm Position", arm.getCurrentPosition());
        telemetry.addData("Grip toggle: ", gripToggle);
    }
    public void HardwareMap(HardwareMap hardwareMap){
        arm = hardwareMap.dcMotor.get("Arm");
        grip = hardwareMap.get(Servo.class,"Grip");
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
