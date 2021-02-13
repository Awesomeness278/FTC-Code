package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    Move1(2),
    Move2(3),
    Rotate(4),
    DropWobble(5),
    Rotate2(6),
    Move3(7),
    Wait(8),
    Move4(9),
    Stop(10);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}