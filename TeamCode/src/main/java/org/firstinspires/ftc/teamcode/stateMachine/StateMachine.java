package org.firstinspires.ftc.teamcode.stateMachine;

import java.util.ArrayList;

public class StateMachine {
    Autonomous opMode;
    public StateMachine(Autonomous opMode){
        this.opMode = opMode;
    }
    ArrayList<StateManager> states = new ArrayList<>();
    public void addState(States stateID,StateManager state) {
        states.add(stateID.stateNum, state);
    }
    public void runState(States test){
        states.get(test.stateNum).Run(this);
    }
}