package org.firstinspires.ftc.teamcode.stateMachine;

import java.util.ArrayList;

public class StateMachine {
    ArrayList<StateManager> states = new ArrayList<>();
    public void addState(StateManager state){
        states.add(state);
    }
}