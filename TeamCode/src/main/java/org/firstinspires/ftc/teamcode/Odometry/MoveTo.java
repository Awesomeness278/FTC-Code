package org.firstinspires.ftc.teamcode.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;
import org.firstinspires.ftc.teamcode.stateMachine.*;

import java.util.Vector;

/**
 * Created by Sarthak on 10/4/2019.
 */
@TeleOp
public class MoveTo extends LinearOpMode {
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back;
    //Odometry Wheels
    DcMotor verticalLeft, verticalRight, horizontal;

    final double COUNTS_PER_INCH = 307.699557;
    double tx;
    double ty;
    double to;
    public void setupGoto(double tx, double ty, double to){
        this.tx = tx;
        this.ty = ty;
        this.to = to;
    }
    //Hardware Map Names for drive motors and odometry wheels. THIS WILL CHANGE ON EACH ROBOT, YOU NEED TO UPDATE THESE VALUES ACCORDINGLY
    String rfName = "Right Front Motor", rbName = "Right Back Motor", lfName = "Left Front Motor", lbName = "Left Back Motor";
    String verticalLeftEncoderName = rbName, verticalRightEncoderName = lfName, horizontalEncoderName = lbName;
    int state = 0;
    double sp = 0.1;
    OdometryGlobalCoordinatePosition globalPositionUpdate;

    @Override
    public void runOpMode() throws InterruptedException {
        setupGoto(0,-12,0);
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        initDriveHardwareMap(rfName, rbName, lfName, lbName, verticalLeftEncoderName, verticalRightEncoderName, horizontalEncoderName);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

        //Create and start GlobalCoordinatePosition thread to constantly update the global coordinate positions
        globalPositionUpdate = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(globalPositionUpdate);
        positionThread.start();

        globalPositionUpdate.reverseRightEncoder();
        globalPositionUpdate.reverseNormalEncoder();

        while(opModeIsActive()) {
            //Display Global (x, y, theta) coordinates
            telemetry.addData("X Position", globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH);
            telemetry.addData("Y Position", globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH);
            telemetry.addData("Orientation (Degrees)", globalPositionUpdate.returnOrientation());

            telemetry.addData("Vertical left encoder position", verticalLeft.getCurrentPosition());
            telemetry.addData("Vertical right encoder position", verticalRight.getCurrentPosition());
            telemetry.addData("horizontal encoder position", horizontal.getCurrentPosition());
            telemetry.addData("State",state);
            telemetry.addData("X Distance",globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH-tx);
            telemetry.addData("Y Distance",globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH-ty);
            telemetry.addData("Total Distance",Math.sqrt(Math.pow(tx-globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH,2)+Math.pow(ty-globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH,2)));

            telemetry.addData("Thread Active", positionThread.isAlive());
            telemetry.update();
            if(state == 0){
                state++;
            }if(state == 1){
                double x = globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH-tx;
                double y = globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH-ty;
                double r = 1/Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                x = x * -r;
                y = y * r;
                double scalar = Math.sqrt(Math.pow(y,2)+Math.pow(x,2))/(Math.abs(y)+Math.abs(x));
                left_front.setPower(-sp);
                left_back.setPower(-sp);
                right_front.setPower(-sp);
                right_back.setPower(-sp);
                if(globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH<-12){
                    state++;
                }
            }else if(state == 2){
                state++;
                left_front.setPower(0);
                left_back.setPower(0);
                right_front.setPower(0);
                right_back.setPower(0);
            }
        }
        //Stop the thread
        globalPositionUpdate.stop();

    }

    private boolean OrientTo(double orientation,double targetOrientation){
        if (Math.abs(orientation - targetOrientation) > sp) {
            if(orientation - targetOrientation < 0) {
                left_back.setPower(0.5);
                left_front.setPower(0.5);
                right_back.setPower(-0.5);
                right_front.setPower(-0.5);
            } else {
                left_back.setPower(-0.5);
                left_front.setPower(-0.5);
                right_back.setPower(0.5);
                right_front.setPower(0.5);
            }
            return false;
        }else{
            return true;
        }
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String vlEncoderName, String vrEncoderName, String hEncoderName){
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);

        verticalLeft = hardwareMap.dcMotor.get(vlEncoderName);
        verticalRight = hardwareMap.dcMotor.get(vrEncoderName);
        horizontal = hardwareMap.dcMotor.get(hEncoderName);

        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right_front.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }

    /**
     * Calculate the power in the x direction
     * @param desiredAngle angle on the x axis
     * @param speed robot's speed
     * @return the x vector
     */
    private double calculateX(double desiredAngle, double speed) {
        return Math.sin(Math.toRadians(desiredAngle)) * speed;
    }

    /**
     * Calculate the power in the y direction
     * @param desiredAngle angle on the y axis
     * @param speed robot's speed
     * @return the y vector
     */
    private double calculateY(double desiredAngle, double speed) {
        return Math.cos(Math.toRadians(desiredAngle)) * speed;
    }
}