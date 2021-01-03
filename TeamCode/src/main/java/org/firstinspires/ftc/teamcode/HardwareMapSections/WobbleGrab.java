package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class WobbleGrab {
    public WobbleGrab(Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }
    Gamepad gamepad1;
    Gamepad gamepad2;
    DcMotor arm;
    Servo grip;

    int armStartPosition = -54;

    int armVerticalPosition = armStartPosition - 152;
    int armHorizontalPosition = armStartPosition - 286;
    double gripOpenPosition = -0.15;
    double gripClosedPosition = 0.25;

    boolean xPressed = false;
    boolean gripToggle = false;
    boolean armToggle = false;

    public void ArmLogic() throws InterruptedException {
        if(gamepad2.x){
            if(!xPressed) {
                if(armToggle) {
                    gripToggle = true;
                    wait(2000);
                    armToggle = !armToggle;
                }else{
                    armToggle = !armToggle;
                    wait(2000);
                    gripToggle = false;
                }
            }
            xPressed = true;
        } else xPressed = false;

        if(gripToggle){
            grip.setPosition(gripOpenPosition);
        }else{
            grip.setPosition(gripClosedPosition);
        }

        if(armToggle){
            arm.setTargetPosition(armHorizontalPosition);
        }else{
            arm.setTargetPosition(armVerticalPosition);
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