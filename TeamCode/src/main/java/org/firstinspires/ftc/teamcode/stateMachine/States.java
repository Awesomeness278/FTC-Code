package org.firstinspires.ftc.teamcode.stateMachine;

public enum States {
    Tensorflow(0),
    MoveToWobble(1),
    DodgeRings(2),
    MoveToWobblePosition(3),
    RotateToWobble(4),
    DropWobble(5),
    Rotate2(6),
    Move3(7),
    StraightenAfterWobble(8),
    Shoot(9),
    Rotate4(10),
    Move4(11),
    Stop(12),
    WobblePathMove(13),
    MoveToLine(14),
    Shoot2(15),
    Park(16),
    MoveToWaitPosition(17),
    Wait(18);
    public int stateNum;
    States(int stateNum){ this.stateNum = stateNum; }
}