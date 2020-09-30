package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class testOpMode extends LinearOpMode{
    private DcMotor motor;
    private double servoPower;
    private double tgtPowers;
    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"Motor 0");
        //servo = hardwareMap.get(Servo.class,"servo");
        //color = hardwareMap.get(ColorRangeSensor.class, "colorRangeSensor");
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            //NormalizedRGBA sensedColor = color.getNormalizedColors();
            tgtPowers = -this.gamepad1.left_stick_y;
            motor.setPower(tgtPowers);
            //servo.setPosition(servoPower);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
