package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Start extends StateManager {
    @Override
    public void Run(StateMachine machine) {
        while(!ExitCondition(machine)){

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
        opMode.LeftBackMotor = opMode.hardwareMap.get(DcMotor.class,"Left Back Motor");
        opMode.LeftFrontMotor = opMode.hardwareMap.get(DcMotor.class,"Left Front Motor");
        opMode.RightBackMotor = opMode.hardwareMap.get(DcMotor.class,"Right Back Motor");
        opMode.RightFrontMotor = opMode.hardwareMap.get(DcMotor.class,"Right Front Motor");
        int stateNumber = 1;
        machine.runState(States.Tensorflow);
    }
}