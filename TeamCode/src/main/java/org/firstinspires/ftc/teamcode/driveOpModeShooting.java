package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class driveOpModeShooting extends LinearOpMode{

    //Motors 1-4 are for driving, 1 & 2 left, 3 & 4 right, 5 for intake, and 6 for flywheel
    private DcMotor[] motors = new DcMotor[6];
    private Servo[] servo = new Servo[3];

    private ColorRangeSensor color;

    private double[] tgtPowers = new double[4];

    Vector dir;

    boolean buttonLocka
    @Override
    public void runOpMode(){
        for(int i = 0; i < 6; i++){
            motors[i] = hardwareMap.get(DcMotor.class, "Motor "+(i));
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        for(int i = 0; i < 3; i++){
            servo[i] = hardwareMap.get(Servo.class,"Servo "+(i));
        }
        motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        //color = hardwareMap.get(ColorRangeSensor.class, "colorRangeSensor");
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            //NormalizedRGBA sensedColor = color.getNormalizedColors();
            dir = new Vector(this.gamepad1.left_stick_x,-this.gamepad1.left_stick_y);
            double drive = gamepad1.left_stick_y/2;
            double drive2 = gamepad1.right_stick_y/2;
            tgtPowers[0] = drive;
            tgtPowers[1] = drive;
            tgtPowers[2] = drive2;
            tgtPowers[3] = drive2;
            for(int i = 0; i < 4; i++){
                motors[i].setPower(tgtPowers[i]);
            }
            telemetry.addData("Zero Power Behavior",motors[0].getZeroPowerBehavior());
            telemetry.addData("Status", "Running");
            telemetry.addData("Direction","X: "+dir.x+", Y: "+dir.y);
            telemetry.update();

        }
    }
}
