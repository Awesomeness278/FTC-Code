package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Start(0),
    Tensorflow(1),
    MoveToWobble(2),
    StopMovingToWobble(3),
    DropWobble(4);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}
