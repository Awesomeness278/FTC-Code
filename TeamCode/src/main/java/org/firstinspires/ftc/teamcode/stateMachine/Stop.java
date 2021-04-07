package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.util.ReadWriteFile;

public class Stop extends StateManager {
    public Stop(){}
    @Override
    public void Run(StateMachine machine) {
        if(machine.opMode.velocityData.setWritable(true)) {
            ReadWriteFile.writeFile(machine.opMode.velocityData, "testing testing 1 2 3");
            machine.opMode.telemetry.addData("Data", "Saved");
            machine.opMode.telemetry.update();
            machine.opMode.stop();
        }
    }

    @Override
    public boolean ExitCondition(StateMachine machine) { return true; }

    @Override
    public void Exit(StateMachine machine) {
        machine.runState(States.Tensorflow);
    }
}