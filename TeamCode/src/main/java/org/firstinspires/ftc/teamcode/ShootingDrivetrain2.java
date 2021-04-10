package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import java.nio.channels.Pipe;

@TeleOp(name = "PID testing")
public class ShootingDrivetrain2 extends LinearOpMode {
    double moveSpeed = 1;
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back, conveyor, arm, intake;
    DcMotorEx shooter;
    Servo grip, blocker, straightener, parker;
    //Hardware Map Names for drive motors and odometry wheels.
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor", shootName = "Shooter";
    PIDFCoefficients coefficients = new PIDFCoefficients(0,0,0,0);

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values.
        initDriveHardwareMap(rfName, rbName, lfName, lbName, shootName);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double flywheelPower = 0.95;
        double convPower = 0.7;
        final double movementConstant = 0.8;
        final double rotationConstant = 0.75;
        double blockerDownPosition = 0.4;
        double blockerUpPosition = 0.7;
        double parkerPos = 0;
        double parkerUpPos = 0.95;
        double parkerDownPos = 0.3;
        double veloc = -360;


        boolean blockerDown = false;
        boolean dPadUpPressed = false;
        boolean dPadDownPressed = false;
        boolean rightBumperPressed = false;
        boolean armPressed = false;
        boolean xPressed = false;
        double armMoving = 0;
        boolean yPressed = false;
        boolean aPressed = false;
        boolean leftThumbstickUp = false, leftThumbstickDown = false, leftThumbstsickLeft = false, leftThumbstickRight = false;
        int selectedCoefficient = 0;
        String[] coefficientNames = new String[]{"p","i","d","f"};

        int flywheelSwitch = 0;
        int armTargetPosition = 0;
        int vertArmPos = 100;
        int horArmPos = -100;
        //int horArmPos = -245;
        boolean gripToggle = false;

        double armMoveTime = 1.5;

        waitForStart();
        while (opModeIsActive()) {
            shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
            /*if(gripPos.length<3){
               gripPos.push(grip.getPosition());
            }*/

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
                    veloc += 5;
                }
                dPadUpPressed = true;
            } else {
                dPadUpPressed = false;
            }

            if (gamepad2.dpad_down) {
                if (!dPadDownPressed) {
                    veloc -= 5;
                }
                dPadDownPressed = true;
            } else {
                dPadDownPressed = false;
            }
            telemetry.addData("Flywheel Power: ", veloc);
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.addData("Parker Position", parker.getController().getServoPosition(2));
            telemetry.addData("Selected Coefficient",coefficientNames[selectedCoefficient]);
            try {
                String a = Double.toString(PIDFCoefficients.class.getField(coefficientNames[selectedCoefficient]).getDouble(coefficients));
                telemetry.addData("Selected Coefficient Value",/*a.substring(0,a.indexOf('.')+2)*/a);
            }catch(NoSuchFieldException | IllegalAccessException ignored){}
            telemetry.update();
            if(gamepad2.left_stick_y<-0.5&&leftThumbstickDown){
                try {
                    PIDFCoefficients.class.getField(coefficientNames[selectedCoefficient]).setDouble(coefficients,PIDFCoefficients.class.getField(coefficientNames[selectedCoefficient]).getDouble(coefficients)-0.25);
                }catch(NoSuchFieldException | IllegalAccessException ignored){}
                leftThumbstickDown = false;
            }else if(gamepad2.left_stick_y>-0.5){
                leftThumbstickDown = true;
            }
            if(gamepad2.left_stick_y>0.5&&leftThumbstickUp){
                leftThumbstickUp = false;
                try {
                    PIDFCoefficients.class.getField(coefficientNames[selectedCoefficient]).setDouble(coefficients,PIDFCoefficients.class.getField(coefficientNames[selectedCoefficient]).getDouble(coefficients)+0.25);
                }catch(NoSuchFieldException | IllegalAccessException ignored){}
            }else if(gamepad2.left_stick_y<0.5){
                leftThumbstickUp = true;
            }
            if(gamepad2.left_stick_x<-0.5&&leftThumbstsickLeft){
                selectedCoefficient--;
                if(selectedCoefficient<0){
                    selectedCoefficient+=coefficientNames.length;
                }
                leftThumbstsickLeft = false;
            }else if(gamepad2.left_stick_x>-0.5){
                leftThumbstsickLeft = true;
            }
            if(gamepad2.left_stick_x>0.5&&leftThumbstickRight){
                selectedCoefficient++;
                selectedCoefficient = selectedCoefficient%coefficientNames.length;
                leftThumbstickRight = false;
            }else if(gamepad2.left_stick_x<0.5){
                leftThumbstickRight = true;
            }
            if (gamepad2.right_bumper) {
                if (!rightBumperPressed) {
                    flywheelSwitch = (flywheelSwitch + 1) % 2;
                }
                rightBumperPressed = true;
            } else {
                rightBumperPressed = false;
            }
            if(flywheelSwitch==1){
                shooter.setVelocity(veloc,AngleUnit.DEGREES);
            }
            else if(flywheelSwitch==0){
                shooter.setVelocity(0,AngleUnit.DEGREES);
            }

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

            if(gamepad2.y && !yPressed){
                if(blockerDown) {
                    blocker.setPosition(blockerDownPosition);
                    blockerDown = false;
                }else{
                    blocker.setPosition(blockerUpPosition);
                    blockerDown = true;
                }
                yPressed = true;
            }else if(!gamepad2.y){
                yPressed = false;
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
         /*  if(gripPos[1]==gripPos[0]&&gamepad2.x){
               grip.setPosition(0);
               gripPos = []
           }*/
            if(Math.abs(gamepad2.right_stick_y)>0.1){
                straightener.setPosition((gamepad2.right_stick_y+1)/2);
            }else{
                straightener.setPosition(0.5);
            }

            if(gamepad1.a&&!aPressed){
                if(parkerPos==0){
                    parker.setPosition(parkerUpPos);
                    parkerPos = 1;
                }else if(parkerPos==1){
                    parker.setPosition(parkerDownPos);
                    parkerPos = 0;
                }
                aPressed = true;
            }else if(!gamepad1.a){
                aPressed = false;
            }
        }
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String shootName) {
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);
        shooter = hardwareMap.get(DcMotorEx.class,shootName);
        conveyor = hardwareMap.dcMotor.get("Conveyor");
        arm = hardwareMap.dcMotor.get("Arm");
        intake = hardwareMap.dcMotor.get("Intake");
        grip = hardwareMap.get(Servo.class, "Grip");
        blocker = hardwareMap.get(Servo.class, "Blocker");
        straightener = hardwareMap.get(Servo.class, "Straightener");
        parker = hardwareMap.get(Servo.class, "Parker");

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

        // conveyor.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}