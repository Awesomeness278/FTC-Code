package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.stateMachine.PIDcontroller;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Shooting + Arm + Drivetrain - the second")
public class ShootingDrivetrain2 extends LinearOpMode {
    double moveSpeed = 1;
    double pPos = 0;
    double pos = 0;
    ElapsedTime time = new ElapsedTime();
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back, shooter, conveyor, arm, intake;
    Servo grip;

    //Hardware Map Names for drive motors and odometry wheels.
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor", shootName = "Shooter";
    PIDcontroller pid;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values.
        initDriveHardwareMap(rfName, rbName, lfName, lbName, shootName);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double flywheelPower = 1;
        double convPower = 0.7;
        final double movementConstant = 0.8;
        final double rotationConstant = 0.75;

        boolean dPadUpPressed = false;
        boolean dPadDownPressed = false;
        boolean rightBumperPressed = false;
        boolean armPressed = false;
        boolean xPressed = false;
        double armMoving = 0;

        int flywheelSwitch = 0;
        int armTargetPosition = 0;
        int vertArmPos = 100;
        int horArmPos = -100;
        //int horArmPos = -245;
        boolean gripToggle = false;

        double armMoveTime = 1.5;

        waitForStart();
        while (opModeIsActive()) {

            //Movement
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

            leftFront*=movementConstant;
            rightFront*=movementConstant;
            leftBack*=movementConstant;
            rightBack*=movementConstant;

            leftFront += (-gamepad1.left_stick_y + gamepad1.left_stick_x)*movementConstant - gamepad1.right_stick_x*-rotationConstant;
            leftBack += (-gamepad1.left_stick_y - gamepad1.left_stick_x)*movementConstant - gamepad1.right_stick_x*-rotationConstant;
            rightFront += (-gamepad1.left_stick_y - gamepad1.left_stick_x)*movementConstant + gamepad1.right_stick_x*-rotationConstant;
            rightBack += (-gamepad1.left_stick_y + gamepad1.left_stick_x)*movementConstant + gamepad1.right_stick_x*-rotationConstant;
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(leftFront / scalar * moveSpeed);
            left_back.setPower(leftBack / scalar * moveSpeed);
            right_front.setPower(rightFront / scalar * moveSpeed);
            right_back.setPower(rightBack / scalar * moveSpeed);

            //Flywheel
            if (gamepad2.dpad_up) {
                if (!dPadUpPressed) {
                    flywheelPower += 0.005;
                }
                dPadUpPressed = true;
            } else {
                dPadUpPressed = false;
            }

            if (gamepad2.dpad_down) {
                if (!dPadDownPressed) {
                    flywheelPower -= 0.005;
                }
                dPadDownPressed = true;
            } else {
                dPadDownPressed = false;
            }
            telemetry.addData("Flywheel Power: ", flywheelPower);
            double PIDPower = 0;
            if(flywheelSwitch==1) {
                PIDPower = pid.run(flywheelPower);
            }
            pPos = Math.abs(pos);
            pos = Math.abs(shooter.getCurrentPosition());
            double rpm = (pos-pPos)/time.time(TimeUnit.MILLISECONDS);
            time.reset();
            rpm = rpm/2.6;
            telemetry.addData("Rotations per minute:",rpm);
            telemetry.addData("Suggested Change In Flywheel Power",PIDPower);
            telemetry.addData("Total Flywheel Power",flywheelPower+PIDPower);
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.addData("x is pressed: ", xPressed);
            telemetry.addData("Grip toggle: ", gripToggle);
            telemetry.update();
            if (gamepad2.right_bumper) {
                pid.PIDTimer.reset();
                if (!rightBumperPressed) {
                    flywheelSwitch = (flywheelSwitch + 1) % 2;
                }
                rightBumperPressed = true;
            } else {
                rightBumperPressed = false;
            }
            shooter.setPower(-(flywheelSwitch * (PIDPower+flywheelPower)));

            //Intake and Conveyor
            if(gamepad2.left_trigger>=0.5){
                intake.setDirection(DcMotorSimple.Direction.REVERSE);
                intake.setPower(1);
            }
            if(gamepad2.right_trigger>=0.5){
                conveyor.setDirection(DcMotorSimple.Direction.FORWARD);
                conveyor.setPower(0.15);
            }
            if(gamepad2.a){
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
                intake.setPower(1);
            }else{
                intake.setPower(0);
            }
            if (gamepad2.b) {
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
                conveyor.setDirection(DcMotorSimple.Direction.REVERSE);
                conveyor.setPower(convPower);
                intake.setPower(1);
            } else {
                conveyor.setPower(0);
                intake.setPower(0);
            }
            //Arm
            if(gamepad1.y){
                armTargetPosition -= 1;
                arm.setTargetPosition(armTargetPosition);
            }
            if(gamepad1.x){
                armTargetPosition += 1;
                arm.setTargetPosition(armTargetPosition);
            }
            if(gamepad2.dpad_left && !armPressed){
                arm.setPower(0.08);
                armTargetPosition = horArmPos;
                armMoving = getRuntime();
                armPressed = true;
                arm.setTargetPosition(armTargetPosition);
            }else if(gamepad2.dpad_right && !armPressed) {
                arm.setPower(0.08);
                armTargetPosition = vertArmPos;
                armMoving = getRuntime();
                armPressed = true;
                arm.setTargetPosition(armTargetPosition);
            }else if(!gamepad2.dpad_left && !gamepad2.dpad_right){
                armPressed = false;
                if(getRuntime()-armMoving>armMoveTime) {
                    arm.setPower(0);
                }
            }

            if (gamepad2.x) {
                if (!xPressed) {
                    gripToggle = !gripToggle;
                }
                xPressed = true;
            } else xPressed = false;

            if (gripToggle) {
                grip.setPosition(0.35);
            } else {
                grip.setPosition(-0.1);
            }
        }
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String shootName) {
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);
        shooter = hardwareMap.dcMotor.get(shootName);
        conveyor = hardwareMap.dcMotor.get("Conveyor");
        arm = hardwareMap.dcMotor.get("Arm");
        intake = hardwareMap.dcMotor.get("Intake");
        grip = hardwareMap.get(Servo.class, "Grip");

        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

        pid = new PIDcontroller(shooter, new PIDCoefficients(0.002,0.00002,0.002));
        pid.setup();

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}