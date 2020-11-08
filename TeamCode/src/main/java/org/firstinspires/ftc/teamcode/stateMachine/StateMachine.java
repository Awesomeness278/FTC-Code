package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

public abstract class StateMachine {
    public abstract void Run(StateManager manager, Autonomous opMode);
    public abstract boolean ExitCondition(Autonomous opMode);
    public abstract void Exit(StateManager manager, Autonomous opMode);
}