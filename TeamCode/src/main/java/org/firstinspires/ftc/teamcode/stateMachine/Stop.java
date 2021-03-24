package org.firstinspires.ftc.teamcode.stateMachine;

public class Stop extends StateManager {
    public Stop(){}
    @Override
    public void Run(StateMachine machine) {
        machine.opMode.stop();
    }

    @Override
    public boolean ExitCondition(StateMachine machine) { return true; }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Tensorflow);
    }
}