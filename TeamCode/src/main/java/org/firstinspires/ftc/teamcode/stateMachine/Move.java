package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Move extends StateManager {
    float tx;
    float ty;
    double sp;
    int exit;
    double COUNTS_PER_INCH = 307.699557;


    public Move(float x,float y,int exit){
        this.tx = x;
        this.ty = y;
        this.sp = 0.5;
        this.exit = exit;
    }

    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        DcMotor verticalLeft = opMode.LeftBackMotor;
        DcMotor verticalRight = opMode.LeftFrontMotor;
        DcMotor horizontal = opMode.RightFrontMotor;
        DcMotor left_back = opMode.LeftBackMotor;
        DcMotor left_front = opMode.LeftFrontMotor;
        DcMotor right_back = opMode.RightBackMotor;
        DcMotor right_front = opMode.RightFrontMotor;
        double pdist = 100000;
        double dist = 100000;

        while(dist<=pdist) {
            double x = opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH - ty;
            double nx = x * Math.cos(Math.toDegrees(opMode.odometry.returnOrientation())) - y * Math.sin(Math.toDegrees(opMode.odometry.returnOrientation()));
            double ny = x * Math.sin(Math.toDegrees(opMode.odometry.returnOrientation())) + y * Math.cos(Math.toDegrees(opMode.odometry.returnOrientation()));
            x = nx;
            y = ny;
            double r = 1 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            x = x * -r;
            y = y * r;
            double scalar = Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2)) / (Math.abs(y) + Math.abs(x));
            left_front.setPower(-sp);
            left_back.setPower(-sp);
            right_front.setPower(-sp);
            right_back.setPower(-sp);
            pdist = dist;
            dist = Math.sqrt(Math.pow(x - tx, 2) + Math.pow(y - ty, 2));
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.opMode.telemetry.addData("Moved","Yes");
        machine.runState(States.MoveToWobble);
    }
}