package org.firstinspires.ftc.teamcode.stateMachine;

public class State extends StateManager {
    @Override
    public void Run(StateMachine manager, Autonomous opMode) {
        while(!ExitCondition(opMode)){

        }
        Exit(manager, opMode);
    }

    @Override
    public boolean ExitCondition(Autonomous opMode) {
        return true;
    }

    @Override
    public void Exit(StateMachine manager, Autonomous opMode) {
        int stateNumber = 0;
        manager.states.get(stateNumber).Run(manager,opMode);
    }
}