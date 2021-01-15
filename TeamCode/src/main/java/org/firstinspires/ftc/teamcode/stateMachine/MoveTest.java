package org.firstinspires.ftc.teamcode.stateMachine;

import android.sax.StartElementListener;

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
        startX = machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH;
        startY = machine.opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH;
        Autonomous opMode = machine.opMode;
        DcMotor left_back = opMode.left_back;
        DcMotor left_front = opMode.left_front;
        DcMotor right_back = opMode.right_back;
        DcMotor right_front = opMode.right_front;
        double leftFront = 0;
        double leftBack = 0;
        double rightFront = 0;
        double rightBack = 0;

        while(ExitCondition(machine)&&opMode.opModeIsActive()) {
            double x = opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH - ty;
            opMode.telemetry.addData("Orientation",opMode.odometry.returnOrientation());
            opMode.telemetry.addData("Runtime",opMode.getRuntime());
            opMode.telemetry.update();
            double r = 1 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            x = x * -r;
            y = y * r;
            leftFront += (-y + x)-opMode.odometry.returnOrientation()/10;
            leftBack += (-y - x)-opMode.odometry.returnOrientation()/10;
            rightFront += (-y - x)+opMode.odometry.returnOrientation()/10;
            rightBack += (-y + x)+opMode.odometry.returnOrientation()/10;
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
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        return opMode.dist(startX,startY,opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH,opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH)<opMode.dist(tx,ty,startX,startY);
    }

    @Override
    public void Exit(StateMachine machine) {
        int stateNumber = 0;
        machine.runState(exit);
    }
}