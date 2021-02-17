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
    Rotate3(8),
    Wait(9),
    Rotate4(10),
    Move4(11),
    Stop(12);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}