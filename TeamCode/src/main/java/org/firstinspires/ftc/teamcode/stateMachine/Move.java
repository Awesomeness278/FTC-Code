package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Odometry.MoveTo;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

public class Move extends StateMachine{
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
    public void Run(StateManager manager, Autonomous opMode) {
        DcMotor verticalLeft = opMode.motors[2];
        DcMotor verticalRight = opMode.motors[1];
        DcMotor horizontal = opMode.motors[3];
        DcMotor left_back = opMode.motors[0];
        DcMotor left_front = opMode.motors[1];
        DcMotor right_back = opMode.motors[2];
        DcMotor right_front = opMode.motors[3];
        double pdist = 100000;
        double dist = 100000;

        while(dist<=pdist) {
            double x = opMode.globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH - ty;
            double nx = x * Math.cos(Math.toDegrees(opMode.globalPositionUpdate.returnOrientation())) - y * Math.sin(Math.toDegrees(opMode.globalPositionUpdate.returnOrientation()));
            double ny = x * Math.sin(Math.toDegrees(opMode.globalPositionUpdate.returnOrientation())) + y * Math.cos(Math.toDegrees(opMode.globalPositionUpdate.returnOrientation()));
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
        Exit(manager, opMode);
    }

    @Override
    public boolean ExitCondition(Autonomous opMode) {
        return true;
    }

    @Override
    public void Exit(StateManager manager, Autonomous opMode) {
        opMode.telemetry.addData("Moved","Yes");
        manager.states.get(exit).Run(manager,opMode);
        opMode.stop();
    }
}