package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Move Grip")
public class MoveGrip extends LinearOpMode {
    Servo grip;
    double pos = 0.15;

    @Override
    public void runOpMode() {
        initDriveHardwareMap("Grip");

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            if(gamepad1.a) {
                pos+=-0.001;
            }

            if(gamepad1.b) {
                pos-=-0.001;
            }
            grip.setPosition(pos);
        }
    }
    private void initDriveHardwareMap(String gripName) {
        grip = hardwareMap.get(Servo.class, gripName);
        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}