package org.firstinspires.ftc.teamcode.stateMachine;

public class State extends StateManager {
    public State(){};
    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)&&!machine.opMode.opModeIsActive()){

        }
        Exit(machine);
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