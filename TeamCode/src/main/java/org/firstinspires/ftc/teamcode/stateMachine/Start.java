package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Start extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)&&machine.opMode.opModeIsActive()){

        }
        Exit(machine);
    }

    @Override
    public boolean ExitCondition(StateMachine machine) {
        return true;
    }

    @Override
    public void Exit(StateMachine machine) {
        Autonomous opMode = machine.opMode;
        opMode.telemetry.addData("status","starting");
        int stateNumber = 1;
        machine.runState(States.Tensorflow);
    }
}