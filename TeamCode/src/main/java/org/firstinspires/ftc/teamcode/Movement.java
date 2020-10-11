package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//312 rotations per minute on max power (when power = 1)
//5.2 rotations per second, linear scale from 0 to 1

@Autonomous
public class Movement extends OpMode{
    DcMotor[] motors = new DcMotor[2];

    float[] coords = new float[]{0, 0, 0}; //x,y,theta
    float[] destination = new float[]{0, 0, 0}; //x,y,speed
    double destAngle = 0;
    double setRuntime = 0;
    int drive = 0;
    double movementTime;

    float baseline = 1; //distance between the two wheels
    float circumference = 1; //the circumference of each wheel

    @Override
    public void init() {
        for(int i = 0; i < 2; i++){
            motors[i] = hardwareMap.get(DcMotor.class, "Motor "+(2*i));
        }
        motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status","Initialized");
        telemetry.update();
        motors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        odometryCalculations();
        if(drive>0) {
            if (destAngle * (coords[0] - destination[0]) - (coords[1] - destination[1]) >= 0.01) {
                moveTo(destination[0], destination[1], 0.5);
            }
            if (getRuntime() >= setRuntime) {
                motors[0].setPower(0);
                motors[1].setPower(0);
                drive = (drive + 1)%3;
                if (drive == 2) setRuntime = movementTime;
            } else {
                motors[0].setPower(destination[2]);
                motors[1].setPower(destination[2]*(drive*2-3));
            }
        }
    }

    private void odometryCalculations() {
        //run calculations and update position
    }

    private void moveTo(float x, float y, double power){
        float[] dist = {x-coords[0],y-coords[1]};
        destAngle = Math.atan(dist[0] / dist[1]);
        double rotationAngle = (coords[2] - destAngle + Math.PI) % (2 * Math.PI) - Math.PI;
        double rotationTime = getRuntime() + (0.5 * baseline * rotationAngle) / (power*5.2*circumference);
        movementTime = getRuntime() + rotationTime + Math.sqrt(Math.pow(dist[0], 2) + Math.pow(dist[1], 2)) / (power*5.2*circumference);
        drive = 1;
        setRuntime = rotationTime;
    }
}
