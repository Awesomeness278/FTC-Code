package org.firstinspires.ftc.teamcode.stateMachine;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    StateMachine machine = new StateMachine();
    DcMotor LeftFrontMotor;
    DcMotor RightFrontMotor;
    DcMotor LeftBackMotor;
    DcMotor RightBackMotor;
    DcMotor Shooter;
    DcMotor Conveyor;
    DcMotor Intake;
    DcMotor Claw;
    ColorRangeSensor[] colors;
    Servo[] servos;
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
        odometry = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(odometry);
        odometry.reverseRightEncoder();
        odometry.reverseNormalEncoder();
        positionThread.start();
        machine.addState(States.Start,new Start());
        machine.addState(States.MoveToWobble,new Move(12,72,States.StopMovingToWobble.stateNum));
        waitForStart();
        machine.runState(States.Start);
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
}
