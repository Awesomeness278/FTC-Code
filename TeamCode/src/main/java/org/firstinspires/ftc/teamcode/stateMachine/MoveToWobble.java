package org.firstinspires.ftc.teamcode.stateMachine;

public class MoveToWobble extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        if(opMode.recognition.getLabel()=="Quad"){
            Move move = new Move(0.0f,0.0f,States.StopMovingToWobble.stateNum);
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        int stateNumber = 0;
        machine.runState(States.Start);
    }
}