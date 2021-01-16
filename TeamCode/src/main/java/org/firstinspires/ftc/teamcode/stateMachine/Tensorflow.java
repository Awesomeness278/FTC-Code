package org.firstinspires.ftc.teamcode.stateMachine;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class Tensorflow extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        List<Recognition> recognitions = opMode.tfod.getRecognitions();
        Recognition recognition;
        if(!recognitions.isEmpty()) {
            recognition = recognitions.get(0);
            opMode.recognition = recognition;
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.MoveToWobble);
    }
}