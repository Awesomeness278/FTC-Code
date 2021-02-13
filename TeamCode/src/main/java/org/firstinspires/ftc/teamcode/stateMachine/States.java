package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    Move1(2),
    Move2(3),
    Rotate(4),
    DropWobble(5),
    Move3(6),
    Wait(7),
    Move4(8),
    Stop(9);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}