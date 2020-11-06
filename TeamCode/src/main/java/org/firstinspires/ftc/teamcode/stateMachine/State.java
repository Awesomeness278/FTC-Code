package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

public class State extends StateMachine{
    @Override
    public void Run(StateManager manager, Autonomous opMode) {
        while(!ExitCondition(opMode)){

        }
        Exit(manager, opMode);
    }

    @Override
    public boolean ExitCondition(Autonomous opMode) {
        return true;
    }

    @Override
    public void Exit(StateManager manager, Autonomous opMode) {
        int stateNumber = 0;
        manager.states.get(stateNumber).Run(manager,opMode);;
    }
}