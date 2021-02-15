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
        initDriveHardwareMap();

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Grip position", grip.getPosition());

            if(gamepad1.a) {
                pos+=-0.001;
            }

            if(gamepad1.b) {
                pos-=-0.001;
            }
            grip.setPosition(pos);

            telemetry.addData("Grip position:",grip.getPosition());
        }
    }
    private void initDriveHardwareMap() {
        grip = hardwareMap.get(Servo.class, "Grip");
        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}