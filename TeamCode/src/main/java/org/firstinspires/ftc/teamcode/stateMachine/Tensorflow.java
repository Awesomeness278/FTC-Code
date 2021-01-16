package org.firstinspires.ftc.teamcode.stateMachine;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class Tensorflow extends StateManager {
    public Tensorflow(){}
    @Override
    public void Run(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        if(!opMode.tfod.getRecognitions().isEmpty()) {
            List<Recognition> recognitions = opMode.tfod.getRecognitions();
            Recognition recognition = recognitions.get(0);
            opMode.recognition = recognition;
            opMode.telemetry.addData("Label",recognition.getLabel());
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