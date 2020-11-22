package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class operatorMovementProgramOdometry extends LinearOpMode{

    private static final double moveSpeed = 0.75;
    private Servo[] conveyorBeltServos = new Servo[3];
    double p = 0.70;
    boolean Switch = false;
    boolean change = false;
    boolean toggle = false;
    @Override
    public void runOpMode(){
        DcMotor leftFrontMotor = hardwareMap.get(DcMotor.class, "Left Front Motor");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor leftBackMotor = hardwareMap.get(DcMotor.class, "Left Back Motor");
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor rightFrontMotor = hardwareMap.get(DcMotor.class, "Right Front Motor");
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor rightBackMotor = hardwareMap.get(DcMotor.class, "Right Back Motor");
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "Intake Motor");
        DcMotor flywheelMotor = hardwareMap.get(DcMotor.class, "Flywheel Motor");
        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo[] conveyorBeltServos = new Servo[2];
        conveyorBeltServos[0] = hardwareMap.get(Servo.class,"Top Servo");
        conveyorBeltServos[1] = hardwareMap.get(Servo.class,"Bottom Servo");

        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("Power",p);
            telemetry.update();
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



            /*
            if (gamepad1.a) {
                leftFront += 1;
                leftBack = 0;
                rightFront = 0;
                rightBack = 0;
            }
            if (gamepad1.b) {
                leftFront = 0;
                leftBack += 1;
                rightFront = 0;
                rightBack = 0 ;
            }
            if (gamepad1.x) {
                leftFront = 0;
                leftBack = 0;
                rightFront += 1;
                rightBack = 0;
            }
            if (gamepad1.y) {
                leftFront = 0;
                leftBack = 0;
                rightFront = 0;
                rightBack += 1;
            }
*/

            if(gamepad1.left_bumper && Switch){
                p-=0.005;
                Switch=false;
            }else if(gamepad1.right_bumper && Switch){
                p+=0.005;
                Switch=false;
            }
            if(!gamepad1.left_bumper&&!gamepad1.right_bumper){
                Switch=true;
            }
            leftFront += -gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
            leftBack += -gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;
            rightFront += -gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x;
            rightBack += -gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x;
            double scalar = Math.max(Math.max(Math.abs(leftFront),Math.abs(leftBack)),Math.max(Math.abs(rightFront),Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            leftFrontMotor.setPower(leftFront/scalar*moveSpeed);
            leftBackMotor.setPower(leftBack/scalar*moveSpeed);
            rightFrontMotor.setPower(rightFront/scalar*moveSpeed);
            rightBackMotor.setPower(rightBack/scalar*moveSpeed);
            if(gamepad1.a && change){
                toggle = !toggle;
                change=false;
            }
            if(!gamepad1.a){
                change=true;
            }
            if(toggle){
                flywheelMotor.setPower(p);
            }else{
                flywheelMotor.setPower(0);
            }
            if(gamepad1.b){
                intakeMotor.setPower(1);
            } else {
                intakeMotor.setPower(0);
            }
            if(gamepad1.x){
                conveyorBeltServos[0].setPosition(0.25);
            } else {
                conveyorBeltServos[0].setPosition(0.50);
            }
            if(gamepad1.y){
                conveyorBeltServos[1].setPosition(0.25);
            } else {
                conveyorBeltServos[1].setPosition(0.50);
            }
        }
    }
}
