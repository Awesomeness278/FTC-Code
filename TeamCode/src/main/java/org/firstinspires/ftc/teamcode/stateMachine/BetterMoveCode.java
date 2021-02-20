package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;

public class BetterMoveCode extends StateManager {
    public BetterMoveCode(double tx, double ty, States exit){
        this.tx = -tx;
        this.ty = -ty;
        this.exit = exit;
    }

    double speed = 0.5;

    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)&&!machine.opMode.opModeIsActive()){
            double relativeTargetX = this.tx-machine.opMode.odometry.returnXCoordinate();
            double relativeTargetY = this.ty-machine.opMode.odometry.returnYCoordinate();
            double orientation = -machine.opMode.odometry.returnOrientation();
            double rotatedX = relativeTargetX*Math.cos(orientation)-relativeTargetY*Math.sin(orientation);
            double rotatedY = relativeTargetX*Math.sin(orientation)+relativeTargetY*Math.cos(orientation);
            double vectorMagnitude = 1/(Math.abs(rotatedX)+Math.abs(rotatedY));
            double normalizedX = rotatedX*vectorMagnitude;
            double normalizedY = rotatedY*vectorMagnitude;
            Autonomous opMode = machine.opMode;
            DcMotor left_back = opMode.left_back;
            DcMotor left_front = opMode.left_front;
            DcMotor right_back = opMode.right_back;
            DcMotor right_front = opMode.right_front;
            left_back.setPower(speed*(-normalizedY-normalizedX));
            left_front.setPower(speed*(-normalizedY+normalizedX));
            right_back.setPower(speed*(-normalizedY+normalizedX));
            right_front.setPower(speed*(-normalizedY-normalizedX));
        }
        Exit(machine);
    }

    States exit;
    double tx;
    double ty;

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return machine.opMode.dist(machine.opMode.odometry.returnXCoordinate(),machine.opMode.odometry.returnYCoordinate(),tx,ty)<2;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(this.exit);
    }
}