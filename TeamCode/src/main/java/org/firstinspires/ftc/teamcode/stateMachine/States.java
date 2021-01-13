package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Start(0),
    Tensorflow(1),
    MoveToWobble(2),
    Move1(3),
    StopMovingToWobble(4),
    DropWobble(5);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}
