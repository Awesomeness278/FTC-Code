package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Shooting + Arm + Drivetrain")
public class ShootingDrivetrain extends LinearOpMode {
    double moveSpeed = 0.5;
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back, shooter, conveyor, arm, intake;
    Servo grip;

    //Hardware Map Names for drive motors and odometry wheels.
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor", shootName = "Shooter", convName = "Conveyor", armName = "Arm", intakeName = "Intake", gripName = "Grip";

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values.
        initDriveHardwareMap(rfName, rbName, lfName, lbName, shootName, convName, armName, intakeName, gripName);
        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double flywheelPower = 0;
        double gripAmount = 0;

        boolean dPadUpPressed = false;
        boolean dPadDownPressed = false;
        boolean rightBumperPressed = false;
        boolean leftBumperPressed = false;

        int flywheelSwitch = 0;
        int armTargetPosition = 0;

        waitForStart();
        while(opModeIsActive()){

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
            leftFront += -gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
            leftBack += -gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;
            rightFront += -gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x;
            rightBack += -gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x;
            double scalar = Math.max(Math.max(Math.abs(leftFront),Math.abs(leftBack)),Math.max(Math.abs(rightFront),Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(leftFront/scalar*moveSpeed);
            left_back.setPower(leftBack/scalar*moveSpeed);
            right_front.setPower(rightFront/scalar*moveSpeed);
            right_back.setPower(rightBack/scalar*moveSpeed);

            //Flywheel

            if(gamepad2.dpad_up){
                if(!dPadUpPressed) {
                    flywheelPower += 0.005;
                }
                dPadUpPressed = true;
            } else {
                dPadUpPressed = false;
            }
            if(gamepad2.dpad_down){
                if(!dPadDownPressed) {
                    flywheelPower -= 0.005;
                }
                dPadDownPressed = true;
            } else {
                dPadDownPressed = false;
            }
            telemetry.addData("Flywheel Power: ",flywheelPower);
            telemetry.update();
            if(gamepad2.right_bumper){
                if(!rightBumperPressed) {
                    flywheelSwitch = (flywheelSwitch + 1) % 2;
                }
                rightBumperPressed = true;
            } else {
                rightBumperPressed = false;
            }
            shooter.setPower(flywheelSwitch*flywheelPower);

            //Intake and Conveyor

            if(gamepad2.a){
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }
            if(gamepad2.b){
                conveyor.setPower(1);
            } else {
                conveyor.setPower(0);
            }

            //Arm

            if(gamepad2.left_bumper){
                if(!leftBumperPressed) {
                    if(armTargetPosition==0){
                        armTargetPosition = 90;
                    }else{
                        armTargetPosition = 0;
                    }
                }
                leftBumperPressed = true;
            } else {
                leftBumperPressed = false;
            }
            arm.setTargetPosition(armTargetPosition);
            if(gamepad1.x){
                grip.setPosition(0.25);
            } else if (gamepad1.y) {
                grip.setPosition(0.75);
            } else {
                grip.setPosition(0.50);
            }
        }
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String shootName, String convName, String armName, String intakeName, String gripName){
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);
        shooter = hardwareMap.dcMotor.get(shootName);
        conveyor = hardwareMap.dcMotor.get("Conveyor");
        arm = hardwareMap.dcMotor.get("Arm");
        intake = hardwareMap.dcMotor.get("Intake");
        grip = hardwareMap.get(Servo.class,"Grip");

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
        right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}
