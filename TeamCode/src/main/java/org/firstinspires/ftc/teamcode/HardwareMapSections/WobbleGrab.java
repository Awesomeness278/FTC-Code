package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class WobbleGrab {
    DcMotor arm;
    Servo grip;

    boolean leftBumperPressed = false;
    boolean xPressed = false;
    boolean gripToggle = false;
    int armTargetPosition = -54;

    public void ArmLogic(){
        if(gamepad2.left_bumper){
            arm.setPower(0.5);
            if(!leftBumperPressed) {
                if(armTargetPosition==-206){
                    armTargetPosition = -340;
                }else{
                    armTargetPosition = -206;
                }
            }
            leftBumperPressed = true;
        } else {
            leftBumperPressed = false;
        }
        arm.setTargetPosition(armTargetPosition);

        if(gamepad2.x){
            if(!xPressed) {
                gripToggle = !gripToggle;
            }
            xPressed = true;
        } else xPressed = false;

        if(gripToggle == true){
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
