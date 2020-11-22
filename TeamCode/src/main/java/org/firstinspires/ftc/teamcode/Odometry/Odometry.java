package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class Odometry extends LinearOpMode {
    DcMotor verticalLeft = hardwareMap.get(DcMotor.class,"Right Back Motor");
    DcMotor verticalRight = hardwareMap.get(DcMotor.class,"Left Front Motor");
    DcMotor horizontal = hardwareMap.get(DcMotor.class,"Left Back Motor");

    double ticksPerInch = 307.699557;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        int pvlt = 0;
        int pvrt = 0;
        int pht = 0;
        int x = 0;
        int y = 0;
        int a = 0;
        while(opModeIsActive()) {
            int vlticks = verticalLeft.getCurrentPosition()-pvlt;
            int vrticks = verticalRight.getCurrentPosition()-pvrt;
            int hticks = horizontal.getCurrentPosition()-pht;
            pvlt = verticalLeft.getCurrentPosition();
            pvrt = verticalRight.getCurrentPosition();
            pht = horizontal.getCurrentPosition();
            double ld = vlticks / ticksPerInch;
            double rd = vrticks / ticksPerInch;
            double hd = hticks / ticksPerInch;
            double md = (rd + ld) / 2;
            double L = 15.625;
            double theta = (rd - ld)/L;
            double dth = L / 2;
            double betterhd = dth;
            double circumfrence = 2 * Math.PI * dth;
            hd = hd - (circumfrence * (theta / 360));
            x+=hd*Math.cos(theta+a);
            y+=hd*Math.sin(theta+a);
            double q = theta/2;
            double hyp = (md/theta)*Math.sin(theta/Math.cos(theta/2));
            x+=md*Math.cos(q+a);
            y+=md*Math.sin(q+a);
            a+=theta;
        }
    }
}