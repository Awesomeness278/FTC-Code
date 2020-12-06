package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class claw extends LinearOpMode {
    double min = 0;
    double max = 1;
    double dir = 0.01;
    double position = 0.5;
    boolean up = false;
    boolean down = false;
    Servo servo;
    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class,"Grip");
        waitForStart();
        while(opModeIsActive()){
            position+=dir;
            if(position>max){
                dir*=-1;
            }
            if(position<max){
                dir*=-1;
            }
            if(gamepad1.dpad_up && !up){
                up = true;
                min+=0.01;
            }else if(!gamepad1.dpad_up){
                up = false;
            }
            if(gamepad1.dpad_down && !down){
                down = true;
                max-=0.01;
            }else if(!gamepad1.dpad_down){
                down = false;
            }
            telemetry.addData("Min",min);
            telemetry.addData("Max",max);
            telemetry.addData("position",position);
        }
    }
}