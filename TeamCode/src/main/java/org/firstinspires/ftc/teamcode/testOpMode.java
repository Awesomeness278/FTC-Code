package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class testOpMode extends LinearOpMode{
    private DcMotor[] motors = new DcMotor[4];
    private double[] tgtPowers = new double[4];
    private DcMotor motor0;
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    double tgtPower0 = 0;
    double tgtPower1 = 0;
    double tgtPower2 = 0;
    double tgtPower3 = 0;
    @Override
    public void runOpMode(){
        for(int i = 0; i < 4; i++){
            motors[i] = hardwareMap.get(DcMotor.class, "Motor "+i);
        }
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {
            tgtPowers[0] = -this.gamepad1.left_stick_y;
            tgtPowers[1] = -this.gamepad1.right_stick_y;
            tgtPowers[2] = -this.gamepad1.left_stick_x;
            tgtPowers[3] = -this.gamepad1.right_stick_x;
            for(int i = 0; i < 4; i++){
                motors[i].setPower(tgtPowers[i]);
            }
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
