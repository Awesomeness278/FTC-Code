package org.firstinspires.ftc.teamcode.stateMachine;

public class wait extends StateManager {
    public wait(){}
    @Override
    public void Run(StateMachine machine) {
        double delay = 2;
        double time = 0.2;
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()) {
            double leftFront = 0;
            double rightFront = 0;
            double leftBack = 0;
            double rightBack = 0;
            leftFront += -machine.opMode.odometry.returnOrientation()/10;
            leftBack += -machine.opMode.odometry.returnOrientation()/10;
            rightFront += machine.opMode.odometry.returnOrientation()/10;
            rightBack += machine.opMode.odometry.returnOrientation()/10;
            double scalar = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
            if (scalar < 1) scalar = 1;
            machine.opMode.left_front.setPower(leftFront / scalar * 0.1);
            machine.opMode.left_back.setPower(leftBack / scalar * 0.1);
            machine.opMode.right_front.setPower(rightFront / scalar * 0.1);
            machine.opMode.right_back.setPower(rightBack / scalar * 0.1);
            machine.opMode.Shooter.setPower(-1);
            if (machine.opMode.getRuntime() - time < machine.opMode.getRuntime() - machine.opMode.getRuntime() % (time+delay)) {
                machine.opMode.Conveyor.setPower(1);
            } else {
                machine.opMode.Conveyor.setPower(0);
            }
        }
        machine.opMode.Shooter.setPower(0);
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return machine.opMode.getRuntime()>25;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move4);
    }
}