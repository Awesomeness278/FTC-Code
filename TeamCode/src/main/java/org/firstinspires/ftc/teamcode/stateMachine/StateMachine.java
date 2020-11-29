package org.firstinspires.ftc.teamcode.stateMachine;

import java.util.ArrayList;

public class StateMachine {
    Autonomous opMode;
    ArrayList<StateManager> states;
    public void addState(States stateID,StateManager state){
        states.add(stateID.stateNum,state);
    }

    public void Setup(){
        int stateNum = States.values().length;
        states = new ArrayList<>(stateNum);
    }

    public void runState(States test){
        states.get(test.stateNum).Run(this);
    }
}