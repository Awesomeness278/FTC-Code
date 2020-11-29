package org.firstinspires.ftc.teamcode.stateMachine;

public abstract class StateManager {
    public abstract void Run(StateMachine manager, Autonomous opMode);
    public abstract boolean ExitCondition(Autonomous opMode);
    public abstract void Exit(StateMachine manager, Autonomous opMode);
}