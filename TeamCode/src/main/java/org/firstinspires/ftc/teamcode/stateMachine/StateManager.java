package org.firstinspires.ftc.teamcode.stateMachine;
public abstract class StateManager {
    public abstract void Run(StateMachine manager);
    public abstract boolean ExitCondition(StateMachine manager);
    public abstract void Exit(StateMachine manager);
}