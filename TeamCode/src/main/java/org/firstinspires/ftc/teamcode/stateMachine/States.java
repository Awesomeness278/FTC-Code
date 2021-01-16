package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    Move1(2),
    Move2(3),
    Move3(4),
    Stop(5);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}
