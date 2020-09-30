package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class driveOpMode extends LinearOpMode{
    private DcMotor[] motors = new DcMotor[2];
    private Servo servo;
    private ColorRangeSensor color;
    private double servoPower;
    private double[] tgtPowers = new double[4];
    double leftPower;
    double rightPower;
    Vector dir;
    @Override
    public void runOpMode(){
        for(int i = 0; i < 2; i++){
            motors[i] = hardwareMap.get(DcMotor.class, "Motor "+(2*i));
        }
        //servo = hardwareMap.get(Servo.class,"servo");
        //color = hardwareMap.get(ColorRangeSensor.class, "colorRangeSensor");
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            //NormalizedRGBA sensedColor = color.getNormalizedColors();
            dir = new Vector(this.gamepad1.left_stick_x,-this.gamepad1.left_stick_y);
            double drive = -gamepad1.left_stick_y;
            double drive2  =  gamepad1.right_stick_y;
            tgtPowers[0] = drive;
            tgtPowers[1] = drive;
            tgtPowers[2] = drive2;
            tgtPowers[3] = drive2;
            for(int i = 0; i < 2; i++){
                motors[i].setPower(tgtPowers[2*i]);
            }
            //servo.setPosition(servoPower);
            telemetry.addData("Status", "Running");
            telemetry.addData("Direction","X: "+dir.x+", Y: "+dir.y);
            telemetry.update();
        }
    }
}
