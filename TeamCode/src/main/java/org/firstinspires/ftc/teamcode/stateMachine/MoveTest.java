package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

public class MoveTest extends StateManager {
    public MoveTest(double tx,double ty,States exit){
        this.ty = -ty;
        this.tx = -tx;
        this.exit = exit;
        if(exit.stateNum == States.Stop.stateNum){
            this.tx = Double.NaN;
        }
    }
    double tx;
    double ty;
    double sp = 0.4;
    States exit;
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        if(Double.isNaN(this.tx)){
            this.tx = opMode.odometry.returnXCoordinate()/opMode.COUNTS_PER_INCH;
        }
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){
            double tvx = machine.opMode.odometry.returnXCoordinate()/machine.opMode.COUNTS_PER_INCH - tx;//Gets the target vector
            double tvy = machine.opMode.odometry.returnYCoordinate()/machine.opMode.COUNTS_PER_INCH - ty;
            double max = Math.max(Math.abs(tvx), Math.abs(tvy));
            double nx = tvx/max;//Normalizes the target vector.
            double ny = tvy/max;
            double rx = nx*Math.cos(-Math.toRadians(machine.opMode.odometry.returnOrientation())) - ny*Math.sin(-Math.toRadians(machine.opMode.odometry.returnOrientation()));
            double ry = nx*Math.sin(-Math.toRadians(machine.opMode.odometry.returnOrientation())) + ny*Math.cos(-Math.toRadians(machine.opMode.odometry.returnOrientation()));
            double leftFront = rx+ry;
            double leftBack = ry-rx;
            double rightFront = ry-rx;
            double rightBack = rx+ry;
            opMode.left_front.setPower(-leftFront*sp);
            opMode.left_back.setPower(-leftBack*sp);
            opMode.right_back.setPower(-rightBack*sp);
            opMode.right_front.setPower(-rightFront*sp);
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) { return machine.opMode.dist(tx,ty,machine.opMode.odometry.returnXCoordinate()/machine.opMode.COUNTS_PER_INCH,machine.opMode.odometry.returnYCoordinate()/machine.opMode.COUNTS_PER_INCH)<4; }

    @Override
    public void Exit(StateMachine machine) {
        if(exit.stateNum==States.Stop.stateNum){
            machine.opMode.stop();
        }else {
            machine.runState(exit);
        }
    }
}