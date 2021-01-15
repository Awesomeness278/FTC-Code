package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MoveTest extends StateManager {
    double tx;
    double ty;
    double COUNTS_PER_INCH = 307.699557;
    States exit;
    double sp = 0.4;
    public MoveTest(double tx, double ty,States exit){
        this.tx = tx;
        this.ty = ty;
        this.exit = exit;
    }
    double startX;
    double startY;
    @Override
    public void Run(StateMachine machine) {
        startX = machine.opMode.odometry.returnXCoordinate();
        startY = machine.opMode.odometry.returnYCoordinate();
        Autonomous opMode = machine.opMode;
        DcMotor left_back = opMode.LeftBackMotor;
        DcMotor left_front = opMode.LeftFrontMotor;
        DcMotor right_back = opMode.RightBackMotor;
        DcMotor right_front = opMode.RightFrontMotor;
        double leftFront = 0;
        double leftBack = 0;
        double rightFront = 0;
        double rightBack = 0;

        while(ExitCondition(machine)) {
            double x = opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH - ty;
            double nx = x * Math.cos(Math.toDegrees(opMode.odometry.returnOrientation())) - y * Math.sin(Math.toDegrees(opMode.odometry.returnOrientation()));
            double ny = x * Math.sin(Math.toDegrees(opMode.odometry.returnOrientation())) + y * Math.cos(Math.toDegrees(opMode.odometry.returnOrientation()));
            x = nx;
            y = ny;
            double r = 1 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            x = x * -r;
            y = y * r;
            leftFront += (y + x);
            leftBack += (y - x);
            rightFront += (y - x);
            rightBack += (y + x);
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(leftFront / scalar * sp);
            left_back.setPower(leftBack / scalar * sp);
            right_front.setPower(rightFront / scalar * sp);
            right_back.setPower(rightBack / scalar * sp);
        }
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setPower(0);
        left_front.setPower(0);
        right_back.setPower(0);
        right_front.setPower(0);
        opMode.stop();
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        return opMode.dist(startX,startY,opMode.odometry.returnXCoordinate(),opMode.odometry.returnYCoordinate())<opMode.dist(tx,ty,startX,startY);
    }

    @Override
    public void Exit(StateMachine machine) {
        int stateNumber = 0;
        machine.runState(exit);
    }
}