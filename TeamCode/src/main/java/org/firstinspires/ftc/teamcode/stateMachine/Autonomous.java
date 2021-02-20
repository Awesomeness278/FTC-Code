package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    public Autonomous(){
    }
    StateMachine machine = new StateMachine(this);
    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    int position = 3;
    DcMotor right_back;
    DcMotor Shooter;
    DcMotor Conveyor;
    DcMotor Arm;
    Servo Claw;
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor";
    String verticalLeftEncoderName = rbName, verticalRightEncoderName = lfName, horizontalEncoderName = rfName;
    OdometryGlobalCoordinatePosition odometry;
    DcMotor verticalLeft;
    DcMotor verticalRight;
    DcMotor horizontal;
    int targetShootingSpot = 0;
    private static final String VUFORIA_KEY =
            "AfweTBj/////AAABmZ1QwFXvX0ltj9QRI7IS1wtULCTBA7CyU8KibbraimizSOgb5iPrsHVE4P/nnAbJuNWXHsqZgW784iI7nfekundyBUv80cdOoe8y/O9125JNbD4fkyufJvrK2RSpv2w9GPY1AtM3fxo70t6r89/WQnpcAHPp244gr0Ua8GL5qUt8XPPE3WcTATty3C/GayFSfe+MTbV8OtB5qN34XhstZYDUgxHcJ+xQLwkYj+FtLTyDc+kRrg+oqLkYA3zNwksq9vWEvTTV0SzsFtU3NbFZtz3P068I25yPHOSqd4bNq36LAcrJchYGidrbJLtRqrEG+4lFD8FWEkpKoWIm4d1DiM0xCcQhiqHH/KQ3fDNP7Xd3";
    VuforiaLocalizer vuforia;
    int delay = 0;
    TFObjectDetector tfod;
    static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    static final String LABEL_FIRST_ELEMENT = "Quad";
    static final String LABEL_SECOND_ELEMENT = "Single";
    double COUNTS_PER_INCH = 307.699557;
    int upDelay = 5;
    Recognition recognition;
    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        assert tfod != null;
        tfod.setZoom(1.3,16.0/9.0);
        initDriveHardwareMap(rfName, rbName, lfName, lbName, verticalLeftEncoderName, verticalRightEncoderName, horizontalEncoderName);
        odometry = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        odometry.reverseRightEncoder();
        odometry.reverseLeftEncoder();
        Thread positionThread = new Thread(odometry);
        positionThread.start();
        while(!gamepad1.right_stick_button) {
            if (gamepad1.a) {
                position = 1;
            }
            if (gamepad1.b) {
                position = 2;
            }
            if (gamepad1.x) {
                position = 3;
            }
            if (gamepad1.y) {
                position = 4;
            }
            String[] positions = new String[4];
            positions[0] = "Outer Blue";
            positions[1] = "Inner Blue";
            positions[2] = "Inner Red";
            positions[3] = "Outer Red";
            telemetry.addData("Starting Position", positions[position-1]);
            if (gamepad1.left_trigger > 0.3) {
                delay = 0;
            }
            if (gamepad1.right_trigger > 0.3) {
                delay = Math.min(upDelay, 5);
            }
            telemetry.addData("Starting Delay:", Integer.toString(delay).concat("s"));
            if (gamepad1.dpad_left) {
                targetShootingSpot = 2;
            }
            if (gamepad1.dpad_up || gamepad1.dpad_down) {
                targetShootingSpot = 0;
            }
            if (gamepad1.dpad_right) {
                targetShootingSpot = 1;
            }
            String[] shootingPositions = new String[3];
            shootingPositions[0] = "Center";
            shootingPositions[1] = "Right";
            shootingPositions[2] = "Left";
            telemetry.addData("Target Shooting Spot",shootingPositions[targetShootingSpot]);
            telemetry.update();
        }
        telemetry.addData("Settings","Submitted");
        telemetry.update();
        waitForStart();
        resetStartTime();
        AutonomousData.getInstance().SetStartingLocation(position);
        machine.opMode.Claw.setPosition(0);
        machine.addState(States.Tensorflow, new Tensorflow(delay));
        machine.addState(States.MoveToWobble, new MoveToWobble());
        machine.addState(States.Rotate, new Rotate(AutonomousData.getInstance().getWobbleRotation(),States.DropWobble));
        machine.addState(States.DropWobble, new DropWobble());
        machine.addState(States.Rotate2,new Rotate(0,States.Move3));
        switch(targetShootingSpot) {
            case 0:
                machine.addState(States.Move3, new MoveTest(AutonomousData.getInstance().getShootingXPosition(), 56, States.Rotate3));
                machine.addState(States.Rotate3,new Rotate(0,States.Wait));
                machine.addState(States.Wait, new wait(0,1));
                machine.addState(States.Rotate4, new Rotate(0,States.Move4));
                break;
            case 1:
                machine.addState(States.Move3, new MoveTest(AutonomousData.getInstance().getShootingXPosition2(), 56, States.Wait));
                machine.addState(States.Rotate3,new Rotate(10,States.Wait));
                machine.addState(States.Wait, new wait(-15,0.95));
                machine.addState(States.Rotate4, new Rotate(0,States.Move4));
                break;
            case 2:
                machine.addState(States.Move3, new MoveTest(AutonomousData.getInstance().getShootingXPosition3(), 56, States.Wait));
                machine.addState(States.Rotate3,new Rotate(15,States.Wait));
                machine.addState(States.Wait, new wait(15,0.95));
                machine.addState(States.Rotate4, new Rotate(0,States.Move4));
                break;
        }
        machine.addState(States.Move4, new MoveTest(AutonomousData.getInstance().getLineXPosition(), 68, States.Stop));
        machine.runState(States.Tensorflow);

        stop();
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    public double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String vlEncoderName, String vrEncoderName, String hEncoderName){
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);

        verticalLeft = hardwareMap.dcMotor.get(vlEncoderName);
        verticalRight = hardwareMap.dcMotor.get(vrEncoderName);
        horizontal = hardwareMap.dcMotor.get(hEncoderName);

        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
        Conveyor = hardwareMap.get(DcMotor.class,"Conveyor");
        Shooter = hardwareMap.get(DcMotor.class,"Shooter");
        Arm = hardwareMap.get(DcMotor.class,"Arm");
        Claw = hardwareMap.get(Servo.class,"Grip");
        Arm.setTargetPosition(0);
        Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Conveyor.setTargetPosition(0);
        //Conveyor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
