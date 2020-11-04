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
                leftFront = 1;
                leftBack = 1;
                rightFront = 1;
                rightBack = 1;
            } else if (gamepad1.dpad_right) {
                leftFront = 1;
                leftBack = -1;
                rightFront = -1;
                rightBack = 1;
            } else if (gamepad1.dpad_down) {
                leftFront = -1;
                leftBack = -1;
                rightFront = -1;
                rightBack = -1;
            } else if (gamepad1.dpad_left) {
                leftFront = -1;
                leftBack = 1;
                rightFront = 1;
                rightBack = -1;
            } else if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0){
                double scalar = Math.sqrt(Math.pow(gamepad1.left_stick_y,2)+Math.pow(gamepad1.left_stick_x,2))/(Math.abs(gamepad1.left_stick_y)+Math.abs(gamepad1.left_stick_x));
                leftFront = (-gamepad1.left_stick_y+gamepad1.left_stick_x)*scalar;
                leftBack = (-gamepad1.left_stick_y-gamepad1.left_stick_x)*scalar;
                rightFront = (-gamepad1.left_stick_y-gamepad1.left_stick_x)*scalar;
                rightBack = (-gamepad1.left_stick_y+gamepad1.left_stick_x)*scalar;
            }
            leftFront = leftFront*(1-Math.abs(gamepad1.right_stick_x)/2)+gamepad1.right_stick_x/2;
            leftBack = leftBack*(1-Math.abs(gamepad1.right_stick_x)/2)+gamepad1.right_stick_x/2;
            rightFront = rightFront*(1-Math.abs(gamepad1.right_stick_x)/2)-gamepad1.right_stick_x/2;
            rightBack = rightBack*(1-Math.abs(gamepad1.right_stick_x)/2)-gamepad1.right_stick_x/2;
            leftFrontMotor.setPower(leftFront*moveSpeed);
            leftBackMotor.setPower(leftBack*moveSpeed);
            rightFrontMotor.setPower(rightFront*moveSpeed);
            rightBackMotor.setPower(rightBack*moveSpeed);
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
