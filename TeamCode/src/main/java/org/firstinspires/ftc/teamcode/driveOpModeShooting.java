package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
//import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class driveOpModeShooting extends LinearOpMode{

    private static final double moveSpeed = 0.5;
    //private Servo[] conveyorBeltServos = new Servo[3];

    @Override
    public void runOpMode(){

        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "Left Front Motor");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "Left Back Motor");
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "Right Front Motor");
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "Right Back Motor");
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "Intake Motor");
        DcMotor flywheelMotor = hardwareMap.get(DcMotor.class, "Flywheel Motor");
        
        Servo[] conveyorBeltServos = new Servo[3];

        for(int i = 0; i < conveyorBeltServos.length; i++){
            conveyorBeltServos[i] = hardwareMap.get(Servo.class,"Servo "+(i));
        }*/

        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
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
            leftFrontMotor.setPower(leftFront/scalar*moveSpeed);
            leftBackMotor.setPower(leftBack/scalar*moveSpeed);
            rightFrontMotor.setPower(rightFront/scalar*moveSpeed);
            rightBackMotor.setPower(rightBack/scalar*moveSpeed);
            /*if(gamepad1.a){
                flywheelMotor.setPower(1);
                intakeMotor.setPower(1);
                for (int i = 1; i < conveyorBeltServos.length; i++){
                    conveyorBeltServos[i].setPosition(0.25);
                }
            } else {
                flywheelMotor.setPower(0);
                intakeMotor.setPower(0);
                for (int i = 1; i < conveyorBeltServos.length; i++){
                    conveyorBeltServos[i].setPosition(0.5);
                }
            }*/
        }
    }
}
