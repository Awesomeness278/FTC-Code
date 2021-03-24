package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MoveTestWithAutoHeading extends StateManager {
    double tx;
    double ty;
    double COUNTS_PER_INCH = 307.699557;
    States exit;
    double speed = 0.6;
    public MoveTestWithAutoHeading(double tx, double ty, States exit){
        this.tx = -tx;
        this.ty = -ty;
        this.exit = exit;
        if(exit==States.Stop){
            this.tx = Double.NaN;
        }
    }
    double startX;
    double startY;
    @Override
    public void Run(StateMachine machine) {
        if (Double.isNaN(this.tx)) {
            this.tx = machine.opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH;
        }
        startX = machine.opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH;
        startY = machine.opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH;
        Autonomous opMode = machine.opMode;
        DcMotor left_back = opMode.left_back;
        DcMotor left_front = opMode.left_front;
        DcMotor right_back = opMode.right_back;
        DcMotor right_front = opMode.right_front;

        double leftFront = 0;
        double leftBack = 0;
        double rightFront = 0;
        double rightBack = 0;

        while (ExitCondition(machine) && opMode.opModeIsActive()) {
            double x = opMode.odometry.returnXCoordinate() / COUNTS_PER_INCH - tx;
            double y = opMode.odometry.returnYCoordinate() / COUNTS_PER_INCH - ty;
            machine.updater.addTelemetry();
            double radius = 1 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            x = x * -radius;
            y = y * -radius;
            double heading = (y > 0 ? Math.atan(x / y) : Math.atan(x / y) + Math.PI);
            double headingDegrees = Math.toDegrees(heading);
            double sp = Math.abs(headingDegrees % 360 - opMode.odometry.returnOrientation() % 360);
            if (sp > 180) {
                sp -= 360;
                sp = Math.abs(sp);
            }
            sp /= 180;
            sp = Math.pow(sp, 0.4);
            sp = Math.min(1 - sp,0.1);
            leftFront += (headingDegrees - opMode.odometry.returnOrientation())/10;
            leftBack += (headingDegrees - opMode.odometry.returnOrientation())/10;
            rightFront += (headingDegrees - opMode.odometry.returnOrientation())/10;
            rightBack += (headingDegrees - opMode.odometry.returnOrientation())/10;
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(sp + leftFront / scalar * speed);
            left_back.setPower(sp + leftBack / scalar * speed);
            right_front.setPower(sp + rightFront / scalar * speed);
            right_back.setPower(sp + rightBack / scalar * speed);
        }
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setPower(0);
        left_front.setPower(0);
        right_back.setPower(0);
        right_front.setPower(0);
        if (!opMode.opModeIsActive()) {
            opMode.stop();
        } else {
            Exit(machine);
        }
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        return opMode.dist(startX,startY,opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH,opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH)<=opMode.dist(startX,startY,tx,ty);
    }

    @Override
    public void Exit(StateMachine machine) {
        if(exit.stateNum==States.Stop.stateNum) {
            machine.opMode.stop();
        }else{
            machine.runState(exit);
        }
    }
}