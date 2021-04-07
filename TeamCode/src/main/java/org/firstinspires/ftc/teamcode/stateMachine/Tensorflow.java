package org.firstinspires.ftc.teamcode.stateMachine;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class Tensorflow extends StateManager {
    public Tensorflow(int delay){
        this.delay = delay;
    }
    int delay;
    @Override
    public void Run(StateMachine machine) {
        machine.updater.addTelemetry();
        Autonomous opMode = machine.opMode;
        if(!opMode.tfod.getRecognitions().isEmpty()) {
            List<Recognition> recognitions = opMode.tfod.getRecognitions();
            Recognition recognition = recognitions.get(0);
            opMode.recognition = recognition;
        }
        while(machine.opMode.getRuntime()<delay){
            machine.updater.addTelemetry();
        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move3);
    }
}