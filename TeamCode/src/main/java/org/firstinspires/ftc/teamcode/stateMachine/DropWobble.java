package org.firstinspires.ftc.teamcode.stateMachine;

public class DropWobble extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        machine.opMode.Arm.setPower(-0.15);
        machine.opMode.Arm.setTargetPosition(-245);
        double startWait = machine.opMode.getRuntime();
        while(machine.opMode.getRuntime()-startWait<1){
        }
        machine.opMode.Claw.setPosition(1);
        startWait = machine.opMode.getRuntime();
        while(machine.opMode.getRuntime()-startWait<1){
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move3);
    }
}