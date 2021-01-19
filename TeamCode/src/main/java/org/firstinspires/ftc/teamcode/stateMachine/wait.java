package org.firstinspires.ftc.teamcode.stateMachine;

public class wait extends StateManager {
    public wait(){}
    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){}
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