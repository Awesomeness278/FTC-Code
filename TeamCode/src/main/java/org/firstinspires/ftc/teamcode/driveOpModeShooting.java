package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class driveOpModeShooting extends LinearOpMode{

    private static final double moveSpeed = 0.5;
    //private Servo[] conveyorBeltServos = new Servo[3];

    @Override
    public void runOpMode(){

        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "Left Front Motor");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "Left Back Motor");
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "Right Front Motor");
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "Right Back Motor");
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "Intake Motor");
        //DcMotor flywheelMotor = hardwareMap.get(DcMotor.class, "Flywheel Motor");

        /*for(int i = 0; i < 3; i++){
            conveyorBeltServos[i] = hardwareMap.get(Servo.class,"Servo "+(i));
        }*/

        //color = hardwareMap.get(ColorRangeSensor.class, "colorRangeSensor");
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.a) {
                leftFrontMotor.setPower(moveSpeed);
                leftBackMotor.setPower(0);
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
            } else if (gamepad1.b) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(moveSpeed);
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(0);
            } else if (gamepad1.x) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(0);
                rightFrontMotor.setPower(moveSpeed);
                rightBackMotor.setPower(0);
            } else if (gamepad1.y) {
                leftFrontMotor.setPower(0);
                leftBackMotor.setPower(0);
                rightFrontMotor.setPower(0);
                rightBackMotor.setPower(moveSpeed);
            } else if (gamepad1.dpad_up) {
                leftFrontMotor.setPower(moveSpeed);
                leftBackMotor.setPower(moveSpeed);
                rightFrontMotor.setPower(moveSpeed);
                rightBackMotor.setPower(moveSpeed);
            } else if (gamepad1.dpad_right) {
                leftFrontMotor.setPower(moveSpeed);
                leftBackMotor.setPower(-moveSpeed);
                rightFrontMotor.setPower(-moveSpeed);
                rightBackMotor.setPower(moveSpeed);
            } else if (gamepad1.dpad_down) {
                leftFrontMotor.setPower(-moveSpeed);
                leftBackMotor.setPower(-moveSpeed);
                rightFrontMotor.setPower(-moveSpeed);
                rightBackMotor.setPower(-moveSpeed);
            } else if (gamepad1.dpad_left) {
                leftFrontMotor.setPower(-moveSpeed);
                leftBackMotor.setPower(moveSpeed);
                rightFrontMotor.setPower(moveSpeed);
                rightBackMotor.setPower(-moveSpeed);
            } else if (gamepad1.right_stick_x != 0) {
                leftFrontMotor.setPower(moveSpeed*gamepad1.right_stick_x);
                leftBackMotor.setPower(moveSpeed*gamepad1.right_stick_x);
                rightFrontMotor.setPower(-moveSpeed*gamepad1.right_stick_x);
                rightBackMotor.setPower(-moveSpeed*gamepad1.right_stick_x);
            } else {
                double scalar = Math.sqrt(Math.pow(gamepad1.left_stick_y,2)+Math.pow(gamepad1.left_stick_x,2))/(Math.abs(gamepad1.left_stick_y)+Math.abs(gamepad1.left_stick_x));
                leftFrontMotor.setPower((-gamepad1.left_stick_y+gamepad1.left_stick_x)*moveSpeed*scalar);
                leftBackMotor.setPower((-gamepad1.left_stick_y-gamepad1.left_stick_x)*moveSpeed*scalar);
                rightFrontMotor.setPower((-gamepad1.left_stick_y-gamepad1.left_stick_x)*moveSpeed*scalar);
                rightBackMotor.setPower((-gamepad1.left_stick_y+gamepad1.left_stick_x)*moveSpeed*scalar);
            }

            /*double foo = this.gamepad1.a ? 0.5 : 0;
            flywheelMotor.setPower(foo);
            intakeMotor.setPower(foo);*/
        }
    }
}
