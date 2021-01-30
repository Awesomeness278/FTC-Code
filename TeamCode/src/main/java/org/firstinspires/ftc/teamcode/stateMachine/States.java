package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    Move1(2),
    Move2(3),
    DropWobble(4),
    Move3(5),
    Wait(6),
    Move4(7),
    Stop(8);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}