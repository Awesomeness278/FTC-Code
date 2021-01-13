/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "tfod")

public class ConceptTensorFlowObjectDetectionWebcam extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    double moveSpeed = 0.5;
    double movementConstant = 0.3;
    double rotationConstant = 0.3;
    double leftFront = 0;
    double rightFront = 0;
    double leftBack = 0;
    double rightBack = 0;
    DcMotor left_back;
    DcMotor right_back;
    DcMotor right_front;
    DcMotor left_front;


    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AfweTBj/////AAABmZ1QwFXvX0ltj9QRI7IS1wtULCTBA7CyU8KibbraimizSOgb5iPrsHVE4P/nnAbJuNWXHsqZgW784iI7nfekundyBUv80cdOoe8y/O9125JNbD4fkyufJvrK2RSpv2w9GPY1AtM3fxo70t6r89/WQnpcAHPp244gr0Ua8GL5qUt8XPPE3WcTATty3C/GayFSfe+MTbV8OtB5qN34XhstZYDUgxHcJ+xQLwkYj+FtLTyDc+kRrg+oqLkYA3zNwksq9vWEvTTV0SzsFtU3NbFZtz3P068I25yPHOSqd4bNq36LAcrJchYGidrbJLtRqrEG+4lFD8FWEkpKoWIm4d1DiM0xCcQhiqHH/KQ3fDNP7Xd3";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        left_back = hardwareMap.get(DcMotor.class, "Left Back Motor");
        right_back = hardwareMap.get(DcMotor.class, "Right Back Motor");
        right_front = hardwareMap.get(DcMotor.class, "Right Front Motor");
        left_front = hardwareMap.get(DcMotor.class, "Left Front Motor");
        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        try {
            initVuforia();
            initTfod();
            if (tfod != null) {
                tfod.activate();
            }
        }catch (Exception ex)
        {
            StackTraceElement[] stackTrace = ex.getStackTrace();

            String fullTrace = "";

            for (StackTraceElement trace : stackTrace) {
                String traceInfo
                        = trace.getClassName() + "."
                        + trace.getMethodName() + ":" + trace.getLineNumber()
                        + "(" + trace.getFileName() + ")\n";

                fullTrace = fullTrace.concat(traceInfo);
            }
            telemetry.addData("Trace",fullTrace);
            telemetry.update();
        }

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            if(recognition.getLabel().equals(LABEL_FIRST_ELEMENT)){
                                leftFront += -movementConstant;
                                leftBack += movementConstant;
                                rightFront += movementConstant;
                                rightBack += -movementConstant;
                            }else if(recognition.getLabel().equals(LABEL_SECOND_ELEMENT)){
                                leftFront += movementConstant;
                                leftBack += -movementConstant;
                                rightFront += -movementConstant;
                                rightBack += movementConstant;
                            }
                            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
                            if (scalar < 1) scalar = 1;
                            telemetry.addData("Motor powers",""+leftFront+","+leftBack+","+rightBack+","+rightFront);
                            left_front.setPower(leftFront / scalar * moveSpeed);
                            left_back.setPower(leftBack / scalar * moveSpeed);
                            right_front.setPower(rightFront / scalar * moveSpeed);
                            right_back.setPower(rightBack / scalar * moveSpeed);
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        }
                        if(updatedRecognitions.size() == 0){
                            leftFront +=  rotationConstant;
                            leftBack +=  rotationConstant;
                            rightFront +=  -rotationConstant;
                            rightBack += -rotationConstant;
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
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