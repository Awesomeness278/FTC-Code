package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    Move1(2),
    Move2(3),
    Move3(4),
    Wait(5),
    Move4(6),
    Stop(7);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}
