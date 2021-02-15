package org.firstinspires.ftc.teamcode.stateMachine;

import java.util.ArrayList;

public class StateMachine {
    ArrayList<StateManager> states = new ArrayList<>();
    Autonomous opMode;
    public StateMachine(Autonomous opMode){
        this.opMode = opMode;
        for(int i = 0; i < 10; i++){
            states.add(new State());
        }
    }
    public void addState(States stateID,StateManager state) {
        states.set(stateID.stateNum,state);System.out.print(states.size());
    }
    public void runState(States test){
        states.get(test.stateNum).Run(this);
    }
}