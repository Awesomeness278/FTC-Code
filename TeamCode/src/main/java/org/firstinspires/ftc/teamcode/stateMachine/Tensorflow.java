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
        machine.opMode.telemetry.addData("Orientation",machine.opMode.odometry.returnOrientation());
        machine.opMode.telemetry.addData("X",machine.opMode.odometry.returnXCoordinate()/machine.opMode.COUNTS_PER_INCH);
        machine.opMode.telemetry.addData("Y",machine.opMode.odometry.returnYCoordinate()/machine.opMode.COUNTS_PER_INCH);
        machine.opMode.telemetry.update();
        Autonomous opMode = machine.opMode;
        if(!opMode.tfod.getRecognitions().isEmpty()) {
            List<Recognition> recognitions = opMode.tfod.getRecognitions();
            Recognition recognition = recognitions.get(0);
            opMode.recognition = recognition;
            opMode.telemetry.addData("Label",recognition.getLabel());
        }
        while(machine.opMode.getRuntime()<delay){}
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Move1);
    }
}