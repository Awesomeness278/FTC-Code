package org.firstinspires.ftc.teamcode.stateMachine;

public class Park extends StateManager {
    public Park(){}
    @Override
    public void Run(StateMachine machine) {
        machine.opMode.parker.setPosition(1);
        machine.opMode.stop();
    }

    @Override
    public boolean ExitCondition(StateMachine machine) { return true; }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Tensorflow);
    }
}