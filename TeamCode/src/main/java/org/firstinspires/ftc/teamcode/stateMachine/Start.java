package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Start extends StateManager {
    @Override
    public void Run(StateMachine manager, Autonomous opMode) {
        while(!ExitCondition(opMode)){

        }
        Exit(manager, opMode);
    }

    @Override
    public boolean ExitCondition(Autonomous opMode) {
        return true;
    }

    @Override
    public void Exit(StateMachine manager, Autonomous opMode) {
        opMode.telemetry.addData("status","starting");
        opMode.motors = new DcMotor[4];
        opMode.motors[0] = opMode.hardwareMap.get(DcMotor.class,"Left Back Motor");
        opMode.motors[1] = opMode.hardwareMap.get(DcMotor.class,"Left Front Motor");
        opMode.motors[2] = opMode.hardwareMap.get(DcMotor.class,"Right Back Motor");
        opMode.motors[3] = opMode.hardwareMap.get(DcMotor.class,"Right Front Motor");
        int stateNumber = 1;
        manager.states.get(stateNumber).Run(manager,opMode);
    }
}