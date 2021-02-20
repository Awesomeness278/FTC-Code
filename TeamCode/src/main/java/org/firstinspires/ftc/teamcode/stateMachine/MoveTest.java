package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;

public class MoveTest extends StateManager {
    public MoveTest(double tx, double ty, States exit){
        this.tx = -tx;
        this.ty = ty;
        this.exit = exit;
        if(this.exit==States.Stop){
            this.tx = Double.NaN;
        }
    }

    double speed = 0.5;
    double COUNTS_PER_INCH;

    @Override
    public void Run(StateMachine machine) {
        this.COUNTS_PER_INCH = machine.opMode.COUNTS_PER_INCH;
        if(Double.isNaN(this.tx)){
            this.tx = machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH;
        }
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){
            double relativeTargetX = this.tx-machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH;
            double relativeTargetY = this.ty-machine.opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH;
            double orientation = Math.toRadians(-machine.opMode.odometry.returnOrientation());
            double rotatedX = relativeTargetX*Math.cos(orientation)-relativeTargetY*Math.sin(orientation);
            double rotatedY = relativeTargetX*Math.sin(orientation)+relativeTargetY*Math.cos(orientation);
            double vectorMagnitude = 1/(Math.abs(rotatedX)+Math.abs(rotatedY));
            double scaledX = rotatedX*vectorMagnitude;
            double scaledY = rotatedY*vectorMagnitude;
            Autonomous opMode = machine.opMode;
            DcMotor left_back = opMode.left_back;
            DcMotor left_front = opMode.left_front;
            DcMotor right_back = opMode.right_back;
            DcMotor right_front = opMode.right_front;
            left_back.setPower(speed*(-scaledY-scaledX));
            left_front.setPower(speed*(-scaledY+scaledX));
            right_back.setPower(speed*(-scaledY+scaledX));
            right_front.setPower(speed*(-scaledY-scaledX));
        }
        Exit(machine);
    }

    States exit;
    double tx;
    double ty;

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return machine.opMode.dist(machine.opMode.odometry.returnXCoordinate()/COUNTS_PER_INCH,machine.opMode.odometry.returnYCoordinate()/COUNTS_PER_INCH,tx,ty)<=2;
    }

    @Override
    public void Exit(StateMachine machine) {
        if(this.exit==States.Stop){
            machine.opMode.stop();
        }else {
            machine.runState(this.exit);
        }
    }
}