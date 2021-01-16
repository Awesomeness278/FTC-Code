package org.firstinspires.ftc.teamcode.stateMachine;


import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

import static org.firstinspires.ftc.teamcode.stateMachine.Tensorflow.*;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    public Autonomous(){
    }
    StateMachine machine = new StateMachine(this);
    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;
    DcMotor Shooter;
    DcMotor Conveyor;
    DcMotor Intake;
    DcMotor Claw;
    ColorRangeSensor[] colors;
    Servo[] servos;
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor";
    String verticalLeftEncoderName = rbName, verticalRightEncoderName = lfName, horizontalEncoderName = rfName;
    OdometryGlobalCoordinatePosition odometry;
    DcMotor verticalLeft;
    DcMotor verticalRight;
    DcMotor horizontal;
    private static final String VUFORIA_KEY =
            "AfweTBj/////AAABmZ1QwFXvX0ltj9QRI7IS1wtULCTBA7CyU8KibbraimizSOgb5iPrsHVE4P/nnAbJuNWXHsqZgW784iI7nfekundyBUv80cdOoe8y/O9125JNbD4fkyufJvrK2RSpv2w9GPY1AtM3fxo70t6r89/WQnpcAHPp244gr0Ua8GL5qUt8XPPE3WcTATty3C/GayFSfe+MTbV8OtB5qN34XhstZYDUgxHcJ+xQLwkYj+FtLTyDc+kRrg+oqLkYA3zNwksq9vWEvTTV0SzsFtU3NbFZtz3P068I25yPHOSqd4bNq36LAcrJchYGidrbJLtRqrEG+4lFD8FWEkpKoWIm4d1DiM0xCcQhiqHH/KQ3fDNP7Xd3";

    VuforiaLocalizer vuforia;

    TFObjectDetector tfod;
    static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    static final String LABEL_FIRST_ELEMENT = "Quad";
    static final String LABEL_SECOND_ELEMENT = "Single";
    double COUNTS_PER_INCH = 307.699557;
    Recognition recognition;
    @Override
    public void runOpMode() throws InterruptedException {
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        initDriveHardwareMap(rfName, rbName, lfName, lbName, verticalLeftEncoderName, verticalRightEncoderName, horizontalEncoderName);
        odometry = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(odometry);
        odometry.reverseRightEncoder();
        positionThread.start();
        waitForStart();
        machine.addState(States.Tensorflow,new Tensorflow());
        machine.addState(States.MoveToWobble,new MoveToWobble());
        machine.addState(States.Move2,new MoveTest(12,48,States.Move3));
        machine.addState(States.Move3,new MoveTest(12,60,States.Stop));
        machine.runState(States.Tensorflow);
    }
    /*
    * Any methods that you use more than once can be defined here.
    * */
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
        tfodParameters.minResultConfidence = 0.8f;
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

        right_back.setDirection(DcMotorSimple.Direction.REVERSE);
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }
}
