package org.firstinspires.ftc.teamcode.HardwareMapSections;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class DrivetrainMovement {
    DcMotor left_front, left_back, right_front, right_back;
    double moveSpeed = 1;
    int motorChange = 1;
    double leftFront = 0;
    double leftBack = 0;
    double rightFront = 0;
    double rightBack = 0;
    double scalarConstant = 1;
    String rfname = "Right Front Motor", rbname = "Right Back Motor", lfname = "Left Back Motor", lbname = "Left Back Motor";

    public void DriveRobot() {
        DPadInput(gamepad1.dpad_up, motorChange, motorChange, motorChange, motorChange);
        DPadInput(gamepad1.dpad_right, motorChange, -motorChange, -motorChange, motorChange);
        DPadInput(gamepad1.dpad_down, -motorChange, -motorChange, -motorChange, -motorChange);
        DPadInput(gamepad1.dpad_left, -motorChange, motorChange, motorChange, -motorChange);

        DPadInput(true, -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x,
                -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                -gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
        double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
        if (scalar < scalarConstant) scalar = scalarConstant;
        left_front.setPower(leftFront / scalar * moveSpeed);
        left_back.setPower(leftBack / scalar * moveSpeed);
        right_front.setPower(rightFront / scalar * moveSpeed);
        right_back.setPower(rightBack / scalar * moveSpeed);
    }

    public void DPadInput(boolean DPadButton, float lfChange, float lbChange, float rfChange, float rbChange){

        if (DPadButton) {
            leftFront += lfChange;
            leftBack += lbChange;
            rightFront += rfChange;
            rightBack += rbChange;
        }
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
