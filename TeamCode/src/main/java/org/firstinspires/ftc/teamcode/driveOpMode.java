package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.teamcode.Vector2d;

@TeleOp
public class driveOpMode extends LinearOpMode{
    private DcMotor[] motors = new DcMotor[4];
    private Servo servo;
    private double servoPower;
    private double[] tgtPowers = new double[4];
    double tgtPower0 = 0;
    double tgtPower1 = 0;
    double tgtPower2 = 0;
    double tgtPower3 = 0;
    Vector2d dir;
    @Override
    public void runOpMode(){
        for(int i = 0; i < 4; i++){
            motors[i] = hardwareMap.get(DcMotor.class, "Motor "+i);
        }
        servo = hardwareMap.get(Servo.class,"servo");
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            dir = new Vector2d(this.gamepad1.left_stick_x,-this.gamepad1.left_stick_y);
            servoPower = -gamepad1.right_stick_y;
            tgtPowers[0] = this.gamepad1.left_stick_y;
            tgtPowers[1] = -this.gamepad1.left_stick_y;
            tgtPowers[2] = -this.gamepad1.left_stick_y;
            tgtPowers[3] = this.gamepad1.left_stick_y;
            for(int i = 0; i < 4; i++){
                motors[i].setPower(tgtPowers[i]);
            }
            servo.setPosition(servoPower);
            telemetry.addData("Status", "Running");
            telemetry.addData("Direction","X: "+dir.x+", Y: "+dir.y);
            telemetry.update();
        }
    }
}
