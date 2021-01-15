package org.firstinspires.ftc.teamcode.stateMachine;

public class Move extends StateManager {
    public Move(double destinationX, double destinationY, int destinationOrientation){

    }
    @Override
    public void Run(StateMachine machine){
        Autonomous opMode = machine.opMode;
        opMode.odometry.returnOrientation();
        double changeX = opMode.odometry.returnXCoordinate() - destinationX;
        double changeY = opMode.odometry.returnYCoordinate() - destinationY;
        if(changeX > 0) {
            double slope = changeY / changeX;
            double orient = Math.atan(slope)/180;
        }

        leftFront += -gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
        leftBack += -gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;
        rightFront += -gamepad1.left_stick_y+gamepad1.left_stick_x-gamepad1.right_stick_x;
        rightBack += -gamepad1.left_stick_y-gamepad1.left_stick_x-gamepad1.right_stick_x;
        leftFrontMotor.setPower(leftFront/scalar*moveSpeed);
        leftBackMotor.setPower(leftBack/scalar*moveSpeed);
        rightFrontMotor.setPower(rightFront/scalar*moveSpeed);
        rightBackMotor.setPower(rightBack/scalar*moveSpeed);

        while(!ExitCondition(machine)){

        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        int stateNumber = 0;
        machine.runState(States.Start);
    }
}