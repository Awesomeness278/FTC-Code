package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

public class Rotate extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        OdometryGlobalCoordinatePosition odometry = machine.opMode.odometry;
        int dir = 0;
        if(targetDegrees<odometry.returnOrientation()){
            dir = -1;
        }else{
            dir = 1;
        }
        DcMotor left_front = machine.opMode.left_front;
        DcMotor left_back = machine.opMode.left_back;
        DcMotor right_front = machine.opMode.right_front;
        DcMotor right_back = machine.opMode.right_back;
        while(Math.abs(targetDegrees)<Math.abs(odometry.returnOrientation())){
            double leftFront = 0;
            double rightFront = 0;
            double leftBack = 0;
            double rightBack = 0;
            leftFront += dir;
            leftBack += dir;
            rightFront -= dir;
            rightBack -= dir;
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            left_front.setPower(leftFront / scalar * 1);
            left_back.setPower(leftBack / scalar * 1);
            right_front.setPower(rightFront / scalar * 1);
            right_back.setPower(rightBack / scalar * 1);
        }
        left_back.setPower(0);
        left_front.setPower(0);
        right_back.setPower(0);
        right_front.setPower(0);
        Exit(machine);
    }

    double targetDegrees;

    public Rotate(double targetDegrees){
        this.targetDegrees = targetDegrees;
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Tensorflow);
    }
}