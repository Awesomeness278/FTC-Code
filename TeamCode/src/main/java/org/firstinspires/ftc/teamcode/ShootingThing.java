package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

@TeleOp
public class ShootingThing extends LinearOpMode {
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back, Intake, Shooter;
    Servo BottomServo, TopServo;

    final double moveSpeed = 0.5;

    //Hardware Map Names for drive motors and odometry wheels. THIS WILL CHANGE ON EACH ROBOT, YOU NEED TO UPDATE THESE VALUES ACCORDINGLY
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor";

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        initDriveHardwareMap(rfName, rbName, lfName, lbName, Intake, Shooter, TopServo, BottomServo);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double bottom = 0;
        double top = 0;

        double leftFront;
        double leftBack;
        double rightFront;
        double rightBack;
        //double intake;
        //double shooter;

        waitForStart();

        while(opModeIsActive()){

            leftFront = 0.0;
            leftBack = 0.0;
            rightFront = 0.0;
            rightBack = 0.0;
            //intake = 0.0;
            //shooter = 0.0;

            bottom += 1.0;

            if(gamepad2.a) {
                top += 0.5;
            }

            //intake += -gamepad2.left_stick_y;
            //shooter += (-gamepad2.right_stick_y)*0.05+0.75;

            telemetry.addData("test","test");
            Intake.setPower(1);
            Shooter.setPower(1);
            BottomServo.setPosition(bottom);
            TopServo.setPosition(top);

      /*      if (gamepad1.dpad_up) {
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
            left_front.setPower(leftFront/scalar*moveSpeed);
            left_back.setPower(leftBack/scalar*moveSpeed);
            right_front.setPower(rightFront/scalar*moveSpeed);
            right_back.setPower(rightBack/scalar*moveSpeed);
*/
            telemetry.update();
        }

    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, DcMotor Intake, DcMotor Shooter, Servo TopServo, Servo BottomServo){
       /* right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName); */
        Intake = hardwareMap.dcMotor.get("Intake");
        Shooter = hardwareMap.dcMotor.get("Shooter");
        TopServo = hardwareMap.get(Servo.class,"Top Servo");
        BottomServo = hardwareMap.get(Servo.class,"Bottom Servo");

/*
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

        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        //right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back.setDirection(DcMotorSimple.Direction.REVERSE);
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
*/
        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }

}
