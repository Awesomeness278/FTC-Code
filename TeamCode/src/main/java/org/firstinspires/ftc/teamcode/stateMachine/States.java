package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    START(0),
    TENSORFLOW(1),
    MOVETOWOBBLE(2),
    StopMovingToWobble(3),
    DropWobble(4);
    public int stateNum;
    States(int stateNum){
        this.stateNum = stateNum;
    }
}
