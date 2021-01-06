package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;
import java.util.Date;
//import java.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Calendar;

@TeleOp(name = "Shooting + Arm + Drivetrain")
public class ShootingDrivetrain extends LinearOpMode {
    double moveSpeed = 1;
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back, shooter, conveyor, arm, intake;
    Servo grip;

    //Hardware Map Names for drive motors and odometry wheels.
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor", shootName = "Shooter", convName = "Conveyor", armName = "Arm", intakeName = "Intake", gripName = "Grip";


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values.
        initDriveHardwareMap(rfName, rbName, lfName, lbName, shootName, convName, armName, intakeName, gripName);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double flywheelPower = 0.8;
        double gripAmount = 0;
        final double movementConstant = 0.8;
        final double rotationConstant = 0.5;

        boolean dPadUpPressed = false;
        boolean dPadDownPressed = false;
        boolean rightBumperPressed = false;
        boolean leftBumperPressed = false;
        boolean xPressed = false;

        int flywheelSwitch = 0;
        int armTargetPosition = 0;
        int vertArmPos = -123;
        int horArmPos = -272;
        int homeArmPos = 0;
        boolean gripToggle = false;
        int timeSinceLeftBumper = 0;

        waitForStart();
        while (opModeIsActive()) {

            arm.setPower(0.25);

            timeSinceLeftBumper--;

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

		  /*  if(gamepad2.dpad_up && !dPadUpPressed){
				flywheelPower += 0.005;
				dPadUpPressed = true;
			} else if(!gamepad2.dpad_up){
				dPadUpPressed = false;
			}*/

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
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.addData("x is pressed: ", xPressed);
            telemetry.addData("Grip toggle: ", gripToggle);
            telemetry.update();
            if (gamepad2.right_bumper) {
                if (!rightBumperPressed) {
                    flywheelSwitch = (flywheelSwitch + 1) % 2;
                }
                rightBumperPressed = true;
            } else {
                rightBumperPressed = false;
            }
            shooter.setPower(-(flywheelSwitch * flywheelPower));

            //Intake and Conveyor

            if (gamepad2.a) {
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }
            if (gamepad2.b) {
                conveyor.setPower(0.7);
            } else {
                conveyor.setPower(0);
            }
            //Arm
            if(gamepad2.left_bumper && !leftBumperPressed){
                if(timeSinceLeftBumper>0){
                    armTargetPosition = homeArmPos;
                }else{
                    if (armTargetPosition == vertArmPos) {
                        armTargetPosition = horArmPos;
                    } else {
                        armTargetPosition = vertArmPos;
                    }
                    timeSinceLeftBumper = 50;
                }
                leftBumperPressed = true;
            }else if(!gamepad2.left_bumper){
                leftBumperPressed = false;
            }
            arm.setTargetPosition(armTargetPosition);

            if (gamepad2.x) {
                if (!xPressed) {
                    gripToggle = !gripToggle;
                }
                xPressed = true;
            } else xPressed = false;

            if (gripToggle == true) {
                grip.setPosition(-0.15);
            } else {
                grip.setPosition(0.25);
            }
        }
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String shootName, String convName, String armName, String intakeName, String gripName) {
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

        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        //right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        //right_back.setDirection(DcMotorSimple.Direction.REVERSE);


        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}