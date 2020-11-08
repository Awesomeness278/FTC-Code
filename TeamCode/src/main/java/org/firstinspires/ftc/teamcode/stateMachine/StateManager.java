package org.firstinspires.ftc.teamcode.stateMachine;

import java.util.ArrayList;

public class StateManager {
    ArrayList<StateMachine> states = new ArrayList<>();
    public void addState(StateMachine state){
        states.add(state);
    }
}