package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class DrivetrainMovement {
    DcMotor left_front, left_back, right_front, right_back;
    double moveSpeed = 1;
    String rfname = "Right Front Motor", rbname = "Right Back Motor", lfname = "Left Back Motor", lbname = "Left Back Motor";

    public void DriveRobot(){
        double leftFront = 0;
        double leftBack = 0;
        double rightFront = 0;
        double rightBack = 0;
        if (gamepad1.dpad_up) {
            leftFront += 1;
            leftBack += 1;
            rightFront += 1;
            rightBack += 1;
        }
        if (gamepad1.dpad_right) {
            leftFront += 1;
            leftBack += -1;
            rightFront += -1;
            rightBack += 1;
        }
        if (gamepad1.dpad_down) {
            leftFront += -1;
            leftBack += -1;
            rightFront += -1;
            rightBack += -1;
        }
        if (gamepad1.dpad_left) {
            leftFront += -1;
            leftBack += 1;
            rightFront += 1;
            rightBack += -1;
        }
        leftFront += -gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x;
        leftBack += -gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x;
        rightFront += -gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;
        rightBack += -gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
        double scalar = Math.max(Math.max(Math.abs(leftFront),Math.abs(leftBack)),Math.max(Math.abs(rightFront),Math.abs(rightBack)));
        if (scalar < 1) scalar = 1;
        left_front.setPower(leftFront/scalar*moveSpeed);
        left_back.setPower(leftBack/scalar*moveSpeed);
        right_front.setPower(rightFront/scalar*moveSpeed);
        right_back.setPower(rightBack/scalar*moveSpeed);
    }

    public void HardwareMap(HardwareMap hardwareMap){

        this.right_front = hardwareMap.dcMotor.get(rfname);
        this.right_back = hardwareMap.dcMotor.get(rbname);
        this.left_front = hardwareMap.dcMotor.get(lfname);
        this.left_back = hardwareMap.dcMotor.get(lbname);

        this.right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //this.left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        //this.right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        //this.left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        this.right_back.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
