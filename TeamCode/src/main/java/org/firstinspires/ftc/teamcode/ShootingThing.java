package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ShootingThing extends LinearOpMode {
    //Drive motors
    DcMotor Intake, Shooter;
    Servo BottomServo, TopServo;

    //Hardware Map Names for drive motors and odometry wheels. THIS WILL CHANGE ON EACH ROBOT, YOU NEED TO UPDATE THESE VALUES ACCORDINGLY
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor";

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        initDriveHardwareMap();

        telemetry.addData("Status", "Init Complete");
        telemetry.update();

        double bottom = 0;
        double top = 0;

        waitForStart();

        while(opModeIsActive()){

            bottom += 1.0;

            if(gamepad2.a) {
                top += 0.5;
            }

            telemetry.addData("test","test");
            Intake.setPower(1);
            Shooter.setPower(1);
            BottomServo.setPosition(bottom);
            TopServo.setPosition(top);
            telemetry.update();
        }

    }

    private void initDriveHardwareMap(){
        Intake = hardwareMap.dcMotor.get("Intake");
        Shooter = hardwareMap.dcMotor.get("Shooter");
        TopServo = hardwareMap.get(Servo.class,"Top Servo");
        BottomServo = hardwareMap.get(Servo.class,"Bottom Servo");

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }

}
